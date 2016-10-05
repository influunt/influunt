'use strict';

var expect = require('chai').expect;
var TabelasHorariasPage = require('../../support/page-objects/tabelas_horarias');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');

module.exports = function() {
  var tabelasHorariasPage = new TabelasHorariasPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^o usuário clicar no botão Tabela Horária do controlador$/, function () {
    return objetosComuns.clicarLinkComTexto('Tabela Horária');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de tabela horária$/, function () {
    return tabelasHorariasPage.isTabelaHoraria();
  });

  this.Given(/^o usuário selecionar o valor "([^"]*)" no campo "([^"]*)" para o evento$/, function (valor, campo) {
    return tabelasHorariasPage.selecionarValor(campo, valor);
  });

  this.Given(/^o sistema deverá apresentar erro no evento$/, function () {
    return tabelasHorariasPage.enventoPossuiErro();
  });
};
