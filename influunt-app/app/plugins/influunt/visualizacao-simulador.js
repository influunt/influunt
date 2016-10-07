var influunt;
(function (influunt) {
  var components;
  (function (components) {
    var Simulador = (function () {
      function Simulador(inicioSimulacao,fimSimulacao,velocidade,config) {

        var velocidade = velocidade;
        var config = config;
        var inicioSimulacao = inicioSimulacao;
        var fimSimulacao = fimSimulacao;
        var duracaoSimulacao = (fimSimulacao.unix() - inicioSimulacao.unix()) / velocidade;
        
        var game = new Phaser.Game(1000, 700, Phaser.AUTO, 'canvas', { preload: preload, create: create, update: update, render: render });

        function preload() {
          game.load.spritesheet('pedestre', '/images/simulador/sprite_pedestre.png', 86, 25);
          game.load.spritesheet('veicular', '/images/simulador/sprite_veicular.png', 86, 25);
          game.load.spritesheet('estado', '/images/simulador/modos.png', 10, 25);
          game.load.image('grid', '/images/simulador/grid.png');
          game.load.image('a1_e1', '/images/simulador/fixture/Anel1E1.jpg');
          game.load.start()
        }

        var ALTURA_GRUPO = 25;
        var MARGEM_LATERAL = 1000;
        var MARGEM_SUPERIOR = 190;
        var VERMELHO = 0xFF0000;
        var VERDE = 0x00FF00;
        var AMARELO = 0xFFFF00;
        var TINT_VERDE = 0x59b159;
        var TINT_VERMELHO = 0xdd6f6f;
        var TINT_BRANCO = 0xffffff;
        var grupos = [];
        var cursors;
        var intervalosGroup;
        var textoIntervalosGroup;
        var gruposSemaforicosGroup;
        var updateTime;
        var totalGruposSemaforicos = 0;
        var gruposSemaforicos = [];
        var estadoAtual = [];
        var estadoGrupoSemaforico = {};
        var log = [];
        var drawLog = false;
        var pendingToDraw = {};
        var aneis={};

        var tempo = 0;
  
        var relogio;
        var plano;
        var aneis = {};
        var dataHora = {};
        var client;
        var started = false;
        
        function create() {
          
          game.stage.backgroundColor = '#cccccc';
          cursors = game.input.keyboard.createCursorKeys();    
          game.world.setBounds(0, 0, 1000 + (duracaoSimulacao * 10 * Math.max(1,velocidade)), 800);
          
          
          intervalosGroup = game.add.group();
          textoIntervalosGroup = game.add.group();    
          gruposSemaforicosGroup = game.add.group();
          
          var onConnect = function () {
               // Once a connection has been made, make a subscription and send a message.
               client.subscribe('simulador/' + config.simulacaoId + '/estado');
          }
          // Create a client instance
          client = new Paho.MQTT.Client('mosquitto.rarolabs.com.br', 9001, 'simulador_web_' + config.simulacaoId);

          client.onMessageArrived = function(message) {
            if(message.destinationName.endsWith('/estado')){
              var json = JSON.parse(message.payloadString);
              processarEstado(json.estados)
              processarEventos(json.eventos)
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
          relogio = game.add.text(990,650, "0", style);
    
          relogio.fixedToCamera = true;
          relogio.anchor.set(1,0);

          style = { font: "15px Open Sans", fill: "#333" };
          plano = game.add.text(10,680, "Plano Atual: 1", style);
          plano.fixedToCamera = true;
          plano.anchor.set(0,1);

          style = { font: "15px Open Sans", fill: "#ff6700" };
          dataHora = game.add.text(500,668, "Seg, 27/09/2016 - 12:11:34", style);
          dataHora.fixedToCamera = true;
          dataHora.anchor.set(0.5);

          // drawLine(10,0,460,'blue',true);
          // drawLine(30,0,460,'blue',true);
    
          var grid = game.add.sprite(88 , MARGEM_SUPERIOR + ALTURA_GRUPO - 4, 'grid');      
          grid.fixedToCamera = true;
          
          game.time.events.repeat(20000 / velocidade, Math.ceil(duracaoSimulacao / 120), loadMore, this);
          //addToLog("Ola mundo");
          
          desenhaLinha(85,"orange","DV1");

        }
        
        function update(time) {

        }

        function render() {
          if(!started && Object.keys(pendingToDraw).length > 90){
            game.time.events.repeat(2000,  Math.ceil(duracaoSimulacao / 2), destroySprites,this)
            game.time.events.repeat(1000, duracaoSimulacao, moveToLeft, this);
            started = true;
          }

        }

        function renderIntervalos(){
          var limite = Math.max(1,velocidade);
          for(var i = tempo; i < tempo + limite; i++){
            if(pendingToDraw[i]!= undefined){
              desenhaIntervalos(i,pendingToDraw[i])
              delete pendingToDraw[i];
            }else{
              break;
            }
          }
        }
                
        function moveToLeft(){
          renderIntervalos();
          for(var i = 0; i < totalGruposSemaforicos; i++){
            if(estadoGrupoSemaforico[tempo] && estadoGrupoSemaforico[tempo][i]){
              gruposSemaforicos[i]['sprite'].play(estadoGrupoSemaforico[tempo][i]);            
            }
          }

          tempo += (velocidade);
          relogio.setText(tempo + "s");
          game.camera.x+=(10 * velocidade);
          if(drawLog){
            desenhaLog()
          }
        }        

        function loadMore(){
            var message = new Paho.MQTT.Message("proxima");
            message.destinationName = 'simulador/' + config.simulacaoId + '/proxima_pagina';
            client.send(message);
        }
       
        function destroySprites(){
          intervalosGroup.children.forEach(function(e){
            if(tempo > e.name + 100){
              e.kill();
              intervalosGroup.remove(e,false,true);
            }
          })
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
          
        function processarEstado(estados){
          estados.forEach(function(e){
            var x = e.timestamp;
            pendingToDraw[x] = e.estado;
          })
          
        }

        function processarEventos(eventos){
          eventos.forEach(function(e){
            switch(e.tipo){
              case "ALTERACAO_EVENTO":
                desenhaEventoMudancaPlano(e);
                break;
              case "AGENDAMENTO_TROCA_DE_PLANO":
                desenhaEventoAgendamentoTrocaDePlano(e);
                break;
            }
          });
          
        }

        function criaAnel(anel,index,indexAnel){
          
          var indexAtual = inicializaGrupos(anel.tiposGruposSemaforicos,index);
          aneis[anel.numero] = {}
          aneis[anel.numero]["inicio_grupo"] = index;
          aneis[anel.numero]["fim_grupo"] = indexAtual - 1;
          index = indexAtual;          
          
          var ml = 10;
          var style = { font: "12px Open Sans", fill: "#000", fontWeight:'bolder' };
          aneis[anel] = {sprite: game.add.sprite(ml + indexAnel * 155, 20, 'a1_e1') };      
          aneis[anel]['sprite'].fixedToCamera = true;
          aneis[anel]['sprite'].tint = TINT_VERDE;
    
          aneis[anel]["text"] = game.add.text(ml + indexAnel * 155,3, "Anel " + anel.numero, style);
          aneis[anel]["text"].fixedToCamera = true; 
    
          style = { font: "12px Open Sans", fill: "blue", fontWeight:'bolder' };
          aneis[anel]["textTempoCiclo"] = game.add.text(ml + indexAnel * 155,172, "TC: 12/48", style);
          aneis[anel]["textTempoCiclo"].fixedToCamera = true; 

          style = { font: "12px Open Sans", fill: "#ff6700", fontWeight:'bolder' };
          aneis[anel]["textNumeroCiclo"] = game.add.text((ml + indexAnel * 155) + 150 ,172, "NC: 2", style);
          aneis[anel]["textNumeroCiclo"].fixedToCamera = true; 
          aneis[anel]["textNumeroCiclo"].anchor.set(1,0);

          return index;

        }

        function inicializaGrupos(tipos,index){

          tipos.forEach(function(tipo){
            
            var y;
            if(index + 1 == 1){
              y = (index + 1 * ALTURA_GRUPO) + 1
            }else{
              y = ((index + 1) * ALTURA_GRUPO) + (1 * (index + 1));
            }
            criaGrupoSemaforico(index, y + MARGEM_SUPERIOR, tipo == 'VEICULAR');
            index++;
          })
          return index;
        }

        function criaGrupoSemaforico(grupo, y,veicular){
          var grupoSemaforico = {};
          grupoSemaforico['numero'] = grupo;
          grupoSemaforico['state']  = 'apagado';
    
          if(veicular){
            grupoSemaforico['sprite'] = game.add.sprite(0 , y, 'veicular');      
      
            //Estados
            grupoSemaforico['DESLIGADO'] = grupoSemaforico['sprite'].animations.add('DESLIGADO', [0]);
            grupoSemaforico['DESLIGADO'].enableUpdate = true;

            grupoSemaforico['VERMELHO'] = grupoSemaforico['sprite'].animations.add('VERMELHO', [3]);           
            grupoSemaforico['VERMELHO'].enableUpdate = true;

            grupoSemaforico['VERMELHO_LIMPEZA'] = grupoSemaforico['sprite'].animations.add('VERMELHO_LIMPEZA', [3]);           
            grupoSemaforico['VERMELHO_LIMPEZA'].enableUpdate = true;

            grupoSemaforico['AMARELO'] = grupoSemaforico['sprite'].animations.add('AMARELO', [2]);           
            grupoSemaforico['AMARELO'].enableUpdate = true;

            grupoSemaforico['VERDE'] = grupoSemaforico['sprite'].animations.add('VERDE', [1]);           
            grupoSemaforico['VERDE'].enableUpdate = true;

            grupoSemaforico['AMARELO_INTERMITENTE'] = grupoSemaforico['sprite'].animations.add('AMARELO_INTERMITENTE', [0,2],2,true);           
            grupoSemaforico['AMARELO_INTERMITENTE'].enableUpdate = true;

          }else{

            grupoSemaforico['sprite'] = game.add.sprite(0 , y, 'pedestre');      
      
            //Estados
            grupoSemaforico['DESLIGADO'] = grupoSemaforico['sprite'].animations.add('DESLIGADO', [0]);
            grupoSemaforico['DESLIGADO'].enableUpdate = true;

            grupoSemaforico['VERDE'] = grupoSemaforico['sprite'].animations.add('VERDE', [1]);           
            grupoSemaforico['VERDE'].enableUpdate = true;

            grupoSemaforico['VERMELHO'] = grupoSemaforico['sprite'].animations.add('VERMELHO', [2]);           
            grupoSemaforico['VERMELHO'].enableUpdate = true;
      
            grupoSemaforico['VERMELHO_LIMPEZA'] = grupoSemaforico['sprite'].animations.add('VERMELHO_LIMPEZA', [2]);           
            grupoSemaforico['VERMELHO_LIMPEZA'].enableUpdate = true;

            grupoSemaforico['VERMELHO_INTERMITENTE'] = grupoSemaforico['sprite'].animations.add('VERMELHO_INTERMITENTE', [0,2],2,true);
            grupoSemaforico['VERMELHO_INTERMITENTE'].enableUpdate = true;
      
          }
          grupoSemaforico['sprite'].fixedToCamera = true;
          gruposSemaforicosGroup.add(grupoSemaforico['sprite']);
          gruposSemaforicos[grupo] = grupoSemaforico;
    
          var style = { font: "12px Open Sans", fill: "#000000" };
          var text = game.add.text(6,y + 27, "G" + grupo, style);
    
          text.fixedToCamera = true;
          text.anchor.set(0,1);
          gruposSemaforicosGroup.add(text);
    
        }

        function desenhaIntervalos(inicio, estado){
          var grupos = decodeEstado(estado.split(','));
          estadoGrupoSemaforico[inicio] = estadoGrupoSemaforico[inicio] || {};
    
          for(var grupo = 1; grupo <= grupos.length; grupo++){

            if(estadoAtual[grupo] != grupos[grupo - 1]){
              estadoAtual[grupo] = grupos[grupo - 1];
            }
      
            var x,y;
            x = (inicio * 10) + MARGEM_LATERAL;
            if(grupo == 1){
              y = (grupo * ALTURA_GRUPO) + 1
            }else{
              y = (grupo * ALTURA_GRUPO) + (1 * grupo);
            }
    
            intervalosGroup.add(desenhaEstado(x,y + MARGEM_SUPERIOR,grupos[grupo - 1]));      
            estadoGrupoSemaforico[inicio][grupo - 1] = grupos[grupo - 1];
          }
        }
  
        function desenhaEstado(x,y,estado){
          var s = game.add.sprite(x , y, 'estado');
          s.name = (x - MARGEM_LATERAL) / 10;
    
          s.animations.add('DESLIGADO', [0]);

          switch(estado){
            case 'DESLIGADO':
                  s.animations.add('DESLIGADO', [0]);
                  s.play('DESLIGADO');
                  break;
            case 'VERDE':
                  s.animations.add('VERDE', [0]);
                  s.play('VERDE');
                  break;
            case 'AMARELO':
                  s.animations.add('AMARELO', [4]);
                  s.play('AMARELO');
                  break;
            case 'VERMELHO':
                  s.animations.add('VERMELHO', [1]);
                  s.play('VERMELHO');
                  break;
            case 'VERMELHO_INTERMITENTE':
                  s.animations.add('VERMELHO_INTERMITENTE', [3]);
                  s.play('VERMELHO_INTERMITENTE');
                  break;
            case 'AMARELO_INTERMITENTE':
                  s.animations.add('AMARELO_INTERMITENTE', [5]);
                  s.play('AMARELO_INTERMITENTE');
                  break;
            case 'VERMELHO_LIMPEZA':
                  s.animations.add('VERMELHO_LIMPEZA', [2]);
                  s.play('VERMELHO_LIMPEZA');
                  break;
          }
    
          return s;
        }
        
        function desenhaEventoMudancaPlano(evento){
          desenhaLinha(evento.timestamp,'blue',evento.planoAtual);
        }
        function desenhaEventoAgendamentoTrocaDePlano(evento){
          var y1 = (aneis[evento.anel]["inicio_grupo"] * ALTURA_GRUPO) + (aneis[evento.anel]["inicio_grupo"] * 1);
          var y2 = ((aneis[evento.anel]["fim_grupo"] + 1) * ALTURA_GRUPO + ((aneis[evento.anel]["fim_grupo"]+ 1) * 1));
          desenhaSeqmento(evento.timestamp * 10,(evento.momentoTroca * 10) + 10,y1 + 15,y2 + 15,'blue')
        }
        
        function desenhaLinha(x,color,label){

         var bmd = game.add.bitmapData(20,460);
         var color = color;

         bmd.ctx.closePath();
         bmd.ctx.beginPath();
         bmd.ctx.lineWidth = "2";
         bmd.ctx.setLineDash([5, 5]);
         bmd.ctx.strokeStyle = color;
         bmd.ctx.moveTo(10,0);
         bmd.ctx.lineTo(10,460);
         bmd.ctx.stroke();
         bmd.ctx.closePath();
         bmd.ctx.fillStyle = color;
         bmd.ctx.fillRect(0,0,20,20);
         bmd.ctx.fillStyle = "#fff";
         bmd.ctx.font = "12px Open Sans";
         bmd.ctx.fillText(label, 7, 15);
         bmd.render();
         game.add.sprite((MARGEM_LATERAL + (x * 10)) - 10, MARGEM_SUPERIOR, bmd);
         
        }

        function desenhaSeqmento(x1,x2,y1,y2,color){
          var w  = x2 - x1;
          var h  = y2 - y1;
           

          console.log("x1",x1);
          console.log("x2",x2);
          console.log("y1",y1);
          console.log("y1",y2);                              
          console.log("w",w);
          console.log("h",h);
         var bmd = game.add.bitmapData(w,h);
         var color = color;

         //bmd.ctx.fillStyle = "#000";
         // bmd.ctx.fillRect(0,0,x2-x1,y2-y1);

         bmd.ctx.lineWidth = "2";
         bmd.ctx.setLineDash([5, 5]);
         bmd.ctx.strokeStyle = color;

         bmd.ctx.beginPath();
           bmd.ctx.moveTo(w-2,0);
           bmd.ctx.lineTo(w-2,h);
           bmd.ctx.stroke();
        bmd.ctx.closePath();

         bmd.ctx.beginPath();
           bmd.ctx.moveTo(10,h/2);
           bmd.ctx.lineTo(w,h/2);
           bmd.ctx.stroke();  
         bmd.ctx.closePath();
         
         bmd.ctx.fillStyle = color;
         bmd.ctx.font = "12px Open Sans";
         bmd.ctx.fillText((w/10) - 1 + "s", w/2, h/2 - 3);
         

         bmd.render();
         game.add.sprite((MARGEM_LATERAL + x1) - 10, MARGEM_SUPERIOR + y1 + 15, bmd);
         
        }
  
  
        function decodeEstado(data){

          return _.chain(data).map(function(e){ 
            var i = parseInt(e);
            var left = i >> 4;
            var right = i & 15;
            return [left,right];
            }).flatten().map(function(e){
              switch(e){
                case 0:
                  return 'DESLIGADO';
                  break;
                case 1:
                  return 'VERDE';
                  break;
                case 2:
                  return 'AMARELO';
                  break;
                case 3:
                  return 'VERMELHO';
                  break;
                case 4:
                  return 'VERMELHO_INTERMITENTE';
                  break;
                case 5:
                  return 'AMARELO_INTERMITENTE';
                  break;
                case 6:
                  return 'VERMELHO_LIMPEZA';
                  break;
              }    
            }).value();
    
        }

        

        // function desenhaLog(){
        //   style = { font: "12px Open Sans", fill: "#000", wordWrap: true,    wordWrapWidth: 350 };
        //   for(var i =0; i < log.length; i++){
        //     var linha = game.add.text(650,15 + i * 40, "(252) 22/02 às 19:00 - O plano 1 vai ser trocado pelo plano 2 no anel 3, controlador ", style);
        //     linha.fixedToCamera = true;
        //   }
        //   return linha;
        // }
        //
        // function addToLog(string){
        //   log.unshift(string);
        //   if(log.length > 4){
        //     log.pop();
        //   }
        //   drawLog = true;
        // }
        
 

      }
      return Simulador;
    }());
    components.Simulador = Simulador;
  })(components = influunt.components || (influunt.components = {}));
})(influunt || (influunt = {}));

