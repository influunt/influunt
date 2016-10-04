'use strict';

/**
* @ngdoc function
* @name influuntApp.controller:SimulacaoCtrl
* @description
* # SimulacaoCtrl
* Controller of the influuntApp
*/
angular.module('influuntApp')
.controller('SimulacaoCtrl', ['$scope', '$controller',
function ($scope, $controller) {
  var game = new Phaser.Game(1000, 650, Phaser.AUTO, 'canvas', { preload: preload, create: create, update: update, render: render });

  function preload() {
    game.load.spritesheet('pedestre', '/images/simulador/sprite_pedestre.png', 86, 25);
    game.load.spritesheet('veicular', '/images/simulador/sprite_veicular.png', 86, 25);
    game.load.spritesheet('estado', '/images/simulador/modos.png', 10, 25);
    game.load.image('grid', '/images/simulador/grid.png');
    
    game.load.image('a1_e1', '/images/simulador/fixture/Anel1E1.jpg');
    game.load.image('a1_e2', '/images/simulador/fixture/Anel1E2.jpg');
    game.load.image('a1_e3', '/images/simulador/fixture/Anel1E3.jpg');

    game.load.image('a2_e1', '/images/simulador/fixture/Anel2E1.jpg');
    game.load.image('a2_e2', '/images/simulador/fixture/Anel2E2.jpg');
    game.load.image('a2_e3', '/images/simulador/fixture/Anel2E3.jpg');

    game.load.image('a3_e1', '/images/simulador/fixture/Anel3E1.jpg');
    game.load.image('a3_e2', '/images/simulador/fixture/Anel3E2.jpg');
    game.load.image('a3_e3', '/images/simulador/fixture/Anel3E3.jpg');

    game.load.image('a4_e1', '/images/simulador/fixture/Anel4E1.jpg');
    game.load.image('a4_e2', '/images/simulador/fixture/Anel4E2.jpg');
    game.load.image('a4_e3', '/images/simulador/fixture/Anel4E3.jpg');
            
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
  var gruposSemaforicos = [];
  var estadoAtual = [];

  var estados = ["19,22,80,19,38,64,0,0","19,22,80,19,38,64,0,0","19,22,80,19,38,64,0,0",
                 "22,19,80,19,38,64,0,0","22,19,80,19,38,64,0,0","22,19,80,19,38,64,0,0",
                 "19,22,80,19,38,64,0,0","19,22,80,19,38,64,0,0","19,22,80,19,38,64,0,0",
                 ];
  var tipoGrupos = ['V','V','V','V','V','V','P','P','P','P','P','P','P','P','P','P'];
  var tempo = 0;
  
  var relogio;
  var plano;
  var aneis = {};
  var dataHora = {};
  
  function create() {
    intervalosGroup = game.add.group();
    textoIntervalosGroup = game.add.group();    
    gruposSemaforicosGroup = game.add.group();

    game.stage.backgroundColor = '#cccccc';
    cursors = game.input.keyboard.createCursorKeys();    
    game.world.setBounds(0, 0, 86400, 800);
    inicializaGrupos(tipoGrupos);
    game.time.events.repeat(1000, 86400, moveToLeft, this);

    criaAnel(0);
    criaAnel(1);
    criaAnel(2);
    criaAnel(3);
    
    var style = { font: "30px Open Sans", fill: "#fff" };
    relogio = game.add.text(990,20, "0", style);
    
    relogio.fixedToCamera = true;
    relogio.anchor.set(1,0);

    style = { font: "15px Open Sans", fill: "blue" };
    plano = game.add.text(990,60, "Plano Atual: 1", style);
    plano.fixedToCamera = true;
    plano.anchor.set(1,0);

    style = { font: "15px Open Sans", fill: "#ff6700" };
    dataHora = game.add.text(990,80, "Seg, 27/09/2016 - 12:11:34", style);
    dataHora.fixedToCamera = true;
    dataHora.anchor.set(1,0);
    
    drawLine(10,0,460,'blue',true);
    drawLine(30,0,460,'blue',true);
    
    var grid = game.add.sprite(89 , MARGEM_SUPERIOR + ALTURA_GRUPO - 4, 'grid');      
    grid.fixedToCamera = true;

  }
  
  function criaAnel(anel){
    var ml = 10;
    var style = { font: "12px Open Sans", fill: "#000", fontWeight:'bolder' };
    aneis[anel] = {sprite: game.add.sprite(ml + anel * 155, 20, 'a1_e1') };      
    aneis[anel]['sprite'].fixedToCamera = true;
    aneis[anel]['sprite'].tint = TINT_VERDE;
    
    aneis[anel]["text"] = game.add.text(ml + anel * 155,3, "Anel " + (anel + 1), style);
    aneis[anel]["text"].fixedToCamera = true; 
    
    style = { font: "12px Open Sans", fill: "blue", fontWeight:'bolder' };
    aneis[anel]["textTempoCiclo"] = game.add.text(ml + anel * 155,172, "TC: 12/48", style);
    aneis[anel]["textTempoCiclo"].fixedToCamera = true; 

    style = { font: "12px Open Sans", fill: "#ff6700", fontWeight:'bolder' };
    aneis[anel]["textNumeroCiclo"] = game.add.text((ml + anel * 155) + 150 ,172, "NC: 2", style);
    aneis[anel]["textNumeroCiclo"].fixedToCamera = true; 
    aneis[anel]["textNumeroCiclo"].anchor.set(1,0);
  }

  function drawLine(x,y1,y2,color,mark){
    
   var bmd = game.add.bitmapData(20,y2);
   var color = color;

   bmd.ctx.closePath();
   bmd.ctx.beginPath();
   bmd.ctx.lineWidth = "2";
   bmd.ctx.setLineDash([5, 5]);
   bmd.ctx.strokeStyle = color;
   bmd.ctx.moveTo(10,y1);
   bmd.ctx.lineTo(10,y2);
   bmd.ctx.stroke();
   bmd.ctx.closePath();
   if(mark){
     bmd.ctx.fillStyle = color;
     bmd.ctx.fillRect(0,0,20,20);
   }
   
   bmd.render();
   game.add.sprite(MARGEM_LATERAL + x, MARGEM_SUPERIOR + y1, bmd);
  }

  function update(time) {

  }
  
  function moveToLeft(){
    if(estados[tempo]!=undefined){
      desenhaIntervalos(tempo,estados[tempo]);
    }
    tempo++;
    relogio.setText(tempo + "s");
    game.camera.x+=10;
  }
  function render() {
    //game.debug.text(updateTime.stingify(), 32, 32);

  }
  
  function inicializaGrupos(tipos){
    //Inicializa Grupos Semaforicos
    for(var grupo = 1; grupo <= tipoGrupos.length; grupo++){
      var y;
      if(grupo == 1){
        y = (grupo * ALTURA_GRUPO) + 1
      }else{
        y = (grupo * ALTURA_GRUPO) + (1 * grupo);
      }
      criaGrupoSemaforico(grupo, y + MARGEM_SUPERIOR,tipoGrupos[grupo - 1] == 'V');
    }
  }
  

  
  
  function desenhaIntervalos(inicio, estado){
    var grupos = decode(estado.split(','));
    
    for(var grupo = 1; grupo <= grupos.length; grupo++){
      if(estadoAtual[grupo] != grupos[grupo - 1]){
        gruposSemaforicos[grupo]['sprite'].play(grupos[grupo - 1]);
        estadoAtual[grupo] = grupos[grupo - 1];
      }
      
      var x,y;
      x = (inicio * 10) + MARGEM_LATERAL;
      if(grupo == 1){
        y = (grupo * ALTURA_GRUPO) + 1
      }else{
        y = (grupo * ALTURA_GRUPO) + (1 * grupo);
      }
    
      intervalosGroup.add(drawIntervalo(x,y + MARGEM_SUPERIOR,grupos[grupo - 1]));      
      
    }
  }
  
  function drawIntervalo(x,y,estado){
    var s = game.add.sprite(x , y, 'estado');      
    
    s.animations.add('DESLIGADO', [0]);
    s.enableUpdate = true;
    
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

      grupoSemaforico['AMARELO_INTERMITENTE'] = grupoSemaforico['sprite'].animations.add('AMARELO_INTERMITENTE', [2,0,2],1,true);           
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

      grupoSemaforico['VERMELHO_INTERMITENTE'] = grupoSemaforico['sprite'].animations.add('VERMELHO_INTERMITENTE', [2,0,2],1,true);
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
  
  function decode(data){

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
            return 'VERMELHO';
            break;
          case 3:
            return 'AMARELO';
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
}]);
