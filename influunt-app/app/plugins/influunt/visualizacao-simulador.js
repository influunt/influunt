var influunt;
(function (influunt) {
  var components;
  (function (components) {
    var Simulador = (function () {
      function Simulador(inicioSimulacao,fimSimulacao,velocidade,config) {
        
        var velocidade = parseFloat(velocidade);
        var config = config;
        var quantidadeDeAneis = _.filter(config.aneis,function(a){return a.tiposGruposSemaforicos.length > 0}).length;
        var inicioSimulacao = inicioSimulacao;
        var fimSimulacao = fimSimulacao;
        var duracaoSimulacao = (fimSimulacao.unix() - inicioSimulacao.unix()) / velocidade;
        var imgVI = document.getElementById("modo_vi");
        var imgAI = document.getElementById("modo_ai");        
        
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
        var MARGEM_LATERAL = 966 ;
        var MARGEM_SUPERIOR = 20;
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
        var offsetDeAneis = {};
        var offsetGrupo = {};

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
              processaEstagios(json.aneis)
              // processarEstado(json.estados)
              // processarEventos(json.eventos)
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
    
          // var grid = game.add.sprite(118 , MARGEM_SUPERIOR + ALTURA_GRUPO - 4, 'grid');
          // grid.fixedToCamera = true;
          
          game.time.events.repeat(20000 / velocidade, Math.ceil(duracaoSimulacao / 120), loadMore, this);
          //addToLog("Ola mundo");
          
          
          // desenhaDetector(3,0,4,"#082536","V1");
          // desenhaDetector(4,5,9,"#082536","P4");
          // desenhaDetector(5,5,9,"#082536","V8");
          // desenhaDetector(5,10,14,"#082536","V3");
          // desenhaAlerta(8,"12");
          // desenhaFalha(10,"44");
          
        }
        
        function update(time) {

        }

        function render() {
          if(!started && intervalosGroup.children.length > 0){
            game.time.events.repeat(1000, duracaoSimulacao, moveToLeft, this);
            started = true;
          }

        }

        function renderIntervalos(){
          // var limite = Math.max(1,velocidade);
          // for(var i = tempo; i < tempo + limite; i++){
          //   if(pendingToDraw[i]!= undefined){
          //     desenhaIntervalos(i,pendingToDraw[i])
          //     delete pendingToDraw[i];
          //   }else{
          //     break;
          //   }
          // }
        }
                
        function moveToLeft(){
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
        
        function processaEstagios(aneis){
          Object.keys(aneis).forEach(function(anel){
            var topOffset = offsetDeAneis[parseInt(anel)];
            aneis[anel].forEach(function(estagio){
              desenhaEstagio(topOffset,estagio);
            })
          });
        }
        
        function desenhaEstagio(y,estagio){

          var h = Object.keys(estagio.grupos).length * ALTURA_GRUPO + ALTURA_GRUPO + (Object.keys(estagio.grupos).length - 1);
          var w = estagio.w / 100;
          var tempoInicio = estagio.x / 1000;
          console.log("tempoInicio",tempoInicio);

          var x = (estagio.x / 100) + MARGEM_LATERAL;
          var bmd = game.add.bitmapData(w,h);
          
          
          _.each(estagio.grupos,function(grupo,grupoKey){

            console.log("Grupo",grupo);
            console.log("GrupoKey",grupoKey);            

              
            var yi =  offsetGrupo[grupoKey] * ALTURA_GRUPO + ALTURA_GRUPO;
            if(offsetGrupo[grupoKey] > 0){
              yi += offsetGrupo[grupoKey]
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


          bmd.ctx.fillStyle = "#7788AA";
          bmd.ctx.strokeStyle = "#426383";
          bmd.ctx.fillRect(0,0,w,ALTURA_GRUPO);
          bmd.ctx.lineWidth = "2";
          bmd.ctx.strokeRect(0,0,w,h);
          bmd.ctx.textAlign = "left";
          bmd.ctx.fillStyle = "#fff";
          bmd.ctx.font = "14px Open Sans";
          bmd.ctx.fillText("E" + estagio.estagio, 5, 18);
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
          var offset =  anel.numero == 1 ? 0 : (anel.numero - 1) * ALTURA_GRUPO;
          var indexAtual = inicializaGrupos(anel.tiposGruposSemaforicos,index,offset);
          aneis[anel.numero] = {}
          aneis[anel.numero]["inicio_grupo"] = index;
          aneis[anel.numero]["fim_grupo"] = indexAtual - 1;

          var corAnel = "#777777";
          if(anel.numero % 2 == 0){
            corAnel = "#999999";
          }
          var y1;
          var y2;

          if(anel.numero == 1){
            y1 = (index * ALTURA_GRUPO) - ALTURA_GRUPO;
            y2 = (indexAtual * ALTURA_GRUPO) + (indexAtual - 1);
          }else{
            y1 = (index * ALTURA_GRUPO) + (index - 1)  + (((anel.numero - 1) * ALTURA_GRUPO) - ALTURA_GRUPO);            
            y2 = (indexAtual * ALTURA_GRUPO) + (indexAtual - 1) + (anel.numero - 1) * ALTURA_GRUPO;
            offset = anel.numero * ALTURA_GRUPO;
          }

          desenhaAnel(anel.numero,y1,y2,corAnel,indexAnel + 1 == quantidadeDeAneis );
          index = indexAtual;          
          
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
          sprite.fixedToCamera = true;
          
        }

        function inicializaGrupos(tipos,index,offset){
          var posicaoNoAnel = 0;
          tipos.forEach(function(tipo){
            
            var y;
            if(index + 1 == 1){
              y = (index + 1 * ALTURA_GRUPO) + 1
            }else{
              y = ((index + 1) * ALTURA_GRUPO) + (1 * (index + 1));
            }
            criaGrupoSemaforico(index, y + MARGEM_SUPERIOR + offset, tipo == 'VEICULAR');
            index++;
            offsetGrupo[index] = posicaoNoAnel++;
          })
          return index;
        }

        function criaGrupoSemaforico(grupo, y,veicular){
          var grupoSemaforico = {};
          grupoSemaforico['numero'] = grupo;
          grupoSemaforico['state']  = 'apagado';

          if(veicular){
            grupoSemaforico['sprite'] = game.add.sprite(30 , y, 'veicular');      
      
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

            grupoSemaforico['sprite'] = game.add.sprite(30 , y, 'pedestre');      
      
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
          var text = game.add.text(36,y + 27, "G" + grupo, style);
    
          text.fixedToCamera = true;
          text.anchor.set(0,1);
          gruposSemaforicosGroup.add(text);
    
        }

        
        function desenhaEventoMudancaPlano(evento){
          desenhaPlano(evento.timestamp,'blue',evento.planoAtual);
        }
        function desenhaEventoAgendamentoTrocaDePlano(evento){
          var y1 = (aneis[evento.anel]["inicio_grupo"] * ALTURA_GRUPO) + (aneis[evento.anel]["inicio_grupo"] * 1);
          var y2 = ((aneis[evento.anel]["fim_grupo"] + 1) * ALTURA_GRUPO + ((aneis[evento.anel]["fim_grupo"]+ 1) * 1));
          desenhaAgendamento(evento.timestamp * 10,(evento.momentoTroca * 10) + 10,y1 + 15,y2 + 15,'blue')
        }
        
        function desenhaPlano(x,color,label){

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

         if(label.length > 1){
           bmd.ctx.font = "8px Open Sans";
           bmd.ctx.fillText(label, 5, 14);
         }else{
           bmd.ctx.font = "12px Open Sans";
           bmd.ctx.fillText(label, 7, 15);
         }


         bmd.render();
         game.add.sprite((MARGEM_LATERAL + (x * 10)) - 10, MARGEM_SUPERIOR, bmd);
         
        }

        function desenhaAlerta(x,label){
          desenhaPlano(x,"orange",label);
        }

        function desenhaFalha(x,label){
          desenhaPlano(x,"#FFB4B6",label);
        }
        
        function desenhaAgendamento(x1,x2,y1,y2,color){
          var w  = x2 - x1;
          var h  = y2 - y1;
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

        function desenhaDetector(x,inicio,fim,color,label){

          var y1 = (inicio * ALTURA_GRUPO) + inicio + 15;
          var y2 = ((fim + 1) * ALTURA_GRUPO + ((fim + 1) * 1)) + 15;
          x = x * 10;


          var w  = ALTURA_GRUPO + 2;
          var h  = y2 - y1;
          var bmd = game.add.bitmapData(w,h);
          var color = color;

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
         bmd.ctx.fillText(label, 5,h/2-1);

         bmd.render();
         game.add.sprite(((MARGEM_LATERAL + x) - 10) + 3, MARGEM_SUPERIOR + y1 + 15, bmd);
         
        }  
  
        function decodeEstado(estado,ctx,w){

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

