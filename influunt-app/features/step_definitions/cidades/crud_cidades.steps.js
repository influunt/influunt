'use strict';

var expect = require('chai').expect;
var CidadesPage = require('../../support/page-objects/cidades');

module.exports = function() {
  var cidadesPage = new CidadesPage();

  this.Given(/^que exista o usuário "([^"]*)" com senha "([^"]*)"$/, function(arg1, arg2, callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^que o usuário "([^"]*)" entre no sistema com a senha "([^"]*)"$/, function(arg1, arg2, callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^que exista ao menos uma cidade cadastrada no sistema$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o usuário acessar a tela de cidades$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^deve ser exibida uma lista com as cidades já cadastradas no sistema$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o usuário acessa a tela de listagem de cidades$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^Clica no botão de Nova Cidade$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de Cadastro de nova Cidades$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o usuário acessa a tela de cadastro de novas cidades$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^preenche os campos corretamente$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o registro deverá ser salvo com sucesso$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de cidades$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^Clica no botão de visualizar cidade$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de visualização cidades$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^esta tela deve exibir o formulário de cidades sem permissão de alteração para os campos$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^Clica no botão de editar cidade$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição cidades$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o usuário acessa o formulário de edição de cidades$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^clica no botão de excluir uma cidade$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a cidade$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o usuário responde sim$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^a cidade deverá ser excluida$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o usuário responde não$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^nenhuma cidade deve ser excluída$/, function(callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });
};
