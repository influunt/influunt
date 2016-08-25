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

  this.Given(/^o sistema deverá indicar erro nas quantidades de grupos semaforicos dos anéis$/, function () {
    return wizardPage.errorMessagesGruposSemaforicosAneis().then(function(result) {
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

  this.Given(/^o usuário adicionar um novo anel ativo$/, function () {
    return wizardPage.marcarSegundoAnelComoAtivo();
  });

  this.Given(/^o usuário selecionar o anel (\d+)$/, function (numAnel) {
    return wizardPage.selecionarAnel(numAnel);
  });

  this.Given(/^o sistema irá avançar para o passo "([^"]*)"$/, function (passo) {
    return wizardPage.isWizardPasso(passo);
  });

  this.Given(/^o usuário clicar no botão "([^"]*)"$/, function (text) {
    return wizardPage.clicarBotao(text);
  });

  this.Given(/^que o usuario adicione (\d+) grupos semafóricos ao anel$/, function (numGrupos) {
    return wizardPage.adicionarGruposSemaforicosAoAnel(numGrupos);
  });

  this.Given(/^o usuario associar o grupo semafórico "([^"]*)" com o estágio "([^"]*)"$/, function (grupo, estagio) {
    return wizardPage.associarGrupoSemaforicoEstagio(grupo, estagio);
  });

  this.Given(/^o usuário selecionar o estágio "([^"]*)"$/, function (estagio) {
    return wizardPage.selecionarEstagio(estagio);
  });

  this.Given(/^o usuario marcar o grupo semafórico "([^"]*)" como "([^"]*)"$/, function (grupo, tipoGrupo) {
    return wizardPage.selecionarTipoGrupoSemaforico(grupo, tipoGrupo);
  });

  this.Given(/^que a tabela de conflitos esteja em branco$/, function () {
    return wizardPage.clearVerdesConflitantes();
  });

  this.Given(/^que a tabela de estágios alternativos esteja em branco$/, function () {
    return wizardPage.clearEstagiosAlternativos();
  });

  this.Given(/^marcar conflito entre os grupos "([^"]*)" e "([^"]*)"$/, function (g1, g2) {
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

  this.Given(/^preencher o campo de alternativa para a transição "([^"]*)" com o estágio "([^"]*)"$/, function (transicao, estagio) {
    return wizardPage.selecionaEstagioAlternativoParaTransicaoProibida(transicao, estagio);
  });

  this.Given(/^o usuario preencher os dados dos verdes conflitantes corretamente$/, function () {
    return wizardPage.fillVerdesConflitantes();
  });

  this.Given(/^o sistema irá redirecionar o usuário para a página de listagem de controladores$/, function () {
    return wizardPage.indexPage();
  });

  this.Given(/^o sistema deverá indicar tabela incompleta para o grupo "([^"]*)"$/, function (grupo) {
    return wizardPage.errorMessagesVerdesConflitantesGrupo(grupo);
  });

  this.Given(/^que o usuário marque (\d+) no tempo "([^"]*)"$/, function (value, field) {
    return wizardPage.marcarTempoAtrasoGrupo(value, field);
  });

  this.Given(/^que o usuário marque (\d+) no tempo "([^"]*)" da transição "([^"]*)"$/, function (value, field, transicao) {
    return wizardPage.marcarTempoEntreVerdes(value, field, transicao);
  });

  this.Given(/^que o usuário adicione um detector do tipo "([^"]*)"$/, function (tipoDetector) {
    return wizardPage.adicionarDetector(tipoDetector);
  });

  this.Given(/^que o usuário associe o detector "([^"]*)" com o estágio "([^"]*)"$/, function (detector, estagio) {
    return wizardPage.associarDetectorEstagio(detector, estagio);
  });

  this.Given(/^o sistema deve limpar os erros da tela$/, function() {
    return wizardPage.limparTelaComReload();
  });

  this.Given(/^o usuario limpar os campos$/, function() {
    return wizardPage.limparCampos();
  });

  this.Given(/^o usuario preencher o campo NÚMERO SMEE com 123$/, function () {
    return wizardPage.preencherCampoSMEECom123();
  });
};
