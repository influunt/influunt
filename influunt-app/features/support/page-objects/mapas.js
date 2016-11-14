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
    return world.sleep(1000).then(function(){
      return world.getElementByXpath('//img[contains(@src, "images/leaflet/influunt-icons/anel.svg")]['+anel+']').click();
    });
  };

  this.openPlanos = function() {
    return world.sleep(500).then(function(){
      return world.getElementByXpath(''+opcaoPanel+'//h3[contains(text(), "5 Planos")]//i').click();
    });
  };

  this.clickButton = function(text){
    return world.getElementByXpath(''+opcaoPanel+'//button[contains(text(), "'+text+'")]').click();
  };

  this.clicarPlano = function(plano) {
    return world.getElementByXpath(''+opcaoPanel+'//a[contains(text(), "'+plano+'")]').click();
  };

  this.clicarMenuFiltros = function() {
    return world.sleep(500).then(function(){
      return world.getElementByXpath('//i[contains(@class, "fa-map")]').click();
    });
  };

  this.acaoPanelOpened = function() {
    return world.waitForByXpath(opcaoPanel);
  };

  this.filterPanelOpened = function() {
    return world.waitForByXpath('//div[contains(@class, "theme-config-box open")]');
  };

  this.checkValoresInPainel = function(valor) {
    return world.waitForByXpath(''+opcaoPanel+'//h3[contains(text(), "'+valor+'")]');
  };

  this.checkAgrupamentoMapa = function() {
    return world.waitFor('path[class^="influunt-agrupamento"]');
  };

  this.clickOpcoesFiltro = function(text){
    return world.sleep(500).then(function(){
      return world.getElementByXpath('//*[ancestor::div[span[text()="'+text+'"]]][1]').click();
    });
  };

  this.checkPointsOnMapa = function(tipoIcone, quantidade) {
    return world.sleep(500).then(function(){
      return world.calculateByXpath('//img[contains(@src, "images/leaflet/influunt-icons/'+tipoIcone+'.svg")]', quantidade);
    });
  };

  this.checkAgrupamentoMapa = function(numAgrupamento) {
    return world.waitForOverlayDisappear();
    return world.sleep(500).then(function(){
      return world.waitForByXpath('//div[contains(@class, "marker-cluster")]//span[contains(text(), "'+numAgrupamento+'")]');
    });
  };

  this.clicarAgrupamentoMapa = function(numAgrupamento) {
    return world.sleep(500).then(function(){
      return world.getElementByXpath('//div[contains(@class, "marker-cluster")]//span[contains(text(), "'+numAgrupamento+'")]').click();
    });
  };

  this.alertEnviarPlano = function() {
    return world.sleep(500).then(function(){
      return world.getTextInSweetAlert();
    });
  };
};

module.exports = MapasPage;
