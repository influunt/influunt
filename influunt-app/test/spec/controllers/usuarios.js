'use strict';

describe('Controller: UsuariosCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var UsuariosCtrl,
    scope,
    httpBackend,
    listaPerfis,
    listaAreas;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $httpBackend) {
    scope = $rootScope.$new();
    UsuariosCtrl = $controller('UsuariosCtrl', {
      $scope: scope
    });

    httpBackend = $httpBackend;

    listaAreas = [
      {descricao: 'area 1'},
      {descricao: 'area 2'}
    ];
    listaPerfis = [
      {nome: 'cidade 1'},
      {nome: 'cidade 2'}
    ];
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });

  it('Deve retornar a lista de perfis', function() {
    httpBackend.expectGET('/areas').respond(listaAreas);
    httpBackend.expectGET('/perfis').respond(listaPerfis);
    scope.beforeShow();
    httpBackend.flush();
    expect(scope.perfis.length).toEqual(listaPerfis.length);
  });

  it('Deve copiar o login do usuário para o id', function() {
    httpBackend.expectGET('/areas').respond([]);
    httpBackend.expectGET('/perfis').respond([]);
    httpBackend.expectGET('/usuarios').respond({id: 1, login: 'abc'});
    scope.show();
    httpBackend.flush();

    expect(scope.objeto.id).toEqual(1);
    expect(scope.objeto.login).toEqual('abc');
  });
});
