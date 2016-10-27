'use strict';

var FinalizarControladorPage = require('../../support/page-objects/finalizar_controlador');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');
var expect = require('chai').expect;

module.exports = function() {
  var finalizarControladorPage = new FinalizarControladorPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^o sistema "([^"]*)" mostrar o botão "([^"]*)"$/, function (mostrarBotao, tooltipText) {
    if (mostrarBotao === 'pode') {
      return finalizarControladorPage.deveMostrarBotao(tooltipText);
    } else {
      return finalizarControladorPage.naoDeveMostrarBotao(tooltipText);
    }
  });

  this.Given(/^o sistema deverá mostar um modal para salvar o histórico$/, function() {
    return objetosComuns.textoSweetAlert().then(function(text) {
      expect(text).to.equal('Descreva abaixo a modificação feita no cadastro deste controlador');
    });
  });
};
