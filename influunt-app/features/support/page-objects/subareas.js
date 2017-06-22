'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var Subareas = function () {

  this.selecionarValor = function(valor, select) {
    return world.waitForOverlayDisappear().then(function (){
      return world.selectByOptionAtribute('div', '[name="'+select+'"]', 'label', valor);
    });
  };
};

module.exports = Subareas;
