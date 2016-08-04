'use strict';

xdescribe('Controller: ControladoresCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresCtrl,
    scope,
    $q,
    Restangular,
    $httpBackend,
    $state;

  beforeEach(inject(function ($controller, $rootScope, _$q_, _$httpBackend_, _Restangular_, _$state_) {
    scope = $rootScope.$new();
    ControladoresCtrl = $controller('ControladoresCtrl', {
      $scope: scope
    });

    $q = _$q_;
    Restangular = _Restangular_;
    $httpBackend = _$httpBackend_;
    $state = _$state_;
  }));


  describe('CRUD de contrladores', function() {
    it('Deve conter as definições das funções de CRUD', function() {
      expect(scope.index).toBeDefined();
      expect(scope.show).toBeDefined();
      expect(scope.new).toBeDefined();
      expect(scope.save).toBeDefined();
      expect(scope.confirmDelete).toBeDefined();
    });

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
      helpers = {cidades:[{},{}],fabricantes:[{},{},{}]};
      $httpBackend.expectGET('/helpers/controlador').respond(helpers);
      scope.inicializaWizard();
      $httpBackend.flush();
    });

    describe('inicializaWizard', function() {
      it('Deve retornar o objeto vazio.', function() {
        expect(scope.objeto).toEqual({limiteEstagio: 16, limiteGrupoSemaforico: 16, limiteAnel: 4, limiteDetectorPedestre: 4, limiteDetectorVeicular: 8, limiteTabelasEntreVerdes: 2, enderecos: [{localizacao: "", latitude: null, longitude: null}, {localizacao: "", latitude: null, longitude: null}]});
      });

      it('Deve inicializar o objeto de dados acessórios', function() {
        expect(scope.data.fabricantes).toEqual(helpers.fabricantes);
        expect(scope.data.cidades).toEqual(helpers.cidades);
      });

      it('Deve inicializar a cidade com a primeira cidade da lista de cidades, do dados acessórios',
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

  describe('Funções auxiliares', function () {
    describe('closeAlert', function () {
      beforeEach(function() {
        scope.errors = {
          aneis: [{
            general: {},
            other: {},
          }]
        };
      });

      it('Ao fechar o alert de aneis, deve limpar a lista de general notifications', function() {
        scope.closeAlert(0);
        expect(scope.errors.aneis[0].general).toBeUndefined();
      });

      it('Deve limpar uma lista customizada, se houver um segundo parametro informado', function() {
        scope.closeAlert(0, 'other');
        expect(scope.errors.aneis[0].other).toBeUndefined();
      });

      it('Deve manter o objeto de erros inalterado se um anel incorreto for informado', function() {
        var originalErrors = _.cloneDeep(scope.errors);
        scope.closeAlert(1);
        expect(scope.errors).toEqual(originalErrors);
      });
    });

    describe('anelTemErro', function () {
      beforeEach(function() {
        scope.errors = {aneis: [{a: 1}]};
      });

      it('se houver algum erro listado, o objeto deve retornar true', function () {
        expect(scope.anelTemErro(0)).toBeTruthy();
      });

      it('se não houver erros listados, o objeto deve retornar false', function () {
        scope.errors.aneis = [];
        expect(scope.anelTemErro(0)).not.toBeTruthy();
      });
    });

    describe('selecionaAnel', function () {
      beforeEach(function() {
        scope.aneis = [{}, {}, {}];
      });

      it('Deve selecionar um anel da lista de aneis conforme o id passado por parametro', function() {
        scope.selecionaAnel(1);
        expect(scope.currentAnel).toBe(scope.aneis[1]);
        expect(scope.currentAnelIndex).toBe(1);
      });

      it('Se o houver um currentEstagioId definido, esta funcao deve selecionar o estagio', function() {
        scope.currentEstagioId = 1;
        spyOn(scope, 'selecionaEstagio');
        scope.selecionaAnel(1);

        expect(scope.selecionaEstagio).toHaveBeenCalled();
      });

    })

  });
});
