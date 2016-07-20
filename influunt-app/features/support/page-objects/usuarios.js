'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var UsuariosPage = function () {
  var INDEX_PATH = '/app/usuarios';
  var NEW_PATH = '/app/usuarios/new';

  var indexTableSelector = 'tbody tr[data-ng-repeat="usuario in lista"]'
  var novoUsuarioButton = 'a[href*="/usuarios/new"]';
  var formUsuario = 'form[name="formUsuarios"]';
  var inputNomeUsuario = '[name="nome"]';

  var totalUsuariosIndex = 0;

  this.existeAoMenosUmUsuario = function() {
    return world.execSqlScript('features/support/scripts/usuarios/create_perfil.sql');
  };

  this.indexPage = function() {
    world.visit(INDEX_PATH);
    world.getElements(indexTableSelector).then(function(elements) {
      totalUsuariosIndex = elements.length;
    });
    return world.waitFor(indexTableSelector);
  };

  this.getItensTabela = function() {
    return world.getElements('tbody tr td');
  };

  this.clicarBotaoNovoUsuario = function() {
    return world.clickButton(novoUsuarioButton);
  };

  this.formUsuario = function() {
    return world.getElement(formUsuario);
  };

  this.newPage = function() {
    return world.visit(NEW_PATH).then(function(){
      return world.waitFor(formUsuario);
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

  this.clicarUltimoLinkComTexto = function(texto) {
    return world.getElementByXpath('(//a[text()="'+texto+'"])[last()]').click();
  };

  this.isShow = function() {
    return world.getElement('h5 small').getText().then(function(text) {
      return text.match(/ - #/);
    });
  };

  this.textoFieldNomeUsuario = function() {
    return world.waitFor(inputNomeUsuario).then(function() {
      return world.getElement(inputNomeUsuario);
    }).then(function(element) {
      return element.getAttribute('value');
    });
  };

  this.textoConfirmacaoApagarRegistro = function() {
    return world.getElement('div[class*="sweet-alert"] p').getText();
  };

  this.nenhumUsuarioDeveSerExcluido = function() {
    return world.getElements(indexTableSelector).then(function(elements) {
      return elements.length === totalUsuariosIndex;
    });
  };

  this.toastMessage = function() {
    return world.waitFor('#toast-container div').then(function() {
      return world.getElement('#toast-container div').getText();
    });
  };
};

module.exports = UsuariosPage;
