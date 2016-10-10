
'use strict';

var worldObj = require('../world');

var world = new worldObj.World();

var ResumoControladorPage = function () {

  this.isResumoControlador = function() {
    return world.waitForByXpath('//influunt-revisao[contains(@data-ng-controller, "ControladoresRevisaoCtrl")]');
  };

  this.informacaoDadoBasico = function (campo, texto) {
    return world.checkText(campo, texto);
  };
};

module.exports = ResumoControladorPage;
