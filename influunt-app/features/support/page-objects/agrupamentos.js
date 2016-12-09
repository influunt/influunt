'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var AgrupamentosPage = function () {
  var NEW_PATH = '/app/agrupamentos/new';

  var formAgrupamentos = 'form[name="formAgrupamentos"]';
  var inputNomeAgrupamento = '[name="nome"]';

  var selects = {
    'Dias':                            '[name="agrupamentoPlanoDiaSemana"]',
    'Hora':                            '[name="planoHora"]',
    'Minuto':                          '[name="planoMinuto"]',
    'Segundo':                         '[name="planoSegundo"]',
    'Plano':                           '[name="posicaoPlano"]'
  };

  this.existeAoMenosUmAgrupamento = function() {
    return world.execSqlScript('features/support/scripts/agrupamentos/create_agrupamento.sql');
  };

  this.getItensTabela = function() {
    return world.getElements('tbody tr td');
  };

  this.formAgrupamentos = function() {
    return world.getElement(formAgrupamentos);
  };

  this.newPage = function() {
    return world.visit(NEW_PATH).then(function(){
      return world.waitFor(formAgrupamentos);
    });
  };

  this.textoExisteNaTabela = function(text) {
    return world.waitForByXpath('//td[contains(text(), "'+text+'")]').then(function() {
      return world.getElementByXpath('//td[contains(text(), "'+text+'")]');
    });
  };

  this.isIndex = function() {
    return world.getElements('tbody tr[data-ng-repeat="agrupamentos in lista"]');
  };

  this.isShow = function() {
    return world.waitForByXpath('//td[contains(text(), "Corredor da Paulista")]');
  };

  this.textoFieldNomeAgrupamento = function() {
    return world.waitFor(inputNomeAgrupamento).then(function() {
      return world.getElement(inputNomeAgrupamento);
    }).then(function(element) {
     return element.getAttribute('value');
    });
  };

  this.textoConfirmacaoApagarRegistro = function() {
    return world.getElement('div[class*="sweet-alert"] p').getText();
  };

  this.nenhumAgrupamentoDeveSerExcluido = function() {
    return world.getElements('tbody tr[data-ng-repeat="agrupamento in lista"]').then(function(elements) {
      return elements.length === 1;
    });
  };

  this.alertaAtulizarTabelaHoraria = function() {
    return world.getTextInSweetAlert();
  };

  this.selecionarValor = function(valor, select) {
    return world.waitForOverlayDisappear().then(function (){
      return world.selectByOptionAtribute('div', selects[select], 'value', valor);
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

module.exports = AgrupamentosPage;
