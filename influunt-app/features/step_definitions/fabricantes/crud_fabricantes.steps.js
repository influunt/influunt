'use strict';

var expect = require('chai').expect;
var FabricantesPages = require('../../support/page-objects/fabricantes');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');


module.exports = function() {
  var fabricantesPage = new FabricantesPages();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^que exista ao menos um fabricante cadastrado no sistema$/, { timeout: 15 * 1000 }, function() {
    return fabricantesPage.existeAoMenosUmFabricante();
  });

  this.Given(/^o usuário acessar a tela de listagem de fabricantes$/, function() {
    return fabricantesPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com os fabricantes já cadastrados no sistema$/, function() {
    return fabricantesPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(3);
    });
  });

  this.Given(/^clicar no botão de Novo Fabricante$/, function() {
    return objetosComuns.clicarLinkNovo();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de Cadastro de novo fabricante$/, function() {
    return fabricantesPage.fieldNomeFabricante().then(function(field) {
      return expect(field).to.exist;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de novos fabricantes$/, function() {
    return fabricantesPage.newPage();
  });

  this.Given(/^o registro do fabricante deverá ser salvo com "([^"]*)" igual a "([^"]*)"$/, function (campo, valor) {
    return fabricantesPage.textoExisteNaTabela(valor);
  });

  this.Given(/^clicar no botão de adicionar um novo modelo de controlador$/, function () {
    return  fabricantesPage.clicarBotaoNovoModelo();
  });

  this.Given(/^preencher a descrição do modelo com "([^"]*)"$/, function (descModelo) {
    return fabricantesPage.preencherDescricaoModelo(descModelo);
  });

  this.Given(/^selecionar a configuração "([^"]*)"$/, function (config) {
    return fabricantesPage.selecionarConfiguracao(config);
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de fabricantes$/, function() {
    return fabricantesPage.getPageTitleH2().then(function(title) {
      expect(title).to.equal('Fabricantes');
    });
  });

  this.Given(/^clicar no botão de visualizar fabricante$/, function() {
    objetosComuns.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de fabricantes$/, function() {
    return fabricantesPage.isIndex();
  });

  this.Given(/^clicar no botão de editar fabricante$/, function() {
    objetosComuns.clicarLinkComTexto('Editar');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição fabricantes$/, function() {
    return fabricantesPage.textoFieldNomeFabricante().then(function(fieldValue) {
      return expect(fieldValue).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de fabricantes$/, function() {
    fabricantesPage.indexPage();
    return objetosComuns.clicarLinkComTexto('Editar');
  });

  this.Given(/^clicar no botão de excluir um fabricante$/, function() {
    objetosComuns.clicarLinkComTexto('Excluir');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o fabricante$/, function() {
    return fabricantesPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^o fabricante deverá ser excluido$/, function() {
    return fabricantesPage.toastMessage().then(function(text) {
      expect(text).to.match(/Removido com sucesso/);
    });
  });

  this.Given(/^nenhum fabricante deve ser excluído$/, function() {
    return fabricantesPage.nenhumFabricanteDeveSerExcluido().then(function(res) {
      return expect(res).to.be.true;
    });
  });

  this.Given(/^preenche os campos do modelo controlador corretamente$/, function (callback) {
    fabricantesPage.fillFabricanteFormFull('Teste Cadastro Fabricante');
    callback(null, 'pending');
  });

  this.Given(/^preenche todos os campos do formulário$/, function () {
    return fabricantesPage.fillFabricanteFormFull('Teste Cadastro Fabricante');
  });

};
