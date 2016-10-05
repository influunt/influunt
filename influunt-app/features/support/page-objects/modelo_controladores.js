'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var ModelosPage = function () {
  var INDEX_PATH = '/app/modelos';
  var NEW_PATH = '/app/modelos/new';

  var formModelos = 'form[name="formModelos"]';
  var inputDescricaoModelo = '[name="descricao"]';

  var totalModelosIndex = 2;

  this.existeAoMenosUmModelo = function() {
    return world.execSqlScript('features/support/scripts/modelos_controladores/create_modelo_controlador.sql');
  };

  this.indexPage = function() {
    world.visit(INDEX_PATH);
   };

  this.getItensTabela = function() {
    return world.sleep(1000).then(function() {
      return world.getElements('tbody tr td');
    });
  };

  this.clicarBotaoNovoModelo = function() {
    return world.clickButton('a[href*="/modelos/new"]');
  };

  this.formModelos = function() {
    return world.getElement(formModelos);
  };

  this.newPage = function() {
    return world.visit(NEW_PATH).then(function(){
      return world.waitFor(formModelos);
    });
  };

  this.textoExisteNaTabela = function(text) {
    return world.waitForByXpath('//td[contains(text(), "'+text+'")]').then(function() {
      return world.getElementByXpath('//td[contains(text(), "'+text+'")]');
    });
  };

  this.isIndex = function() {
    return world.getElements('tbody tr[data-ng-repeat="modelos in lista"]');
  };

  this.isShow = function() {
    return world.getElement('h5 small').getText().then(function(text) {
      return text.match(/ - #/);
    });
  };

  this.textoFieldDescricaoModelo = function() {
    return world.waitFor(inputDescricaoModelo).then(function() {
      return world.getElement(inputDescricaoModelo);
    }).then(function(element) {
     return element.getAttribute('value');
    });
  };

  this.textoConfirmacaoApagarRegistro = function() {
    return world.getElement('div[class*="sweet-alert"] p').getText();
  };

  this.limparCampoDescricao = function() {
    return world.clearField(inputDescricaoModelo);
  };

  this.nenhumModeloDeveSerExcluido = function() {
    return world.getElements('tbody tr[data-ng-repeat="modelo in lista"]').then(function(elements) {
      return elements.length === totalModelosIndex;
    });
  };

  this.toastMessage = function() {
    return world.sleep(1000).then(function() {
      return world.waitFor('#toast-container div.toast-message').then(function() {
        return world.getElement('#toast-container div').getText();
      });
    });
  };
};

module.exports = ModelosPage;
