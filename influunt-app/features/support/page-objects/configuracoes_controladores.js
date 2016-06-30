'use strict';

var worldObj = require('../world');
var world = new worldObj.World();
var lastIndex = 0;

var ConfiguracoesPage = function () {
  // var INDEX_PATH = '/app/cidades';
  var NEW_PATH = '/app/configuracoes_controladores/new';

  var inputDescConfig = '[name="configuracoes_controladores_descricao"]';
  var inputLimitAneis = '[name="configuracoes_controladores_limite_anel"]';
  var inputLimiteGS = '[name="configuracoes_controladores_limite_grupo_semaforico"]';
  var inputLimiteEstagio = '[name="configuracoes_controladores_limite_estagio"]';
  var inputLimiteDP = '[name="configuracoes_controladores_limite_detector_pedestre"]';
  var inputLimiteDV = '[name="configuracoes_controladores_limite_detector_veicular"]';

  this.world = world;

  this.newPage = function() {
    world.visit(NEW_PATH);
    return world.waitFor(inputDescConfig);
  };

  this.fillForm = function(descricao) {
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

  this.textoExisteNaTabela = function(text) {
    return world.getElementsByXpath('//td[contains(text(), "'+text+'")]');
  };

  this.getUrl = function() {
    return world.getCurrentUrl();
  };

};

module.exports = ConfiguracoesPage;
