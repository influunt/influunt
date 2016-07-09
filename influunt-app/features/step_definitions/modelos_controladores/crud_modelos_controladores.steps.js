'use strict';

var expect = require('chai').expect;
var ModelosControladoresPage = require('../../support/page-objects/modelos_controladores');

module.exports = function() {
  var modelosPage = new ModelosControladoresPage();

  this.Given(/^que exista ao menos um modelo de controlador cadastrado no sistema$/, function() {
    return modelosPage.existeAoMenosUmModeloDeControlador();
  });

  this.Given(/^o usuário acessar a tela de listagem de modelos de controladores$/, function() {
    return modelosPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com os modelos de controladores já cadastrados no sistema$/, function() {
    return modelosPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(4);
    });
  });

  this.Given(/^clicar no botão de Novo Modelo de Controlador$/, function() {
    return modelosPage.clicarBotaoNovoModelo();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de cadastro de novo modelo de controlador$/, function() {
    return modelosPage.fieldDescricaoModelo().then(function(field) {
      return expect(field).to.exist;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de novos modelos de controladores$/, function() {
    return modelosPage.newPage();
  });

  this.Given(/^o registro do modelo deverá ser salvo com descricao "([^"]*)"$/, function(descricao) {
    return modelosPage.textoExisteNaTabela(descricao);
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de modelos de controladores$/, function() {
    return modelosPage.isIndex();
  });

  this.Given(/^clicar no botão de visualizar um modelo de controlador$/, function() {
    return modelosPage.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de modelos de controladores$/, function() {
    return modelosPage.modeloIdH5().then(function(modeloId) {
      expect(modeloId).to.match(/- #/);
    });
  });

  this.Given(/^clicar no botão de editar um modelo de controlador$/, function() {
    return modelosPage.clicarLinkComTexto('Editar');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição modelos de controladores$/, function() {
    return modelosPage.textoFieldDescricaoModelo().then(function(fieldValue) {
      return expect(fieldValue).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de modelos de controladores$/, function() {
    return modelosPage.indexPage().then(function() {
      return modelosPage.clicarLinkComTexto('Editar');
    });
  });

  this.Given(/^clicar no botão de excluir um modelo de controlador$/, function() {
    return modelosPage.clicarLinkComTexto('Excluir');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação perguntando se o usuário quer mesmo excluir o modelo$/, function() {
    return modelosPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^nenhum modelo deve ser excluído$/, function() {
    return modelosPage.nenhumModeloDeveSerExcluido().then(function(res) {
      return expect(res).to.be.true;
    });
  });

  this.Given(/^o modelo deverá ser excluido$/, function() {
    return modelosPage.toastMessage().then(function(text) {
      return expect(text).to.match(/Removido com sucesso/);
    });
  });

};
