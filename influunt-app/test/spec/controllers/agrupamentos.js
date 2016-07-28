'use strict';

describe('Controller: AgrupamentosCtrl', function () {
  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var AgrupamentosCtrl,
    scope;

  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AgrupamentosCtrl = $controller('AgrupamentosCtrl', {
      $scope: scope
    });
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });

  it('Deve carregar a lista de controladores antes de exibir dados', inject(function($httpBackend) {
    var controladores = [{id: 1, localizacao: 'conf1'}, {id: 2, localizacao: 'fab2'}];
    $httpBackend.expectGET('/controladores').respond(controladores);
    scope.beforeShow();
    $httpBackend.flush();

    expect(scope.controladores.length).toBe(2);
  }));
});
