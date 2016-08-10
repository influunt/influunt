'use strict';

var CrudPage = require('../support/page-objects/crud');

module.exports = function() {
  var crudPage = new CrudPage();

  this.Given(/^o usuario preencher o campo "([^"]*)" com "([^"]*)"$/, function (campo, valor) {
    return crudPage.preencherCampo(campo, valor);
  });

  this.Given(/^o usuario selecionar o valor "([^"]*)" no campo "([^"]*)"$/, function (valor, campo) {
    return crudPage.selecionarValor(campo, valor);
  });

  this.Given(/^o usuario buscar o endereço "([^"]*)" para o endereço (\d+)$/, function (query, numEndereco) {
    return crudPage.buscarEndereco(query, numEndereco);
  });

};
