'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var PlanosPage = function () {

  this.isPlanos = function() {
    return world.waitFor('ul[class="menu-planos"]');
  };

  this.selecionarModoOperacao = function(modoOperacao) {
    var modos = ['Atuado', 'Coordenado', 'Isolado', 'Intermitente', 'Apagado'];
    var index = modos.indexOf(modoOperacao) + 1;
    return world.waitFor('[name="modoOperacao"]').then(function(){
      return world.getElement('[name="modoOperacao"] option:nth-child('+index+')').click().then(function() {
      return world.sleep(500);
      });
    });
  };

  this.isTabelaEntreVerdesHidden = function() {
    return world.waitForInverse('select[name="tabelaEntreverdes"]').then(true);
  };

  this.isTabelaEntreVerdesVisible = function() {
    return world.waitFor('select[name="tabelaEntreverdes"]').then(true);
  };

  this.isTempoDeCicloHidden = function() {
    return world.waitForInverse('influunt-knob[title="TEMPO DE CICLO"]');
  };

  this.isEstagioDispensavelHidden = function() {
    return world.waitForInverse('input[name="dispensavel"]').then(true);
  };

  this.isTempoDeCicloVisible = function() {
    return world.waitFor('influunt-knob[title="TEMPO DE CICLO"]');
  };

  this.isTempoDefasagemHidden = function() {
    return world.waitForInverse('influunt-knob[title="DEFASAGEM"]');
  };

  this.isTempoDefasagemVisible = function() {
    return world.waitFor('influunt-knob[title="DEFASAGEM"]');
  };

  this.hiddenDiagramaIntervalo = function() {
    return world.waitForByXpathInverse('//h5[text()="Diagrama de Intervalos"]');
  };

  this.clicarBotaoConfigurarEstagio = function(estagio) {
    world.waitForOverlayDisappear();
    world.sleep(300);

    return world.getElementByXpath('//ul[contains(@class, "planos")]//h4[contains(@id, "'+estagio+'")]/../button').click().then(function() {
      return world.waitFor('div#modal-configuracao-estagio');
    }).then(function() {
      return world.waitForAnimationFinishes('div.modal-content');
    });
  };

  this.clicarBotaoApagarEstagio = function(estagio) {
    return world.waitForOverlayDisappear()
      .then(function(){
        return world.getElementByXpath('//ul[contains(@class, "planos")]//h4[contains(@id, "'+estagio+'")]/i[contains(@class, "fa-trash")]').click();
      })
      .then(function() {
        return world.waitFor('div#modal-configuracao-estagio');
      })
      .then(function() {
        return world.waitForAnimationFinishes('div.modal-content');
      });
  };

  this.marcarValorConfig = function(field, value) {
    var baseSelector = 'influunt-knob[title="'+field.toUpperCase()+'"]';
    world.sleep(300);
    return world.waitForOverlayDisappear().then(function() {
      return world.getElement(baseSelector + ' p.knob-value').click().then(function() {
        return world.resetValue(baseSelector + ' input.rs-input', value);
      }).then(function() {
        return world.sleep(500);
      });
    });
  };

  this.clicarBotaoCopiar = function() {
    return world.getElement('div#modal-copiar-plano div.modal-footer button').click();
  };

  this.trocarEstagiosDeLugar = function(estagio1, estagio2) {
    var elementFrom, elementTo, locationFrom, locationTo;
    return world.getElementByXpath('//ul[contains(@class, "planos")]//h4[contains(text(), "'+estagio1+'")]/..').then(function(element) {
      elementFrom = element;
      return world.getElementByXpath('//ul[contains(@class, "planos")]//h4[contains(text(), "'+estagio2+'")]/..');
    }).then(function(element) {
      elementTo = element;
      return elementFrom.getLocation();
    }).then(function(loc) {
      locationFrom = loc;
      return elementTo.getLocation();
    }).then(function(loc) {
      locationTo = loc;
      var diffX = (locationTo.x - locationFrom.x) * 1.5;
      return world.dragAndDrop(elementFrom, { x: diffX, y: 0 });
    });
  };

  this.numeroEstagiosIncluidos = function(numeroEstagios) {
    return world.getElements('div.sortable-list').then(function(elements) {
      return elements.length === numeroEstagios;
    });
  };

  this.clicarBotaoAddPlano = function(estagio) {
    return world.getElementByXpath('//div[contains(@class, "add-card-container")]//p[contains(text(), "'+estagio+'")]').click();
  };

  this.clicarBotaoAcaoPlano = function(action, plano) {
    return world.getElementByXpath('//ul[@id="side-menu"]//span[contains(text(), "'+plano+'")]/..//i[contains(@class, "'+action+'")]').click().then(function() {
      return world.sleep(500);
    });
  };

  this.textoConfirmacaoEditarPlano = function() {
    return world.getTextInSweetAlert();
  };

  this.preencherCampoEditarPlano = function(valor) {
    var campo = 'input[type="text"]';
    return world.setValue(campo, valor);
  };

  this.selecionarPlano = function(valor) {
    var campo = 'controladores';
    return world.select2OptionByXpath(campo, valor);
  };

  this.nomePlanoAlterado = function(plano) {
    return world.waitForByXpath('//ul[@id="side-menu"]//span[contains(text(), "'+plano+'")]');
  };

  this.getTextInModal = function() {
    var modal = 'div[class*="modal-content"] h3';
    return world.waitFor(modal).then(function() {
      return world.getElement(modal).getText();
    });
  };

  this.isPlanoAtivo = function(plano) {
    return world.waitForByXpath('//ul[@id="side-menu"]//span[contains(text(), "'+plano+'")]/..//div[contains(@class, "checked")]');
  };

  this.errosInPlanos = function(numeroPlano){
    return world.waitForByXpath('//li[contains (@id, "'+numeroPlano+'")]//span[contains (@class, "badge-danger")]');
  };

  this.erroInEstagio = function(estagio){
    return world.waitForByXpath('//h4[contains (@id, "'+estagio+'")]//span[contains (@class, "badge-danger")]');
  };

  this.erroDefasagem = function(){
    var xpath = '//span[contains(@messages, "[erroDefasagem()]")][contains(@class, "ng-hide")]';
    return world.waitForByXpathInverse(xpath);
  };

  this.clickInPlano = function(numeroPlano){
    return world.waitForOverlayDisappear().then(function() {
      return world.getElementByXpath('//li[contains (@id, "'+numeroPlano+'")]//ins[contains(@class, "iCheck-helper")]').click();
    });
  };

  this.clicarSimVerdeSeguranca = function(){
    return world.waitFor('div[class^="sweet-alert"][class$="visible"]').then(function() {
      return world.getElementByXpath('//button[contains(@class, "confirm")]').click();
    });
  };

  this.clicarConfirmacaoAlert = function(){
    return world.waitFor('div[class^="sweet-alert"][class$="visible"]')
      .then(function() {
        return world.getElementByXpath('//button[contains(@class, "confirm")]').click();
      })
      .then(function() {
        return world.sleep(100);
      });
  };

  this.selectGrupoInDiagrama = function(grupo) {
    return world.waitForOverlayDisappear().then(function() {
      return world.getElementByXpath('//input[contains(@type, "checkbox")][contains(@name, "'+grupo+'")]').click();
    });
  };

  this.alertVerdeSeguranca = function() {
    return world.getTextInSweetAlert();
  };

  this.checkEstagioDispensavel = function() {
    return world.waitForByXpath('//input[contains(@name, "dispensavel")]').then(function() {
      return world.getElementByXpath('//input[contains(@name, "dispensavel")]/..').click();
    });
  };

  this.selecionaEstagioQueRecebeDispensavel = function(estagio) {
    return world.getElementByXpath('//select[contains(@name, "tipoEstagio")]//option[contains(text(), "'+estagio+'")]').click();
  };

  this.deveConterEstagioQueRecebeDispensavel = function(estagio) {
    return world.waitForByXpath('//select[contains(@name, "tipoEstagio")]//option[contains(@selected, "selected")][contains(text(), "'+estagio+'")]');
  };

  this.selecionarCicloDuplo = function() {
    return world.getElementByXpath('//input[contains(@name, "cicloDuplo")]//following-sibling::ins').click();
  };

  this.valorDoKnob = function(valor, Knob) {
    return world.waitForByXpath('//influunt-knob[contains(@title, "'+Knob+'")]//p[contains(@class, "knob-value")][contains(text(), "'+valor+'")]');
  };
};

module.exports = PlanosPage;
