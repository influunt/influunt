'use strict';

var Banco = require('../support/page-objects/banco_de_dados.js');

module.exports = function() {
  var banco = new Banco();

  this.Given(/^que o sistema possui ao menos um controlador cadastrado$/, function() {
    return banco.cadastrarControlador();
  });

  this.Given(/^que o sistema possua controladores cadastrados$/, function() {
    return banco.controladores();
  });

  this.Given(/^que o sistema possua "([^"]*)" controladores cadastrados$/, function(total) {
    var quantTotal = parseInt(total);
    banco.removeControladores();
    for (var i = 0; i < quantTotal; i++) {
      banco.variosControladores();
    }
  });

  this.Given(/^que o sistema possua controladores cadastrados e configurados$/, function() {
    return banco.variosControladoresConfigurados();
  });

  this.Given(/^que o sistema possua planos para o controlador cadastrado$/, function() {
    return banco.cadastrarPlanoParaControlador();
  });

  this.Given(/^que o sistema possua tabela horária para o controlador cadastrado$/, function() {
    return banco.cadastrarTabelaHorariaParaControlador();
  });

  this.Given(/^for desabilitada no perfil visualizar todas as áreas$/, function () {
    return banco.desabilitarPermissoes();
  });

  this.Given(/^for abilitado no perfil visualizar todas as áreas$/, function () {
    return banco.desabilitarPermissoes();
  });

  this.Given(/^que exista ao menos um agrupamento cadastrado no sistema$/, function() {
    return banco.existeAoMenosUmAgrupamento();
  });

  this.Given(/^que este controlador esteja finalizado$/, function() {
    return banco.finalizarControlador();
  });

  this.Given(/^que o usuário não possa definir perfis$/, function () {
    return banco.desabilitarPermissoes();
  });

  this.Given(/^que possua controladores com áreas diferentes cadastrados$/, function () {
    return banco.controladoresAreasDiferentes();
  });
};
