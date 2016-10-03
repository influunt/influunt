'use strict';

describe('Controller: AreasCtrl', function () {

  var AreasCtrl,
    scope,
    httpBackend,
    listaCidades;

  beforeEach(inject(function ($controller, $rootScope, $httpBackend) {
    scope = $rootScope.$new();
    AreasCtrl = $controller('AreasCtrl', {
      $scope: scope
    });

    httpBackend = $httpBackend;
    listaCidades = {
      data: [
        {nome: 'cidade 1'},
        {nome: 'cidade 2'}
      ]
    };
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
    expect(scope.objeto.limites.length).toBe(1);
  });

  it('Deve remover uma coordenada do registro de área dado o index do array passado por parametro', function() {
    var expectation = [{value: 1},{value: 3}];
    scope.objeto = {
      limites: [
        {value: 1},
        {value: 2},
        {value: 3}
      ]
    };

    scope.removerCoordenadas(1);
    expect(scope.objeto.limites).toEqual(expectation);
  });

  it('Deve retornar a lista de cidades', function() {
    httpBackend.expectGET('/cidades').respond(listaCidades);
    scope.beforeShow();
    httpBackend.flush();
    expect(scope.cidades.length).toEqual(listaCidades.data.length);
  });
});
