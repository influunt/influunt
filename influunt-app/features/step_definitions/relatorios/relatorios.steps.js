'use strict';

var RelatoriosPage = require('../../support/page-objects/relatorios');

module.exports = function() {
  var relatoriosPage = new RelatoriosPage();

  this.Given(/^o usuário acessar o relatório "([^"]*)"$/, function (localizacao) {
    return relatoriosPage.visitarRelatorio(localizacao);
  });

  this.Given(/^o sistema deverá apresentar o controlador com status em falha$/, function () {
    return relatoriosPage.controladorComFalhaNaListagem();
  });
};
