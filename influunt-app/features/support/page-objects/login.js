'use strict';

var worldObj = require('../world');

var world = new worldObj.World();

var LoginPage = function () {
  var LOGIN_URL = 'http://localhost:9000/#/login';
  var LOGIN_PATH = '/login';

  this.acessar = function() {
    return world.visit(LOGIN_PATH);
  };

  this.preencherUsuario = function(usuario) {
    return world.setValue('[name="usuario"]', usuario);
  };

  this.preencherSenha = function(senha) {
    return world.setValue('[name="senha"]', senha);
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
    return world.getElements('.senha p.error-msg:not(.ng-hide)')
      .then(function(elements) {
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
    return world.waitFor('h2.ng-binding').then(function(){
      return world.getElement('h2.ng-binding').getText().then(function(text) {
        return text === 'Dashboard';
      });
    });
  };

};


module.exports = LoginPage;
