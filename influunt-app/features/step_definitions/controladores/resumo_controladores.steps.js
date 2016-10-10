'use strict';

var ResumoControladorPage = require('../../support/page-objects/resumo_controladores');

module.exports = function() {
  var resumoControladorPage = new ResumoControladorPage();

  this.Given(/^o sistema deverá redirecionar para a revisão do controlador$/, function () {
    return resumoControladorPage.isResumoControlador();
  });
};
