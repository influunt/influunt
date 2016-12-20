'use strict';

var worldObj = require('../world');
var expect = require('chai').expect;
var world = new worldObj.World();

var ComandosCentral = function () {
  this.world = world;

  this.verificaAnelSincronizado = function(anelCla) {
    return world.waitForOverlayDisappear()
      .then(function (){
        return world.getElementByXpath('//*[ancestor::tr[td[text()="'+anelCla+'"]]][3]').getText()
        .then(function (text) {
          expect(text).to.be.equal('Sincronizado');
      });
    });
  };

  this.selecionarAnelSincronizado = function(anelCla) {
    return world.waitForOverlayDisappear().then(function (){
      return world.getElementByXpath('//td[text()="'+anelCla+'"]/parent::tr//ins').clcik();
    });
  };
};

module.exports = ComandosCentral;
