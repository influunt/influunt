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

  this.cadastrarTabelaHorariaParaControlador = function() {
    return world.execSqlScript('features/support/scripts/controladores/tabela_horaria_controlador.sql');
  };

  this.controladoresAreasDiferentes = function() {
    return world.execSqlScript('features/support/scripts/controladores/controladores_por_areas.sql');
  };

  this.variosControladores = function() {
    return world.execSqlScript('features/support/scripts/controladores/controladores.sql');
  };

  this.variosControladoresConfigurados = function() {
    return world.execSqlScript('features/support/scripts/controladores/controladores_finalizados.sql');
  };

  this.desabilitarPermissoes = function() {
    return world.execSqlScript('features/support/scripts/perfis/remover_permissoes.sql');
  };

  this.clicarLinkNovo = function() {
    return world.waitForOverlayDisappear().then(function (){
      return world.getElement('i[class="fa fa-plus"]').click();
    });
  };

  this.isListagemControladores = function() {
    return world.waitForOverlayDisappear().then(function (){
      return world.waitForByXpath('//h5[contains(text(), "Programação")]');
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
    return world.sleep(300).then(function(){
      return world.getElementByXpath(xpath).click();
    });
  };

  this.limparCampo = function(campo) {
    return world.clearField(campo);
  };

  this.errosImpeditivos = function(texto){
    return world.waitForByXpath('//div[contains (@class, "alert")]//li[contains(text(), "'+texto+'")]');
  };

  this.textoSweetAlert = function() {
    return world.sleep(400).then(function(){
      return world.getTextInSweetAlert();
    });
  };

  this.botaoConfirmSweetAlert = function() {
    return world.waitFor('div[class^="sweet-alert"][class$="visible"]').then(function() {
      return world.clickButton('div[class^="sweet-alert"] button.confirm');
    });
  };

   this.preencherSweetAlert = function(descricao) {
    return world.waitFor('div[class^="sweet-alert"][class$="visible"]').then(function() {
      return world.setValueByXpath('//input[contains(@type, "text")][contains(@tabindex, "3")]', descricao);
    });
  };

  this.realizarScrollUp = function(){
    return world.scrollToUp();
  };

  this.realizarScrollDown = function(){
    return world.scrollToDown();
  };

  this.realizarScrollDownModal = function() {
    return world.scrollToDownModal();
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

  this.checarClcControladorListagem = function(numero) {
    return world.getElementByXpath('//td[contains(text(), "'+numero+'.000.0001")]');
  };

  this.checarValoresNaTabela = function(valor) {
    return world.waitForOverlayDisappear().then(function() {
      return world.sleep(1000);
    }).then(function() {
      return world.getElementByXpath('//td[contains(text(), "'+valor+'")]');
    });
  };

  this.clicarEditarEmResumo = function(selectorText) {
    return world.waitForOverlayDisappear().then(function() {
      return world.getElementByXpath('//button[contains(@tooltip-template, "'+selectorText+'")]').click();
    });
  };

  this.clicarBotaoEspecificoTabelaControladores = function(botao, controlador) {
    var _this = this;
    return world.waitForOverlayDisappear().then(function() {
      return _this.botaoAcaoControladores(botao, controlador).click();
    });
  };

  this.botaoAcaoControladores = function(botao, controlador) {
    var _this = this;
    return world.getElementByXpath(_this.xpathBotoesControladores(botao,controlador));
  };

  this.naoPodeMostraBotao = function(botao, controlador) {
    var _this = this;
    return world.waitForOverlayDisappear().then(function() {
      return world.waitForByXpathInverse(_this.xpathBotoesControladores(botao,controlador));
    });
  };

  this.xpathBotoesControladores = function(botao, controlador) {
   return '//td[contains(text(), "'+controlador+'")]//following-sibling::td//a[contains(@tooltip-template, "'+botao+'")]';
  };

  this.selecionarBySelect2Option = function(field, option) {
    return world.select2OptionByXpath(field, option);
  };

  this.selectBySelectOptionAtribute = function(campo, selectSelector, optionAtribute, value) {
    return world.sleep(300).then(function(){
      return world.selectByOptionAtribute(campo, selectSelector, optionAtribute, value);
    });
  };

  this.removeSelect2Option = function(option, field) {
    return world.sleep(400).then(function(){
      return world.getElementByXpath('//li[contains(@title, "'+option+'")]//span').click().then(function(){
        return world.getElementByXpath('//select[contains(@name, "'+field+'")]//following::li[contains(@class, "select2-search")]').click();
      });
    });
  };

  this.visitarListagem = function(local) {
    return world.visit('/app/'+local+'');
  };

  this.fecharModal = function(modal) {
    return world.sleep(600).then(function(){
      return world.getElementByXpath('//div[contains(@id, "'+modal+'")]//button[contains(text(), "Fechar")]').click();
    });
  };

  this.checarBadgeStatusControlador = function(status) {
    var _this = this;
    switch(status) {
      case 'Em Edição':
        return _this.getBadgeByClassStatus('badge-warning', status);
      case 'Configurado':
        return _this.getBadgeByClassStatus('badge-primary', status);
      default:
        throw new Error('Status não encontrado: '+status);
    }
  };

  this.getBadgeByClassStatus = function(badge, text) {
    return world.waitForByXpath('//span[contains(@class, "'+badge+'")][contains(text(), "'+text+'")]');
  };

  this.naoConsigaSelecionar = function(campo, valueToSelector) {
    return world.waitForByXpathInverse('//select[contains(@name, "'+campo+'")]//option[contains(@label, "'+valueToSelector+'")]');
  };

  this.checkPosicaoHistorico = function(posicao, data) {
    return world.waitForOverlayDisappear().then(function() {
      return world.waitForByXpath('(//div[contains(@class, "vertical-timeline-content")])['+posicao+']//small[contains(text(), "'+data+'")]');
    });
  };

  this.preencherCampo = function(campo, valor) {
    world.sleep(500);
    return world.setValue('[name="'+campo+'"]', valor);
  };

  this.clicarVisualizarResumo = function() {
    return world.getElementByXpath('//i[contains(@class, "fa-eye")]').click();
  };

  this.verificarValoresEmLinhasNaTabela = function(valor) {
    return world.waitForOverlayDisappear().then(function() {
      return world.sleep(1000);
    }).then(function() {
      return world.getElementByXpath('//li[contains(text(), "'+valor+'")]');
    });
  };
};

module.exports = ObjetosComuns;
