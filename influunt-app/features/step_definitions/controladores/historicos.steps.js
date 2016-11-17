'use strict';

var HistoricoPage = require('../../support/page-objects/historico');

module.exports = function() {
  var historicoPage = new HistoricoPage();

  this.Given(/^o sistema deve mostrar um hitórico das alterações$/, function () {
    return historicoPage.isModalHistorico();
  });

  this.Given(/^o sistema deve redirecionar para tela tabela horárias e suas diferenças$/, function () {
    return historicoPage.isTabelaHorariaDiff();
  });

  this.Given(/^o sistema deve possuir alteração no evento de "([^"]*)" como "([^"]*)"$/, function (dia, estado) {
    return historicoPage.checkEventosAlteradosTabelaHoraria(dia, estado);
  });

  this.Given(/^o usuário em histórico clicar em ver alterações$/, function () {
    return historicoPage.clicarVerAltercoes();
  });
};
