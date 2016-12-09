'use strict';

var PerfisPage = require('../../support/page-objects/perfis');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');

module.exports = function() {
  var perfisPage = new PerfisPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^que o usuário acesse a página de listagem de perfis$/, function() {
    return perfisPage.indexPage();
  });

  this.Given(/^o sistema deverá redirecionar a listagem de perfis$/, function() {
    return perfisPage.indexPage();
  });

  this.Given(/^o usuário clicar no botão novo Perfil$/, function() {
    return objetosComuns.clicarLinkNovo();
  });

  this.Given(/^o usuário clicar em "([^"]*)" do perfil "([^"]*)"$/, function(botao, perfil) {
    return perfisPage.clicarNoLinkDoPerfil(perfil, botao);
  });

  this.Given(/^o usuário desmarcar a permisão "([^"]*)"$/, function(permissao) {
    return perfisPage.marcarPermissao(permissao);
  });

  this.Given(/^o botão "([^"]*)" não deverá aparecer$/, function (botao) {
    return perfisPage.botaoDeveEstarEscondido(botao);
  });

  this.Given(/^clicar no botão de "([^"]*)" o "([^"]*)"$/, function (botao, perfil) {
    return perfisPage.clicarNoLinkDoPerfil(perfil, botao);
  });

  this.Given(/^o perfil deverá ser salvo com nome igual a "([^"]*)"$/, function (perfilNome) {
    return perfisPage.checkPerfilNaTabela(perfilNome);
  });

  this.Given(/^perfil "([^"]*)" deve ser excluído$/, function (perfilNome) {
    return perfisPage.checkPerfilExcluido(perfilNome);
  });
};


