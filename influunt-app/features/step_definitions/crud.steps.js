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

  this.Given(/^o usuário buscar o endereço "([^"]*)" para o endereço (\d+)$/, function (query, numEndereco) {
    return crudPage.buscarEndereco(query, numEndereco);
  });

 this.Given(/^o usuário limpar o campo endereço (\d+)$/, function (numEndereco) {
    return crudPage.limparEndereco(numEndereco);
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir$/, function() {
    return crudPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^o item deverá ser excluido$/, function() {
    return crudPage.toastMessage().then(function(text) {
      expect(text).to.match(/Removido com sucesso/);
    });
  });
};
