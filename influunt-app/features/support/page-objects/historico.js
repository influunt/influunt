'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var HistoricoPage = function () {

  this.isModalHistorico = function() {
    return world.waitForOverlayDisappear().then(function() {
      return world.getElementByXpath('//div[contains(@class, "modal")][contains(@id, "modal-timeline")][contains(@style, "display: block;")]');
    });
  };
};

module.exports = HistoricoPage;
