'use strict';

var expect = require('chai').expect;
var LoginPage = require('../support/page-objects/login');

module.exports = function() {
  var LOGIN_URL = 'http://localhost:9000/#/login';

  var loginPage = new LoginPage();

  this.Given(/^o usuário acessa a tela de login$/, function () {
    loginPage.acessar();
  });

  this.Given(/^informa somente o nome "([^"]*)" no campo usuário$/, function (usuario) {
    loginPage.preencherUsuario(usuario);
    loginPage.confirmarFormulario();
  });

  this.Given(/^o usuário não deve conseguir acessar o sistema$/, function () {
    return loginPage.getUrl().then(function(url) {
      expect(url).to.equal(LOGIN_URL);
    });
  });

  this.Given(/^o sistema deve informar "Campo obrigatório" para o campo de senha$/, function () {
    return loginPage.campoSenhaInvalido().then(function(res) {
      expect(res).to.equal(true);
    });
  });

  this.Given(/^informa somente a senha "([^"]*)" no campo de senha$/, function (senha) {
    loginPage.preencherSenha(senha);
    loginPage.confirmarFormulario();
  });

  this.Given(/^o sistema deve informar "Campo obrigatório" para o campo de usuário$/, function () {
    return loginPage.campoUsuarioInvalido().then(function(res) {
      expect(res).to.equal(true);
    });
  });

  this.Given(/^informa usuário ou senha incorretos$/, function (callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o sistema deve informar "([^"]*)"$/, function (arg1, callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^informa usuário e senha corretos$/, function (callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o usuário deve ser enviado para a tela de dashboard$/, function (callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

};
