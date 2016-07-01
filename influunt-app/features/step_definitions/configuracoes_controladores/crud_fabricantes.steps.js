'use strict';

var expect = require('chai').expect;
var ConfiguracoesPage = require('../../support/page-objects/configuracoes_controladores');

module.exports = function() {
  var configPage = new ConfiguracoesPage();

  this.Given(/^o usuário acessar a tela de cadastro de configurações de controladores$/, function () {
    return configPage.newPage();
  });

  this.Given(/^preencher os campos da configuração corretamente$/, function () {
    return configPage.fillForm('Descrição Config');
  });

  this.Given(/^o registro da configuração deverá ser salvo com sucesso$/, function () {
    return configPage.textoExisteNaTabela('Descrição Config');
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de configurações de controladores$/, function () {
    return configPage.getUrl().then(function(url) {
      expect(url).to.match(/\/app\/configuracoes_controladores\/?$/);
    });
  });

}
