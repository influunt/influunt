'use strict';

describe('Controller: PerfisCtrl', function () {

  // load the controller's module
  // beforeEach(module('influuntApp', function(RestangularProvider) {
  //   RestangularProvider.setBaseUrl('');
  // }));

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
      var permissoes = { permissoes: [{ id: 1, chave: '1' }, { id: 2, chave: '2' }], permissoesApp: [{ id: 3, chave: 'opa', permissoes: ['1', '2'] }] };
      var perfil = { permissoes: [{ id: 1 }] };

      $state.params = { id: 1 };
      $httpBackend.expectGET('/permissoes/roles').respond(permissoes);
      $httpBackend.expectGET('/permissoes?page=0&per_page=30&sort_type=asc').respond({});
      $httpBackend.expectGET('/perfis/1').respond(perfil);
      scope.inicializaPermissoes();
      $httpBackend.flush();

      expect(scope.objeto.permissoes).toEqual(perfil.permissoes);
      expect(scope.permissions.length).toBe(permissoes.permissoes.length);
      expect(scope.roles.length).toBe(permissoes.permissoesApp.length);
    });

  it('Deve remover o item da lista de permissoes, caso este já esteja nela', function() {
    scope.objeto = { permissoes: [{ id: '1' }, { id: '2' }] };
    scope.roles = [{ id: '1', permissoes: [{ id: '1', chave: 'a' }, { id: '2', chave: 'b' }] }, { id: '2', permissoes: [{ id: '3', chave: 'c' }, { id: '4', chave: 'd' }] }];
    scope.rolesAtivados = { '1': false, '2': false };

    scope.atualizarPermissoes();
    expect(scope.objeto.permissoes.length).toBe(0);
  });

  it('Deve adicionar o item à lista de permissoes, caso este não esteja nela', function() {
    scope.objeto = { permissoes: [] };
    scope.roles = [{ id: '1', permissoes: [{ id: '1', chave: 'a' }, { id: '2', chave: 'b' }] },
                   { id: '2', permissoes: [{ id: '3', chave: 'c' }, { id: '4', chave: 'd' }] }];
    scope.rolesAtivados = { '1': false, '2': true };

    scope.atualizarPermissoes();
    expect(scope.objeto.permissoes.length).toBe(2);
    expect(_.map(scope.objeto.permissoes, 'id')).toContain('3');
    expect(_.map(scope.objeto.permissoes, 'id')).toContain('4');
  });

});
