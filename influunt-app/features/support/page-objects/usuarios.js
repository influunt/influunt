'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var UsuariosPage = function () {
  this.world = world;

  this.indexPage = function() {
    return world.waitFor('tbody tr[data-ng-repeat="usuario in lista"]');
  };

  this.visitPath = function(path) {
    return world.visit('/app/'+path+'')
  };
};

module.exports = UsuariosPage;
