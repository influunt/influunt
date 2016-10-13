'use strict';

var worldObj = require('../world');

var world = new worldObj.World();

var TabelasHorariasPage = function () {
  var selects = {
    'Dias':                            '[name="eventoDiaDaSemana"]',
    'Hora':                            '[name="eventoHora"]',
    'Minuto':                          '[name="eventoMinuto"]',
    'Segundo':                         '[name="eventoSegundo"]',
    'Plano':                           '[name="eventoPlano"]'
  };

  var eventoAdd = 'tr[influunt-evento^="visualizarPlano"]:last-child';
  var eventAdded = 'tr[data-evento^="evento"]:first-child';

  this.isTabelaHoraria = function() {
    return world.waitForByXpath('//ng-include[contains(@src, "views/tabela_horarios/tabs-eventos.html")]');
  };

  this.selecionarValor = function(valor, select) {
    return world.waitForOverlayDisappear().then(function (){
      return world.selectByOptionAtribute(eventoAdd, selects[select], 'value', valor);
    });
  };

  this.enventoPossuiErro = function() {
    return world.waitForByXpath('//tr[contains(@data-ng-repeat, "evento")]//span[contains(@class, "badge")]');
  };

  this.contagemNaAba = function(valor){
    return world.waitForByXpath('//a[text()="Eventos"]//span[text()="'+valor+'"]');
  };

  this.removerEvento = function(){
    return world.waitForOverlayDisappear().then(function (){
      return world.getElement(''+eventAdded+' i.fa-trash').click();
    });
  };

  this.eventosRemovidos = function(){
    return world.waitForInverse(eventAdded);
  };

  this.clicarEmVisualizarDiagrama = function(){
    return world.getElement(''+eventAdded+' i.fa-eye').click();
  };

  this.verificarCorEvento = function(cor, evento) {
    var _this = this;
    var cssCor = _this.setCssAtributoCor(cor);
    var cssSelector = 'tr[data-evento^="evento"]:nth-child('+evento+') td.'+cssCor+'';
    return world.waitFor(cssSelector);
  };

  this.setCssAtributoCor = function(cor){
    switch(cor){
      case ('Vermelha'):
        return 'horarioColor1';
      case ('VerdeClara'):
        return 'horarioColor2';
      case ('Roxa'):
        return 'horarioColor3';
      case ('Azul'):
        return 'horarioColor4';
      case ('Laranja'):
        return 'horarioColor5';
      case ('Preto'):
        return 'horarioColor6';
      case ('VerdeEscuro'):
        return 'horarioColor7';
      case ('Branco'):
        return 'horarioColor8';
      case ('Rosa'):
        return 'horarioColor9';
      case ('Lilas'):
        return 'horarioColor10';
      case ('VermelhoClaro'):
        return 'horarioColor11';
      default:
        throw new Error('Cor: '+cor+' não encontrada.');
    }
  };
};

module.exports = TabelasHorariasPage;
