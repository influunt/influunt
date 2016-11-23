'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var RelatoriosPage = function () {
  this.world = world;

  this.visitarRelatorio = function(local) {
      return world.visit('/app/relatorios/'+local+'');
  };
};
module.exports = RelatoriosPage;
