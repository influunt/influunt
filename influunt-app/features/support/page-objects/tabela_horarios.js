'use strict';

var worldObj = require('../world');

var world = new worldObj.World();

var TabelaHorariosPage = function () {

  this.cadastrarControlador = function() {
    return world.execSqlScript('features/support/scripts/planos/controlador.sql');
  };
};

module.exports = TabelaHorariosPage;
