'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var MapasPage = function () {
  this.world = world;
  var opcaoPanel = '//div[contains(@class, "open-acao-panel")]';

  this.indexMapa = function() {
    return world.waitForByXpath('//div[contains(@class, "influunt-map")]');
  };

  this.clicarAnelMapa = function(anel) {
    var _this = this;
    return _this.waitRender().then(function(){
      return world.getElementByXpath('//img[contains(@src, "images/leaflet/influunt-icons/anel.svg")]['+anel+']').click();
    });
  };

  this.openPlanos = function() {
    var _this = this;
    return _this.waitRender().then(function(){
      return world.getElementByXpath(''+opcaoPanel+'//h3[contains(text(), "5 Planos")]//i').click();
    });
  };

  this.clickButton = function(text){
    return world.getElementByXpath(''+opcaoPanel+'//button[contains(text(), "'+text+'")]').click();
  };

  this.clicarPlano = function(plano) {
    return world.getElementByXpath(''+opcaoPanel+'//a[contains(text(), "'+plano+'")]').click();
  };

  this.acaoPanelOpened = function() {
    return world.waitForByXpath(opcaoPanel);
  };

  this.checkValoresInPainel = function(valor) {
    return world.waitForByXpath(''+opcaoPanel+'//h3[contains(text(), "'+valor+'")]');
  };

  this.waitRender = function(){
    return world.sleep(800);
  };

  this.alertEnviarPlano = function() {
    return world.getTextInSweetAlert();
  };
};

module.exports = MapasPage;
