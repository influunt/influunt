'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var PerfisPage = function () {
  this.world = world;
  var totalPerfis = 0;

  var INDEX_PATH = '/app/perfis';

  this.indexPage = function() {
    world.visit(INDEX_PATH);
    world.getElements('tbody tr[data-ng-repeat="perfil in lista"]').then(function(elements) {
      totalPerfis = elements.length;
    });
    return world.waitFor('tbody tr[data-ng-repeat="perfil in lista"]');
  };


  this.clicarNoLinkDoPerfil = function(perfil, botao) {
    return world.waitForOverlayDisappear().then(function (){
      return world.getElementByXpath('//tr//td[contains(text(), "'+perfil+'")]//following-sibling::td//a[contains(text(), "'+botao+'")]').click();
    });
  };

  this.marcarPermissao = function(permissao) {
    return world.waitForOverlayDisappear().then(function (){
      return world.getElementByXpath('//*[ancestor::tr[td[text()="'+permissao+'"]]][1]').click();
    });
  };

  this.naoPossuiMenu = function(menu) {
    return world.getElementByXpath('//a[contains(@href, "#'+menu+'")][contains(@class, "ng-hide")]');
  };

  this.checkPerfilNaTabela = function(permissao) {
    return world.waitForOverlayDisappear().then(function (){
      return world.getElementByXpath('//tr//td[contains(text(), "'+permissao+'")]');
    });
  };

  this.checkPerfilExcluido = function(permissao) {
    return world.waitForOverlayDisappear().then(function (){
      return world.waitForByXpathInverse('//tr//td[contains(text(), "'+permissao+'")]');
    });
  };

  this.checarPossuiBotao = function(botao) {
    return world.getElementByXpath('//a[contains(text(), "'+botao+'")]');
  };

  this.desabilitarPermissoes = function() {
    return world.execSqlScript('features/support/scripts/perfis/remover_permissoes.sql');
  };

  this.botaoDeveEstarEscondido = function(botao) {
    return world.getElementByXpath('//a[contains(text(), "'+botao+'")][contains(@class, "ng-hide")]');
  };

  this.setArea2 = function() {
    return world.execSqlScript('features/support/scripts/perfis/set_area_2.sql');
  };

  this.naoDeveTerAcesso = function(local) {
    var _this = this;
    return world.visit('/app/'+local+'').then(function(){
      return _this.isDashboard();
    });
  };

  this.possuiAcesso = function(path, title) {
    return world.visit(path).then(function(){
      return world.getElementByXpath('//div[contains(@class, "ibox-title")]//h5[contains(text(), "'+title+'")]');
    });
  };

  this.isDashboard = function() {
    return world.waitFor('strong.ng-binding').then(function(){
      return world.getElement('strong.ng-binding').getText().then(function(text) {
        return text === 'Dashboard';
      });
    });
  };

  this.naoDeveTerAcessoMenu = function(pathName) {
    var menu = '/app/'+pathName+'';
    var _this = this;
    return _this.naoPossuiMenu(menu);
  };

  this.deveTerAcesso = function(local, titulo) {
    var path = '/app/'+local+'';
    var _this = this;

    return _this.possuiAcesso(path, titulo);
  };
};

module.exports = PerfisPage;
