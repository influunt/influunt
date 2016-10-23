'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var PerfisPage = function () {
  this.world = world;
  var totalPerfis = 0;

  var INDEX_PATH = '/app/perfis';
  var auditoriaPath = '/app/auditorias';
  var programacaoPath = '/app/controladores';
  var cidadesPath = '/app/cidades';
  var areasPath = '/app/areas';
  var modelosPath = '/app/modelos';
  var subareasPath = '/app/subareas';
  var perfisPath = '/app/perfis';
  var simulacaoPath = '/app/simulacao';
  var usuariosPath = '/app/usuarios';
  var fabricantesPath = '/app/fabricantes';

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
      return world.getElementByXpath('//tr//td[contains(text(), "'+permissao+'")]//following-sibling::td').click();
    });
  };

  this.naoPossuiMenu = function(menu) {
    return world.getElementByXpath(menu+'[contains(@class, "ng-hide")]');
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

  this.checarControladorListagem = function(numero) {
    return world.getElementByXpath('//td[contains(text(), "'+numero+'.000.0001")]');
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
      return world.getElementByXpath('//div[contains(@class, "ibox-title")]//h5[contains(text(), '+title+')]');
    });
  };

  this.isDashboard = function() {
    return world.waitFor('strong.ng-binding').then(function(){
      return world.getElement('strong.ng-binding').getText().then(function(text) {
        return text === 'Dashboard';
      });
    });
  };

  this.naoDeveTerAcessoMenu = function(menu) {
    var _this = this;
      switch(menu){
      case ('Auditoria'):
        return _this.naoPossuiMenu('//a[contains(@href, "#'+auditoriaPath+'")]');
      case ('Programação'):
        return _this.naoPossuiMenu('//a[contains(@href, "#'+programacaoPath+'")]');
      case ('Cidades'):
        return _this.naoPossuiMenu('//a[contains(@href, "#'+cidadesPath+'")]');
      case ('Simulação'):
        return _this.naoPossuiMenu('//a[contains(@href, "#'+simulacaoPath+'")]');
      case ('Modelos'):
        return _this.naoPossuiMenu('//a[contains(@href, "#'+modelosPath+'")]');
      case ('Perfis'):
        return _this.naoPossuiMenu('//a[contains(@href, "#'+perfisPath+'")]');
      case ('Usuários'):
        return _this.naoPossuiMenu('//a[contains(@href, "#'+usuariosPath+'")]');
      case ('Áreas'):
        return _this.naoPossuiMenu('//a[contains(@href, "#'+areasPath+'")]');
      default:
        throw new Error('Menu: '+menu+' não encontrado.');
    }
  };

  this.deveTerAcesso = function(local) {
    var _this = this;
      switch(local){
      case ('Auditoria'):
        return _this.possuiAcesso(auditoriaPath, 'Auditorias');
      case ('Programação'):
        return _this.possuiAcesso(programacaoPath, 'Controladores');
      case ('Cidades'):
        return _this.possuiAcesso(cidadesPath, 'Cidades');
      case ('Simulação'):
        return _this.possuiAcesso(simulacaoPath, 'Simulação');
      case ('Modelos'):
        return _this.possuiAcesso(modelosPath, 'Modelos');
      case ('Perfis'):
        return _this.possuiAcesso(perfisPath, 'Perfis');
      case ('Usuários'):
        return _this.possuiAcesso(usuariosPath, 'Usuários');
      case ('Áreas'):
        return _this.possuiAcesso(areasPath, 'Áreas');
      case ('Fabricantes'):
        return _this.possuiAcesso(fabricantesPath, 'Fabricantes');
      case ('Subáreas'):
        return _this.possuiAcesso(subareasPath, 'Subáreas');

      default:
        throw new Error('Path: '+local+' não encontrado.');
    }
  };
};

module.exports = PerfisPage;
