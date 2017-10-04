'use strict';

var worldObj = require('../world');

var world = new worldObj.World();

var LoginPage = function () {
  var LOGIN_PATH = '/login';

  this.acessar = function() {
    return world.visit(LOGIN_PATH);
  };

  this.preencherUsuario = function(usuario) {
    world.sleep(500);
    return world.setValue('[name="usuario"]', usuario);
  };

  this.preencherSenha = function(senha) {
    world.sleep(500);
    return world.setValue('[name="senha"]', senha);
  };

  this.preencherEmail = function(email) {
    world.sleep(500);
    return world.setValue('[name="email"]', email);
  };

  this.clicarBotaoRecuperar = function() {
    return world.getElementByXpath('//button[contains(@id, "acessar")]').click();
  };

  this.confirmarFormulario = function() {
    return world.setValue('[name="senha"]', world.webdriver.Key.ENTER);
  };

  this.preencherFormulario = function(usuario, senha) {
    var thisPage = this;
    return this.preencherUsuario(usuario).then(function() {
      return thisPage.preencherSenha(senha);
    }).then(function() {
      return thisPage.confirmarFormulario();
    });
  };

  this.campoUsuarioInvalido = function() {
    return world.waitFor('.usuario p.error-msg:not(.ng-hide)').then(function() {
      return world.getElements('.usuario p.error-msg:not(.ng-hide)');
    }).then(function(elements) {
      return elements.length === 1;
    });
  };

  this.campoSenhaInvalido = function() {
    return world.waitFor('.senha p.error-msg:not(.ng-hide)').then(function() {
      return world.getElements('.senha p.error-msg:not(.ng-hide)');
    }).then(function(elements) {
        return elements.length === 1;
    });
  };

  this.loginInvalido = function() {
    return world.waitFor('div[class^="sweet-alert"][class$="visible"]').then(function() {
      return world.getElement('div[class^="sweet-alert"] p[style="display: block;"]').getText();
    });
  };

  this.getUrl = function() {
    return world.getCurrentUrl();
  };

  this.isDashboard = function() {
    return world.waitFor('strong.ng-binding').then(function(){
      return world.getElement('strong.ng-binding').getText().then(function(text) {
        return text === 'Dashboard';
      });
    });
  };
};
module.exports = LoginPage;
