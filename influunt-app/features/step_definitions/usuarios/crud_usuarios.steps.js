'use strict';

var expect = require('chai').expect;
var UsuariosPage = require('../../support/page-objects/usuarios');

module.exports = function() {
  var usuariosPage = new UsuariosPage();

  this.Given(/^que exista ao menos um usuário cadastrado no sistema$/, function(callback) {
    // return usuariosPage.existeAoMenosUmUsuario();
    callback();
  });

  this.Given(/^o usuário acessar a tela de listagem de usuários$/, function() {
    return usuariosPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com os usuários já cadastrados no sistema$/, function() {
    return usuariosPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(4);
    });
  });

  this.Given(/^clicar no botão de Novo Usuário$/, function() {
    return usuariosPage.clicarBotaoNovoUsuario();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de Cadastro de novos Usuários$/, function() {
    return usuariosPage.formUsuario().then(function(form) {
      return expect(form).to.exist;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de novos usuários$/, function() {
    return usuariosPage.newPage();
  });

  this.Given(/^o registro do usuário deverá ser salvo com nome igual a "([^"]*)"$/, function(nome) {
    return usuariosPage.textoExisteNaTabela(nome);
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de usuários$/, function() {
    return usuariosPage.isIndex();
  });

  this.Given(/^clicar no botão de visualizar um usuário$/, function() {
    usuariosPage.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de usuários$/, function() {
    return usuariosPage.isShow().then(function(resp) {
      return expect(resp).to.not.be.null;
    });
  });

  this.Given(/^clicar no botão de editar usuário$/, function() {
    usuariosPage.clicarLinkComTexto('Editar');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição de usuários$/, function() {
    return usuariosPage.textoFieldNomeUsuario().then(function(nome) {
      return expect(nome).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de usuários$/, function() {
    return usuariosPage.indexPage().then(function() {
      return usuariosPage.clicarUltimoLinkComTexto('Editar');
    });
  });

  this.Given(/^clicar no botão de excluir um usuário$/, function() {
    return usuariosPage.clicarUltimoLinkComTexto('Excluir');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o usuário$/, function() {
    return usuariosPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^nenhum usuário deve ser excluído$/, function() {
    return usuariosPage.nenhumUsuarioDeveSerExcluido().then(function(res) {
      return expect(res).to.be.true;
    });
  });

  this.Given(/^o usuário deverá ser excluido$/, function() {
    return usuariosPage.toastMessage().then(function(text) {
      expect(text).to.match(/Removido com sucesso/);
    });
  });
};
