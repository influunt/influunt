'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var PerfisPage = function () {
  var INDEX_PATH = '/app/perfis';
  var NEW_PATH = '/app/perfis/new';

  var inputNomePerfil = '[name="nome"]';
  var novoPerfilButton = 'a[href*="/perfis/new"]';
  var formPerfil = 'form[name="formPerfil"]';
  var indexTableSelector = 'tbody tr[data-ng-repeat="perfil in lista"]'

  var totalPerfisIndex = 0;

  this.existeAoMenosUmPerfil = function() {
    return world.execSqlScript('features/support/scripts/perfis/create_perfil.sql');
  };

  this.indexPage = function() {
    world.visit(INDEX_PATH);
    world.getElements(indexTableSelector).then(function(elements) {
      totalPerfisIndex = elements.length;
    });
    return world.waitFor(indexTableSelector);
  };

  this.getItensTabela = function() {
    return world.getElements('tbody tr td');
  };

  this.clicarBotaoNovoPerfil = function() {
    return world.clickButton(novoPerfilButton);
  };

  this.formPerfis = function() {
    return world.getElement(formPerfil);
  };

  this.newPage = function() {
    return world.visit(NEW_PATH).then(function(){
      return world.waitFor(formPerfil);
    });
  };

  this.textoExisteNaTabela = function(text) {
    return world.waitForByXpath('//td[contains(text(), "'+text+'")]').then(function() {
      return world.getElementByXpath('//td[contains(text(), "'+text+'")]');
    });
  };

  this.isIndex = function() {
    return world.getElements(indexTableSelector);
  };

  this.clicarLinkComTexto = function(texto) {
    return world.findLinkByText(texto).click();
  };

  this.isShow = function() {
    return world.getElement('h5 small').getText().then(function(text) {
      return text.match(/ - #/);
    });
  };

  this.textoFieldNomePerfil = function() {
    return world.waitFor(inputNomePerfil).then(function() {
      return world.getElement(inputNomePerfil);
    }).then(function(element) {
      return element.getAttribute('value');
    });
  };

  this.textoConfirmacaoApagarRegistro = function() {
    return world.getElement('div[class*="sweet-alert"] p').getText();
  };

  this.nenhumPerfilDeveSerExcluido = function() {
    return world.getElements(indexTableSelector).then(function(elements) {
      return elements.length === totalPerfisIndex;
    });
  };

  this.toastMessage = function() {
    return world.waitFor('#toast-container div').then(function() {
      return world.getElement('#toast-container div').getText();
    });
  };
};

module.exports = PerfisPage;
