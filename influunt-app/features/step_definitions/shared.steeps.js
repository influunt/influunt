'use strict';

var ObjetosComuns = require('../support/page-objects/objetos_comuns');
var expect = require('chai').expect;

module.exports = function() {
  var sharedSteeps = new ObjetosComuns();

 this.Given(/^que o sistema possui ao menos um controlador cadastrado$/, function() {
    return sharedSteeps.cadastrarControlador();
  });

 this.Given(/^que esse controlador possue planos configurado para ele$/, function () {
    return sharedSteeps.cadastrarPlanoParaControlador();
  });

 this.Given(/^o usuário clicar em "([^"]*)"$/, function (botao) {
    return sharedSteeps.clicarLinkComTexto(botao);
  });

 this.Given(/^o sistema deverá apresentar erro de "([^"]*)"$/, function (texto) {
    return sharedSteeps.errosImpeditivos(texto);
  });
};
