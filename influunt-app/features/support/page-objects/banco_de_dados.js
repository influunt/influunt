'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var BancoDeDados = function () {
  this.cadastrarControlador = function() {
    return world.execSqlScript('features/support/scripts/planos/controlador.sql');
  };

  this.cadastrarPlanoParaControlador = function() {
    return world.execSqlScript('features/support/scripts/controladores/plano_controlador.sql');
  };

  this.cadastrarTabelaHorariaParaControlador = function() {
    return world.execSqlScript('features/support/scripts/controladores/tabela_horaria_controlador.sql');
  };

  this.controladoresAreasDiferentes = function() {
    return world.execSqlScript('features/support/scripts/controladores/controladores_por_areas.sql');
  };

  this.variosControladores = function() {
    return world.execSqlScript('features/support/scripts/controladores/controladores.sql');
  };

  this.variosControladoresConfigurados = function() {
    return world.execSqlScript('features/support/scripts/controladores/controladores_finalizados.sql');
  };

  this.desabilitarPermissoes = function() {
    return world.execSqlScript('features/support/scripts/perfis/remover_permissoes.sql');
  };

  this.abilitaPermissoes = function() {
    return world.execSqlScript('features/support/scripts/perfis/adicionar_permissoes.sql');
  };

  this.finalizarControlador = function(){
    return world.execSqlScript('features/support/scripts/controladores/set_controlador_finalizado.sql');
  };

  this.existeAoMenosUmAgrupamento = function() {
    return world.execSqlScript('features/support/scripts/agrupamentos/create_agrupamento.sql');
  };
};
module.exports = BancoDeDados;
