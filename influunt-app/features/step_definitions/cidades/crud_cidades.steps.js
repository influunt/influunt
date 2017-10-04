'use strict';

var expect = require('chai').expect;
var CidadesPage = require('../../support/page-objects/cidades');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');

module.exports = function() {
  var cidadesPage = new CidadesPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^o usuário acessar a tela de listagem de cidades$/, function() {
    return cidadesPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com as cidades já cadastradas no sistema$/, function() {
    return cidadesPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(2);
    });
  });

  this.Given(/^clicar no botão de Nova Cidade$/, function() {
    return objetosComuns.clicarLinkNovo();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de cadastro de novas cidades$/, function() {
    return cidadesPage.fieldNomeCidade().then(function(field) {
      return expect(field).to.exist;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de novas cidades$/, function() {
    return cidadesPage.newPage();
  });

  this.Given(/^clicar no botão de salvar$/, function () {
    return cidadesPage.clicarBotaoSalvar();
  });

  this.Given(/^o registro da cidade deverá ser salvo com nome igual a "([^"]*)"$/, function (nome) {
    return cidadesPage.textoExisteNaTabela(nome);
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de cidades$/, function() {
    return cidadesPage.getPageTitleH2().then(function(title) {
      expect(title).to.equal('Cidades');
    });
  });

  this.Given(/^clicar no botão de visualizar cidade$/, function() {
    objetosComuns.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^clicar no botão de editar cidade$/, function() {
    objetosComuns.clicarLinkComTexto('Editar');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição cidades$/, function() {
    return cidadesPage.textoFieldNomeCidade().then(function(fieldValue) {
      return expect(fieldValue).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de cidades$/, function() {
    cidadesPage.indexPage();
    return objetosComuns.clicarLinkComTexto('Editar');
  });

  this.Given(/^clicar no botão de excluir uma cidade$/, function() {
    objetosComuns.clicarLinkComTexto('Excluir');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a cidade$/, function() {
    return cidadesPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^a cidade deverá ser excluida$/, function() {
    return cidadesPage.toastMessage().then(function(text) {
      expect(text).to.match(/Removido com sucesso/);
    });
  });

  this.Given(/^o usuário responde não$/, function() {
    return cidadesPage.clicarNaoConfirmacaoApagarRegistro();
  });

  this.Given(/^nenhuma cidade deve ser excluída$/, function() {
    return cidadesPage.nenhumaCidadeDeveSerExcluida().then(function(res) {
      return expect(res).to.be.true;
    });
  });

	this.Given(/^que exista uma cidade cadastrada no sistema com o nome "([^"]*)"$/, function (nome) {
	  return cidadesPage.existeAoMenosUmaCidadeComNome(nome);
	});
};
