'use strict';

var expect = require('chai').expect;
var WizardPage = require('../../support/page-objects/wizard_controlador');

module.exports = function() {
  var wizardPage = new WizardPage();

  this.Given(/^que o usuário acesse a página de listagem de controladores$/, function () {
    return wizardPage.indexPage();
  });

  this.Given(/^o usuário clicar no botão Novo Controlador$/, function () {
    return wizardPage.clicarBotaoNovoControlador();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de Cadastro de Controladores$/, function () {
    return wizardPage.isWizardDadosBasicos();
  });

  this.Given(/^que o usuário esteja no wizard no passo "([^"]*)"$/, function (passo) {
    return wizardPage.isWizardPasso(passo);
  });

  this.Given(/^que o usuário deixe os campos em branco$/, function (callback) {
    callback();
  });

  this.Given(/clicar no botão para ir pro próximo passo$/, function () {
    return wizardPage.clicarBotaoProximoPasso();
  });

  this.Given(/^o sistema deverá indicar erro nos campos do passo "([^"]*)"$/, function (passo) {
    return wizardPage.errorMessages(passo).then(function(result) {
      return expect(result).to.be.true;
    });
  });

  this.Given(/^o sistema irá continuar no passo "([^"]*)"$/, function (passo) {
    return wizardPage.isWizardPasso(passo);
  });

  this.Given(/^que o sistema possui os dados necessários cadastrados$/, function () {
    return wizardPage.cadastrarEntidadesDadosBasicos();
  });

  this.Given(/^o usuario adicionar (\d+) imagens para os estágios do anel corrente$/, function (qtde) {
    return wizardPage.adicionarImagensEstagios(qtde);
  });

  this.Given(/^o usuario marcar o segundo anel como ativo$/, function () {
    return wizardPage.marcarSegundoAnelComoAtivo();
  });

  this.Given(/^o usuario selecionar o segundo anel$/, function () {
    return wizardPage.selecionarSegundoAnel();
  });

  this.Given(/^o sistema irá avançar para o passo "([^"]*)"$/, function (passo) {
    return wizardPage.isWizardPasso(passo);
  });

  this.Given(/clicar no botão para finalizar$/, function () {
    return wizardPage.clicarBotaoFinalizar();
  });

  this.Given(/^o usuario associar o grupo semafórico "([^"]*)" com o estágio "([^"]*)"$/, function (grupo, estagio) {
    return wizardPage.associarGrupoSemaforicoEstagio(grupo, estagio);
  });

  this.Given(/^o usuário selecionar o estágio "([^"]*)"$/, function (estagio) {
    return wizardPage.selecionarEstagio(estagio);
  });

  this.Given(/^o usuario marcar o grupo semafórico como "([^"]*)"$/, function (tipoGrupoSemaforico) {
    return wizardPage.selecionarTipoGrupoSemaforico(tipoGrupoSemaforico);
  });


  this.Given(/^que a tabela de conflitos esteja em branco$/, function () {
    return wizardPage.clearVerdesConflitantes();
  });

  this.Given(/^que a tabela de estágios alternativos esteja em branco$/, function () {
    return wizardPage.clearEstagiosAlternativos();
  });

  this.Given(/^marcar conflito entre os estágios "([^"]*)" e "([^"]*)"$/, function (g1, g2) {
    return wizardPage.marcarConflito(g1, g2);
  });

  this.Given(/^o usuário marcar a transição de "([^"]*)" para "([^"]*)" como proibida$/, function (e1, e2) {
    return wizardPage.marcarTransicao(e1, e2);
  });

  this.Given(/^o sistema deverá indicar que o campo de estágio alternativo para a transição "([^"]*)" é obrigatório$/, function (transicao) {
    return wizardPage.isEstagioAlternativoInvalido(transicao).then(function(res) {
      return expect(res).to.be.true;
    });
  });

  this.Given(/^o sistema deverá indicar conflito$/, function () {
    return wizardPage.errorMessagesVerdesConflitantesComConflito().then(function(result) {
      return expect(result).to.be.true;
    });
  });

  this.Given(/^preencher o campo de alternativa para a transição "([^"]*)" com o estágio "([^"]*)"$/, function (transicao, estagio) {
    return wizardPage.selecionaEstagioAlternativoParaTransicaoProibida(transicao, estagio);
  });

  this.Given(/^o usuario preencher os dados dos verdes conflitantes corretamente$/, function () {
    return wizardPage.fillVerdesConflitantes();
  });

  this.Given(/^o sistema irá redirecionar o usuário para a página de listagem de controladores$/, function () {
    return wizardPage.isIndex();
  });

};
