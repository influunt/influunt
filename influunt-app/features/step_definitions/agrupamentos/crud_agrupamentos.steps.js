'use strict';

var expect = require('chai').expect;
var AgrupamentosPage = require('../../support/page-objects/agrupamentos');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');

module.exports = function() {
  var agrupamentosPage = new AgrupamentosPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^o usuário em evento selecionar o valor "([^"]*)" no campo "([^"]*)"$/, function (valor, select) {
    return agrupamentosPage.selecionarValor(valor, select);
  });

  this.Given(/^o usuário em evento selecionar o valor plano "([^"]*)" no campo "([^"]*)"$/, function (valor, select) {
    return agrupamentosPage.selecionarValor(valor, select);
  });

  this.Given(/^deve ser exibida uma lista com os agrupamentos já cadastrados no sistema$/, function() {
    return agrupamentosPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(4);
    });
  });

  this.Given(/^clicar no botão de Novo Agrupamento$/, function() {
    return objetosComuns.clicarLinkNovo();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de cadastro de novos agrupamentos$/, function() {
    return agrupamentosPage.formAgrupamentos().then(function(form) {
      return expect(form).to.exist;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de novos agrupamentos$/, function() {
    return agrupamentosPage.newPage();
  });

  this.Given(/^o registro do agrupamento deverá ser salvo com nome igual a "([^"]*)"$/, function(nome) {
    return agrupamentosPage.textoExisteNaTabela(nome);
  });

  this.Given(/^clicar no botão de visualizar um agrupamento$/, function() {
    return objetosComuns.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de agrupamentos$/, function() {
    return agrupamentosPage.isShow();
  });

  this.Given(/^clicar no botão de editar um agrupamento$/, function() {
    return objetosComuns.clicarLinkComTexto('Editar');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição de agrupamentos$/, function() {
    return agrupamentosPage.textoFieldNomeAgrupamento().then(function(nome) {
      return expect(nome).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de agrupamentos$/, function() {
    return objetosComuns.clicarLinkComTexto('Editar');
  });

  this.Given(/^clicar no botão de excluir um agrupamento$/, function() {
    return objetosComuns.clicarLinkComTexto('Excluir');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir o agrupamento$/, function() {
    return agrupamentosPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^sistema deverá mostar um alerta se deseja atualizar tabela horária$/, function () {
    return agrupamentosPage.alertaAtulizarTabelaHoraria().then(function(text) {
      expect(text).to.equal('Deseja atualizar a tabela horária dos controladores associados a este agrupamento?');
    });
  });


  this.Given(/^nenhum agrupamento deve ser excluído$/, function() {
    return agrupamentosPage.nenhumAgrupamentoDeveSerExcluido().then(function(res) {
      return expect(res).to.be.true;
    });
  });
};
