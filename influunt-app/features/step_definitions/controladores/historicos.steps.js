'use strict';

var HistoricoPage = require('../../support/page-objects/historico');

module.exports = function() {
  var historicoPage = new HistoricoPage();


  this.Given(/^o sistema deve mostrar um hitórico das alterações$/, function () {
    return historicoPage.isModalHistorico();
  });
};
