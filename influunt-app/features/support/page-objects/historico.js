'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var HistoricoPage = function () {

  this.isModalHistorico = function() {
    return world.waitForOverlayDisappear().then(function() {
      return world.getElementByXpath('//div[contains(@id, "modal-timeline")][contains(@style, "display: block;")]');
    });
  };

  this.isTabelaHorariaDiff = function() {
    return world.waitForOverlayDisappear().then(function() {
      return world.getElementByXpath('//div[contains(@id, "page-wrapper")][contains(@class, "page-tabelas_horarias_diff")]');
    });
  };

  this.checkEventosAlteradosTabelaHoraria = function(dia, estado) {
    return world.waitForOverlayDisappear().then(function() {
      return world.getElementByXpath('//span[contains(text(), "'+dia+'")]//ancestor::tr[contains(@class, "'+estado+'")]');
    });
  };

  this.clicarVerAltercoes = function() {
    return world.sleep(1000).then(function() {
      return world.getElementByXpath('//a[contains(@data-ng-click, "closeTimelineModal()")]').click();
    });
  };
};

module.exports = HistoricoPage;
