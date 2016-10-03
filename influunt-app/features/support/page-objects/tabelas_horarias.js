'use strict';

var worldObj = require('../world');

var world = new worldObj.World();

var TabelasHorariasPage = function () {

  this.cadastrarControlador = function() {
    return world.execSqlScript('features/support/scripts/planos/controlador.sql');
  };

  this.clicarBotao = function(button) {
    return world.waitForOverlayDisappear().then(function() {
      return world.findLinkByText(button).click();
    });
  };

  this.isTabelaHoraria = function() {
    return world.waitForByXpath('//ng-include[contains(@src, "views/tabela_horarios/tabs-eventos.html")]');
  };
};

module.exports = TabelasHorariasPage;
