'use strict';

var worldObj = require('../world');
var world = new worldObj.World();
var lastIndex = 0;

var FabricantesPage = function () {
  var INDEX_PATH = '/app/fabricantes';
  var NEW_PATH = '/app/fabricantes/new';

  var inputNomeFabricante = '[name="fabricante_nome"]';
  var submitButton = '[name="commit"]';
  var novaFabricanteButton = 'a[href*="/fabricantes/new"]';

  var totalFabricantesIndex = 0;

  this.world = world;

  this.indexPage = function() {
    world.visit(INDEX_PATH);
    world.getElements('tbody tr[data-ng-repeat="fabricante in lista"]').then(function(elements) {
      totalFabricantesIndex = elements.length;
    });
    // console.log('totalFabricantesIndex: '+totalFabricantesIndex);
    return world.waitFor('tbody tr[data-ng-repeat="fabricante in lista"]');
  };

  this.getPageTitleH2 = function() {
    return world.getElement('h2').then(function(element) {
      return element.getText();
    });
  };

  this.newPage = function() {
    world.visit(NEW_PATH);
    return world.waitFor(inputNomeFabricante);
  };

  this.existeAoMenosUmFabricante = function() {
    this.newPage();
    this.fillFabricanteForm();
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

  this.fillFabricanteForm = function(nomeFabricante) {
    nomeFabricante = nomeFabricante || 'Fabricante '+lastIndex++;
    world.setValue(inputNomeFabricante, nomeFabricante);
    return world.clickButton(submitButton);
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
    return world.clickButton(novaFabricanteButton);
  };

  this.clicarLinkComTexto = function(texto) {
    return world.findLinkByText(texto).click();
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

  this.fillFabricanteFormFull = function(nomeFabricante) {
    nomeFabricante = nomeFabricante || 'Fabricante '+lastIndex++;
    console.log(nomeFabricante);
    world.setValue(inputNomeFabricante, nomeFabricante);
    world.findLinkByText('adicionar modelo controlador').then(function(link){
      return link.click();
    }).then(function(){
      return world.setValue('input[ng-model="modelo.descricao"]', 'modelo '+lastIndex++);
    }).then(function() {
      return world.selectOption('select[name="configuracao"]', 'Descrição Config');
    }).then(function() {
      return world.clickButton('input[name="commit"]');
    })

  };

};

module.exports = FabricantesPage;
