'use strict';

var ObjetosComuns = require('../support/page-objects/objetos_comuns');
var expect = require('chai').expect;

module.exports = function() {
  var sharedSteps = new ObjetosComuns();

  this.Given(/^que o sistema possui ao menos um controlador cadastrado$/, function() {
    return sharedSteps.cadastrarControlador();
  });

  this.Given(/^que o sistema possui planos para o controlador cadastrado$/, function() {
    return sharedSteps.cadastrarPlanoParaControlador();
  });

  this.Given(/^que esse controlador possue planos configurado para ele$/, function () {
    return sharedSteps.cadastrarPlanoParaControlador();
  });

  this.Given(/^o usuário clicar em "([^"]*)"$/, function (botao) {
    return sharedSteps.clicarLinkComTexto(botao);
  });

  this.Given(/^o sistema deverá apresentar erro de "([^"]*)"$/, function (texto) {
    return sharedSteps.errosImpeditivos(texto);
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir$/, function() {
    return sharedSteps.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^o usuário responde sim$/, function() {
    return sharedSteps.clicarSimConfirmacaoApagarRegistro();
  });

  this.Given(/^o usuário realize um scroll up$/, function() {
    return sharedSteps.realizarScrollUp();
  });

  this.Given(/^que o usuário selecione o anel (\d+)$/, function (numeroAnel) {
    return sharedSteps.trocarAnel(numeroAnel);
  });
};
