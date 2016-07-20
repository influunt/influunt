'use strict';

var expect = require('chai').expect;
var PerfisPage = require('../../support/page-objects/perfis');

module.exports = function() {
  var perfisPage = new PerfisPage();

  this.Given(/^que exista ao menos um perfil cadastrado no sistema$/, function() {
    return perfisPage.existeAoMenosUmPerfil();
  });

  this.Given(/^o usuário acessar a tela de listagem de perfis$/, function() {
    return perfisPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com os perfis já cadastrados no sistema$/, function() {
    return perfisPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(2);
    });
  });

  this.Given(/^clicar no botão de Novo Perfil$/, function(callback) {
    perfisPage.clicarBotaoNovoPerfil();
    callback();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de Cadastro de novos Perfis$/, function() {
    return perfisPage.formPerfis().then(function(form) {
      return expect(form).to.exist;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de novos perfis$/, function() {
    return perfisPage.newPage();
  });

  this.Given(/^o registro do perfil deverá ser salvo com nome igual a "([^"]*)"$/, function(nome) {
    return perfisPage.textoExisteNaTabela(nome);
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de perfis$/, function() {
    return perfisPage.isIndex();
  });

  this.Given(/^clicar no botão de visualizar perfil$/, function() {
    perfisPage.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de perfis$/, function() {
    return perfisPage.isShow().then(function(resp) {
      return expect(resp).to.not.be.null;
    });
  });

  this.Given(/^clicar no botão de editar perfil$/, function() {
    perfisPage.clicarLinkComTexto('Editar');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição de perfis$/, function() {
    return perfisPage.textoFieldNomePerfil().then(function(nome) {
      return expect(nome).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de perfis$/, function() {
    return perfisPage.indexPage().then(function() {
      return perfisPage.clicarLinkComTexto('Editar');
    });
  });

  this.Given(/^clicar no botão de excluir um perfil$/, function() {
    perfisPage.clicarLinkComTexto('Excluir');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o perfil$/, function() {
    return perfisPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^nenhum perfil deve ser excluído$/, function() {
    return perfisPage.nenhumPerfilDeveSerExcluido().then(function(res) {
      return expect(res).to.be.true;
    });
  });

  this.Given(/^o perfil deverá ser excluido$/, function() {
    return perfisPage.toastMessage().then(function(text) {
      expect(text).to.match(/Removido com sucesso/);
    });
  });
};
