'use strict';

var ResumoControladorPage = require('../../support/page-objects/resumo_controladores');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');

module.exports = function() {
  var resumoControladorPage = new ResumoControladorPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^o sistema deverá redirecionar para a revisão do controlador$/, function () {
    return resumoControladorPage.isResumoControlador();
  });
};
