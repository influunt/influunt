'use strict';

var PlanosPage = require('../../support/page-objects/planos');
var expect = require('chai').expect;

module.exports = function() {
  var planosPage = new PlanosPage();

  this.Given(/^que o sistema possui ao menos um controlador configurado$/, function () {
    return planosPage.cadastrarControlador();
  });

  this.Given(/^o usuário clicar no botão Planos do controlador$/, function () {
    return planosPage.clicarBotao('Planos');
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
    var modal = 'modal-configuracao-estagio';
    return planosPage.clicarBotaoModal(modal);
  });

  this.Given(/^que o usuário troque de lugar os estágios "([^"]*)" e "([^"]*)"$/, function (estagio1, estagio2) {
    return planosPage.trocarEstagiosDeLugar(estagio1, estagio2);
  });

  this.Given(/^que o usuário clique no botão de adicionar um novo plano$/, function () {
    return planosPage.clicarBotaoAdicionarNovoPlano();
  });

  this.Given(/^o diagrama de intervalos não deverá aparecer$/, function(){
    return planosPage.hiddenDiagramaIntervalo();
  });

  this.Given(/^que o usuário clique no botão apagar o estagio "([^"]*)"$/, function (estagio) {
    return planosPage.clicarBotaoApagarEstagio(estagio);
  });

  this.Given(/^a quantidade de estagios na lista deverá ser (\d+)$/, function (numeroEstagios) {
    return planosPage.estagioExcluido(numeroEstagios);
  });

  this.Given(/^o usuário decide adicionar o estágio removido "([^"]*)"$/, function (estagio) {
    return planosPage.clicarBotaoAddPlano(estagio);
  });

  this.Given(/^que o usuário clicar em editar o "([^"]*)"$/, function (plano) {
    var actionEditar = 'fa-pencil';
    return planosPage.clicarBotaoAcaoPlano(actionEditar, plano);
  });

  this.Given(/^que o usuário clicar em copiar o "([^"]*)"$/, function (plano) {
    var actionCopiar = 'fa-files';
    return planosPage.clicarBotaoAcaoPlano(actionCopiar, plano);
  });

  this.Given(/^o sistema exibe uma caixa para renomear o plano$/, function() {
    return planosPage.textoConfirmacaoEditarPlano().then(function(text) {
      expect(text).to.equal('Digite no campo abaixo um nome para este plano');
    });
  });

  this.Given(/^o sistema exibe uma caixa para copiar o plano 1$/, function() {
    return planosPage.getTextInModal().then(function(text) {
      expect(text).to.equal('selecione os planos que deverão copiar as configurações de PLANO 1');
    });
  });

  this.Given(/^o usuário prenche o campo com "([^"]*)"$/, function (valor) {
    return planosPage.preencherCampoEditarPlano(valor);
  });

  this.Given(/^o usuário selecionar o "([^"]*)"$/, function (valor) {
    return planosPage.selecionarPlano(valor);
  });

  this.Given(/^o sistema deve alterar o nome para "([^"]*)"$/, function (valor) {
    return planosPage.nomePlanoAlterado(valor);
  });

  this.Given(/^o usuário clicar no botão copiar$/, function () {
    var modal = 'modal-copiar-plano';
    return planosPage.clicarBotaoModal(modal);
  });

  this.Given(/^o "([^"]*)" deverá estar ativado$/, function (plano) {
    return planosPage.isPlanoAtivo(plano);
  });

  this.Given(/^o usuário clicar em editar$/, function () {
    return planosPage.clicarBotao('Editar');
  });

  this.Given(/^o usuário clicar em salvar$/, function () {
    return planosPage.clicarBotao('Salvar');
  });

  this.Given(/^que o usuário selecione o anel 2$/, function () {
    return planosPage.clicarAbaAnel2();
  });

  this.Given(/^clicar em cancelar a edição$/, function () {
    return planosPage.clicarBotao('Cancelar Edição');
  });

  this.Given(/^o sistema deverá apresentar erro de "([^"]*)"$/, function (texto) {
    return planosPage.errosImpeditivos(texto);
  });
};
