'use strict';

describe('Controller: UsuariosCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var UsuariosCtrl,
    scope,
    httpBackend,
    listaPapeis;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $httpBackend) {
    scope = $rootScope.$new();
    UsuariosCtrl = $controller('UsuariosCtrl', {
      $scope: scope
    });

    httpBackend = $httpBackend;

    listaPapeis = [
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

  it('Deve retornar a lista de papeis', function() {
    httpBackend.expectGET('/papeis').respond(listaPapeis);
    scope.beforeShow();
    httpBackend.flush();
    expect(scope.papeis.length).toEqual(listaPapeis.length);
  });
});
