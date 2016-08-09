'use strict';

var expect = require('chai').expect;
var AreasPage = require('../../support/page-objects/areas');

module.exports = function() {
  var areasPage = new AreasPage();

  this.Given(/^que exista ao menos uma area cadastrada no sistema$/, { timeout: 15 * 1000 }, function() {
    return areasPage.existeAoMenosUmaArea();
  });

  this.Given(/^o usuário acessar a tela de listagem de areas$/, function() {
    return areasPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com as areas já cadastradas no sistema$/, function() {
    return areasPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(3);
    });
  });

  this.Given(/^clicar no botão de Nova Area$/, function(callback) {
    areasPage.clicarBotaoNovaArea();
    callback();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de Cadastro de novas Areas$/, function() {
    return areasPage.formAreas().then(function(form) {
      return expect(form).to.exist;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de novas areas$/, function() {
    return areasPage.newPage();
  });

  this.Given(/^o registro da área deverá ser salvo com número CTA igual a "([^"]*)"$/, function(numero) {
    return areasPage.textoExisteNaTabela(numero);
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de areas$/, function() {
    return areasPage.isIndex();
  });

  this.Given(/^clicar no botão de visualizar área$/, function() {
    areasPage.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de areas$/, function() {
    return areasPage.isShow().then(function(resp) {
      return expect(resp).to.not.be.null;
    });
  });

  this.Given(/^clicar no botão de editar area$/, function() {
    areasPage.clicarLinkComTexto('Editar');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição de areas$/, function() {
    return areasPage.textoFieldDescricaoArea().then(function(descricao) {
      return expect(descricao).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de areas$/, function() {
    areasPage.indexPage();
    return areasPage.clicarLinkComTexto('Editar');
  });

  this.Given(/^clicar no botão de excluir uma area$/, function() {
    areasPage.clicarLinkComTexto('Excluir');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a area$/, function() {
    return areasPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^a area deverá ser excluida$/, function() {
    return areasPage.toastMessage().then(function(text) {
      expect(text).to.match(/Removido com sucesso/);
    });
  });

  this.Given(/^nenhuma área deve ser excluída$/, function() {
    return areasPage.nenhumaAreaDeveSerExcluida().then(function(res) {
      return expect(res).to.be.true;
    });
  });

	this.Given(/^o sistema deverá indicar erro no campo "([^"]*)"$/, function (campo) {
    return areasPage.getErrorMessageFor(campo).then(function(result) {
      return expect(result).to.exist;
    });
	});

	this.Given(/^o usuario marcar a cidade como "([^"]*)"$/, function (cidade) {
		return areasPage.selecionarCidade(cidade);
	});
};
