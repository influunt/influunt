'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var ObjetosComuns = function () {
  this.cadastrarControlador = function() {
    return world.execSqlScript('features/support/scripts/planos/controlador.sql');
  };

  this.cadastrarPlanoParaControlador = function() {
    return world.execSqlScript('features/support/scripts/controladores/plano_controlador.sql');
  };

  this.controladoresAreasDiferentes = function() {
    return world.execSqlScript('features/support/scripts/controladores/controladores_por_areas.sql');
  };

  this.clicarLinkNovo = function() {
    return world.waitForOverlayDisappear().then(function (){
      return world.getElement('i[class="fa fa-plus"]').click();
    });
  };

  this.clicarBotaoModal = function(modal) {
    world.sleep(500);
    return world.waitForOverlayDisappear().then(function() {
      return world.waitFor('div#'+modal+'').then(function(){
        return world.getElement('div#'+modal+' div.modal-footer button').click();
      });
    });
  };

  this.clicarLinkComTexto = function(texto) {
    return world.waitForOverlayDisappear().then(function (){
      return world.findLinkByText(texto).click();
    });
  };

  this.trocarAnel = function(numeroAnel) {
    var xpath = ('//li[contains(@aria-selected, "false")]//a[contains(text(), "Anel '+numeroAnel+'")]');
    return  world.sleep(300).then(function(){
      return world.getElementByXpath(xpath).click();
    });
  };

  this.limparCampo = function(campo) {
    return world.clearField(campo);
  };

  this.errosImpeditivos = function(texto){
    return world.waitForByXpath('//div[contains (@class, "alert")]//li[contains(text(), "'+texto+'")]');
  };

  this.textoConfirmacaoApagarRegistro = function() {
    return world.getTextInSweetAlert();
  };

  this.botaoConfirmSweetAlert = function() {
    return world.waitFor('div[class^="sweet-alert"][class$="visible"]').then(function() {
      return world.clickButton('div[class^="sweet-alert"] button.confirm');
    });
  };

  this.realizarScrollUp = function(){
    return world.scrollToUp();
  };

  this.realizarScrollDown = function(){
    return world.scrollToDown();
  };

  // verifica diagrama
  this.isDiagramaModo = function(modoOperacao, grupo, indicacaoCor, tempo) {
    var _this = this;
    var script = 'return $("div#visualization div.vis-left div.vis-label:contains(\''+grupo+'\')").index() + 1;';
    return world.execJavascript(script).then(function(indexGrupo) {
      switch(modoOperacao) {
        case 'Apagado':
          return _this.isDiagramaApagado();
        case 'Intermitente':
          return _this.isDiagramaIntermitente();
        case 'Coordenado':
          return _this.verifyDiagramaValues(indexGrupo, indicacaoCor, tempo);
        case 'Isolado':
          return _this.verifyDiagramaValues(indexGrupo, indicacaoCor, tempo);
        default:
          throw new Error('Modo de operação não reconhecido: '+modoOperacao);
      }
    });
  };

  this.deslogar = function(){
    return world.waitForOverlayDisappear().then(function() {
      return world.waitForToastMessageDisapear().then(function() {
        return world.getElement('i.fa-sign-out').click();
      });
    });
  };

  this.logarNoSistema = function(createSqlpath, user, password){
    return world.logar(createSqlpath, user, password);
  };

  this.navegarBreadcrumb = function(opcao){
    return world.waitForOverlayDisappear().then(function() {
      return world.getElementByXpath('//li[contains(@data-ng-repeat, "breadcrumb")]//a[contains(text(), "'+opcao+'")]').click();
    });
  };

  this.isIndexPage = function() {
    var novoControladorButton = 'a[ui-sref="app.wizard_controladores.dados_basicos"]';
    return world.waitFor(novoControladorButton);
  };

  this.isDiagramaApagado = function() {
    return world.waitFor('div#visualization div.vis-foreground div.indicacao-apagado');
  };

  this.isDiagramaIntermitente = function() {
    return world.waitFor('div#visualization div.vis-foreground div.indicacao-intermitente');
  };

  this.checarTotalInseridosNaTabela = function(quantidade) {
    return world.sleep(300).then(function(){
      return world.countTableSize(quantidade);
    });
  };

  this.verifyDiagramaValues = function(indexGrupo, indicacaoCor, tempo) {
    var grupo = 'div[contains(@class, "vis-group")]['+indexGrupo+']';
    var cor = 'div[contains(@class, "'+indicacaoCor+'")]';
    var time = 'div[text()="'+tempo+'"]';

    var xpathSelector = '//div[contains(@id, "visualization")]//div[contains(@class, "vis-foreground")]//'+grupo+'//'+cor+'//'+time+'';
    return world.waitForByXpath(xpathSelector).then(function(){
      return world.getElementByXpath(xpathSelector);
    });
  };

  this.alertInfluuntKnob = function() {
    return world.getTextInSweetAlert();
  };

  this.getErrorMessageFor = function(campo) {
    world.sleep(400);
    return world.waitFor('[name="'+campo+'"] + p[class*="error-msg"]').then(function() {
      return world.getElement('[name="'+campo+'"] + p[class*="error-msg"]').getText();
    });
  };

  this.form = function(nameForm) {
    return world.getElement('form[name="form'+nameForm+'"]');
  };
};

module.exports = ObjetosComuns;
