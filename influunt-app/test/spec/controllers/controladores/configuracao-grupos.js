'use strict';

describe('Controller: ControladoresConfiguracaoGruposCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresConfiguracaoGruposCtrl,
    scope,
    $q,
    $httpBackend,
    $state;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$state_, _$q_) {
    scope = $rootScope.$new();
    ControladoresConfiguracaoGruposCtrl = $controller('ControladoresConfiguracaoGruposCtrl', {
      $scope: scope
      // place here mocked dependencies
    });

    $httpBackend = _$httpBackend_;
    $state = _$state_;
    $q = _$q_;
  }));

  describe('Wizard para um novo controlador', function () {
    var helpers;
    beforeEach(function() {
      helpers = {cidades:[{},{}],fabricantes:[{},{},{}]};
      $httpBackend.expectGET('/helpers/controlador').respond(helpers);
      scope.inicializaWizard();
      $httpBackend.flush();

      scope.objeto = {idControlador: '1234567', aneis: [{ativo: true, estagios: {}}]};
      WizardControladores.fakeInicializaWizard(scope, $q, scope.objeto, scope.inicializaConfiguracaoGrupos);
      scope.$apply();
    });

    describe('assert', function () {
      it('Um controlador será valido se possuir ao menos um anel e um estágio', function() {
        scope.objeto = {aneis: [{estagios: {}}]};
        expect(scope.assert()).toBeTruthy();
        scope.objeto.aneis = [];
        expect(scope.assert()).not.toBeTruthy();
      });
    })

    describe('inicializaVerdesConflitantes', function() {
      it('O primeiro anel deve ser selecionado como current', function() {
        expect(scope.currentAnel).toBe(scope.objeto.aneis[0]);
      });

    });

    describe('adicionaGrupoSemaforico', function () {
      it('Deve adicionar um novo objeto à lista de gruposSemaforicos', function() {
        scope.adicionaGrupoSemaforico();
        expect(scope.currentAnel.gruposSemaforicos.length).toBe(1);
      });
    });

  })

});
