'use strict';

var TabelasHorariasPage = require('../../support/page-objects/tabelas_horarias');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');
var expect = require('chai').expect;

module.exports = function() {
  var tabelasHorariasPage = new TabelasHorariasPage();
  var objetosComuns = new ObjetosComuns();

  // this.Given(/^que o sistema possui ao menos um controlador configurado$/, function () {
  //   return tabelasHorariasPage.cadastrarControlador();
  // });

  this.Given(/^o usuário clicar no botão Tabela Horária do controlador$/, function () {
    return tabelasHorariasPage.clicarBotao('Tabela Horária');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de tabela horária$/, function () {
    return tabelasHorariasPage.isTabelaHoraria();
  });
};
