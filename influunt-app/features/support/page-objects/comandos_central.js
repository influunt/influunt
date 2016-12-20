'use strict';

var worldObj = require('../world');
var expect = require('chai').expect;
var world = new worldObj.World();

var ComandosCentral = function () {
  this.world = world;

  var selects = {
    'Transação':                       'select[name="transacao"]',
    'Plano':                           'select[name="eventoPlano"]',
    'Hora':                            'select[name="hora"]',
    'Minuto':                          'select[name="minuto"]',
    'Segundo':                         'select[name="segundo"]',
    'Duração':                         '[name="duracao"]'
  };

  this.verificaAnelSincronizado = function(anelCla) {
    return world.waitForOverlayDisappear()
      .then(function (){
        return world.getElementByXpath('//*[ancestor::tr[td[text()="'+anelCla+'"]]][3]').getText()
        .then(function (text) {
          expect(text).to.be.equal('Sincronizado');
      });
    });
  };

  this.selecionarAnelSincronizado = function(anelCla) {
    return world.waitForOverlayDisappear().then(function (){
      return world.getElementByXpath('//td[text()="'+anelCla+'"]/parent::tr//ins').click();
    });
  };

  this.clicarBotaoAcoes = function() {
    return world.getElementByXpath('//button[contains (@data-toggle, "modal")]').click();
  };

  this.clickForaModal = function() {
    return world.getElementByXpath('//div[contains(@class, "modal")][contains(@id, "modal-transacoes-distribuidas")]').click();
  };

  this.botaoStatus = function(status) {
    var desabilitado = '//button[contains (@disabled, "disabled")]';
    if (status === "habilitado") {
      return world.waitForByXpathInverse(desabilitado);
    } else {
      return world.waitForByXpath(desabilitado);
    }
  };

  this.selecionarValor = function(select, valor) {
    return world.waitForOverlayDisappear().then(function (){
      return world.selectByOptionAtribute('div', selects[select], 'value', valor);
    });
  };

  this.checkRadio = function(value) {
    return world.waitForOverlayDisappear().then(function (){
      return world.getElementByXpath('//input[contains(@value, "'+value+'")]/parent::div').click();
    });
  };

  this.getErrorMsgs = function(error) {
    return world.getElement('p[class*="error-msg"]').getText().then(function (text) {
      expect(text).to.be.equal(error);
    });
  };
};

module.exports = ComandosCentral;