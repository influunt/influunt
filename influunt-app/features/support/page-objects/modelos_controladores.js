'use strict';

var worldObj = require('../world');
var world = new worldObj.World();
var lastIndex = 0;

var ModelosControladoresPage = function () {
  var INDEX_PATH = '/app/modelos_controladores';
  var NEW_PATH = '/app/modelos_controladores/new';

  var submitButton = '[name="commit"]';
  var novoModeloButton = 'a[href*="/modelos_controladores/new"]';
  var inputDescricaoModelo = '[name="modelos_controladores_descricao"]';

  var totalModelosIndex = 0;

  this.existeAoMenosUmModeloDeControlador = function() {
    return world.execSqlScript('features/support/scripts/modelos_controladores/create_modelo_controlador.sql');
  };

  this.indexPage = function() {
    world.visit(INDEX_PATH);
    world.getElements('tbody tr[data-ng-repeat="modelo in lista"]').then(function(elements) {
      totalModelosIndex = elements.length;
    });
    return world.waitFor('tbody tr[data-ng-repeat="modelo in lista"]');
  };

  this.getItensTabela = function() {
    return world.getElements('tbody tr td');
  };

  this.clicarBotaoNovoModelo = function() {
    return world.clickButton(novoModeloButton);
  };

  this.fieldDescricaoModelo = function() {
    return world.getElement(inputDescricaoModelo);
  };

  this.newPage = function() {
    return world.visit(NEW_PATH).then(function() {
      return world.waitFor(inputDescricaoModelo);
    });
  };

  this.fillForm = function(descricaoModelo) {
    descricaoModelo = descricaoModelo || 'Modelo '+lastIndex++;
    return world.setValue(inputDescricaoModelo, descricaoModelo).then(function() {
      return world.selectOption('select[name="fabricante"]', 'Raro Labs');
    }).then(function() {
      return world.selectOption('select[name="configuracao"]', 'Mínima');
    }).then(function() {
      return world.clickButton(submitButton);
    });
  };

  this.textoExisteNaTabela = function(text) {
    return world.getElementsByXpath('//td[contains(text(), "'+text+'")]');
  };

  this.getUrl = function() {
    return world.getCurrentUrl();
  };

  this.clicarLinkComTexto = function(texto) {
    return world.findLinkByText(texto).click();
  };

  this.modeloIdH5 = function() {
    return world.getElement('h5 small').then(function(element) {
      return element.getText();
    });
  };

  this.textoFieldDescricaoModelo = function() {
    return this.fieldDescricaoModelo().getAttribute('value');
  };

  this.textoConfirmacaoApagarRegistro = function() {
    return world.getElement('div[class*="sweet-alert"] p').getText();
  };

  this.nenhumModeloDeveSerExcluido = function() {
    return world.getElements('tbody tr[data-ng-repeat="modelo in lista"]').then(function(elements) {
      return elements.length === totalModelosIndex;
    });
  };

  this.toastMessage = function() {
    return world.waitFor('#toast-container div').then(function() {
      return world.getElement('#toast-container div').getText();
    });
  };

};

module.exports = ModelosControladoresPage;
