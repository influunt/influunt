'use strict';

var worldObj = require('../world');
var world = new worldObj.World();
var lastIndex = 0;

var ConfiguracoesPage = function () {
  var INDEX_PATH = '/app/configuracoes_controladores';
  var NEW_PATH = '/app/configuracoes_controladores/new';

  var inputDescConfig = '[name="configuracoes_controladores_descricao"]';
  var inputLimitAneis = '[name="configuracoes_controladores_limite_anel"]';
  var inputLimiteGS = '[name="configuracoes_controladores_limite_grupo_semaforico"]';
  var inputLimiteEstagio = '[name="configuracoes_controladores_limite_estagio"]';
  var inputLimiteDP = '[name="configuracoes_controladores_limite_detector_pedestre"]';
  var inputLimiteDV = '[name="configuracoes_controladores_limite_detector_veicular"]';
  var novaConfiguracaoButton = 'a[href*="/configuracoes_controladores/new"]';

  var formConfiguracao = 'form[name="formConfiguracoesControladores"]'

  this.world = world;

  var totalConfiguracoesIndex = 0;

  this.newPage = function() {
    world.visit(NEW_PATH);
    return world.waitFor(inputDescConfig);
  };

  this.isIndex = function() {
    return world.getElements('tbody tr[data-ng-repeat="configuracao in lista"]');
  };

  this.isShow = function() {
    return world.getElement('h5 small').getText().then(function(text) {
      return text.match(/- #/);
    });
  };

  this.indexPage = function() {
    world.visit(INDEX_PATH);
    world.getElements('tbody tr[data-ng-repeat="configuracao in lista"]').then(function(elements) {
      totalConfiguracoesIndex = elements.length;
    });
    return world.waitFor('tbody tr[data-ng-repeat="configuracao in lista"]');
  };

  this.existeAoMenosUmaConfiguracao = function() {
    this.newPage();
    this.fillConfiguracaoForm();
    return world.waitFor('#toast-container div');
  };

  this.fillConfiguracaoForm = function(descricao) {
    return world.setValue(inputDescConfig, descricao).then(function() {
      return world.setValue(inputLimitAneis, 1);
    }).then(function() {
      return world.setValue(inputLimiteGS, 1);
    }).then(function() {
      return world.setValue(inputLimiteEstagio, 1);
    }).then(function() {
      return world.setValue(inputLimiteDP, 1);
    }).then(function() {
      return world.setValue(inputLimiteDV, 1);
    }).then(function() {
      return world.clickButton('input[name="commit"]');
    })
  };

  this.textoFieldDescricaoConfiguracao = function() {
    return world.getElement(inputDescConfig).then(function(element) {
      return element.getAttribute('value');
    })
  };

  this.clicarLinkComTexto = function(texto) {
    return world.findLinkByText(texto).click();
  };

  this.clicarBotaoNovaConfiguracao = function() {
    return world.clickButton(novaConfiguracaoButton);
  };

  this.getItensTabela = function() {
    return world.getElements('tbody tr td');
  };

  this.formConfiguracao = function() {
    return world.getElement(formConfiguracao);
  };

  this.textoExisteNaTabela = function(text) {
    return world.getElementsByXpath('//td[contains(text(), "'+text+'")]');
  };

  this.getUrl = function() {
    return world.getCurrentUrl();
  };

  this.textoConfirmacaoApagarRegistro = function() {
    return world.getElement('div[class*="sweet-alert"] p').getText();
  };

  this.toastMessage = function() {
    return world.waitFor('#toast-container div').then(function() {
      return world.getElement('#toast-container div').getText();
    });
  };

  this.nenhumaConfiguracaoDeveSerExcluida = function() {
    return world.getElements('tbody tr[data-ng-repeat="configuracao in lista"]').then(function(elements) {
      return elements.length === totalConfiguracoesIndex;
    });
  };

};

module.exports = ConfiguracoesPage;
