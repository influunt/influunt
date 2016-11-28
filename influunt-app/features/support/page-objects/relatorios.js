'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var RelatoriosPage = function () {
  this.world = world;

  this.visitarRelatorio = function(local) {
      return world.visit('/app/relatorios/'+local+'');
  };

  this.controladorComFalhaNaListagem = function() {
      return world.waitForByXpath('//td[contains(text(), "Em falha")]');
  };
};
module.exports = RelatoriosPage;
