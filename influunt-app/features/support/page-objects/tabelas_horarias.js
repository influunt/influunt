'use strict';

var worldObj = require('../world');

var world = new worldObj.World();

var TabelasHorariasPage = function () {
  var campos = {
    'Dias':                            '[name="eventoDiaDaSemana"]',
    'Hora':                            '[name="eventoHora"]',
    'Minuto':                          '[name="eventoMinuto"]',
    'Segundo':                         '[name="eventoSegundo"]',
    'Plano':                           '[name="eventoPlano"]'
  };

  this.isTabelaHoraria = function() {
    return world.waitForByXpath('//ng-include[contains(@src, "views/tabela_horarios/tabs-eventos.html")]');
  };

  this.selecionarValor = function(campo, valor) {
    return world.selectOption(campos[campo], valor);
  };

  this.enventoPossuiErro = function() {
    return world.waitForByXpath('//tr[contains(@data-ng-repeat, "evento")]//span[contains(@class, "badge")]');
  };
};

module.exports = TabelasHorariasPage;
