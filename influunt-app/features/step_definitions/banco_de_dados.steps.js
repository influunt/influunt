'use strict';

var InsertBanco = require('../support/page-objects/banco_de_dados.js');

module.exports = function() {
  var insertBanco = new InsertBanco();

  this.Given(/^que o sistema possui ao menos um controlador cadastrado$/, function() {
    return insertBanco.cadastrarControlador();
  });

  this.Given(/^que o sistema possua controladores cadastrados$/, function() {
    return insertBanco.variosControladores();
  });

  this.Given(/^que o sistema possua controladores cadastrados e configurados$/, function() {
    return insertBanco.variosControladoresConfigurados();
  });

  this.Given(/^que o sistema possua planos para o controlador cadastrado$/, function() {
    return insertBanco.cadastrarPlanoParaControlador();
  });

  this.Given(/^que o sistema possua tabela horária para o controlador cadastrado$/, function() {
    return insertBanco.cadastrarTabelaHorariaParaControlador();
  });

  this.Given(/^for desabilitada no perfil visualizar todas as áreas$/, function () {
    return insertBanco.desabilitarPermissoes();
  });

  this.Given(/^for abilitado no perfil visualizar todas as áreas$/, function () {
    return insertBanco.desabilitarPermissoes();
  });

  this.Given(/^que exista ao menos um agrupamento cadastrado no sistema$/, function() {
    return insertBanco.existeAoMenosUmAgrupamento();
  });

  this.Given(/^que este controlador esteja finalizado$/, function() {
    return insertBanco.finalizarControlador();
  });

  this.Given(/^que o usuário não possa definir perfis$/, function () {
    return insertBanco.desabilitarPermissoes();
  });

  this.Given(/^que possua controladores com áreas diferentes cadastrados$/, function () {
    return insertBanco.controladoresAreasDiferentes();
  });
};
