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

   this.clicarNoBotaoEspecifico = function(botao, usuario) {
    return world.sleep(1000);
    return world.waitForOverlayDisappear().then(function (){
      return world.getElementByXpath('//td[contains(text(), "Jhon Doe")]//following-sibling::td//a[contains(text(), "'+botao+'")]').then(function(element){
        return world.moveMouseToElement(element);
        return element.click();
        return world.clickAtLocationt(element);
        return element.click();
        return element.click();
      })
    });
  };
};

module.exports = UsuariosPage;
