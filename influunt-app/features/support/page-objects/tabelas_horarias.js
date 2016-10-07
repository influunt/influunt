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
      return world.selectByValue(eventoAdd, selects[select], valor);
    });
  };

  this.enventoPossuiErro = function() {
    return world.waitForByXpath('//tr[contains(@data-ng-repeat, "evento")]//span[contains(@class, "badge")]');
  };

  this.contagemNaAba = function(valor){
    return world.waitForByXpath('//a[text()="Eventos"]//span[text()="'+valor+'"]');
  };

  this.removerEvento = function(){
    return world.getElement(''+eventAdded+' i.fa-trash').click();
  };

  this.eventosRemovidos = function(){
    return world.waitForInverse(eventAdded);
  };
};

module.exports = TabelasHorariasPage;
