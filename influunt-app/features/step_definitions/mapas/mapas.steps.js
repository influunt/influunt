'use strict';

var MapasPage = require('../../support/page-objects/mapas');
var expect = require('chai').expect;

module.exports = function() {
  var mapasPage = new MapasPage();

  this.Given(/^o sistema deve redirecionar para o mapa$/, { timeout: 15 * 1000 }, function() {
    return mapasPage.indexMapa();
  });

  this.Given(/^o usuário clicar no anel "([^"]*)" no mapa$/, function(anel) {
    return mapasPage.clicarAnelMapa(anel);
  });

  this.Given(/^painel com opções deverá aparecer$/, function() {
    return mapasPage.acaoPanelOpened();
  });

  this.Given(/^painel ação deve conter "([^"]*)"$/, function(valor) {
    return mapasPage.checkValoresInPainel(valor);
  });

  this.Given(/^o painel com opções esteja aberto$/, function() {
    return mapasPage.acaoPanelOpened();
  });

  this.Given(/^o usuário clicar para abrir os planos$/, function() {
    return mapasPage.openPlanos();
  });

  this.Given(/^o usuário clicar no plano "([^"]*)"$/, function(plano) {
    return mapasPage.clicarPlano(plano);
  });

  this.Given(/^no painel clicar no botão "([^"]*)"$/, function(botao) {
    return mapasPage.clickButton(botao);
  });

  this.Given(/^o sistema deve mostrar um alert para o usuário com a mensagem "([^"]*)"$/, function (msg) {
    return mapasPage.alertEnviarPlano().then(function(text) {
      expect(text).to.equal(msg);
    });
  });
};
