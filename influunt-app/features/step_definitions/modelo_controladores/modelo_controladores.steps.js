'use strict';

var expect = require('chai').expect;
var ModelosPage = require('../../support/page-objects/modelo_controladores');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');
var worldObj = require('../../support/world');

module.exports = function() {
  var modelosPage = new ModelosPage();
  var objetosComuns = new ObjetosComuns();
  var world = new worldObj.World();

  this.Given(/^que tenha um fabricante cadastrado$/, function() {
    return modelosPage.cadastrarFabricante();
  });

  this.Given(/^que o usuário esteja na tela de listagem de modelos$/, function() {
    return modelosPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com o modelo já cadastrado no sistema$/, function() {
    return modelosPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(1);
    });
  });

  this.Given(/^clicar no botão de Novo Modelo$/, function() {
    return objetosComuns.clicarLinkNovo();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de cadastro de novos modelos$/, function() {
    return modelosPage.formModelos().then(function(form) {
      return expect(form).to.exist;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de novos modelos$/, function() {
    return modelosPage.newPage();
  });

  this.Given(/^o sistema deverá permanecer no form$/, function() {
    return modelosPage.newPage();
  });

  this.Given(/^o registro do modelo deverá ser salvo com nome igual a "([^"]*)"$/, function(nome) {
    return modelosPage.textoExisteNaTabela(nome);
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de modelos$/, function() {
    return modelosPage.isIndex();
  });

  this.Given(/^clicar no botão de visualizar um modelo$/, function() {
    objetosComuns.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de modelos$/, function() {
    return modelosPage.isShow().then(function(resp) {
      return expect(resp).to.not.be.null;
    });
  });

  this.Given(/^clicar no botão "([^"]*)" do "([^"]*)"$/, function(botao, modelo) {
    modelosPage.clicarNoBotaoPorModelo(modelo, botao);
  });

  this.Given(/^o usuário limpe o campo Descrição$/, function() {
    modelosPage.limparCampoDescricao();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição de modelos$/, function() {
    return modelosPage.textoFieldDescricaoModelo().then(function(descricao) {
      return expect(descricao).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de modelos$/, function() {
    modelosPage.indexPage();
    return objetosComuns.clicarLinkComTexto('Editar');
  });

  this.Given(/^nenhum modelo deve ser excluído$/, function() {
    return modelosPage.nenhumModeloDeveSerExcluido().then(function(res) {
      return expect(res).to.be.true;
    });
  });
};
