'use strict';

var RelatoriosPage = require('../../support/page-objects/relatorios');

module.exports = function() {
  var relatoriosPage = new RelatoriosPage();

  this.Given(/^o usuário acessar o relatório "([^"]*)"$/, function (localizacao) {
    return relatoriosPage.visitarRelatorio(localizacao);
  });

  this.Given(/^o sistema deverá apresentar na listagem controlador "([^"]*)"$/, function (mensagem) {
    return relatoriosPage.controladorComFalhaNaListagem(mensagem);
  });

  this.Given(/^o usuário selecionar no campo "([^"]*)" selecionar o label "([^"]*)"$/, function (campo, valor) {
    return relatoriosPage.selecionarValor(campo, valor);
  });

  this.Given(/^o usuário clicar em Gerar$/, function () {
    return relatoriosPage.clicarBotaoGerar();
  });

  this.Given(/^o botão gerar deverá estar desabilitado$/, function () {
    return relatoriosPage.botaoGerarDisabled();
  });
};
