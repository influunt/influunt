'use strict';

var expect = require('chai').expect;
var SubareasPage = require('../../support/page-objects/subareas');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');

module.exports = function() {
  var subareasPage = new SubareasPage();

  this.Given(/^o usuário selecionar "([^"]*)" para o campo "([^"]*)"$/, function (valor, select) {
    return subareasPage.selecionarValor(valor, select);
  });

};
