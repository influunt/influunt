'use strict';

describe('Controller: SubAreasCtrl', function () {

  var SubAreasCtrl,
    scope,
    httpBackend,
    listaAreas,
    helpers;

  beforeEach(inject(function ($controller, $rootScope, $httpBackend) {
    scope = $rootScope.$new();
    SubAreasCtrl = $controller('SubAreasCtrl', {
      $scope: scope
    });

    httpBackend = $httpBackend;
    listaAreas = {
      data: [
        {id: 1, descricao: '1'},
        {id: 2, descricao: '2'}
      ]
    };

    helpers = {
      cidades:[{idJson: 'c1'},{idJson: 'c2'}],
      fabricantes:[{idJson: 'f1'},{idJson: 'f2'},{idJson: 'f3'}]
    };
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });

  it('Deve retornar a lista de areas', function() {
    httpBackend.expectGET('/controladores?page=0&per_page=300').respond({});
    httpBackend.expectGET('/areas').respond(listaAreas);
    scope.beforeShow();
    httpBackend.flush();
    expect(scope.areas.length).toEqual(listaAreas.data.length);
  });
});
