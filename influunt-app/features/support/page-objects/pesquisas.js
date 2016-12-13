'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var PesquisasPage = function () {
  this.world = world;

  this.clicarPesquisar = function() {
    return world.waitForOverlayDisappear().then(function (){
      return world.waitToggle().then(function(){
        return world.waitForToastWarningDisapear().then(function () {
          return world.getElementByXpath('//i[contains(@class, "fa-search")]').click();
        });
      });
    });
  };

  this.preencherCampo = function(campo, valor) {
    var xpath = '//label[contains(text(), "'+campo+'")]//following-sibling::div[1]//following-sibling::div[2]//input';
    return world.waitToggle().then(function(){
      return world.waitForToastWarningDisapear().then(function () {
        return world.setValueByXpath(xpath, valor);
      });
    });
  };

  this.clicarBotaoPesquisar = function(){
    return world.waitToggle().then(function(){
      return world.waitForToastWarningDisapear().then(function () {
        return world.getElementByXpath('//input[contains(@class, "botao-pesquisar")]').click();
      });
    });
  };

  this.clicarBotaoLimpar = function(){
    return world.waitToggle().then(function(){
      return world.waitForToastWarningDisapear().then(function () {
        return world.getElementByXpath('//input[contains(@value, "Limpar pesquisa")]').click();
      });
    });
  };

  this.setarData = function(campo, valor){
    var xpath = '//input[contains(@data-ng-model, "ngModel[campo.nome].'+campo+'")]';
    return world.waitForOverlayDisappear().then(function (){
      return world.waitToggle().then(function(){
        return world.waitForToastWarningDisapear().then(function () {
          return world.setValueByXpath(xpath, valor);
        });
      });
    });
  };

  this.selecionarStatus = function(valor) {
    return world.waitForOverlayDisappear().then(function (){
      return world.waitToggle().then(function(){
        return world.waitForToastWarningDisapear().then(function () {
          return world.selectByOptionAtribute('div', 'select[ng-model="ngModel[campo.nome].valor"]', 'value', valor);
        });
      });
    });
  };

  this.selecionarPeloCampo = function(campo, valor){
    var xpathOptionSelect = '//label[contains(text(), "'+campo+'")]//following-sibling::div[1]//following-sibling::div[1]//select//option[contains(@value, "'+valor+'")]';

    return world.waitToggle().then(function(){
      return world.waitForToastWarningDisapear().then(function () {
        return world.getElementByXpath(xpathOptionSelect).click();
      });
    });
  };
};

module.exports = PesquisasPage;
