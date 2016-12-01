'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var RelatoriosPage = function () {
  this.world = world;

  this.visitarRelatorio = function(local) {
    return world.visit('/app/relatorios/'+local+'');
  };

  this.controladorComFalhaNaListagem = function(mensagem) {
    return world.waitForByXpath('//td[contains(text(), "'+mensagem+'")]');
  };

  this.selecionarValor = function(campo, valor) {
    return world.waitForOverlayDisappear().then(function (){
      return world.selectByOptionAtribute('div', '[name="'+campo+'"]', 'label', valor);
    });
  };



  this.setarData = function(valor){
    var xpath = '//input[contains(@type, "datetime")]';
    return world.waitForOverlayDisappear().then(function (){
      return world.waitToggle().then(function(){
        return world.setValueByXpath(xpath, valor);
      });
    });
  };
};
module.exports = RelatoriosPage;
