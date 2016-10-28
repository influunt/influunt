'use strict';

var PesquisasPage = require('../../support/page-objects/pesquisas');

module.exports = function() {
  var pesquisasPage = new PesquisasPage();

  this.Given(/^o usuário clicar em pesquisar$/, function() {
    return pesquisasPage.clicarPesquisar();
  });

  this.Given(/^o usuário selecionar o status "([^"]*)"$/, function(valor) {
    return pesquisasPage.selecionarStatus(valor);
  });

  this.Given(/^o usuário clicar no botão pesquisar$/, function() {
    return pesquisasPage.clicarBotaoPesquisar();
  });

  this.Given(/^o usuário clicar em limpar a pesquisa$/, function() {
    return pesquisasPage.clicarBotaoLimpar();
  });

  this.Given(/^preencher o campo "([^"]*)" com "([^"]*)"$/, function(campo, valor) {
    return pesquisasPage.preencherCampo(campo, valor);
  });

  this.Given(/^preencher o campo data inicial com "([^"]*)"$/, function(valor) {
    var campo = 'start';
    return pesquisasPage.setarData(campo, valor);
  });

  this.Given(/^preencher o campo data final com "([^"]*)"$/, function(valor) {
    var campo = 'end';
    return pesquisasPage.setarData(campo, valor);
  });

  this.Given(/^selecionar igual para "([^"]*)"$/, function(campo) {
    var valor = 'eq';
    return pesquisasPage.selecionarPeloCampo(campo, valor);
  });
};
