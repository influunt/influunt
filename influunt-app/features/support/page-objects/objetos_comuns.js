'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var ObjetosComuns = function () {
  this.cadastrarControlador = function() {
    return world.execSqlScript('features/support/scripts/planos/controlador.sql');
  };

  this.cadastrarPlanoParaControlador = function() {
    return world.execSqlScript('features/support/scripts/controladores/plano_controlador.sql');
  };

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

  this.errosImpeditivos = function(texto){
    return world.waitForByXpath('//div[contains (@class, "alert")]//li[contains(text(), "'+texto+'")]');
  };

  this.textoConfirmacaoApagarRegistro = function() {
    return world.getTextInSweetAlert();
  };

  this.clicarSimConfirmacaoApagarRegistro = function() {
    return world.waitFor('div[class^="sweet-alert"][class$="visible"]').then(function() {
      return world.clickButton('div[class^="sweet-alert"] button.confirm');
    });
  };
};

module.exports = ObjetosComuns;
