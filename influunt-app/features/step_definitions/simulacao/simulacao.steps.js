'use strict';

var SimulacaoPage = require('../../support/page-objects/simulacao');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');
var ResumoControladorPage = require('../../support/page-objects/resumo_controladores');

var expect = require('chai').expect;

module.exports = function() {
  var simulacaoPage = new SimulacaoPage();
  var objetosComuns = new ObjetosComuns();
  var resumo = new ResumoControladorPage();

  this.Given(/^usuário clicar em abrir "([^"]*)"$/, function(texto) {
    return simulacaoPage.abrirInbox(texto);
  });

  this.Given(/^usuário clicar em simular$/, function() {
    return simulacaoPage.clicarBotaoSimular();
  });

  this.Given(/^o sistema deverá apresentar erro no campo "([^"]*)"$/, function(campo) {
    return simulacaoPage.getErrorMessage(campo).then(function(result) {
      return expect(result).to.exist;
    });
  });

  this.Given(/^o usuário remova falha da simulação$/, function() {
    return simulacaoPage.removerFalhas();
  });

  this.Given(/^o usuário no campo "([^"]*)" selecionar o valor "([^"]*)" para o label$/, function(campo, valor) {
    return simulacaoPage.selectBySelectOptionLabelAtribute(campo, valor);
  });

  this.Given(/^o usuário no campo "([^"]*)" selecionar o valor "([^"]*)"$/, function(campo, valor) {
    return simulacaoPage.selectBySelectOptionValueAtribute(campo, valor);
  });

  this.Given(/^o simulador deverá aparecer$/, function() {
    return simulacaoPage.canvasOpened();
  });

  this.Given(/^o sistema deverá mostrar as informações iniciais do controlador 1 e anel 1$/, function () {
    var informacoes = {
      'Logradouro': 'Av. Paulista com R. Pamplona',
      'CLA': '1.000.0001.1',
      'SMEE': '123',
      'gsp': '1',
      'gsv': '2',
      'gdp': '1',
      'gdv': '2'
    };
    return resumo.informacaoBasicaAnel(informacoes);
  });
};
