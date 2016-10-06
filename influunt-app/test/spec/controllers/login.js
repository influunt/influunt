'use strict';

describe('Controller: LoginCtrl', function () {

  var LoginCtrl,
    scope,
    $httpBackend,
    $state,
    form;

  // carrega o template de login.
  beforeEach(inject(function ($controller, $rootScope, $compile, $templateCache, _$httpBackend_, _$state_) {
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
    $state = _$state_;
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

  describe('caso o login seja executado com sucesso', function() {
    beforeEach(function(done) { done(); });
    it('o usuário deve ser redirecionado para tela inicial do sistema',
      function(done) {

        var usuario = {
          id: 'teste',
          login: 'teste',
          email: 'teste@example.com',
          root: true,
          permissoes: []
        };
        $httpBackend.expectPOST('/login').respond(usuario);
        $httpBackend.expectGET('/permissoes/roles').respond({ permissoes: [{ chave: 'visualizarStatusControladores' }], permissoesApp: [] });

        scope.submitLogin(true);
        $httpBackend.flush();
        setTimeout(function() {
          expect($state.current.name).toBe('app.main');
          done();
        }, 1000);
      });
  });

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
