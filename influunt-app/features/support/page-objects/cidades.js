'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var CidadesPage = function () {
  var INDEX_PATH = '/app/cidades';
  var NEW_PATH = '/app/cidades/new';

  var inputNomeCidade = '[name="nome"]';
  var novaCidadeButton = 'a[href*="/cidades/new"]';

  var totalCidadesIndex = 0;

  this.world = world;

  this.indexPage = function() {
    world.visit(INDEX_PATH);
    world.getElements('tbody tr[data-ng-repeat="cidade in lista"]').then(function(elements) {
      totalCidadesIndex = elements.length;
    });
    // console.log('totalCidadesIndex: '+totalCidadesIndex);
    return world.waitFor('tbody tr[data-ng-repeat="cidade in lista"]');
  };

  this.getPageTitleH2 = function() {
    return world.getElement('h2').then(function(element) {
      return element.getText();
    });
  };

  this.newPage = function() {
    world.visit(NEW_PATH);
    return world.waitFor(inputNomeCidade);
  };

  this.existeAoMenosUmaCidade = function() {
    return world.execSqlScript('features/support/scripts/cidades/create_cidade.sql');
  };

  this.toastMessage = function() {
    return world.waitFor('#toast-container div.toast-message').then(function() {
      return world.sleep(500);
    }).then(function() {
      return world.getElement('#toast-container div.toast-message').getText();
    });
  };

  this.textoExisteNaTabela = function(text) {
    return world.getElementsByXpath('//td[contains(text(), "'+text+'")]');
  };

  this.clicarBotaoSalvar = function() {
    return world.clickButton('input[type="submit"]');
  };

  this.fieldNomeCidade = function() {
    return world.getElement(inputNomeCidade);
  };

  this.textoFieldNomeCidade = function() {
    return this.fieldNomeCidade().getAttribute('value');
  };

  this.getItensTabela = function() {
    return world.getElements('tbody tr td');
  };

  this.clicarBotaoNovaCidade = function() {
    return world.clickButton(novaCidadeButton);
  };

  this.clicarLinkComTexto = function(texto) {
    return world.waitForOverlayDisappear().then(function (){
      return world.findLinkByText(texto).click();
    });
  };

  this.cidadeIdH5 = function() {
    return world.getElement('h5 small').then(function(element) {
      return element.getText();
    });
  };

  this.clicarSimConfirmacaoApagarRegistro = function() {
    return world.waitFor('div[class^="sweet-alert"][class$="visible"]').then(function() {
      return world.clickButton('div[class^="sweet-alert"] button.confirm');
    });
  };

  this.clicarNaoConfirmacaoApagarRegistro = function() {
    return world.waitFor('div[class^="sweet-alert"][class$="visible"]').then(function() {
      return world.clickButton('div[class^="sweet-alert"] button.cancel');
    });
  };

  this.textoConfirmacaoApagarRegistro = function() {
    return world.getElement('div[class*="sweet-alert"] p').getText();
  };

  this.cidadeDeveSerExcluida = function() {
    return world.getElements('tbody tr[data-ng-repeat="cidade in lista"]').then(function(elements) {
      return elements.length === totalCidadesIndex - 1;
    });
  };

  this.nenhumaCidadeDeveSerExcluida = function() {
    return world.getElements('tbody tr[data-ng-repeat="cidade in lista"]').then(function(elements) {
      return elements.length === totalCidadesIndex;
    });
  };

  this.existeAoMenosUmaCidadeComNome = function(nome) {
    return world.execSqlScript('features/support/scripts/cidades/create_cidade_duplicada.sql');
  };

  this.getErrorMessageFor = function(campo) {
    return world.waitFor('[name="'+campo+'"] + p[class*="error-msg"]').then(function() {
      return world.getElement('[name="'+campo+'"] + p[class*="error-msg"]').getText();
    });
  };

};

module.exports = CidadesPage;
