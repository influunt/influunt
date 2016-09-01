'use strict';

var worldObj = require('../world');

var world = new worldObj.World();

var PlanosPage = function () {

  this.cadastrarControlador = function() {
    return world.execSqlScript('features/support/scripts/planos/controlador.sql');
  };

  this.clicarBotao = function(button) {
    return world.waitForOverlayDisappear().then(function() {
      return world.findLinkByText(button).click();
    });
  };

  this.isPlanos = function() {
    return world.waitFor('ul[class="menu-planos"]');
  };

  this.selecionarModoOperacao = function(modoOperacao) {
    var modos = ['Atuado', 'Coordenado', 'Isolado', 'Intermitente', 'Apagado'];
    var index = modos.indexOf(modoOperacao) + 1;
    return world.getElement('[name="modoOperacao"] option:nth-child('+index+')').click().then(function() {
      return world.sleep(500);
    });
  };

  this.isDiagramaModo = function(grupo, modoOperacao) {
    var _this = this;
    var script = 'return $("div#visualization div.vis-left div.vis-label:contains(\''+grupo+'\')").index() + 1;';
    return world.execJavascript(script).then(function(indexGrupo) {
      switch(modoOperacao) {
        case 'Apagado':
          return _this.isDiagramaApagado(indexGrupo);
        case 'Intermitente':
          return _this.isDiagramaIntermitente(indexGrupo);
        case 'Operação Normal':
          return _this.isDiagramaOperacaoNormal(indexGrupo);
        default:
          throw new Error('Modo de operação não reconhecido: '+modoOperacao);
      }
    });
  };

  this.isDiagramaApagado = function(indexGrupo) {
    return world.waitFor('div#visualization div.vis-foreground div.vis-group:nth-child('+indexGrupo+') div.indicacao-apagado');
  };

  this.isDiagramaIntermitente = function(indexGrupo) {
    return world.waitFor('div#visualization div.vis-foreground div.vis-group:nth-child('+indexGrupo+') div.indicacao-intermitente');
  };

  this.isDiagramaOperacaoNormal = function(indexGrupo) {
    return world.waitFor('div#visualization div.vis-foreground div.vis-group:nth-child('+indexGrupo+') div.indicacao-vermelho').then(function() {
      return world.waitFor('div#visualization div.vis-foreground div.vis-group:nth-child('+indexGrupo+') div.indicacao-verde');
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
    return world.getElement('ul.planos div.sortable-list li.ui-state-default div.sortable button.btn-primary').click().then(function() {
      return world.waitFor('div#modal-configuracao-estagio');
    }).then(function() {
      return world.waitForAnimationFinishes('div.modal-content');
    });
  };

  this.clicarBotaoApagarEstagio = function(estagio) {
     return world.getElement('ul.planos div.sortable-list li.ui-state-default div.sortable h4.ng-binding i.fa-trash').click().then(function() {
      return world.waitFor('div#modal-configuracao-estagio');
    }).then(function() {
      return world.waitForAnimationFinishes('div.modal-content');
    });
  };

  this.marcarValorConfig = function(field, value) {
    var baseSelector = 'influunt-knob[title="'+field.toUpperCase()+'"]';
    return world.getElement(baseSelector + ' p.knob-value').click().then(function() {
      return world.resetValue(baseSelector + ' input.rs-input', value);
    }).then(function() {
      return world.sleep(500);
    });
  };

  this.clicarBotaoModal = function(modal) {
    return world.getElement('div#'+modal+' div.modal-footer button').click();
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

  this.estagioExcluido = function(numeroEstagios) {
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
    var campo = 'input[type="text"]'
    return world.setValue(campo, valor);
  };

  this.selecionarPlano = function(valor) {
    var campo = 'select[name="controladores"]'
    return world.selectOption(campo, valor);
  };

  this.nomePlanoAterado = function(plano) {
    return world.waitForByXpath('//ul[@id="side-menu"]//span[contains(text(), "'+plano+'")]');
  };

  this.getTextInModal = function() {
    var modal = 'div[class*="modal-content"] h3'
    return world.waitFor(modal).then(function() {
      return world.getElement(modal).getText();
    });
  };

  this.isPlanoAtivo = function(plano) {
    return world.waitForByXpath('//ul[@id="side-menu"]//span[contains(text(), "'+plano+'")]/..//div[contains(@class, "checked")]');
  };
};

module.exports = PlanosPage;
