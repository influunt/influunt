'use strict';

describe('Controller: PerfisCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var PerfisCtrl,
    scope,
    $httpBackend,
    $state;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$state_) {
    scope = $rootScope.$new();
    PerfisCtrl = $controller('PerfisCtrl', {
      $scope: scope
      // place here mocked dependencies
    });

    $httpBackend = _$httpBackend_;
    $state = _$state_;
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });

  it('Ao entrar na tela de permissões, deverá carregar o perfil, todas as permissões existentes' +
    ' e relacionar quais permissões do perfil estão ativas', function() {
      var permissoes = [{id: 1}, {id: 2}];
      var perfil = {permissoes: [{id: 1}]};

      $state.params = {id: 1};
      $httpBackend.expectGET('/permissoes').respond(permissoes);
      $httpBackend.expectGET('/perfis/1').respond(perfil);
      scope.inicializaPermissoes();
      $httpBackend.flush();

      expect(scope.objeto.permissoes).toEqual(perfil.permissoes);
      expect(scope.permissoes.length).toBe(permissoes.length);
      expect(scope.permissoesAtivadas).toEqual({1: true});
    });

  it('Deve remover o item da lista de permissoes, caso este já esteja nela', function() {
    var permissao = {id: 1};
    scope.objeto = {permissoes: [{id: 1}, {id: 2}]};

    scope.atualizaListaPermissoes(permissao);
    expect(scope.objeto.permissoes.length).toBe(1);
  });

  it('Deve adicionar o item à lista de permissoes, caso este não esteja nela', function() {
    var permissao = {id: 1};
    scope.objeto = {};

    scope.atualizaListaPermissoes(permissao);
    expect(scope.objeto.permissoes.length).toBe(1);
  });

});
