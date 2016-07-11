'use strict';

var expect = require('chai').expect;
var AgrupamentosPage = require('../../support/page-objects/agrupamentos');

module.exports = function() {
  var agrupamentosPage = new AgrupamentosPage();

  this.Given(/^que exista ao menos um agrupamento cadastrado no sistema$/, function() {
    return agrupamentosPage.existeAoMenosUmAgrupamento();
  });

  this.Given(/^o usuário acessar a tela de listagem de agrupamentos$/, function() {
    return agrupamentosPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com os agrupamentos já cadastrados no sistema$/, function() {
    return agrupamentosPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(4);
    });
  });

  this.Given(/^clicar no botão de Novo Agrupamento$/, function(callback) {
    agrupamentosPage.clicarBotaoNovoAgrupamento();
    callback();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de cadastro de novos agrupamentos$/, function() {
    return agrupamentosPage.formAgrupamentos().then(function(form) {
      return expect(form).to.exist;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de novos agrupamentos$/, function() {
    return agrupamentosPage.newPage();
  });

  this.Given(/^o usuario selecionar o valor "([^"]*)" no campo Controladores$/, function (valor) {
    return agrupamentosPage.selecionarControlador(valor);
  });

  this.Given(/^o registro do agrupamento deverá ser salvo com nome igual a "([^"]*)"$/, function(nome) {
    return agrupamentosPage.textoExisteNaTabela(nome);
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de agrupamentos$/, function() {
    return agrupamentosPage.isIndex();
  });

  this.Given(/^clicar no botão de visualizar um agrupamento$/, function() {
    agrupamentosPage.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de agrupamentos$/, function() {
    return agrupamentosPage.isShow().then(function(resp) {
      return expect(resp).to.not.be.null;
    });
  });

  this.Given(/^clicar no botão de editar um agrupamento$/, function() {
    agrupamentosPage.clicarLinkComTexto('Editar');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição de agrupamentos$/, function() {
    return agrupamentosPage.textoFieldNomeAgrupamento().then(function(nome) {
      return expect(nome).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de agrupamentos$/, function() {
    agrupamentosPage.indexPage();
    return agrupamentosPage.clicarLinkComTexto('Editar');
  });

  this.Given(/^clicar no botão de excluir um agrupamento$/, function() {
    agrupamentosPage.clicarLinkComTexto('Excluir');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o agrupamento$/, function() {
    return agrupamentosPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^nenhum agrupamento deve ser excluído$/, function() {
    return agrupamentosPage.nenhumAgrupamentoDeveSerExcluido().then(function(res) {
      return expect(res).to.be.true;
    });
  });

  this.Given(/^o agrupamento deverá ser excluido$/, function() {
    // return agrupamentosPage.agrupamentoDeveSerExcluido().then(function(res) {
    //   expect(res).to.be.true
    // });
    return agrupamentosPage.toastMessage().then(function(text) {
      expect(text).to.match(/Removido com sucesso/);
    });
  });
};
