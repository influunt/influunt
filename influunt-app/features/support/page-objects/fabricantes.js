'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var FabricantesPage = function () {
  var INDEX_PATH = '/app/fabricantes';
  var NEW_PATH = '/app/fabricantes/new';

  var inputNomeFabricante = '[name="nome"]';
  var novoFabricanteButton = 'a[href*="/fabricantes/new"]';

  var totalFabricantesIndex = 0;

  this.world = world;

  this.indexPage = function() {
    world.visit(INDEX_PATH);
    world.getElements('tbody tr[data-ng-repeat="fabricante in lista"]').then(function(elements) {
      totalFabricantesIndex = elements.length;
    });
    return world.waitFor('tbody tr[data-ng-repeat="fabricante in lista"]');
  };

  this.getPageTitleH2 = function() {
    return world.getElement('h2').then(function(element) {
      return element.getText();
    });
  };

  this.isIndex = function() {
    return world.sleep().then(function() {
      return world.getElementByXpath('//div[contains(@class, "page-fabricantes_show")]');
    });
  };

  this.newPage = function() {
    return world.visit(NEW_PATH);
  };

  this.existeAoMenosUmFabricante = function() {
    return world.execSqlScript('features/support/scripts/fabricantes/create_fabricante.sql');
  };

  this.textoExisteNaTabela = function(text) {
    return world.getElementsByXpath('//td[contains(text(), "'+text+'")]');
  };

  this.preencherNome = function(nome) {
    return world.setValue('[name="fabricante_nome"]', nome);
  };

  this.fieldNomeFabricante = function() {
    return world.getElement(inputNomeFabricante);
  };

  this.textoFieldNomeFabricante = function() {
    return this.fieldNomeFabricante().getAttribute('value');
  };

  this.getItensTabela = function() {
    return world.getElements('tbody tr td');
  };

  this.clicarBotaoNovoFabricante = function() {
    return world.clickButton(novoFabricanteButton);
  };

  this.fabricanteIdH5 = function() {
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

  this.fabricanteDeveSerExcluido = function() {
    return world.getElements('tbody tr[data-ng-repeat="fabricante in lista"]').then(function(elements) {
      return elements.length === totalFabricantesIndex - 1;
    });
  };

  this.nenhumFabricanteDeveSerExcluido = function() {
    return world.getElements('tbody tr[data-ng-repeat="fabricante in lista"]').then(function(elements) {
      return elements.length === totalFabricantesIndex;
    });
  };

  this.clicarBotaoNovoModelo = function() {
    return world.findLinkByText('adicionar modelo controlador').click();
  };

  this.preencherDescricaoModelo = function(descricao) {
    return world.setValue('[name="fabricante_modelo_descricao"]', descricao);
  };

  this.selecionarConfiguracao = function(config) {
    return world.selectOption('[name="configuracao"]', config);
  };

};

module.exports = FabricantesPage;
