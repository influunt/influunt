'use strict';

var PerfisPage = require('../../support/page-objects/perfis');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');

module.exports = function() {
  var perfisPage = new PerfisPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^que o usuário esteja logado no sistema$/, function () {
    return perfisPage.isDashboard();
  });

  this.Given(/^o usuário logue no sistema com usuário "([^"]*)" e perfil "([^"]*)"$/, function (user, perfil) {
    return objetosComuns.logarNoSistema('create_usuario_perfil_'+perfil+'', user, 'mobilab');
  });

  this.Given(/^o usuário logue no sistema com usuário "([^"]*)" e perfil administrador$/, function (user) {
    return objetosComuns.logarNoSistema('create_usuario', user, 'mobilab');
  });

  this.Given(/^o usuário não deve ter acesso ao menu "([^"]*)"$/, function (menu) {
    return perfisPage.naoDeveTerAcessoMenu(menu);
  });

  this.Given(/^o usuário não deverá ter acesso a listagem de "([^"]*)"$/, function (local) {
    return perfisPage.naoDeveTerAcesso(local);
  });

  this.Given(/^o usuário deverá ter acesso a "([^"]*)" com o título "([^"]*)"$/, function (local, titulo) {
    return perfisPage.deveTerAcesso(local, titulo);
  });

  this.Given(/^o usuário terá a opção visualizar o botão "([^"]*)"$/, function (botao) {
    return perfisPage.checarPossuiBotao(botao);
  });

  this.Given(/^o usuário terá a opção de clicar no botão novo controlador$/, function () {
    return objetosComuns.clicarLinkNovo();
  });

  this.Given(/^for desabilitada no perfil visualizar todas as áreas$/, function () {
    return perfisPage.desabilitarPermissoes();
  });

  this.Given(/^a área 2 for setada para o usuário$/, function () {
    return perfisPage.setArea2();
  });

  this.Given(/^o sistema deverá mostrar o controlador da área "([^"]*)"$/, function (numeroClc) {
    return perfisPage.checarControladorListagem(numeroClc);
  });

};


