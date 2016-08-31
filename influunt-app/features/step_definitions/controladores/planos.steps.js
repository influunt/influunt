'use strict';

var PlanosPage = require('../../support/page-objects/planos');

module.exports = function() {
  var planosPage = new PlanosPage();

  this.Given(/^que o sistema possui ao menos um controlador configurado$/, function () {
    return planosPage.cadastrarControlador();
  });

  this.Given(/^o usuário clicar no botão Planos do controlador$/, function () {
    return planosPage.clicarBotaoPlanos();
  });

  this.Given(/^o sistema deverá redirecionar para a tela de planos$/, function () {
    return planosPage.isPlanos();
  });

  this.Given(/^que o usuário esteja na página de planos$/, function () {
    return planosPage.isPlanos();
  });

  this.Given(/^que o usuário selecione o modo de operação "([^"]*)"$/, function (modoOperacao) {
    return planosPage.selecionarModoOperacao(modoOperacao);
  });

  this.Given(/^o diagrama de ciclos deverá marcar o grupo semafórico "([^"]*)" como "([^"]*)"$/, function (grupo, modoOperacao) {
    return planosPage.isDiagramaModo(grupo, modoOperacao);
  });

  this.Given(/^o usuário não deve ter a opção de selecionar uma tabela entre verdes para o plano$/, function () {
    return planosPage.isTabelaEntreVerdesHidden();
  });

  this.Given(/^o usuário deve ter a opção de selecionar uma tabela entre verdes para o plano$/, function () {
    return planosPage.isTabelaEntreVerdesVisible();
  });

  this.Given(/^o usuário não deve ter a opção de marcar o tempo de ciclo do estágio$/, function () {
    return planosPage.isTempoDeCicloHidden();
  });

  this.Given(/^o usuário deve ter a opção de marcar o tempo de ciclo do estágio$/, function () {
    return planosPage.isTempoDeCicloVisible();
  });

  this.Given(/^o usuário não deve ter a opção de marcar a defasagem do ciclo$/, function () {
    return planosPage.isTempoDefasagemHidden();
  });

  this.Given(/^o usuário deve ter a opção de marcar a defasagem do ciclo$/, function () {
    return planosPage.isTempoDefasagemVisible();
  });

  this.Given(/^que o usuário clique no botão de configurar o estágio "([^"]*)"$/, function (estagio) {
    return planosPage.clicarBotaoConfigurarEstagio(estagio);
  });

  this.Given(/^que o usuário marque (\d+) segundos para o "([^"]*)"$/, function (value, field) {
    return planosPage.marcarValorConfig(field, value);
  });

  this.Given(/^que o usuário clique no botão de fechar a caixa de configuração$/, function () {
    return planosPage.fecharCaixaConfiguracao();
  });

  this.Given(/^que o usuário troque de lugar os estágios "([^"]*)" e "([^"]*)"$/, function (estagio1, estagio2) {
    return planosPage.trocarEstagiosDeLugar(estagio1, estagio2);
  });

  this.Given(/^que o usuário clique no botão de adicionar um novo plano$/, function () {
    return planosPage.clicarBotaoAdicionarNovoPlano();
  });
};
