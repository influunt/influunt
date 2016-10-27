'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var FinalizarControladorPage = function () {
  this.world = world;

  this.naoDeveMostrarBotao = function(tooltilText) {
    var xpath = xapthBotao(tooltilText);
    return world.waitForByXpathInverse(xpath);
  };

  this.deveMostrarBotao = function(tooltilText) {
    var xpath = xapthBotao(tooltilText);
    return world.waitForByXpath(xpath);
  };

  var xapthBotao = function(tooltilText) {
    return '//a[contains(@tooltip-template, "'+tooltilText+'")]';
  };
};

module.exports = FinalizarControladorPage;
