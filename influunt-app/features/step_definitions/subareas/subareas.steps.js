'use strict';

var SubareasPage = require('../../support/page-objects/subareas');

module.exports = function() {
  var subareasPage = new SubareasPage();

  this.Given(/^o usuário selecionar "([^"]*)" para o campo "([^"]*)"$/, function (valor, select) {
    return subareasPage.selecionarValor(valor, select);
  });
};
