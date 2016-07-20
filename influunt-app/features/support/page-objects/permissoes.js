'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var PermissoesPage = function () {
  var INDEX_PATH = '/app/permissoes';
  var NEW_PATH = '/app/permissoes/new';

  var indexTableSelector = 'tbody tr[data-ng-repeat="permissao in lista"]'
  var inputDescPermissao = '[name="descricao"]';
  var novaPermissaoButton = 'a[href*="/permissoes/new"]';
  var formPermissao = 'form[name="formPermissao"]';

  var totalPermissoesIndex = 0;

  this.existeAoMenosUmaPermissao = function() {
    return world.execSqlScript('features/support/scripts/permissoes/create_permissao.sql');
  };

  this.indexPage = function() {
    world.visit(INDEX_PATH);
    world.getElements(indexTableSelector).then(function(elements) {
      totalPermissoesIndex = elements.length;
    });
    return world.waitFor(indexTableSelector);
  };

  this.getItensTabela = function() {
    return world.getElements('tbody tr td');
  };

  this.clicarBotaoNovaPermissao = function() {
    return world.clickButton(novaPermissaoButton);
  };

  this.formPermissoes = function() {
    return world.getElement(formPermissao);
  };

  this.newPage = function() {
    return world.visit(NEW_PATH).then(function(){
      return world.waitFor(formPermissao);
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

  this.textoFieldDescricaoPermissao = function() {
    return world.waitFor(inputDescPermissao).then(function() {
      return world.getElement(inputDescPermissao);
    }).then(function(element) {
     return element.getAttribute('value');
    });
  };

  this.textoConfirmacaoApagarRegistro = function() {
    return world.getElement('div[class*="sweet-alert"] p').getText();
  };

  this.nenhumaPermissaoDeveSerExcluida = function() {
    return world.getElements(indexTableSelector).then(function(elements) {
      return elements.length === totalPermissoesIndex;
    });
  };

  this.toastMessage = function() {
    return world.waitFor('#toast-container div').then(function() {
      return world.getElement('#toast-container div').getText();
    });
  };


  // this.getPageTitleH2 = function() {
  //   return world.getElement('h2').then(function(element) {
  //     return element.getText();
  //   });
  // };

  // this.cidadeIdH5 = function() {
  //   return world.getElement('h5 small').then(function(element) {
  //     return element.getText();
  //   });
  // };

  // this.clicarSimConfirmacaoApagarRegistro = function() {
  //   return world.waitFor('div[class^="sweet-alert"][class$="visible"]').then(function() {
  //     return world.clickButton('div[class^="sweet-alert"] button.confirm');
  //   });
  // };

  // this.clicarNaoConfirmacaoApagarRegistro = function() {
  //   return world.waitFor('div[class^="sweet-alert"][class$="visible"]').then(function() {
  //     return world.clickButton('div[class^="sweet-alert"] button.cancel');
  //   });
  // };

  // this.cidadeDeveSerExcluida = function() {
  //   return world.getElements('tbody tr[data-ng-repeat="cidade in lista"]').then(function(elements) {
  //     return elements.length === totalPermissoesIndex - 1;
  //   });
  // };

};

module.exports = PermissoesPage;
