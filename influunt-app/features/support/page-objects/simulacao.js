'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var SimulacaoPage = function () {
  this.world = world;

  this.indexSimulacao = function() {
    return world.waitForByXpath('//div[contains(@class, "influunt-map")]');
  };

  this.abrirInbox = function(text) {
    return world.getElementByXpath('//h5[contains(text(), "'+text+'")]//following-sibling::div//i').click();
  };

  this.clicarBotaoSimular = function() {
    return world.getElementByXpath('//i[contains(@class, "fa-play")]').click();
  };

  this.getErrorMessage = function(campo) {
    return world.waitFor('[name="'+campo+'"] + div + p[class*="error-msg"]').then(function() {
      return world.getElement('[name="'+campo+'"] + div + p[class*="error-msg"]').getText();
    });
  };

  this.removerFalhas = function() {
    return world.getElementByXpath('//a[contains(@data-ng-click, "removerFalhaControlador")]').click();
  };

  this.selectBySelectOptionLabelAtribute = function(campo, valor) {
    var _this = this;
    return _this.selectorBy('div',campo, valor, 'label');
  };

  this.selectBySelectOptionValueAtribute = function(campo, valor) {
    var _this = this;
    var div = 'div [data-ng-show="alarmeControlador.alarme"]';
    return _this.selectorBy(div, campo, valor, 'value');
  };

  this.selectorBy = function(div, campo, valor, typeAttribute) {
    return world.sleep(300).then(function(){
      return world.selectByOptionAtribute(div, 'select[name="'+campo+'"]', typeAttribute, valor);
    });
  };

  this.canvasOpened = function() {
    return world.sleep(300).then(function(){
      return world.waitForByXpath('//canvas');
    });
  };
};

module.exports = SimulacaoPage;
