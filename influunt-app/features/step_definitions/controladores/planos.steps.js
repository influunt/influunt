'use strict';

var PlanosPage = require('../../support/page-objects/planos');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');
var expect = require('chai').expect;

module.exports = function() {
  var planosPage = new PlanosPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^o sistema deverá redirecionar para a tela de planos$/, function () {
    return planosPage.isPlanos();
  });

  this.Given(/^que o usuário esteja na página de planos$/, function () {
    return planosPage.isPlanos();
  });

  this.Given(/^que o usuário selecione o modo de operação "([^"]*)"$/, function (modoOperacao) {
    return planosPage.selecionarModoOperacao(modoOperacao);
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

  this.Given(/^o usuário não deve ter a opção de marcar estágio dispensável$/, function () {
    return planosPage.isEstagioDispensavelHidden();
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

  this.Given(/^o sistema deverá apresentar erro em defasagem$/, function () {
    return planosPage.erroDefasagem();
  });

  this.Given(/^que o usuário clique no botão de configurar o estágio "([^"]*)"$/, function (estagio) {
    return planosPage.clicarBotaoConfigurarEstagio(estagio);
  });

  this.Given(/^que o usuário marque (\d+) segundos para o "([^"]*)"$/, function (value, field) {
    return planosPage.marcarValorConfig(field, value);
  });

  this.Given(/^que o usuário clique no botão de fechar a caixa de configuração$/, function () {
    var modal = 'modal-configuracao-estagio';
    return objetosComuns.clicarBotaoModal(modal);
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
    return planosPage.numeroEstagiosIncluidos(numeroEstagios);
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

  this.Given(/^o usuário selecionar o "([^"]*)"$/, function (valor) {
    return planosPage.selecionarPlano(valor);
  });

  this.Given(/^o sistema deve alterar o nome para "([^"]*)"$/, function (valor) {
    return planosPage.nomePlanoAlterado(valor);
  });

  this.Given(/^o usuário clicar no botão copiar$/, function () {
    var modal = 'modal-copiar-plano';
    return objetosComuns.clicarBotaoModal(modal);
  });

  this.Given(/^o "([^"]*)" deverá estar ativado$/, function (plano) {
    return planosPage.isPlanoAtivo(plano);
  });

  this.Given(/^clicar em cancelar a edição$/, function () {
    return planosPage.clicarBotao('Cancelar Edição');
  });

  this.Given(/^que o usuário clicar no plano (\d+)$/, function(numeroPlano) {
    return planosPage.clickInPlano(numeroPlano);
  });

  this.Given(/^o usuário queira limpar o plano (\d+)$/, function(numeroPlano){
    return planosPage.clickInPlano(numeroPlano);
  });

  this.Given(/^o sistema deverá mostrar erro no plano (\d+)$/, function (numeroPlano) {
    return planosPage.errosInPlanos(numeroPlano);
  });

  this.Given(/^o usuário responde sim para verde de segurança/, function () {
    return planosPage.clicarSimVerdeSeguranca();
  });

  this.Given(/^o usuário responde ok/, function () {
    return planosPage.clicarConfirmacaoAlert();
  });

  this.Given(/^o sistema deverá mostrar um alerta para verdes segurança menor$/, function () {
    return planosPage.alertVerdeSeguranca().then(function(text) {
      expect(text).to.equal('Tem certeza que deseja colocar o tempo de verde menor que o tempo de verde de segurança dos grupos semafóricos?');
    });
  });

  this.Given(/^o sistema deverá mostrar um alerta para verdes segurança mínimo de pedestre$/, function () {
    return planosPage.alertVerdeSeguranca().then(function(text) {
      expect(text).to.equal('Não é permitido colocar o tempo de verde menor que o tempo de verde de segurança em estágio de pedestre.');
    });
  });

  this.Then(/^o sistema deverá mostrar um alerta para valor digitado maior que o limite máximo$/, function () {
    return objetosComuns.alertInfluuntKnob().then(function(text) {
      expect(text).to.equal('O valor digitado é maior que o limite máximo.');
    });
  });

  this.Then(/^o sistema deverá mostrar um alerta para valor digitado menor que o limite mínimo$/, function () {
    return objetosComuns.alertInfluuntKnob().then(function(text) {
      expect(text).to.equal('O valor digitado é menor que o limite mínimo.');
    });
  });

  this.Given(/^o sistema deverá apresentar erro no estágio "([^"]*)"$/, function (estagio) {
    return planosPage.erroInEstagio(estagio);
  });

  this.Given(/^e o usuário clicar em estágio dispensável$/, function () {
    return planosPage.checkEstagioDispensavel();
  });

  this.Given(/^o usuário selecionar o estágio que recebe o estágio dispensável "([^"]*)"$/, function (estagio) {
    return planosPage.selecionaEstagioQueRecebeDispensavel(estagio);
  });

  this.Given(/^o estágio "([^"]*)" deve estar selecionado para estágio dispensável$/, function (estagio) {
    return planosPage.deveConterEstagioQueRecebeDispensavel(estagio);
  });

  this.Given(/^o valor "([^"]*)" deverá ser "([^"]*)"$/, function (Knob,valor) {
    return planosPage.valorDoKnob(valor, Knob);
  });

  this.Given(/^o usuário no diagrama selecionar o grupo "([^"]*)"$/, function (grupo) {
    return planosPage.selectGrupoInDiagrama(grupo);
  });

  this.Given(/^o usuário ativar o ciclo duplo$/, function () {
    return planosPage.selecionarCicloDuplo();
  });
};
