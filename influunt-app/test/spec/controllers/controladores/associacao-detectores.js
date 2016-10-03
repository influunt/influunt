'use strict';

describe('Controller: ControladoresAssociacaoDetectoresCtrl', function () {

  var ControladoresAssociacaoDetectoresCtrl,
    scope,
    $q,
    $httpBackend,
    $state,
    influuntAlert,
    helpers;

  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$state_, _$q_, _influuntAlert_) {
    scope = $rootScope.$new();
    ControladoresAssociacaoDetectoresCtrl = $controller('ControladoresAssociacaoDetectoresCtrl', {$scope: scope});

    $httpBackend = _$httpBackend_;
    $state = _$state_;
    $q = _$q_;

    helpers = {cidades:[{},{}],fabricantes:[{},{},{}]};
    $httpBackend.expectGET('/helpers/controlador').respond(helpers);
    scope.inicializaWizard();
    $httpBackend.flush();
    influuntAlert = _influuntAlert_;
  }));

  it('Deve conter as definições de funções do ControladorCtrl', function() {
    expect(scope.inicializaWizard).toBeDefined();
  });

  describe('assertAssociacaoDetectores', function () {
    it('O controlador deve ter ao meno um anel e um estagio.', function() {
      scope.objeto = {aneis: [{idJson: 1, estagios: [{idJson: 'e1'}]}], estagios: [{idJson: 'e1'}]};
      var result = scope.assertAssociacaoDetectores();
      expect(result).toBeTruthy();
    });

    it('Um controlador que não tenha ao menos um anel deve ser considerado inválido', function() {
      scope.objeto = {aneis: []};
      var result = scope.assertAssociacaoDetectores();
      expect(result).not.toBeTruthy();
    });
  });

  describe('inicializaAssociacaoDetectores', function () {
    describe('Configuração inválida', function () {
      it('Deve interromper o "inicializaAssociacaoDetectores" se o controlador estiver inválido', function() {
        var objeto = {};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAssociacaoDetectores);
        expect(scope.currentAnelIndex).not.toBeDefined();
      });
    });

    describe('Configuração inicializada', function () {
      beforeEach(function() {
        var objeto = {
          aneis: [
            {
              idJson: 1,
              ativo: true,
              estagios: [
                {idJson: 'e1', detectores: [{idJson: 'd1'}]},
                {idJson: 'e2'}
              ]
            }
          ],
          detectores: [
            {idJson: 'd1', tipo: 'VEICULAR', estagio: {idJson: 'e1'}, posicao: 1},
            {idJson: 'd2', tipo: 'PEDESTRE', estagio: {idJson: 'e1'},posicao: 2}
          ],
          estagios: [
            {idJson: 'e1', id: 'e1', posicao: 1, anel: {idJson: 1}},
            {idJson: 'e2', id: 'e2', posicao: 2, anel: {idJson: 1}}
          ]
        };

        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAssociacaoDetectores);
      });

      it('Deve carregar a lista de estágios e detectores do anel', function() {
        expect(scope.currentEstagios).toBeDefined();
        expect(scope.currentDetectores).toBeDefined();
      });
    });
  });

  describe('toggleAssociacaoDetector', function () {
    var e1, e2, d1, d2;
    beforeEach(function() {
      var objeto = {
        aneis: [
          {
            idJson: 1,
            ativo: true,
            estagios: [{idJson: 'e1'},{idJson: 'e2'}],
            detectores: [{idJson: 'd1'}, {idJson: 'd2'}]
          }
        ],
        detectores: [
          {idJson: 'd1', posicao: 1, tipo: 'VEICULAR'},
          {idJson: 'd2', posicao: 2, tipo: 'PEDESTRE'}
        ],
        estagios: [
          {idJson: 'e1', id: 'e1', posicao: 1, anel: {idJson: 1}},
          {idJson: 'e2', id: 'e2', posicao: 2, anel: {idJson: 1}}
        ]
      };

      WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAssociacaoDetectores);
      e1 = scope.objeto.estagios[0];
      e2 = scope.objeto.estagios[1];
      d1 = scope.objeto.detectores[0];
      d2 = scope.objeto.detectores[1];
      scope.toggleAssociacaoDetector(e1, d1);
      scope.$apply();
    });

    it('Dado o estágio E1 e detector D1 sejam associados, o D1 deverá ter o atributo "estágio" com E1',
      function() {
        expect(d1.estagio.idJson).toBeDefined();
        expect(d1.estagio.idJson).toBe(e1.idJson);
      });

    it('O estágio E1 deve ser assinalado como "temDetector"', function() {
      expect(e1.temDetector).toBeTruthy();
    });

    it('Quando o detector D1 for associado ao estágio E2, a antiga associacao E1-D1 será desfeita', function() {
      scope.toggleAssociacaoDetector(e2, d1);
      expect(d1.estagio.idJson).toBeDefined();
      expect(d1.estagio.idJson).toBe(e2.idJson);
      expect(e1.temDetector).not.toBeTruthy();
    });

    it('Deve remover uma associacao E1-D1 ao executar novamente o "toggleAssociacaoDetector"', function() {
      scope.toggleAssociacaoDetector(e1, d1);
      expect(d1.estagio.idJson).not.toBeDefined();
      expect(e1.temDetector).not.toBeTruthy();
    });

    it('Não deve permitir que um estágio tenha dois ou mais detectores', function() {
      scope.toggleAssociacaoDetector(e1, d2);
      expect(d2.estagio).not.toBeDefined();
      expect(d1.estagio.idJson).toBe(e1.idJson);
      expect(e1.temDetector).toBeTruthy();
    });
  });

  describe('adicionar/excluir detectores', function () {
    beforeEach(function() {
      var objeto = {
        aneis: [
          {
            idJson: 1,
            ativo: true,
            estagios: [{idJson: 'e1'},{idJson: 'e2'}, {idJson: 'e3'}, {idJson: 'e4'}]
          }
        ],
        estagios: [
          {idJson: 'e1', id: 'e1', posicao: 1, anel: {idJson: 1}},
          {idJson: 'e2', id: 'e2', posicao: 2, anel: {idJson: 1}},
          {idJson: 'e3', id: 'e3', posicao: 3, anel: {idJson: 1}},
          {idJson: 'e4', id: 'e4', posicao: 4, anel: {idJson: 1}}
        ]
      };

      WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAssociacaoDetectores);
      scope.adicionaDetector('VEICULAR');
      scope.adicionaDetector('PEDESTRE');
      scope.adicionaDetector('VEICULAR');
      scope.adicionaDetector('PEDESTRE');
    });

    it('Deve adicionar os detectores no controlador, e a referencia a eles no anel', function() {
      expect(scope.objeto.detectores.length).toBe(4);
      expect(scope.currentAnel.detectores.length).toBe(4);
      expect(scope.currentDetectores.length).toBe(4);
    });

    it('Os detectores deverão ter as posições construídas por tipo (DP1, DP2, DV1, DV2)', function() {
      expect(scope.objeto.detectores[0].posicao).toBe(1);
      expect(scope.objeto.detectores[1].posicao).toBe(1);
      expect(scope.objeto.detectores[2].posicao).toBe(2);
      expect(scope.objeto.detectores[3].posicao).toBe(2);
    });

    it('Deve calcular a quantidade de detectores que pode ser adicionado', function(){
      expect(scope.maxDetectoresPorAnel).toBe(0);
    });

    describe('remover detector', function () {
      var deferred;
      beforeEach(function() {
        scope.toggleAssociacaoDetector(scope.objeto.estagios[0], scope.objeto.detectores[0]);

        deferred = $q.defer();
        localStorage.setItem('token', 123);
        spyOn(influuntAlert, 'delete').and.returnValue(deferred.promise);
      });

      it('É necessário que o usuário confirme que pretende excluir um detector da lista', function() {
        scope.excluirDetector(scope.objeto.detectores[0]);
        deferred.resolve(false);
        scope.$apply();

        expect(scope.objeto.detectores.length).toBe(4);
        expect(scope.currentAnel.detectores.length).toBe(4);
        expect(scope.currentDetectores.length).toBe(4);
      });

      it('ao remover um detector da lista, este deverá ser removido do controlador e do anel', function() {
        scope.excluirDetector(scope.objeto.detectores[0]);
        deferred.resolve(true);
        scope.$apply();

        expect(scope.objeto.detectores.length).toBe(3);
        expect(scope.currentAnel.detectores.length).toBe(3);
        expect(scope.currentDetectores.length).toBe(3);
      });

      it('Deve aumentar a quantidade de detectores que pode ser adicionado', function(){
        scope.excluirDetector(scope.objeto.detectores[0]);
        deferred.resolve(true);
        scope.$apply();

        expect(scope.maxDetectoresPorAnel).toBe(1);
      });

      it('Estagios que possuem detectores associados devem ter a associação removida.', function() {
        expect(scope.objeto.estagios[0].temDetector).toBeTruthy();

        scope.excluirDetector(scope.objeto.detectores[0]);
        deferred.resolve(true);
        scope.$apply();

        expect(scope.objeto.estagios[0].temDetector).not.toBeTruthy();
      });
    });
  });
});
