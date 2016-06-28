'use strict';

var worldObj = require('../world');
var world = new worldObj.World();
var lastIndex = 0;

var CidadesPage = function () {
  var INDEX_PATH = '/app/cidades';
  var NEW_PATH = '/app/cidades/new';

  var inputNomeCidade = '[name="cidade_nome"]';
  var submitButton = '[name="commit"]';
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
    this.newPage();
    this.fillCidadeForm();
    return world.waitFor('#toast-container div');
  };

  this.toastMessage = function() {
    return world.waitFor('#toast-container div').then(function() {
      return world.getElement('#toast-container div').getText();
    });
  };

  this.textoExisteNaTabela = function(text) {
    return world.getElementsByXpath('//td[contains(text(), "'+text+'")]');
  };

  this.fillCidadeForm = function(nomeCidade) {
    nomeCidade = nomeCidade || 'Cidade '+lastIndex++;
    world.setValue(inputNomeCidade, nomeCidade);
    world.clickButton(submitButton);
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
    return world.findLinkByText(texto).click();
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

};

module.exports = CidadesPage;
