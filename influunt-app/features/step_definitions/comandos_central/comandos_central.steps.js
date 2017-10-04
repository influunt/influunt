'use strict';

var ComandoCentral = require('../../support/page-objects/comandos_central');

module.exports = function() {
  var comandoCentral = new ComandoCentral();

  this.Given(/^o sistema dever possuir o anel "([^"]*)" sincronizado$/, function (anelCla) {
    return comandoCentral.verificaAnelSincronizado(anelCla);
  });

  this.Given(/^o usuário selecionar o anel "([^"]*)" para configuração$/, function (anelCla) {
    return comandoCentral.selecionarAnelSincronizado(anelCla);
  });

  this.Given(/^o usuário clicar no botão ações$/, function () {
    return comandoCentral.clicarBotaoAcoes();
  });

  this.Given(/^o usuário no campo "([^"]*)" selecionar o valor de "([^"]*)"$/, function (select, valor) {
    return comandoCentral.selecionarValor(select, valor);
  });

  this.Given(/^o sistema deverá apresentar erro com a mensagem "([^"]*)"$/, function (msg) {
    return comandoCentral.getErrorMsgs(msg);
  });

  this.Given(/^o botão deverá estar "([^"]*)"$/, function (status) {
    return comandoCentral.botaoStatus(status);
  });

  this.Given(/^o usuário escolher "([^"]*)"$/, function (value) {
    return comandoCentral.checkRadio(value);
  });

  this.Given(/^deve ser exibido "([^"]*)" aneis sincronizados na listagem$/, function(quantidade) {
    return comandoCentral.getItensTabela(quantidade);
  });

  this.Given(/^o usuário acessar a tela de listagem de comandos da central$/, function() {
    return comandoCentral.indexPage();
  });

};
