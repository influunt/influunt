'use strict';

var TabelaHorariosPage = require('../../support/page-objects/tabela_horarios');

module.exports = function() {
  var tabelaHorariosPage = new TabelaHorariosPage();

  this.Given(/^o usuário clicar no botão Tabela Horários$/, function () {
    return tabelaHorariosPage.clicarBotao('Tabela Horário');
  });

  this.Given(/^o usuário clicar no botão editar$/, function () {
    return tabelaHorariosPage.clicarBotao('Editar');
  });

  this.Given(/^o sistema deverá redirecionar para tabelas horários$/, function () {
    return tabelaHorariosPage.isTabelasHorarios();
  });

  this.Given(/^que o usuário esteja na página tabela horários$/, function () {
    return tabelaHorariosPage.isTabelasHorarios();
  });

  this.Given(/^a tabela horários deverá estar editável$/, function () {
    return tabelaHorariosPage.isEditavel();
  });

  this.Given(/^o usuario selecionar "([^"]*)" no campo "([^"]*)"$/, function (valor, campo) {
    return tabelaHorariosPage.selecionarValor(campo, valor);
  });
};
