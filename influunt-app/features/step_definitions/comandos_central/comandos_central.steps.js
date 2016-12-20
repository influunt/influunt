'use strict';

var ComandoCentral = require('../../support/page-objects/comandos_central');

module.exports = function() {
  var comandoCentral = new ComandoCentral();

  this.Given(/^o sistema dever possuir o anel "([^"]*)" sincronizado$/, function (anelCla) {
    return comandoCentral.verificaAnelSincronizado(anelCla);
  });

  this.Given(/^o usuário selecionar o anel "([^"]*)" /, function (anelCla) {
    return comandoCentral.selecionarAnelSincronizado(anelCla);
  });
};
