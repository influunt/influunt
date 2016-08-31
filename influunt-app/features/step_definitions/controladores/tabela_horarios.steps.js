'use strict';

var TabelaHorariosPage = require('../../support/page-objects/tabela_horarios');

module.exports = function() {
  var tabelaHorarios = new TabelaHorariosPage();

  this.Given(/^que o sistema possui ao menos um controlador configurado$/, function () {
    return tabelaHorarios.cadastrarControlador();
  });
};
