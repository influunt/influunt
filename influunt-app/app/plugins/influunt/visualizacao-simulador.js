'use strict';

var influunt;
(function (influunt) {
  var components;
  (function (components) {
    var Simulador = (function () {
      function Simulador(inicioSimulacao,fimSimulacao,velocidade,config) {
        var game;
        
        velocidade = parseFloat(velocidade);

        var quantidadeDeAneis = _.filter(config.aneis,function(a){return a.tiposGruposSemaforicos.length > 0;}).length;
        var duracaoSimulacao = (fimSimulacao.unix() - inicioSimulacao.unix()) / velocidade;
        var imgVI = document.getElementById("modo_vi");
        var imgAI = document.getElementById("modo_ai");        

        var ALTURA_GRUPO = 25;
        var MARGEM_LATERAL = 966 ;
        var MARGEM_SUPERIOR = 70;
        var ALTURA_INTERVALOS = 0;
        var cursors;
        var intervalosGroup;
        var textoIntervalosGroup;
        var gruposSemaforicosGroup;
        var grupoControles;
        var eventosGroup;
        var totalGruposSemaforicos = 0;
        var gruposSemaforicos = [];
        var estadoGrupoSemaforico = {};
        var offsetDeAneis = {};
        var offsetGrupo = {};
        var tempo = 0;
        var relogio;
        var plano;
        var aneis = {};
        var dataHora = {};
        var client;
        var repeater;
        var started = false;
        var botaoPlay, botaoPause, botaoBackward, 
            botaoFoward, botaoFastBackward, botaoFastFoward,
            botaoLog, botaoExport;


        function decodeEstado(estado,ctx){

          switch(estado){
            case 'DESLIGADO':
              return "#3d3d3d";
            case 'VERDE':
              return "#00ad4d";
            case 'AMARELO':
              return "#fffb00";
            case 'VERMELHO':
              return "#ff1b00";
            case 'VERMELHO_INTERMITENTE':
              return ctx.createPattern(imgVI,"repeat");
            case 'AMARELO_INTERMITENTE':
              return ctx.createPattern(imgAI,"repeat");
            case 'VERMELHO_LIMPEZA':
              return "#a31100";
            }
        }
        
        function preload () {
          game.load.spritesheet('pedestre', '/images/simulador/sprite_pedestre.png', 86, 25);
          game.load.spritesheet('veicular', '/images/simulador/sprite_veicular.png', 86, 25);
          game.load.spritesheet('estado', '/images/simulador/modos.png', 10, 25);
          game.load.spritesheet('controles', '/images/simulador/controles.png', 30, 26);          
          game.load.image('grid', '/images/simulador/grid.png');
          game.load.image('a1_e1', '/images/simulador/fixture/Anel1E1.jpg');
          game.load.start();
        }
        
        function controleOver(botao){
          if(botao.animations.name === "ON"){
            botao.play("HOVER");            
          }

        }
        function controleOut(botao){
          if(botao.animations.name === "HOVER"){
            botao.play("ON");            
          }
        }
        
        function pause(botao){
          botaoPlay.play("ON");
          botaoFastBackward.play("ON");
          botaoBackward.play("ON");
          botaoExport.play("ON");          
          botaoLog.play("ON");          
          botaoFastFoward.play("ON");                    
          botaoFoward.play("ON");                              
          botaoPlay.play("ON");
          botaoPause.play("OFF");
          game.time.events.remove(repeater);
          botaoPlay.onDown.add(play, self);          
        }
        
        function play(){
          botaoPause.play("ON");
          botaoFastBackward.play("OFF");
          botaoBackward.play("OFF");
          botaoExport.play("OFF");          
          botaoLog.play("OFF");          
          botaoFastFoward.play("OFF");                    
          botaoFoward.play("OFF");                              
          botaoPlay.play("OFF");
          repeater = game.time.events.repeat(1000, duracaoSimulacao - tempo, moveToLeft, this);
        }
        
        function criaControles(){
          var inicio = 375, incremento = 31, y = 10;
          botaoFastBackward = game.add.sprite(inicio ,y , 'controles');
          botaoFastBackward.animations.add('HOVER', [0]);
          botaoFastBackward.animations.add('OFF', [8]);          
          botaoFastBackward.animations.add('ON', [16]);
          botaoFastBackward.play('OFF');
          botaoFastBackward.inputEnabled = true;
          botaoFastBackward.events.onInputOver.add(controleOver,this);
          botaoFastBackward.events.onInputOut.add(controleOut,this);          

          grupoControles.add(botaoFastBackward);

          inicio += incremento;
          botaoBackward = game.add.sprite(inicio , y, 'controles');
          botaoBackward.animations.add('HOVER', [1]);
          botaoBackward.animations.add('OFF', [9]);          
          botaoBackward.animations.add('ON', [17]);
          botaoBackward.play('OFF');
          botaoBackward.inputEnabled = true;
          botaoBackward.events.onInputOver.add(controleOver,this);
          botaoBackward.events.onInputOut.add(controleOut,this);          
          
          grupoControles.add(botaoBackward);          

          inicio += incremento;
          botaoPlay =  game.add.sprite(inicio , y, 'controles');
          botaoPlay.animations.add('HOVER', [2]);
          botaoPlay.animations.add('OFF', [10]);          
          botaoPlay.animations.add('ON', [18]);
          botaoPlay.play('OFF');
          botaoPlay.inputEnabled = true;   
          botaoPlay.events.onInputDown.add(play,this);          
          botaoPlay.events.onInputOver.add(controleOver,this);
          botaoPlay.events.onInputOut.add(controleOut,this);          
                 
          grupoControles.add(botaoPlay);          
          
          inicio += incremento;
          botaoPause = game.add.sprite(inicio , y, 'controles');
          botaoPause.animations.add('HOVER', [3]);
          botaoPause.animations.add('OFF', [11]);          
          botaoPause.animations.add('ON', [19]);
          botaoPause.play('OFF');
          botaoPause.inputEnabled = true;   
          botaoPause.events.onInputOver.add(controleOver,this);
          botaoPause.events.onInputOut.add(controleOut,this);          
          botaoPause.events.onInputDown.add(pause, this);                 
          grupoControles.add(botaoPause);          

          
          inicio += incremento;
          botaoFoward = game.add.sprite(inicio , y, 'controles');
          botaoFoward.animations.add('HOVER', [4]);
          botaoFoward.animations.add('OFF', [12]);          
          botaoFoward.animations.add('ON', [20]);
          botaoFoward.play('OFF');
          botaoFoward.inputEnabled = true;          
          botaoFoward.events.onInputOver.add(controleOver,this);
          botaoFoward.events.onInputOut.add(controleOut,this);          

          grupoControles.add(botaoFoward);          
          
          inicio += incremento;
          botaoFastFoward = game.add.sprite(inicio , y, 'controles');
          botaoFastFoward.animations.add('HOVER', [5]);
          botaoFastFoward.animations.add('OFF', [13]);          
          botaoFastFoward.animations.add('ON', [21]);
          botaoFastFoward.play('OFF');
          botaoFastFoward.inputEnabled = true;      
          botaoFastFoward.events.onInputOver.add(controleOver,this);
          botaoFastFoward.events.onInputOut.add(controleOut,this);          
              
          grupoControles.add(botaoFastFoward);          
          
          inicio += incremento + 8;
          botaoLog = game.add.sprite(inicio , y, 'controles');
          botaoLog.animations.add('HOVER', [6]);
          botaoLog.animations.add('OFF', [14]);          
          botaoLog.animations.add('ON', [22]);
          botaoLog.play('OFF');
          botaoLog.inputEnabled = true;          
          botaoLog.events.onInputOver.add(controleOver,this);
          botaoLog.events.onInputOut.add(controleOut,this);          
          
          grupoControles.add(botaoLog);          
          
          inicio += incremento;
          botaoExport = game.add.sprite(inicio , y, 'controles');
          botaoExport.animations.add('HOVER', [7]);
          botaoExport.animations.add('OFF', [15]);          
          botaoExport.animations.add('ON', [23]);
          botaoExport.inputEnabled = true;
          botaoExport.play('OFF');
          botaoExport.events.onInputOver.add(controleOver,this);
          botaoExport.events.onInputOut.add(controleOut,this);          
          

          grupoControles.add(botaoExport);                    
        }
        
        function moveToLeft(){
          for(var i = 0; i < totalGruposSemaforicos; i++){
            if(estadoGrupoSemaforico[tempo] && estadoGrupoSemaforico[tempo][i]){
              gruposSemaforicos[i].sprite.play(estadoGrupoSemaforico[tempo][i]);            
            }
          }

          tempo += (velocidade);
          relogio.setText(tempo + "s");
          game.camera.x+=(10 * velocidade);
        }        

        function loadMore(){
            var message = new Paho.MQTT.Message("proxima");
            message.destinationName = 'simulador/' + config.simulacaoId + '/proxima_pagina';
            client.send(message);
        }
        
        function desenhaDetector(anel,x,color,tipo,label){
          var y1 = offsetDeAneis[anel + 1] + ALTURA_GRUPO;
          var y2 = y1 + (config.aneis[anel].tiposGruposSemaforicos.length * ALTURA_GRUPO) +
                                            config.aneis[anel].tiposGruposSemaforicos.length - 1;
          x -= 15;

          var w  = ALTURA_GRUPO + 2;
          var h  = y2 - y1;
          var bmd = game.add.bitmapData(w,h);


          bmd.ctx.lineWidth = "2";
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

          bmd.ctx.fillStyle = "#fff";
          bmd.ctx.font = "8px Open Sans";
          if(tipo === "ACIONAMENTO_DETECTOR_VEICULAR"){
            label = "V" + label;
          }else{
            label = "P" + label;
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
              var limite = tempoInicio + intervalo[0] / 1000 + intervalo[1] / 1000;
              for(var i = tempoInicio + intervalo[0] / 1000; i < limite; i++){
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
            if(evento[1] == "ACIONAMENTO_DETECTOR_VEICULAR" || evento[1] == "PEDESTRE"){
              desenhaDetector(parseInt(anel) - 1,x + evento[0] / 100,"#082536",evento[1],evento[2]);
            }
          });
          
          bmd.ctx.fillStyle = "#7788AA";
          bmd.ctx.strokeStyle = "#426383";
          bmd.ctx.fillRect(0,0,w,ALTURA_GRUPO);
          bmd.ctx.lineWidth = "2";
          bmd.ctx.strokeRect(0,0,w,h);
          bmd.ctx.textAlign = "left";
          bmd.ctx.fillStyle = "#fff";

          if(estagio.estagio !== null){
            bmd.ctx.font = "14px Open Sans";
            bmd.ctx.fillText("E" + estagio.estagio, 5, 18);
          }

          bmd.ctx.font = "10px Open Sans";

          bmd.ctx.textAlign = "right";          
          bmd.ctx.fillText((w /10.0) + "s", w - 5, 16);

          bmd.ctx.lineWidth = "1";
          bmd.ctx.strokeStyle = "#ccc";
          
          for(var i = 10; i < w; i+=10){
              bmd.ctx.beginPath();
              bmd.ctx.moveTo(i,ALTURA_GRUPO);
              bmd.ctx.lineTo(i,h);
              bmd.ctx.stroke();
              bmd.ctx.closePath();
          }
          
          bmd.render();
          intervalosGroup.add(game.add.sprite(x, y, bmd));
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
    
          var style = { font: "12px Open Sans", fill: "#000000" };
          var text = game.add.text(36,y + 27, "G" + (grupo + 1), style);
    
          text.fixedToCamera = true;
          text.anchor.set(0,1);
          gruposSemaforicosGroup.add(text);
    
        }
                
        function inicializaGrupos(tipos,index,offset){
          var posicaoNoAnel = 0;
          tipos.forEach(function(tipo){
            
            var y;
            if(index + 1 === 1){
              y = (index + 1 * ALTURA_GRUPO) + 1;
            }else{
              y = ((index + 1) * ALTURA_GRUPO) + (1 * (index + 1));
            }
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
          
          bmd.ctx.textAlign = "center";
          bmd.ctx.fillStyle = "#fff";
          bmd.ctx.font = "12px Open Sans";
          bmd.ctx.fillText(numero, 15, h/2 + 6);
          
          bmd.render();
          var spriteY = MARGEM_SUPERIOR + y1 + ALTURA_GRUPO + 1;
          var sprite = game.add.sprite(0, spriteY, bmd);
          offsetDeAneis[numero] = spriteY;
          ALTURA_INTERVALOS = spriteY + h;
          sprite.fixedToCamera = true;
          
        }
        
        function criaAnel(anel,index,indexAnel){
          var offset =  parseInt(anel.numero) === 1 ? 0 : (anel.numero - 1) * ALTURA_GRUPO;
          var indexAtual = inicializaGrupos(anel.tiposGruposSemaforicos,index,offset);
          aneis[anel.numero] = {};
          aneis[anel.numero].inicio_grupo = index;
          aneis[anel.numero].fim_grupo = indexAtual - 1;

          var corAnel = "#777777";
          if(parseInt(anel.numero) % 2 === 0){
            corAnel = "#999999";
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
            bmd.ctx.lineWidth = "2";
            bmd.ctx.setLineDash([5, 1]);
            bmd.ctx.strokeStyle = color;
            bmd.ctx.moveTo(10,0);
            bmd.ctx.lineTo(10,h);
            bmd.ctx.stroke();
            bmd.ctx.closePath();
            bmd.ctx.fillStyle = color;
            bmd.ctx.fillRect(0,0,20,20);

            bmd.ctx.fillStyle = "#fff";

            if(label.length > 1){
              bmd.ctx.font = "8px Open Sans";
              bmd.ctx.fillText(label, 5, 14);
            }else{
              bmd.ctx.font = "12px Open Sans";
              bmd.ctx.fillText(label, 7, 15);
            }


            bmd.render();
            game.add.sprite((MARGEM_LATERAL + (x * 10)) - 10, MARGEM_SUPERIOR - 25, bmd);

        }
              
        function processaPlanos(trocas){
          trocas.forEach(function(troca){
            console.log(troca);
            console.log("Inicio Simulacao",inicioSimulacao.unix());
            var x = (troca[0] - (inicioSimulacao.unix() * 1000));
            console.log("x",x);
            desenhaPlano(x,"orange",troca[2]);
          });
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
                    
          
          criaControles();
          
          var onConnect = function () {
               // Once a connection has been made, make a subscription and send a message.
               client.subscribe('simulador/' + config.simulacaoId + '/estado');
          };
          
          // Create a client instance
          client = new Paho.MQTT.Client('mosquitto.rarolabs.com.br', 9001, 'simulador_web_' + config.simulacaoId);

          client.onMessageArrived = function(message) {
            if(message.destinationName.endsWith('/estado')){
              var json = JSON.parse(message.payloadString);
              processaEstagios(json.aneis);
              processaPlanos(json.trocas);
            }
          };
          
          function onConnectionLost(responseObject) {
            if (responseObject.errorCode !== 0) {
              console.log("onConnectionLost:"+responseObject.errorMessage);
            }
          }

          // connect the client
          client.connect({onSuccess:onConnect});
          
          criaAneis();
    
          var style = { font: "25px Open Sans", fill: "#666" };
          relogio = game.add.text(1000,5, "0", style);
    
          relogio.fixedToCamera = true;
          relogio.anchor.set(1,0);

          style = { font: "15px Open Sans", fill: "#333" };
          plano = game.add.text(10,40, "Plano Atual: 1", style);
          plano.fixedToCamera = true;
          plano.anchor.set(0,1);

          // style = { font: "15px Open Sans", fill: "#ff6700" };
          // dataHora = game.add.text(500,20, "Seg, 27/09/2016 - 12:11:34", style);
          // dataHora.fixedToCamera = true;
          // dataHora.anchor.set(0.5);

          game.time.events.repeat(20000 / velocidade, Math.ceil(duracaoSimulacao / 120) + 1, loadMore, this);
          
        }
        
        function render() {
          if(!started && intervalosGroup.children.length > 0){
            repeater = game.time.events.repeat(1000, duracaoSimulacao, moveToLeft, this);
            started = true;
            botaoPause.play("ON");
          }
        }




        
        
        //TODO: Habilitar Na proxima versao
        // function desenhaEventoMudancaPlano(evento){
     //      desenhaPlano(evento.timestamp,'blue',evento.planoAtual);
     //    }
     //    function desenhaEventoAgendamentoTrocaDePlano(evento){
     //      var y1 = (aneis[evento.anel].inicio_grupo * ALTURA_GRUPO) + (aneis[evento.anel].inicio_grupo * 1);
     //      var y2 = ((aneis[evento.anel].fim_grupo + 1) * ALTURA_GRUPO + ((aneis[evento.anel].fim_grupo + 1) * 1));
     //      desenhaAgendamento(evento.timestamp * 10,(evento.momentoTroca * 10) + 10,y1 + 15,y2 + 15,'blue');
     //    }
     //

        // function desenhaAlerta(x,label){
        //   desenhaPlano(x,"orange",label);
        // }
        //
        // function desenhaFalha(x,label){
        //   desenhaPlano(x,"#FFB4B6",label);
        // }
        // function desenhaAgendamento(x1,x2,y1,y2,color){
        //   var w  = x2 - x1;
        //   var h  = y2 - y1;
        //   var bmd = game.add.bitmapData(w,h);
        //
        //  bmd.ctx.lineWidth = "2";
        //  bmd.ctx.setLineDash([5, 5]);
        //  bmd.ctx.strokeStyle = color;
        //
        //  bmd.ctx.beginPath();
        //    bmd.ctx.moveTo(w-2,0);
        //    bmd.ctx.lineTo(w-2,h);
        //    bmd.ctx.stroke();
        // bmd.ctx.closePath();
        //
        //  bmd.ctx.beginPath(); 
        //    bmd.ctx.moveTo(10,h/2);
        //    bmd.ctx.lineTo(w,h/2);
        //    bmd.ctx.stroke();
        //  bmd.ctx.closePath();
        //
        //  bmd.ctx.fillStyle = color;
        //  bmd.ctx.font = "12px Open Sans";
        //  bmd.ctx.fillText((w/10) - 1 + "s", w/2, h/2 - 3);
        //
        //
        //  bmd.render();
        //  game.add.sprite((MARGEM_LATERAL + x1) - 10, MARGEM_SUPERIOR + y1 + 15, bmd);
        //
        // }

        game = new Phaser.Game(1000, 700, Phaser.AUTO, 'canvas', { preload: preload, create: create, render: render });
      }
      return Simulador;
    }());
    components.Simulador = Simulador;
  })(components = influunt.components || (influunt.components = {}));
})(influunt || (influunt = {}));

