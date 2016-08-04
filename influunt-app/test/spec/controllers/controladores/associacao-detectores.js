'use strict';

xdescribe('Controller: ControladoresAssociacaoDetectoresCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresAssociacaoDetectoresCtrl,
    scope,
    $q,
    $httpBackend,
    $state;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$q_, _$httpBackend_, _$state_) {
    scope = $rootScope.$new();
    ControladoresAssociacaoDetectoresCtrl = $controller('ControladoresAssociacaoDetectoresCtrl', {
      $scope: scope
      // place here mocked dependencies
    });

    $q = _$q_;
    $httpBackend = _$httpBackend_;
    $state = _$state_;
  }));

  describe('Wizard para novo controlador', function() {
    var helpers;

    beforeEach(function() {
      helpers = {cidades:[{},{}],fabricantes:[{},{},{}]};
      $httpBackend.expectGET('/helpers/controlador').respond(helpers);
      scope.inicializaWizard();
      $httpBackend.flush();
    });

    it('Não deve permanecer na tela de associação de detectores se não houver ao menos um anel ativo', function() {
      var objeto = {};
      WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAssociacaoDetectores);
      expect($state.current.name).not.toBe('app.wizard_controladores.associacao_detectores');
    });

    it('Não deve permanecer na tela de associação de detectores se não houver ao menos um estagio ativo', function() {
      var objeto = {};
      WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAssociacaoDetectores);
      expect($state.current.name).not.toBe('app.wizard_controladores.associacao_detectores');
    });
  });

  describe('Funções auxiliares', function () {
    describe('toggleAssociacaoDetector', function () {
      var e1, e2, d1;
      beforeEach(function() {
        e1 = {id: 'E1'};
        e2 = {id: 'E2'};
        d1 = {id: 'D1'};

        scope.currentAnel = {
          estagios: [e1, e2]
        }
      });

      it('Dado o estágio E1 e detector D1 sejam associados, o D1 deverá ter um atributo estagio contendo E1',
        function() {
          scope.toggleAssociacaoDetector(e1, d1);

          expect(d1.estagio).toBeDefined();
          expect(d1.estagio.id).toBe(e1.id);
        });

      it('Dado que o detector D1 tenha o estágio E2 associado, ao associar D1 com E1, o estágio deverá ser substituído' +
        'por este último', function() {
          d1.estagio = e2;
          scope.toggleAssociacaoDetector(e1, d1);

          expect(d1.estagio).toBeDefined();
          expect(d1.estagio.id).toBe(e1.id);
        });

      it ('Se E1 já está associado a D1 e a função é chamada para com E1 e D1, o estágio será removido do detector',
        function() {
          d1.estagio = e1;
          scope.toggleAssociacaoDetector(e1, d1);

          expect(d1.estagio).toEqual({});
        });
    });
  });
});
