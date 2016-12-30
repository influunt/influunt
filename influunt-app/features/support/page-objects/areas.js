'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var AreasPage = function () {
  var NEW_PATH = '/app/areas/new';
  var formAreas = 'form[name="formAreas"]';

  this.world = world;

  this.newPage = function() {
    return world.visit(NEW_PATH).then(function(){
      return world.waitFor(formAreas);
    });
  };

  this.limetesNaTabela = function(limites) {
    return world.waitForByXpath('//li[contains(text(), "'+limites+'")]');
  };

  this.selecionarCidade = function(cidade) {
    return world.getElementByXpath('//select[contains(@name, "cidade")]//option[contains(@label, "'+cidade+'")]').click();
  };
};

module.exports = AreasPage;
