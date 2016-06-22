'use strict';

describe('Controller: ControladoresCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresCtrl,
    scope,
    $q,
    Restangular;

  beforeEach(inject(function ($controller, $rootScope, _$q_, _Restangular_) {
    scope = $rootScope.$new();
    ControladoresCtrl = $controller('ControladoresCtrl', {
      $scope: scope
    });

    $q = _$q_;
    Restangular = _Restangular_;
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });

  describe('CRUD de contrladores', function() {
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

  describe('Wizard para novo controlador', function() {
    var helpers;

    beforeEach(function() {
      helpers = {"cidades":[{},{}],"fabricantes":[{},{},{}]};
    })

    it('Inicializa Wizard: Deve retornar um objeto vazio.', function() {
      scope.inicializaWizard();
      expect(scope.objeto).toEqual({});
    });

    it('Inicializa Wizard: Deve criar o objeto de helpers', inject(function($httpBackend) {

    }));

  });

  describe('Wizard para edição de contrladores', function () {
    it('Inicializa Wizard: ', function() {});
  })


});
