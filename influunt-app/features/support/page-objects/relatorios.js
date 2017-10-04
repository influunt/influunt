'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var RelatoriosPage = function () {
  this.world = world;

  var botaoGerar = '//input[contains(@value, "Gerar")]';

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

  this.botaoGerarDisabled = function () {
    return world.waitForOverlayDisappear().then(function (){
      return world.waitForByXpath(''+botaoGerar+'[contains(@class,"disabled")]');
    });
  };

  this.clicarBotaoGerar = function() {
    return world.waitForOverlayDisappear().then(function (){
      return world.getElementByXpath(botaoGerar).click();
    });
  };
};
module.exports = RelatoriosPage;
