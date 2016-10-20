'use strict';

var CrudPage = require('../support/page-objects/crud');
var expect = require('chai').expect;

module.exports = function() {
  var crudPage = new CrudPage();

  this.Given(/^o usuário preencher o campo "([^"]*)" com "([^"]*)"$/, function (campo, valor) {
    return crudPage.preencherCampo(campo, valor);
  });

  this.Given(/^o usuário selecionar o valor "([^"]*)" no campo "([^"]*)"$/, function (valor, campo) {
    return crudPage.selecionarValor(campo, valor);
  });

  this.Given(/^o usuário buscar o endereço "([^"]*)" no primeiro endereço$/, function (query) {
    return crudPage.buscarEndereco1(query);
  });

  this.Given(/^o usuário buscar o endereço "([^"]*)" para o endereço 2$/, function (query) {
    return crudPage.buscarEndereco2(query);
  });

 this.Given(/^o usuário limpar o campo endereço 2$/, function () {
    return crudPage.limparEndereco2();
  });

  this.Given(/^o usuário limpar o campo endereço 1$/, function () {
    return crudPage.limparEndereco1();
  });

  this.Given(/^o item deverá ser excluido$/, function() {
    return crudPage.toastMessage().then(function(text) {
      expect(text).to.match(/Removido com sucesso/);
    });
  });
};
