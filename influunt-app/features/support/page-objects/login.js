var worldObj = require('../world');

var world = new worldObj.World();
var driver = worldObj.getDriver();

var LoginPage = function LoginPage() {
  var LOGIN_URL = 'http://localhost:9000/#/login';

  this.acessar = function() {
    world.visit(LOGIN_URL);
  };

  this.preencherUsuario = function(usuario) {
    world.setValue({name: 'usuario'}, usuario);
  };

  this.preencherSenha = function(senha) {
    world.setValue({name: 'senha'}, senha);
  };

  this.confirmarFormulario = function() {
    world.setValue({name: 'senha'}, world.webdriver.Key.ENTER);
  };

  this.campoUsuarioInvalido = function() {
    return world.getElements({css: '.usuario p.error-msg:not(.ng-hide)'})
      .then(function(elements) {
        return elements.length === 1;
      });
  };

  this.campoSenhaInvalido = function() {
    return world.getElements({css: '.senha p.error-msg:not(.ng-hide)'})
      .then(function(elements) {
        return elements.length === 1;
      });
  };

  this.getUrl = function() {
    return world.getCurrentUrl();
  };

};


module.exports = LoginPage;
