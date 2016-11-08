'use strict';

var influunt;
(function (influunt) {
  var components;
  (function (components) {
    var Simulador = (function () {
      function Simulador(inicioSimulacao,fimSimulacao,velocidade,config, mqttUrl, mqttPort) {
        var game;

        velocidade = parseFloat(velocidade);
        config.detectoresHash = {};
        config.detectores.forEach(function(d) {config.detectoresHash["D" + d.tipo[0] + d.posicao] = d;});

        var quantidadeDeAneis = _.filter(config.aneis,function(a){return a.tiposGruposSemaforicos.length > 0;}).length;
        var duracaoSimulacao = (fimSimulacao.unix() - inicioSimulacao.unix()) / velocidade;
        var imgVI = document.getElementById('modo_vi');
        var imgAI = document.getElementById('modo_ai');

        var ALTURA_GRUPO = 25;
        var MARGEM_LATERAL = 956 ;
        var MARGEM_SUPERIOR = 70;
        var ALTURA_INTERVALOS = 0;
        var planos = [];
        var modos = [];
        var cursors;
        var intervalosGroup;
        var textoIntervalosGroup;
        var gruposSemaforicosGroup;
        var loadingGroup;
        var grupoControles;
        var eventosGroup;
        var totalGruposSemaforicos = 0;
        var gruposSemaforicos = [];
        var estadoGrupoSemaforico = {};
        var offsetDeAneis = {};
        var offsetGrupo = {};
        var tempo = 0;
        var descolamentoMaximo = 0;
        var relogio;
        var plano;
        var data;
        var aneis = {};
        var client;
        var repeater;
        var loadMoreRepeater;
        var started = false;
        var botoes = {};
        var specBotoes = null;
        var estagios = {};

        function decodeEstado(estado,ctx){

          switch(estado){
            case 'DESLIGADO':
              return '#3d3d3d';
            case 'VERDE':
              return '#00ad4d';
            case 'AMARELO':
              return '#fffb00';
            case 'VERMELHO':
              return '#ff1b00';
            case 'VERMELHO_INTERMITENTE':
              return ctx.createPattern(imgVI,'repeat');
            case 'AMARELO_INTERMITENTE':
              return ctx.createPattern(imgAI,'repeat');
            case 'VERMELHO_LIMPEZA':
              return '#a31100';
            }
        }

        function preload () {
          game.load.spritesheet('pedestre', '/images/simulador/sprite_pedestre.png', 86, 25);
          game.load.spritesheet('veicular', '/images/simulador/sprite_veicular.png', 86, 25);
          game.load.spritesheet('estado', '/images/simulador/modos.png', 10, 25);
          game.load.spritesheet('controles', '/images/simulador/controles.png', 30, 26);
          game.load.spritesheet('loading', '/images/simulador/loading.png', 200, 200);
          game.load.image('grid', '/images/simulador/grid.png');

          config.aneis.forEach(function(anel,index){
            anel.estagios.forEach(function(estagio){
              var id = 'A' + index + 'E' + estagio.posicao;
              game.load.image(id, estagio.imagem);
            });
          });

          game.load.start();
        }

        function botaoOver(botao){
          if(botao.animations.name === 'ON'){
            botao.play('HOVER');
          }
        }

        function botaoOut(botao){
          if(botao.animations.name === 'HOVER'){
            botao.play('ON');
          }
        }

        function botaoFastBackward(){
          for(var i =0; i < 10; i++){
            moveToRight();
          }
        }

        function botaoFastFoward(){
          for(var i =0; i < 10; i++){
            moveToLeft();
          }
        }

        function botaoLog(){
        }

        function botaoExport(){

        }

        function botaoBackward(){
          moveToRight();
        }

        function botaoFoward(){
          moveToLeft();
        }

        function estagioOut(estagio){
          if(estagios[estagio.name] && estagios[estagio.name].visible){
            estagios[estagio.name].visible = false;
          }
        }

        function estagioDown(estagio){
          if(estagios[estagio.name]){
            if(estagios[estagio.name].visible){
              estagios[estagio.name].visible = false;
            }else{
              estagios[estagio.name].x = estagio.x - 150;
              estagios[estagio.name].y = estagio.y;
              estagios[estagio.name].visible = true;
            }
          }
        }

        function botaoPause(){
          _.each(botoes, function(value,key){
            if(!(key === 'pause' ||  value.name.startsWith('D') && !config.detectoresHash[value.name])){
              value.inputEnabled = true;
              value.play('ON');
            }
          });

          botoes.pause.play('OFF');
          game.time.events.remove(repeater);
        }

        function botaoDetector(detector){
          if(config.detectoresHash[detector.name]){
            var d = config.detectoresHash[detector.name];
            var disparo = inicioSimulacao.clone();
            disparo.add(tempo,"seconds");
            var json = { anel: d.anel, disparo: (disparo.unix() + 1) * 1000, posicao:d.posicao, tipo:d.tipo }

            loadingGroup.visible = true;
            intervalosGroup.children.forEach(function(c){c.destroy();});
            eventosGroup.children.forEach(function(c){c.destroy();});

            var message = new Paho.MQTT.Message(JSON.stringify(json));
            message.destinationName = 'simulador/' + config.simulacaoId + '/detector';
            client.send(message);

            game.time.events.remove(loadMoreRepeater);
          }
        }

        function toggleModoManual() {
          var disparo = inicioSimulacao.clone();
          disparo.add(tempo, 'seconds')
          var json = {disparo: (disparo.unix() + 1) * 1000}

          loadingGroup.visible = true;
          intervalosGroup.children.forEach(function(c){c.destroy();});
          eventosGroup.children.forEach(function(c){c.destroy();});

          var message = new Paho.MQTT.Message(JSON.stringify(json));
          message.destinationName = 'simulador/' + config.simulacaoId + '/toggle_modo_manual';
          client.send(message);

          game.time.events.remove(loadMoreRepeater);
        }

        function trocarEstagioManual() {
          var disparo = inicioSimulacao.clone();
          disparo.add(tempo, 'seconds')
          var json = {disparo: (disparo.unix() + 1) * 1000}

          loadingGroup.visible = true;
          intervalosGroup.children.forEach(function(c){c.destroy();});
          eventosGroup.children.forEach(function(c){c.destroy();});

          var message = new Paho.MQTT.Message(JSON.stringify(json));
          message.destinationName = 'simulador/' + config.simulacaoId + '/trocar_estagio';
          client.send(message);

          game.time.events.remove(loadMoreRepeater);
        }

        function criaControles(){
          var inicio = 200, y = 10;
          specBotoes = [
              {nome: 'fastBackward', action: botaoFastBackward},
              {nome: 'backward', action: botaoBackward},
              {nome: 'play', action: botaoPlay},
              {nome: 'pause', action: botaoPause},
              {nome: 'foward', action: botaoFoward},
              {nome: 'fastFoward', action: botaoFastFoward,incremento: 39},
              {nome: 'DV1', action: botaoDetector},
              {nome: 'DV2', action: botaoDetector},
              {nome: 'DV3', action: botaoDetector},
              {nome: 'DV4', action: botaoDetector},
              {nome: 'DV5', action: botaoDetector},
              {nome: 'DV6', action: botaoDetector},
              {nome: 'DV7', action: botaoDetector},
              {nome: 'DV8', action: botaoDetector, incremento: 39},
              {nome: 'DP1', action: botaoDetector},
              {nome: 'DP2', action: botaoDetector},
              {nome: 'DP3', action: botaoDetector},
              {nome: 'DP4', action: botaoDetector, incremento: 39},

              {nome: 'operacaoManual', action: toggleModoManual},
              {nome: 'trocaEstagioManualWaiting', visivel: showEstagioManualWaiting, incremento: 0},
              {nome: 'trocaEstagioManual', action: trocarEstagioManual, visivel: showEstagioManual}
          ];

          // valores de incremento do arquivo de sprite para os botões.
          var INCREMENTO_SPRITE_BOTAO_DESATIVADO = 21;
          var INCREMENTO_SPRITE_BOTAO_ATIVADO = INCREMENTO_SPRITE_BOTAO_DESATIVADO * 2;
          var INCREMENTO_POSICAO_BOTAO = 31;
          specBotoes.forEach(function(botaoSpec, index) {

            var hoverIcon = index + (!!botaoSpec.action ? 0 : INCREMENTO_SPRITE_BOTAO_DESATIVADO);
            var onIcon = index + (!!botaoSpec.action ? INCREMENTO_SPRITE_BOTAO_ATIVADO : INCREMENTO_SPRITE_BOTAO_DESATIVADO);
            var offIcon = index + INCREMENTO_SPRITE_BOTAO_DESATIVADO;

            botoes[botaoSpec.nome] = game.add.sprite(inicio, y, 'controles');
            botoes[botaoSpec.nome].name = botaoSpec.nome;
            botoes[botaoSpec.nome].animations.add('HOVER', [hoverIcon]);
            botoes[botaoSpec.nome].animations.add('OFF', [offIcon]);
            botoes[botaoSpec.nome].animations.add('ON', [onIcon]);
            botoes[botaoSpec.nome].animations.play('OFF');
            botoes[botaoSpec.nome].inputEnabled = false;
            botoes[botaoSpec.nome].events.onInputOver.add(botaoOver,this);
            botoes[botaoSpec.nome].events.onInputOut.add(botaoOut,this);
            if (botaoSpec.action !== undefined) {
              botoes[botaoSpec.nome].events.onInputDown.add(botaoSpec.action, this);
            }

            if (botaoSpec.incremento === undefined) {
              inicio += INCREMENTO_POSICAO_BOTAO;
            } else {
              inicio += botaoSpec.incremento;
            }

            grupoControles.add(botoes[botaoSpec.nome]);
          });
        }

        function botaoPlay(){
          botoes.pause.play('ON');
          botoes.play.inputEnabled = true;

          _.each(botoes,function(value,key){
            if(!(key === 'pause' ||  value.name.startsWith('D') && !config.detectoresHash[value.name])){
              value.inputEnabled = false;
              value.play('OFF');
            }
          });
          repeater = game.time.events.repeat(1000, duracaoSimulacao - descolamentoMaximo, moveToLeft, this);
        }

        function atualizaEstadosGruposSemaforicos(){
          for(var i = 0; i < totalGruposSemaforicos; i++){
            if(estadoGrupoSemaforico[tempo] && estadoGrupoSemaforico[tempo][i]){
              gruposSemaforicos[i].sprite.play(estadoGrupoSemaforico[tempo][i]);
            }
          }
        }

        function getPlanoAtual(tempo){
          for(var i =  planos.length - 1; i >= 0 ; i--){
            if(tempo >= planos[i][0]){
              return planos[i];
            }
          }
        }

        function getModo(modo) {
          switch(modo){
            case 'TEMPO_FIXO_ISOLADO': return 'TFI';
            case 'TEMPO_FIXO_COORDENADO': return 'TFC';
            case 'ATUADO': return 'ATU';
            case 'APAGADO': return 'APA';
            case 'INTERMITENTE': return 'INT';
            case 'MANUAL': return 'MAN';
          }
        }

        function desenhaPlanoAtual(planoSpec) {
          plano.setText('Plano ' + planoSpec[1]);
          planoSpec[2].forEach(function(modo,index){
            modos[index].setText(getModo(modo));
          })
          var dataText = inicioSimulacao.clone().add(tempo + 1,'s').format("DD/MM/YYYY - HH:mm:ss");
          data.setText(dataText);
        }

        function moveToLeft() {
          tempo += (velocidade);
          relogio.setText((tempo + 1) + 's');
          desenhaPlanoAtual(getPlanoAtual(tempo));
          game.camera.x+=(10 * velocidade);
          atualizaEstadosGruposSemaforicos();
          atualizaBotoesVisiveis();
        }

        function moveToRight() {
          tempo = Math.max(0,tempo - velocidade);
          relogio.setText((tempo + 1) + 's');
          desenhaPlanoAtual(getPlanoAtual(tempo));
          game.camera.x -= (velocidade * 10);
          atualizaEstadosGruposSemaforicos();
          atualizaBotoesVisiveis();
        }

        function loadMore() {
            var message = new Paho.MQTT.Message('proxima');
            message.destinationName = 'simulador/' + config.simulacaoId + '/proxima_pagina';
            client.send(message);
        }

        function desenhaAgendamento(x1, x2, y1, y2, color, hLabel) {
          var w  = x2 - x1;
          var h  = y2 - y1;

          hLabel = hLabel || h/2;

          var bmd = game.add.bitmapData(w,h);

          bmd.ctx.lineWidth = '2';
          bmd.ctx.setLineDash([5, 1]);
          bmd.ctx.strokeStyle = color;

          bmd.ctx.beginPath();
           bmd.ctx.moveTo(w-2,0);
           bmd.ctx.lineTo(w-2,h);
           bmd.ctx.stroke();
          bmd.ctx.closePath();

          bmd.ctx.beginPath();
           bmd.ctx.moveTo(10, hLabel);
           bmd.ctx.lineTo(w, hLabel);
           bmd.ctx.stroke();
          bmd.ctx.closePath();

          bmd.ctx.fillStyle = color;
          bmd.ctx.font = '12px Open Sans';
          bmd.ctx.fillText((w/10) - 1 + 's', w/2, hLabel - 3);


          bmd.render();
          eventosGroup.add(game.add.sprite((MARGEM_LATERAL + x1) - 10, y1 + ALTURA_GRUPO, bmd));
        }

        function desenhaTrocaEstagioManual(x, color, label) {
          var h = getAlturaDiagrama();

          var hLabel = h/3;
          var bmd = game.add.bitmapData(20,h);

          bmd.ctx.closePath();
          bmd.ctx.beginPath();
            bmd.ctx.lineWidth = '2';
            bmd.ctx.setLineDash([5, 5]);
            bmd.ctx.strokeStyle = color;
          bmd.ctx.closePath();

          bmd.ctx.beginPath();
            bmd.ctx.moveTo(10,0);
            bmd.ctx.lineTo(10,h);
            bmd.ctx.stroke();
          bmd.ctx.closePath();

          bmd.ctx.fillStyle = color;
          bmd.ctx.arc(10, hLabel - 4, 8, 0, Math.PI*2, true);
          bmd.ctx.fill();

          bmd.ctx.fillStyle = '#fff';
          bmd.ctx.font = '8px Open Sans';

          bmd.ctx.fillText(label, 5,hLabel-1);

          bmd.render();

          eventosGroup.add(game.add.sprite((MARGEM_LATERAL + x) - 10, MARGEM_SUPERIOR + ALTURA_GRUPO, bmd));
        }

        function desenhaDetector(anel,x,color,tipo,label){
          var y1 = offsetDeAneis[anel + 1] + ALTURA_GRUPO;
          var y2 = y1 + (config.aneis[anel].tiposGruposSemaforicos.length * ALTURA_GRUPO) +
                                            config.aneis[anel].tiposGruposSemaforicos.length - 1;
          x -= 15;

          var w  = ALTURA_GRUPO + 2;
          var h  = y2 - y1;
          var bmd = game.add.bitmapData(w,h);


          bmd.ctx.lineWidth = '2';
          bmd.ctx.setLineDash([5, 5]);
          bmd.ctx.strokeStyle = color;

          bmd.ctx.beginPath();
            bmd.ctx.moveTo(10,0);
            bmd.ctx.lineTo(10,h);
            bmd.ctx.stroke();
          bmd.ctx.closePath();


          bmd.ctx.fillStyle = color;
          bmd.ctx.arc(10,h/2 - 4,8,0,Math.PI*2,true);
          bmd.ctx.fill();

          bmd.ctx.fillStyle = '#fff';
          bmd.ctx.font = '8px Open Sans';
          if(tipo === 'ACIONAMENTO_DETECTOR_VEICULAR'){
            label = 'V' + label;
          }else{
            label = 'P' + label;
          }
          bmd.ctx.fillText(label, 5,h/2-1);

          bmd.render();
          eventosGroup.add(game.add.sprite(x, y1, bmd));
        }

        function desenhaEstagio(y,estagio,anel){
          var h = Object.keys(estagio.grupos).length * ALTURA_GRUPO + ALTURA_GRUPO + (Object.keys(estagio.grupos).length - 1);
          var w = estagio.w / 100;
          var tempoInicio = (estagio.x - estagio.w) / 1000;

          var x = ((estagio.x - estagio.w) / 100) + MARGEM_LATERAL;

          var bmd = game.add.bitmapData(w,h);


          _.each(estagio.grupos,function(grupo,grupoKey){

            var yi =  offsetGrupo[grupoKey] * ALTURA_GRUPO + ALTURA_GRUPO;
            if(offsetGrupo[grupoKey] > 0){
              yi += offsetGrupo[grupoKey];
            }

            grupo.forEach(function(intervalo){
              var limite = tempoInicio + (intervalo[0] / 1000) + (intervalo[1] / 1000);
              for(var i = tempoInicio + (intervalo[0] / 1000); i < limite; i++){
                estadoGrupoSemaforico[i] = estadoGrupoSemaforico[i] || {};
                estadoGrupoSemaforico[i][grupoKey - 1] = intervalo[2];
              }

              var xi = intervalo[0] / 100;
              var wi = intervalo[1] / 100;

              bmd.ctx.fillStyle = decodeEstado(intervalo[2],bmd.ctx,wi);
              bmd.ctx.fillRect(xi, yi, wi, ALTURA_GRUPO);
            });
          });

          estagio.eventos.forEach(function(evento){
            if (evento[1] === 'ACIONAMENTO_DETECTOR_VEICULAR' || evento[1] === 'ACIONAMENTO_DETECTOR_PEDESTRE') {
              desenhaDetector(parseInt(anel) - 1,x + evento[0] / 100,'#082536',evento[1],evento[2]);
            } else if(evento[1] === 'TROCA_DE_PLANO_NO_ANEL') {
              var x1 = (evento[4] - inicioSimulacao.unix() * 1000) / 100;
              var x2 = ((evento[5] - inicioSimulacao.unix() * 1000) / 100) + 10;
              var y1 = offsetDeAneis[evento[3]];
              var y2 = y1 + (config.aneis[anel - 1].tiposGruposSemaforicos.length * ALTURA_GRUPO) +
                                            config.aneis[anel - 1].tiposGruposSemaforicos.length - 1;

              desenhaAgendamento(x1,x2,y1,y2,'#2603339');
            } else if(evento[1] === 'ACIONAMENTO_PLANO_MANUAL') {
              var x1 = (evento[4] - inicioSimulacao.unix() * 1000) / 100;
              var x2 = ((evento[5] - inicioSimulacao.unix() * 1000) / 100) + 10;

              var y1 = MARGEM_SUPERIOR;
              var y2 = y1 + getAlturaDiagrama();
              var color = '#2603339';
              desenhaAgendamento(x1, x2, y1, y2, color, (y2 - y1) / 3);
            } else if (evento[1] === 'TROCA_DE_ESTAGIO_MANUAL') {
              var x1 = (evento[4] - inicioSimulacao.unix() * 1000) / 100;
              var x2 = ((evento[5] - inicioSimulacao.unix() * 1000) / 100) + 10;

              var y1 = MARGEM_SUPERIOR;
              var y2 = y1 + getAlturaDiagrama();
              var color = '#2603339';
              desenhaTrocaEstagioManual(x1, color, 'E'+evento[2]);
              desenhaAgendamento(x1, x2, y1, y2, color, (y2 - y1) / 3);
            }
          });

          bmd.ctx.fillStyle = '#7788AA';
          bmd.ctx.strokeStyle = '#426383';
          bmd.ctx.fillRect(0,0,w,ALTURA_GRUPO);
          bmd.ctx.lineWidth = '2';
          bmd.ctx.strokeRect(0,0,w,h);
          bmd.ctx.textAlign = 'left';
          bmd.ctx.fillStyle = '#fff';

          if(estagio.estagio !== null){
            bmd.ctx.font = '14px Open Sans';
            bmd.ctx.fillText('E' + estagio.estagio, 5, 18);
          }

          bmd.ctx.font = '10px Open Sans';

          if(w > 50){
            bmd.ctx.textAlign = 'right';
            bmd.ctx.fillText((w /10.0) + 's', w - 5, 16);
          }

          bmd.ctx.lineWidth = '1';
          bmd.ctx.strokeStyle = '#ccc';

          for(var i = 10; i < w; i+=10){
              bmd.ctx.beginPath();
              bmd.ctx.moveTo(i,ALTURA_GRUPO);
              bmd.ctx.lineTo(i,h);
              bmd.ctx.stroke();
              bmd.ctx.closePath();
          }

          bmd.render();
          var sprite = game.add.sprite(x, y, bmd);
          sprite.name = 'A' + (anel - 1) + 'E' + estagio.estagio;
          sprite.events.onInputDown.add(estagioDown,this);
          sprite.events.onInputOut.add(estagioOut,this);
          sprite.inputEnabled = true;
          intervalosGroup.add(sprite);
        }

        function processaEstagios(aneis){
          Object.keys(aneis).forEach(function(anel){
            var topOffset = offsetDeAneis[parseInt(anel)];
            aneis[anel].forEach(function(estagio){
              desenhaEstagio(topOffset,estagio,anel);
            });
          });
        }

        function criaGrupoSemaforico(grupo, y,veicular){
          var grupoSemaforico = {};
          grupoSemaforico.numero = grupo;
          grupoSemaforico.state = 'apagado';

          if(veicular){
            grupoSemaforico.sprite = game.add.sprite(30 , y, 'veicular');

            //Estados
            grupoSemaforico.DESLIGADO = grupoSemaforico.sprite.animations.add('DESLIGADO', [0]);
            grupoSemaforico.DESLIGADO.enableUpdate = true;

            grupoSemaforico.VERMELHO = grupoSemaforico.sprite.animations.add('VERMELHO', [3]);
            grupoSemaforico.VERMELHO.enableUpdate = true;

            grupoSemaforico.VERMELHO_LIMPEZA = grupoSemaforico.sprite.animations.add('VERMELHO_LIMPEZA', [3]);
            grupoSemaforico.VERMELHO_LIMPEZA.enableUpdate = true;

            grupoSemaforico.AMARELO = grupoSemaforico.sprite.animations.add('AMARELO', [2]);
            grupoSemaforico.AMARELO.enableUpdate = true;

            grupoSemaforico.VERDE = grupoSemaforico.sprite.animations.add('VERDE', [1]);
            grupoSemaforico.VERDE.enableUpdate = true;

            grupoSemaforico.AMARELO_INTERMITENTE = grupoSemaforico.sprite.animations.add('AMARELO_INTERMITENTE', [0,2],2,true);
            grupoSemaforico.AMARELO_INTERMITENTE.enableUpdate = true;

          }else{

            grupoSemaforico.sprite = game.add.sprite(30 , y, 'pedestre');

            //Estados
            grupoSemaforico.DESLIGADO = grupoSemaforico.sprite.animations.add('DESLIGADO', [0]);
            grupoSemaforico.DESLIGADO.enableUpdate = true;

            grupoSemaforico.VERDE = grupoSemaforico.sprite.animations.add('VERDE', [1]);
            grupoSemaforico.VERDE.enableUpdate = true;

            grupoSemaforico.VERMELHO = grupoSemaforico.sprite.animations.add('VERMELHO', [2]);
            grupoSemaforico.VERMELHO.enableUpdate = true;

            grupoSemaforico.VERMELHO_LIMPEZA = grupoSemaforico.sprite.animations.add('VERMELHO_LIMPEZA', [2]);
            grupoSemaforico.VERMELHO_LIMPEZA.enableUpdate = true;

            grupoSemaforico.VERMELHO_INTERMITENTE = grupoSemaforico.sprite.animations.add('VERMELHO_INTERMITENTE', [0,2],2,true);
            grupoSemaforico.VERMELHO_INTERMITENTE.enableUpdate = true;

          }
          grupoSemaforico.sprite.fixedToCamera = true;
          gruposSemaforicosGroup.add(grupoSemaforico.sprite);
          gruposSemaforicos[grupo] = grupoSemaforico;

          var style = { font: '12px Open Sans', fill: '#000000' };
          var text = game.add.text(36,y + 27, 'G' + (grupo + 1), style);

          text.fixedToCamera = true;
          text.anchor.set(0,1);
          gruposSemaforicosGroup.add(text);
        }

        function inicializaGrupos(tipos,index,offset){
          var posicaoNoAnel = 0;
          tipos.forEach(function(tipo){

            var y = ((index + 1) * ALTURA_GRUPO) + (1 * (index + 1));

            criaGrupoSemaforico(index, y + MARGEM_SUPERIOR + offset, tipo === 'VEICULAR');
            index++;
            offsetGrupo[index] = posicaoNoAnel++;
          });

          return index;
        }

        function desenhaAnel(numero,y1,y2,cor,ultimo){
          if(ultimo){
            y2 += 2;
          }

          var h = y2-y1;
          var bmd = game.add.bitmapData(1000,h);

          bmd.ctx.fillStyle = cor;

          bmd.ctx.fillRect(0,0,30,h);
          bmd.ctx.fillRect(0,0,116,ALTURA_GRUPO);
          bmd.ctx.fillRect(116,0,1000,2);
          bmd.ctx.fillRect(966,0,34,h);
          if(ultimo){
            bmd.ctx.fillRect(0,h - 2,1000,30);
          }

          bmd.ctx.textAlign = 'center';
          bmd.ctx.fillStyle = '#fff';
          bmd.ctx.font = '12px Open Sans';
          bmd.ctx.fillText(numero, 15, h/2 + 6);

          bmd.render();

          var spriteY = MARGEM_SUPERIOR + y1 + ALTURA_GRUPO + 1;
          var sprite = game.add.sprite(0, spriteY, bmd);
          offsetDeAneis[numero] = spriteY;
          ALTURA_INTERVALOS = spriteY + h;
          sprite.fixedToCamera = true;

          var style = { font: '12px Open Sans', fill: '#fff' };

          var modo = game.add.text(982,spriteY + h/2 , '?', style);
          modo.fixedToCamera = true;
          modo.anchor.setTo(0.5, 0.5);
          modos.push(modo);
        }

        function criaAnel(anel,index,indexAnel){
          var offset = (anel.numero - 1) * ALTURA_GRUPO;
          var indexAtual = inicializaGrupos(anel.tiposGruposSemaforicos,index,offset);
          aneis[anel.numero] = {};
          aneis[anel.numero].inicio_grupo = index;
          aneis[anel.numero].fim_grupo = indexAtual - 1;

          var corAnel = '#777777';
          if(parseInt(anel.numero) % 2 === 0){
            corAnel = '#999999';
          }
          var y1;
          var y2;

          if(parseInt(anel.numero) === 1){
            y1 = (index * ALTURA_GRUPO) - ALTURA_GRUPO;
            y2 = (indexAtual * ALTURA_GRUPO) + (indexAtual - 1);
          }else{
            y1 = (index * ALTURA_GRUPO) + (index - 1)  + (((anel.numero - 1) * ALTURA_GRUPO) - ALTURA_GRUPO);
            y2 = (indexAtual * ALTURA_GRUPO) + (indexAtual - 1) + (anel.numero - 1) * ALTURA_GRUPO;
            offset = anel.numero * ALTURA_GRUPO;
          }

          desenhaAnel(anel.numero,y1,y2,corAnel,indexAnel + 1 === quantidadeDeAneis );
          index = indexAtual;

          return index;
        }

        function criaAneis(){
          var i = 0;

          config.aneis.forEach(function(anel,indexAnel){
            if(anel.tiposGruposSemaforicos.length > 0){
              i = criaAnel(anel,i,indexAnel);
            }
          });
          totalGruposSemaforicos = i;
        }

        function desenhaPlano(x,color,label){
          var h = ALTURA_INTERVALOS - 25;
          var bmd = game.add.bitmapData(20,h);

          bmd.ctx.closePath();
          bmd.ctx.beginPath();
          bmd.ctx.lineWidth = '2';
          bmd.ctx.setLineDash([5, 1]);
          bmd.ctx.strokeStyle = color;
          bmd.ctx.moveTo(10,0);
          bmd.ctx.lineTo(10,h);
          bmd.ctx.stroke();
          bmd.ctx.closePath();
          bmd.ctx.fillStyle = color;
          bmd.ctx.fillRect(0,0,20,20);

          bmd.ctx.fillStyle = '#fff';

          if(label.length > 1){
            bmd.ctx.font = '8px Open Sans';
            bmd.ctx.fillText(label, 5, 14);
          }else{
            bmd.ctx.font = '12px Open Sans';
            bmd.ctx.fillText(label, 7, 15);
          }


          bmd.render();
          eventosGroup.add(game.add.sprite((MARGEM_LATERAL + x) - 10, MARGEM_SUPERIOR - 25, bmd));
        }

        function getAlturaDiagrama() {
          var qtdeAneis = _.filter(config.aneis, function(a) {return a.tiposGruposSemaforicos.length > 0}).length;
          var qtdeGrupos = _.chain(config.aneis).map('tiposGruposSemaforicos').flatten().value().length;

          return (qtdeAneis + qtdeGrupos) * ALTURA_GRUPO;
        }

        function processaPlanos(trocas){
          trocas.forEach(function(troca){
            var x = (troca[0] - (inicioSimulacao.unix() * 1000)) / 100;
            planos.push([x / 10,troca[2],troca[3]]);
            desenhaPlano(x,'#260339',troca[2]);
          });
        }

        function criaLoading(){
          var background  = game.add.graphics( 0, 0 );
          background.beginFill(0xCCCCCC, 1);
          background.bounds = new PIXI.Rectangle(0, 0, 1000, 700);
          background.drawRect(0, 0, 1000, 700);
          var loading = game.add.sprite(500, 350, 'loading');
          loading.animations.add('loop', [0,1,2,3,2,1,0],3,true);
          loading.play('loop');
          loading.anchor.set(0.5,0.5);
          loadingGroup.add(background);
          loadingGroup.add(loading);
          loadingGroup.fixedToCamera = true;
        }

        function atualizaBotoesVisiveis() {
          _.each(specBotoes, function(spec) {
            if (typeof spec.visivel === 'function') {
              var botao = botoes[spec.nome];
              botao.visible = spec.visivel.apply(this);
            }
          });
        }

        function showEstagioManual() {
          console.log('showEstagioManual: ', getPlanoAtual(tempo)[2].reduce(function(a, b) { return a && b === 'MANUAL' }, true));
          return getPlanoAtual(tempo)[2].reduce(function(a, b) { return a && b === 'MANUAL' }, true);
        }

        function showEstagioManualWaiting() {
          console.log('showEstagioManualWaiting: ', !getPlanoAtual(tempo)[2].reduce(function(a, b) { return a && b === 'MANUAL' }, true));
          return !getPlanoAtual(tempo)[2].reduce(function(a, b) { return a && b === 'MANUAL' }, true);
        }

        function create() {
          game.stage.backgroundColor = '#cccccc';
          cursors = game.input.keyboard.createCursorKeys();
          game.world.setBounds(0, 0, 1000 + (duracaoSimulacao * 10 * Math.max(1,velocidade)), 800);

          intervalosGroup = game.add.group();
          eventosGroup = game.add.group();
          textoIntervalosGroup = game.add.group();
          gruposSemaforicosGroup = game.add.group();
          grupoControles = game.add.group();
          grupoControles.fixedToCamera = true;

          config.aneis.forEach(function(anel,index){
            anel.estagios.forEach(function(estagio){
              var id = 'A' + index + 'E' + estagio.posicao;
              estagios[id] = game.add.sprite(0, 0, id);
              estagios[id].visible = false;
            });
          });

          criaControles();


          var onConnect = function () {
            // Once a connection has been made, make a subscription and send a message.
            client.subscribe('simulador/' + config.simulacaoId + '/estado');
          };

          // Create a client instance
          client = new Paho.MQTT.Client(mqttUrl, mqttPort, 'simulador_web_' + config.simulacaoId);

          client.onMessageArrived = function(message) {
            if(message.destinationName.endsWith('/estado')){
              var json = JSON.parse(message.payloadString);
json = {"aneis": {"1": [{"w": 50000,"x": 50000,"estagio": 1,"grupos": {"1": [["0",10000,"VERMELHO"],["10000",40000,"VERDE"]],"2": [["0",4000,"VERDE"],["4000",3000,"AMARELO"],["7000",3000,"VERMELHO_LIMPEZA"],["10000",40000,"VERMELHO"]],"3": [["0",10000,"VERMELHO"],["10000",40000,"VERMELHO"]],"4": [["0",7000,"VERMELHO_INTERMITENTE"],["7000",3000,"VERMELHO_LIMPEZA"],["10000",40000,"VERMELHO"]],"5": [["0",10000,"VERMELHO"],["10000",40000,"VERDE"]]},"eventos": []},{"w": 50000,"x": 100000,"estagio": 3,"grupos": {"1": [["0",4000,"VERDE"],["4000",3000,"AMARELO"],["7000",3000,"VERMELHO_LIMPEZA"],["10000",40000,"VERMELHO"]],"2": [["0",10000,"VERMELHO"],["10000",40000,"VERDE"]],"3": [["0",10000,"VERMELHO"],["10000",40000,"VERMELHO"]],"4": [["0",10000,"VERMELHO"],["10000",40000,"VERDE"]],"5": [["0",7000,"VERMELHO_INTERMITENTE"],["7000",3000,"VERMELHO_LIMPEZA"],["10000",40000,"VERMELHO"]]},"eventos": []},{"w": 50000,"x": 150000,"estagio": 1,"grupos": {"1": [["0",10000,"VERMELHO"],["10000",40000,"VERDE"]],"2": [["0",4000,"VERDE"],["4000",3000,"AMARELO"],["7000",3000,"VERMELHO_LIMPEZA"],["10000",40000,"VERMELHO"]],"3": [["0",10000,"VERMELHO"],["10000",40000,"VERMELHO"]],"4": [["0",7000,"VERMELHO_INTERMITENTE"],["7000",3000,"VERMELHO_LIMPEZA"],["10000",40000,"VERMELHO"]],"5": [["0",10000,"VERMELHO"],["10000",40000,"VERDE"]]},"eventos": [[0,"TROCA_DE_PLANO_NO_ANEL","2","1",1478570415000,1478570500000],[0,"ACIONAMENTO_PLANO_MANUAL","null","1",1478570435000,1478570455000],[0,"TROCA_DE_ESTAGIO_MANUAL","1","1",1478570460000,1478570465000]]},{"w": 10000,"x": 160000,"estagio": 2,"grupos": {"1": [["0",3000,"AMARELO"],["3000",3000,"VERMELHO_LIMPEZA"],["6000",4000,"VERMELHO"]],"2": [["0",6000,"VERMELHO"],["6000",4000,"VERMELHO"]],"3": [["0",6000,"VERMELHO"],["6000",4000,"VERDE"]],"4": [["0",6000,"VERMELHO"],["6000",4000,"VERDE"]],"5": [["0",6000,"VERDE"],["6000",4000,"VERDE"]]},"eventos": []},{"w": 70000,"x": 230000,"estagio": 3,"grupos": {"1": [["0",10000,"VERMELHO"],["10000",60000,"VERMELHO"]],"2": [["0",10000,"VERMELHO"],["10000",60000,"VERDE"]],"3": [["0",7000,"VERMELHO_INTERMITENTE"],["7000",3000,"VERMELHO_LIMPEZA"],["10000",60000,"VERMELHO"]],"4": [["0",10000,"VERDE"],["10000",60000,"VERDE"]],"5": [["0",7000,"VERMELHO_INTERMITENTE"],["7000",3000,"VERMELHO_LIMPEZA"],["10000",60000,"VERMELHO"]]},"eventos": []},{"w": 50000,"x": 280000,"estagio": 1,"grupos": {"1": [["0",10000,"VERMELHO"],["10000",40000,"VERDE"]],"2": [["0",4000,"VERDE"],["4000",3000,"AMARELO"],["7000",3000,"VERMELHO_LIMPEZA"],["10000",40000,"VERMELHO"]],"3": [["0",10000,"VERMELHO"],["10000",40000,"VERMELHO"]],"4": [["0",7000,"VERMELHO_INTERMITENTE"],["7000",3000,"VERMELHO_LIMPEZA"],["10000",40000,"VERMELHO"]],"5": [["0",10000,"VERMELHO"],["10000",40000,"VERDE"]]},"eventos": []},{"w": 10000,"x": 290000,"estagio": 2,"grupos": {"1": [["0",3000,"AMARELO"],["3000",3000,"VERMELHO_LIMPEZA"],["6000",4000,"VERMELHO"]],"2": [["0",6000,"VERMELHO"],["6000",4000,"VERMELHO"]],"3": [["0",6000,"VERMELHO"],["6000",4000,"VERDE"]],"4": [["0",6000,"VERMELHO"],["6000",4000,"VERDE"]],"5": [["0",6000,"VERDE"],["6000",4000,"VERDE"]]},"eventos": []}],"2": [{"w": 60000,"x": 60000,"estagio": 2,"grupos": {"6": [["0",6000,"VERMELHO"],["6000",54000,"VERDE"]],"7": [["0",3000,"AMARELO"],["3000",3000,"VERMELHO_LIMPEZA"],["6000",54000,"VERMELHO"]],"8": [["0",6000,"VERMELHO"],["6000",54000,"VERMELHO"]],"9": [["0",3000,"VERMELHO_INTERMITENTE"],["3000",3000,"VERMELHO_LIMPEZA"],["6000",54000,"VERMELHO"]],"10": [["0",6000,"VERMELHO"],["6000",54000,"VERDE"]]},"eventos": []},{"w": 60000,"x": 120000,"estagio": 3,"grupos": {"6": [["0",3000,"AMARELO"],["3000",3000,"VERMELHO_LIMPEZA"],["6000",54000,"VERMELHO"]],"7": [["0",6000,"VERMELHO"],["6000",54000,"VERDE"]],"8": [["0",6000,"VERMELHO"],["6000",54000,"VERMELHO"]],"9": [["0",6000,"VERMELHO"],["6000",54000,"VERDE"]],"10": [["0",3000,"VERMELHO_INTERMITENTE"],["3000",3000,"VERMELHO_LIMPEZA"],["6000",54000,"VERMELHO"]]},"eventos": []},{"w": 60000,"x": 180000,"estagio": 2,"grupos": {"6": [["0",6000,"VERMELHO"],["6000",54000,"VERDE"]],"7": [["0",3000,"AMARELO"],["3000",3000,"VERMELHO_LIMPEZA"],["6000",54000,"VERMELHO"]],"8": [["0",6000,"VERMELHO"],["6000",54000,"VERMELHO"]],"9": [["0",3000,"VERMELHO_INTERMITENTE"],["3000",3000,"VERMELHO_LIMPEZA"],["6000",54000,"VERMELHO"]],"10": [["0",6000,"VERMELHO"],["6000",54000,"VERDE"]]},"eventos": [[0,"TROCA_DE_PLANO_NO_ANEL","2","2",1478570415000,1478570520000]]},{"w": 60000,"x": 240000,"estagio": 3,"grupos": {"6": [["0",3000,"AMARELO"],["3000",3000,"VERMELHO_LIMPEZA"],["6000",54000,"VERMELHO"]],"7": [["0",6000,"VERMELHO"],["6000",54000,"VERDE"]],"8": [["0",6000,"VERMELHO"],["6000",54000,"VERMELHO"]],"9": [["0",6000,"VERMELHO"],["6000",54000,"VERDE"]],"10": [["0",3000,"VERMELHO_INTERMITENTE"],["3000",3000,"VERMELHO_LIMPEZA"],["6000",54000,"VERMELHO"]]},"eventos": []}]},"trocas": [[1478570400000,"null",1,["TEMPO_FIXO_ISOLADO","TEMPO_FIXO_ISOLADO"]],[1478570415000,"null - não sei o que é isso",2,["TEMPO_FIXO_COORDENADO","TEMPO_FIXO_COORDENADO"]],[1478570435000,"null","M",["MANUAL","MANUAL"]]]};
              processaEstagios(json.aneis);
              processaPlanos(json.trocas);
              loadingGroup.visible = false;
              if(!loadMoreRepeater){
                loadMoreRepeater = game.time.events.repeat(8000 / velocidade, Math.ceil(duracaoSimulacao / 120) + 1, loadMore, this);
              }
            }
          };

          function onConnectionLost(responseObject) {
            if (responseObject.errorCode !== 0) {
              console.log('onConnectionLost:'+responseObject.errorMessage);
            }
          }

          // connect the client
          client.connect({onSuccess:onConnect});

          criaAneis();

          var style = { font: '25px Open Sans', fill: '#666' };
          relogio = game.add.text(1000,5, '0', style);

          relogio.fixedToCamera = true;
          relogio.anchor.set(1,0);

          style = { font: '15px Open Sans', fill: '#333' };
          plano = game.add.text(10,35, 'Plano Atual:?', style);
          plano.fixedToCamera = true;
          plano.anchor.set(0,1);

          style = { font: '12px Open Sans', fill: '#333' };
          data = game.add.text(10,47, '?', style);
          data.fixedToCamera = true;
          data.anchor.set(0,1);


          loadMoreRepeater = game.time.events.repeat(8000 / velocidade, Math.ceil(duracaoSimulacao / 120) + 1, loadMore, this);
          loadingGroup = game.add.group();
          criaLoading();
        }

        function render() {
          if(!started && intervalosGroup.children.length > 0){
            repeater = game.time.events.repeat(1000, duracaoSimulacao, moveToLeft, this);
            started = true;
            botoes.pause.play('ON');
            botoes.pause.inputEnabled = true;
          }
        }

        game = new Phaser.Game(1000, 700, Phaser.AUTO, 'canvas', { preload: preload, create: create, render: render });
        return game;
      }
      return Simulador;
    }());
    components.Simulador = Simulador;
  })(components = influunt.components || (influunt.components = {}));
})(influunt || (influunt = {}));

