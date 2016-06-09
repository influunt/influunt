'use strict';

describe('Controller: LoginCtrl', function () {
  var LoginCtrl,
    scope,
    form;

  // load the controller's module
  /**
   * Solução sugerida pelo angular-translate para testar apps com carregamento
   * de traduções de arquivo json
   *  (fonte: https://angular-translate.github.io/docs/#/guide/22_unit-testing-with-angular-translate)
   */
  beforeEach(module('influuntApp', function ($translateProvider) {
    $translateProvider.translations('en', {});
  }));

  // Initialize the controller and a mock scope
  // carrega o template de login.
  beforeEach(inject(function ($controller, $rootScope, $compile, $templateCache) {
    scope = $rootScope.$new();
    LoginCtrl = $controller('LoginCtrl', {
      $scope: scope
    });

    var template = $templateCache.get('views/login/signin.html');
    var $elemento = angular.element(template);
    $compile($elemento)(scope);
    form = scope.loginForm;

    scope.$apply();
  }));

  it('Deve possuir um objeto de credenciais já registrado', function() {
    expect(scope.credenciais).toBeDefined();
  });

  it('O campo de usuário deve estar preenchido', function() {
    scope.credenciais.usuario = null;
    scope.credenciais.senha = 'teste';
    scope.$apply();
    // mocking o click do botão de submit
    scope.submitLogin(form.$valid);

    expect(form.$valid).toBe(false);
  });

  it('O campo de senha deve estar preenchido', function() {
    scope.credenciais.usuario = 'teste';
    scope.credenciais.senha = null;
    scope.$apply();
    // mocking o click do botão de submit
    scope.submitLogin(form.$valid);

    expect(form.$valid).toBe(false);
  });

  it('O formulário é dado como válido se usuário e senha forem preenchidos', function() {
    scope.credenciais.usuario = 'teste';
    scope.credenciais.senha = 'teste';
    scope.$apply();
    // mocking o click do botão de submit
    scope.submitLogin(form.$valid);

    expect(form.$valid).toBe(true);
  });
});
