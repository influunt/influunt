'use strict';

var TabelasHorariasPage = require('../../support/page-objects/tabelas_horarias');

module.exports = function() {
  var tabelasHorariasPage = new TabelasHorariasPage();

  this.Given(/^o usuário clicar no botão Tabela Horária do controlador$/, function () {
    return tabelasHorariasPage.clicarBotao('Tabela Horária');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de tabela horária$/, function () {
    return tabelasHorariasPage.isTabelaHoraria();
  });
};
