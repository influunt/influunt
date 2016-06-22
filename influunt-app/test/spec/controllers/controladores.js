'use strict';

describe('Controller: ControladoresCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresCtrl,
    scope,
    $q;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$q_) {
    scope = $rootScope.$new();
    ControladoresCtrl = $controller('ControladoresCtrl', {
      $scope: scope
    });

    $q = _$q_;
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });

  it('Inicializa Index:', inject(function($httpBackend) {
    var controladores = [{}, {}];
    $httpBackend.expectGET('/controladores').respond(controladores);
    scope.inicializaIndex();
    $httpBackend.flush();

    expect(scope.lista.length).toBe(2);
    expect(scope.filtros).toEqual({});
    expect(scope.filtroLateral).toEqual({});
  }));

  it('before show: carrega a lista de areas', inject(function($httpBackend) {
    var areas = [{}, {}];
    $httpBackend.expectGET('/areas').respond(areas);
    scope.beforeShow();
    $httpBackend.flush();

    expect(scope.areas.length).toBe(2);
  }));
});
