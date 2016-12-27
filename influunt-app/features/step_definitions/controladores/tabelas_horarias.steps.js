'use strict';

var TabelasHorariasPage = require('../../support/page-objects/tabelas_horarias');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');

module.exports = function() {
  var tabelasHorariasPage = new TabelasHorariasPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^o usuário clicar no botão Tabela Horária do controlador$/, function () {
    return objetosComuns.clicarLinkComTexto('Tabela Horária');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de tabela horária$/, function () {
    return tabelasHorariasPage.isTabelaHoraria();
  });

  this.Given(/^o usuário selecionar o valor "([^"]*)" no campo "([^"]*)" para o evento$/, function (valor, select) {
    return tabelasHorariasPage.selecionarValor(valor, select);
  });

  this.Given(/^o usuário selecionar o valor plano "([^"]*)" no campo "([^"]*)" para o evento$/, function (valor, select) {
    return tabelasHorariasPage.selecionarValor(valor, select);
  });

  this.Given(/^o sistema deverá apresentar erro no evento$/, function () {
    return tabelasHorariasPage.enventoPossuiErro();
  });

  this.Given(/^o sistema deverá apresentar na aba "([^"]*)" com o valor "([^"]*)"$/, function (aba, valor) {
    return tabelasHorariasPage.contagemNaAba(aba, valor);
  });

  this.Given(/^que o usuário remover o ultimo evento$/, function () {
    return tabelasHorariasPage.removerEvento();
  });

  this.Given(/^não deve mais possuir eventos inseridos$/, function () {
    return tabelasHorariasPage.eventosRemovidos();
  });

  this.Given(/^o evento "([^"]*)" deverá ser excluído$/, function (evento) {
    return tabelasHorariasPage.eventoRemovido(evento);
  });

  this.Given(/^o usuário clicar em visualizar o diagrama do evento "([^"]*)"$/, function (evento) {
    return tabelasHorariasPage.clicarEmVisualizarDiagrama(evento);
  });

  this.Given(/^o sistema deve mostrar um alert com a mensagem atuado não possue diagrama$/, function () {
    return tabelasHorariasPage.checkTextInSweetAlertAtuado();
  });

  this.Given(/^o usuário clicar em fechar o diagrama$/, function () {
    var modalId = 'modalDiagramaIntervalos';
    return objetosComuns.clicarBotaoModal(modalId);
  });

  this.Given(/^o sistema deverá adicionar uma cor "([^"]*)" no evento "([^"]*)"$/, function (cor, evento) {
    return tabelasHorariasPage.verificarCorEvento(cor, evento);
  });

  this.Given(/^do sistema deverá redirecionar para a listagem de controladores"([^"]*)"$/, function (cor, evento) {
    return tabelasHorariasPage.verificarCorEvento(cor, evento);
  });

  this.Given(/^o quadro de horário deverá marcar "([^"]*)", na hora "([^"]*)", com a cor "([^"]*)"$/, function (diaSemana, hora, cor) {
    return tabelasHorariasPage.verificaQuadroHorario(diaSemana, hora, cor);
  });

  this.Given(/^o usuário mudar de aba para Eventos "([^"]*)"$/, function (aba) {
    return tabelasHorariasPage.mudarEvento(aba);
  });

  this.Given(/^o usuário preencher o campo evento com "([^"]*)"$/, function (valor) {
    return tabelasHorariasPage.preencherDescricaoEvento('eventoDescricao', valor);
  });

};
