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

  this.Given(/^o sistema deverá apresentar a aba com o valor "([^"]*)"$/, function (valor) {
    return tabelasHorariasPage.contagemNaAba(valor);
  });

  this.Given(/^que o usuário remover o ultimo evento$/, function () {
    return tabelasHorariasPage.removerEvento();
  });

  this.Given(/^não deve mais possuir eventos inseridos$/, function () {
    return tabelasHorariasPage.eventosRemovidos();
  });

  this.Given(/^o usuário clicar em visualizar o diagrama$/, function () {
    return tabelasHorariasPage.clicarEmVisualizarDiagrama();
  });

  this.Given(/^o usuário clicar em fechar o diagrama$/, function () {
    var modalId = 'modalDiagramaIntervalos';
    return objetosComuns.clicarBotaoModal(modalId);
  });

  this.Given(/^o sistema deverá adicionar uma cor "([^"]*)" no evento "([^"]*)"$/, function (cor, evento) {
    return tabelasHorariasPage.verificarCorEvento(cor, evento);
  });
};
