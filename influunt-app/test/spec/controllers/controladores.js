'use strict';

describe('Controller: ControladoresCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresCtrl,
    scope,
    $q,
    Restangular,
    $httpBackend;

  beforeEach(inject(function ($controller, $rootScope, _$q_, _$httpBackend_, _Restangular_) {
    scope = $rootScope.$new();
    ControladoresCtrl = $controller('ControladoresCtrl', {
      $scope: scope
    });

    $q = _$q_;
    Restangular = _Restangular_;
    $httpBackend = _$httpBackend_;
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });

  describe('CRUD de contrladores', function() {
    it('Inicializa Index:', function() {
      var controladores = [{}, {}];
      $httpBackend.expectGET('/controladores').respond(controladores);
      scope.inicializaIndex();
      $httpBackend.flush();

      expect(scope.lista.length).toBe(2);
      expect(scope.filtros).toEqual({});
      expect(scope.filtroLateral).toEqual({});
    });

    it('before show: carrega a lista de areas', function() {
      var areas = [{}, {}];
      $httpBackend.expectGET('/areas').respond(areas);
      scope.beforeShow();
      $httpBackend.flush();

      expect(scope.areas.length).toBe(2);
    });
  });

  describe('Wizard para novo controlador', function() {
    var helpers;

    beforeEach(function() {
      helpers = {"cidades":[{},{}],"fabricantes":[{},{},{}]};
      $httpBackend.expectGET('/helpers/controlador').respond(helpers);
      scope.inicializaWizard();
      $httpBackend.flush();
    });

    describe('inicializaWizard', function() {
      it('Inicializa Wizard: Deve retornar o objeto vazio.', function() {

        expect(scope.objeto).toEqual({});
      });

      it('Inicializa Wizard: Deve inicializar o objeto de dados acessórios', function() {
        expect(scope.data.fabricantes).toEqual(helpers.fabricantes);
        expect(scope.data.cidades).toEqual(helpers.cidades);
      });

      it('Inicializa Wizard: Deve inicializar a cidade com a primeira cidade da lista de cidades, do dados acessórios',
        function() {
          expect(scope.helpers.cidade).toEqual(scope.data.cidades[0]);
        });
    });


  });

  describe('Wizard para edição de contrladores', function () {
    var helpers, id, objeto;
    beforeEach(inject(function($state) {
      id = 'id';
      $state.params.id = id;

      helpers = {"cidades":[{},{}],"fabricantes":[{},{},{}]};
      objeto = {id: 1, area: {id: 'area', cidade: {id: 'cidade'}}, modelo: {id: 'modelo', fabricante: {id: 'fab1'}}};
      $httpBackend.expectGET('/controladores/' + id).respond(objeto);
      $httpBackend.expectGET('/helpers/controlador').respond(helpers);
      scope.inicializaWizard();
      $httpBackend.flush();
    }));

    it('Inicializa Wizard: Deve inicializar o objeto conforme retornado pela api', function() {
      expect(scope.objeto.id).toEqual(objeto.id);
      expect(scope.objeto.area).toEqual(objeto.area);
      expect(scope.objeto.modelo).toEqual(objeto.modelo);
    });

    it('Inicializa Wizard: Deve atualizar os helpers com os dados do objeto', function() {
      expect(scope.helpers.cidade).toEqual(objeto.area.cidade);
      expect(scope.helpers.fornecedor).toEqual(objeto.modelo.fabricante);
    });

  });


});
