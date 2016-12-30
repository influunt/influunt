'use strict';

var expect = require('chai').expect;
var AreasPage = require('../../support/page-objects/areas');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');

module.exports = function() {
  var areasPage = new AreasPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^o usuário acessar a tela de cadastro de novas áreas$/, function() {
    return areasPage.newPage();
  });

  this.Given(/^o usuário marcar a cidade como "([^"]*)"$/, function (cidade) {
    return areasPage.selecionarCidade(cidade);
  });

  this.Given(/^o sistema deverá possuir longitude e latidude com os valores "([^"]*)"$/, function (limetes) {
    return areasPage.limetesNaTabela(limetes);
  });
};
