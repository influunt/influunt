'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var ObjetosComuns = function () {
  this.clicarLinkNovo = function() {
    return world.waitForOverlayDisappear().then(function (){
      return world.getElement('i[class="fa fa-plus"]').click();
    });
  };

  this.clicarLinkComTexto = function(texto) {
    return world.waitForOverlayDisappear().then(function (){
      return world.findLinkByText(texto).click();
    });
  };

  this.trocarAnel = function(numeroAnel) {
    var xpath = ('//li[contains(@aria-selected, "false")]//a[contains(text(), "Anel '+numeroAnel+'")]');
    return world.getElementByXpath(xpath).click();
  };

  this.limparCampo = function(campo) {
    return world.clearField(campo);
  };
};

module.exports = ObjetosComuns;
