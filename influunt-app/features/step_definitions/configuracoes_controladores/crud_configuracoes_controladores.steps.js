'use strict';

var expect = require('chai').expect;
var ConfiguracoesPage = require('../../support/page-objects/configuracoes_controladores');

module.exports = function () {
  var configPage = new ConfiguracoesPage();

  this.Given(/^que exista ao menos uma configuração de controlador cadastrado no sistema$/, function () {
    return configPage.existeAoMenosUmaConfiguracao();
  });

  this.Given(/^o usuário acessar a tela de listagem de configurações de controladores$/, function () {
    return configPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com os configurações de controladores já cadastrados no sistema$/, function () {
    return configPage.getItensTabela().then(function (itens) {
      expect(itens).to.have.length.at.least(4);
    });
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de Cadastro de novo configuração controlador$/, function () {
    return configPage.formConfiguracao().then(function (form) {
      return expect(form).to.exist;
    });
  });

  this.Given(/^clicar no botão de Nova configuração controlador$/, function () {
    return configPage.clicarBotaoNovaConfiguracao();
  });

  this.Given(/^clicar no botão de visualizar configuração controlador$/, function () {
    return configPage.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de configurações de controladores$/, function () {
    return configPage.isShow().then(function (resp) {
      return expect(resp).to.not.be.null;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de configurações de controladores$/, function () {
    return configPage.newPage();
  });

  this.Given(/^clicar no botão de editar configuração controlador$/, function () {
    return configPage.clicarLinkComTexto('Editar');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição configurações de controladores$/, function () {
    return configPage.textoFieldDescricaoConfiguracao().then(function (descricao) {
      return expect(descricao).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de configurações de controladores$/, function () {
    return configPage.textoFieldDescricaoConfiguracao().then(function (descricao) {
      return expect(descricao).to.not.be.empty;
    });
  });

  this.Given(/^o registro da configuração controlador deverá ser salvo com descrição "([^"]*)"$/, function (descricao) {
    return configPage.textoExisteNaTabela(descricao);
  });

  this.Given(/^preencher o campo "([^"]*)" com "([^"]*)"$/, function(campo, valor) {
    return configPage.preencherCampo(campo, valor);
  });

  this.Given(/^clicar no botão de excluir um configuração controlador$/, function () {
    return configPage.clicarLinkComTexto('Excluir');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o configuração controlador$/, function () {
    return configPage.textoConfirmacaoApagarRegistro().then(function(text) {
      return expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^a configuração controlador deverá ser excluido$/, function () {
    return configPage.toastMessage().then(function(text) {
      return expect(text).to.match(/Removido com sucesso/);
    });
  });

  this.Given(/^nenhuma configuração do controlador deve ser excluída$/, function () {
    return configPage.nenhumaConfiguracaoDeveSerExcluida().then(function(res) {
      return expect(res).to.be.true;
    });
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de configurações de controladores$/, function () {
    return configPage.getIndexUrl().then(function (url) {
      expect(url).to.match(/\/app\/configuracoes_controladores\/?$/);
    });
  });

};
