'use strict';

var UsuariosPage = require('../../support/page-objects/usuarios');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');

module.exports = function() {
  var usuariosPage = new UsuariosPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^o sistema deverá redirecionar a listagem de usuários$/, function() {
    return usuariosPage.indexPage();
  });

  this.Given(/^o usuário acessar a tela de listagem de usuários$/, function() {
    return usuariosPage.visitPath('usuarios');
  });

  this.Given(/^o usuário selecionar o perfil de administrador$/, function() {
    var campo = 'div';
    var selectSelector = 'select[name="usuario_perfil"]';
    var optionAtribute = 'label';
    var value = 'Administrador';

    return objetosComuns.selectBySelectOptionAtribute(campo, selectSelector, optionAtribute, value);
  });
};

