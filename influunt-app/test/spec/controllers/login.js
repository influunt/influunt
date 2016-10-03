'use strict';

describe('Controller: LoginCtrl', function () {

  var LoginCtrl,
    scope,
    $httpBackend,
    form;

  // carrega o template de login.
  beforeEach(inject(function ($controller, $rootScope, $compile, $templateCache, _$httpBackend_) {
    scope = $rootScope.$new();
    LoginCtrl = $controller('LoginCtrl', {
      $scope: scope
    });

    var template = $templateCache.get('views/login/signin.html');
    var $elemento = angular.element(template);
    $compile($elemento)(scope);
    form = scope.loginForm;

    scope.$apply();
    $httpBackend = _$httpBackend_;
  }));

  it('Deve possuir um objeto de credenciais já registrado', function() {
    expect(scope.credenciais).toBeDefined();
  });

  it('O campo de usuário deve estar preenchido', function() {
    scope.credenciais.usuario = null;
    scope.credenciais.senha = 'teste';
    scope.$apply();
    scope.submitLogin(form.$valid);

    expect(form.$valid).toBe(false);
  });

  it('O campo de senha deve estar preenchido', function() {
    scope.credenciais.usuario = 'teste';
    scope.credenciais.senha = null;
    scope.$apply();
    scope.submitLogin(form.$valid);

    expect(form.$valid).toBe(false);
  });

  it('caso o login seja executado com sucesso, deve ser redirecionado para tela inicial do sistema',
    inject(function($state) {
      var usuario = {
        id: 'teste',
        login: 'teste',
        email: 'teste@example.com',
        root: true,
        permissoes: []
      }
      $httpBackend.expectPOST('/login').respond(usuario);

      scope.submitLogin(true);
      $httpBackend.flush();

      expect($state.current.name).toBe('app.main');
    }));

  it('O formulário é dado como válido se usuário e senha forem preenchidos', function() {
    scope.credenciais.usuario = 'teste';
    scope.credenciais.senha = 'teste';
    scope.$apply();

    setTimeout(function() {
      expect(form.$valid).toBe(true);
    });
  });

  it('Caso haja uma tentativa de login com usuário ou senha inválidos, o sistema deverá informar a falha',
    inject(function($state, SweetAlert) {
      spyOn(SweetAlert, 'swal');
      $httpBackend.expectPOST('/login').respond(401, [{}]);
      scope.submitLogin(true);
      $httpBackend.flush();

      expect(SweetAlert.swal).toHaveBeenCalled();
    }));

});
