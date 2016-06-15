'use strict';

describe('Controller: AreasCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var AreasCtrl,
    scope,
    httpBackend,
    listaCidades;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $httpBackend) {
    scope = $rootScope.$new();
    AreasCtrl = $controller('AreasCtrl', {
      $scope: scope
      // place here mocked dependencies
    });

    httpBackend = $httpBackend;
    listaCidades = [
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

  it('Deve adicionar uma nova coordenada ao registro de área', function() {
    scope.objeto = {};
    scope.adicionarCoordenadas();
    expect(scope.objeto.coordenadas.length).toBe(1);
  });

  it('Deve remover uma coordenada do registro de área dado o index do array passado por parametro', function() {
    var expectation = [{value: 1},{value: 3}];
    scope.objeto = {
      coordenadas: [
        {value: 1},
        {value: 2},
        {value: 3}
      ]
    };

    scope.removerCoordenadas(1);
    expect(scope.objeto.coordenadas).toEqual(expectation);
  });

  it('Deve retornar a lista de cidades', function() {
    httpBackend.expectGET('/cidades').respond(listaCidades);
    scope.beforeShow();
    httpBackend.flush();
    expect(scope.cidades.length).toEqual(listaCidades.length);
  });
});
