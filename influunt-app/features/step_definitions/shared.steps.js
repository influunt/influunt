'use strict';

var ObjetosComuns = require('../support/page-objects/objetos_comuns');
var expect = require('chai').expect;

module.exports = function() {
  var sharedSteps = new ObjetosComuns();

  this.Given(/^que o sistema possui ao menos um controlador cadastrado$/, function() {
    return sharedSteps.cadastrarControlador();
  });

  this.Given(/^que o sistema possui planos para o controlador cadastrado$/, function() {
    return sharedSteps.cadastrarPlanoParaControlador();
  });

  this.Given(/^que esse controlador possue planos configurado para ele$/, function () {
    return sharedSteps.cadastrarPlanoParaControlador();
  });

  this.Given(/^o usuário clicar em "([^"]*)"$/, function (botao) {
    return sharedSteps.clicarLinkComTexto(botao);
  });

  this.Given(/^o sistema deverá apresentar erro de "([^"]*)"$/, function (texto) {
    return sharedSteps.errosImpeditivos(texto);
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir$/, function() {
    return sharedSteps.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^o usuário responde sim$/, function() {
    return sharedSteps.clicarSimConfirmacaoApagarRegistro();
  });

  this.Given(/^o usuário realize um scroll up$/, function() {
    return sharedSteps.realizarScrollUp();
  });

  this.Given(/^o usuário realizar um scroll down$/, function() {
    return sharedSteps.realizarScrollDown();
  });

  this.Given(/^que o usuário selecione o anel (\d+)$/, function (numeroAnel) {
    return sharedSteps.trocarAnel(numeroAnel);
  });

  // verificar o diagrama
  this.Given(/^o sistema deve mostrar o diagrama "([^"]*)" no grupo "([^"]*)" com "([^"]*)" em "([^"]*)" segundos$/, function (modoOperacao, grupo, indicacaoCor, tempo) {
    return sharedSteps.isDiagramaModo(modoOperacao, grupo, indicacaoCor, tempo);
  });

  this.Given(/^o sistema deverá redirecionar o usuário para a página de listagem de controladores$/, function () {
    return sharedSteps.isIndexPage();
  });

  this.Given(/^que o usuário deslogue no sistema$/, function () {
    return sharedSteps.deslogar();
  });

  this.Given(/^o usuário navegar pelo breadcrumb clicando em "([^"]*)"$/, function (opcao) {
    return sharedSteps.navegarBreadcrumb(opcao);
  });

  this.Given(/^o sistema deverá mostrar "([^"]*)" controladores cadastrados$/, function (numero) {
    return sharedSteps.checarTotalInseridosNaTabela(numero);
  });

  this.Given(/^o sistema deverá mostrar "([^"]*)" na listagem$/, function (numero) {
    return sharedSteps.checarTotalInseridosNaTabela(numero);
  });

  this.Given(/^que possua controladores com áreas diferentes cadastrados$/, function () {
    return sharedSteps.controladoresAreasDiferentes();
  });

  this.Given(/^o sistema deverá indicar erro no campo "([^"]*)"$/, function (nomeCampo) {
    return sharedSteps.getErrorMessageFor(nomeCampo).then(function(result) {
      return expect(result).to.exist;
    });
  });

  this.Given(/^o sistema deverá redirecionar para o formulário "([^"]*)"$/, function(formName) {
    return sharedSteps.form(formName).then(function(form) {
      return expect(form).to.exist;
    });
  });

};
