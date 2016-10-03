'use strict';

describe('Controller: ControladoresVerdesConflitantesCtrl', function () {

  var ControladoresVerdesConflitantesCtrl,
    scope,
    $q,
    $httpBackend,
    $state,
    helpers;

  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$state_, _$q_) {
    scope = $rootScope.$new();
    ControladoresVerdesConflitantesCtrl = $controller('ControladoresVerdesConflitantesCtrl', {$scope: scope});

    $httpBackend = _$httpBackend_;
    $state = _$state_;
    $q = _$q_;

    helpers = {cidades:[{},{}],fabricantes:[{},{},{}]};
    $httpBackend.expectGET('/helpers/controlador').respond(helpers);
    scope.inicializaWizard();
    $httpBackend.flush();
  }));

  it('Deve conter as definições de funções do ControladorCtrl', function() {
    expect(scope.inicializaWizard).toBeDefined();
  });

  describe('assertVerdesConflitantes', function () {
    it('O controlador deve ter ao meno um anel e um estagio.', function() {
      scope.objeto = {aneis: [{idJson: 1, estagios: [{idJson: 'e1'}]}], estagios: [{idJson: 'e1'}]};
      var result = scope.assertVerdesConflitantes();
      expect(result).toBeTruthy();
    });

    it('Um controlador que não tenha ao menos um anel deve ser considerado inválido', function() {
      scope.objeto = {aneis: []};
      var result = scope.assertVerdesConflitantes();
      expect(result).not.toBeTruthy();
    });
  });

  describe('inicializaVerdesConflitantes', function () {
    describe('Configuração inválida', function () {
      it('Deve interromper o "inicializaVerdesConflitantes" se o controlador estiver inválido', function() {
        var objeto = {};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaVerdesConflitantes);
        expect(scope.currentAnelIndex).not.toBeDefined();
      });
    });

    describe('Configuração válida', function () {
      beforeEach(function() {
        var objeto = {
          limiteGrupoSemaforico: 8,
          aneis: [
            {
              idJson: 1, ativo: true,
              estagios: [{idJson: 'e1'}],
              gruposSemaforicos: [{idJson: 'gs1'},{idJson: 'gs2'},{idJson: 'gs3'},{idJson: 'gs4'}]
            }
          ],
          estagios: [{idJson: 'e1'}, {idJson: 'e2'}],
          gruposSemaforicos: [
            {idJson: 'gs1', posicao: 1},
            {idJson: 'gs2', posicao: 2},
            {idJson: 'gs3', posicao: 3},
            {idJson: 'gs4', posicao: 4}
          ]
        };
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaVerdesConflitantes);
      });

      it('Deve construir uma matriz n x n, onde n = ao limite de grupos semaforicos do controlador', function() {
        expect(scope.verdesConflitantes.length).toBe(scope.objeto.limiteGrupoSemaforico);
        expect(scope.verdesConflitantes[0].length).toBe(scope.objeto.limiteGrupoSemaforico);
      });
    });

    describe('Configuração inicializada', function () {
      beforeEach(function() {
        var objeto = {
          limiteGrupoSemaforico: 8,
          aneis: [
            {
              idJson: 1, ativo: true,
              estagios: [{idJson: 'e1'}],
              gruposSemaforicos: [{idJson: 'gs1'},{idJson: 'gs2'},{idJson: 'gs3'},{idJson: 'gs4'}]
            }
          ],
          estagios: [{idJson: 'e1'}, {idJson: 'e2'}],
          gruposSemaforicos: [
            {idJson: 'gs1', posicao: 1, verdesConflitantesOrigem: [{idJson: 'vc1'}]},
            {idJson: 'gs2', posicao: 2},
            {idJson: 'gs3', posicao: 3},
            {idJson: 'gs4', posicao: 4}
          ],
          verdesConflitantes: [{idJson: 'vc1', origem: {idJson: 'gs1'}, destino: {idJson: 'gs2'}}]
        };
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaVerdesConflitantes);
      });

      it('Deve iniciar as transicoes "G1-G2", "G2-G1" marcadas como proibidas na matriz', function() {
        expect(scope.verdesConflitantes[0][1]).toBeTruthy();
        expect(scope.verdesConflitantes[1][0]).toBeTruthy();
      });
    });
  });

  describe('toggleVerdeConflitante', function () {
    beforeEach(function() {
      var objeto = {
        limiteGrupoSemaforico: 8,
        aneis: [
          {
            idJson: 1, ativo: true,
            estagios: [{idJson: 'e1'}],
            gruposSemaforicos: [{idJson: 'gs1'},{idJson: 'gs2'},{idJson: 'gs3'},{idJson: 'gs4'}]
          }
        ],
        estagios: [{idJson: 'e1'}, {idJson: 'e2'}],
        gruposSemaforicos: [
          {idJson: 'gs1', posicao: 1},
          {idJson: 'gs2', posicao: 2},
          {idJson: 'gs3', posicao: 3},
          {idJson: 'gs4', posicao: 4}
        ]
      };
      WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaVerdesConflitantes);
    });

    it('Transicoes para o mesmo grupo (G1-G1, por exemplo) não devem ser alteradas', function() {
      scope.toggleVerdeConflitante(0, 0, true);
      expect(scope.verdesConflitantes[0][0]).not.toBeTruthy();
    });

    describe('Ativação de um verde conflitante G1-G2.', function () {
      it('Deve salvar um verde conflitante origem "G1", destino "G2" no array de origens de G1', function() {
        var expectation = {origem: {idJson: 'gs1'}, destino: {idJson: 'gs2'}};
        scope.toggleVerdeConflitante(0, 1);
        var verdeConflitantes = _.find(
          scope.objeto.verdesConflitantes,
          {idJson: scope.objeto.gruposSemaforicos[0].verdesConflitantesOrigem[0].idJson}
        );

        expect(scope.objeto.gruposSemaforicos[0].verdesConflitantesOrigem.length).toBe(1);
        expect(scope.objeto.verdesConflitantes.length).toBe(1);
        expect(verdeConflitantes.origem).toEqual(expectation.origem);
        expect(verdeConflitantes.destino).toEqual(expectation.destino);
      });

      it('A matriz deve marcar G1-G2 e G2-G1 como verde conflitante.', function() {
        scope.toggleVerdeConflitante(0, 1);
        expect(scope.verdesConflitantes[0][1]).toBeTruthy();
        expect(scope.verdesConflitantes[1][0]).toBeTruthy();
      });
    });

    describe('desativação de um verde conflitante enviado à API', function () {
      beforeEach(function() {
        scope.toggleVerdeConflitante(0, 1);
        scope.objeto.verdesConflitantes[0].id = 1;
        scope.toggleVerdeConflitante(0, 1);
      });

      it('O verde conflitante origem "G1", destino "G2" deve permanecer na lista de verdes conflitantes', function() {
        expect(scope.objeto.verdesConflitantes.length).toBe(1);
      });

      it('O verde conflitante origem "G1", destino "G2" deve conter "_destroy" === true', function() {
        expect(scope.objeto.verdesConflitantes[0]._destroy).toBeTruthy();
      });

      it('O grupo semafórico "G1" deve manter a referencia ao verde conflitante removido na lista de origens',
        function() {
          expect(scope.objeto.gruposSemaforicos[0].verdesConflitantesOrigem.length).toBe(1);
        });
    });

    describe('desativação de um verde conflitante local.', function () {
      beforeEach(function() {
        scope.toggleVerdeConflitante(0, 1);
      });

      it('Deve remover o verde conflitante origem "G1", destino "G2" no array de origens de G1', function() {
        scope.toggleVerdeConflitante(0, 1);
        expect(scope.objeto.gruposSemaforicos[0].verdesConflitantesOrigem.length).toBe(0);
        expect(scope.objeto.verdesConflitantes.length).toBe(0);
      });

      it('A matriz deve apontar a posicao 0x1 como um verde não conflitante', function () {
        scope.toggleVerdeConflitante(0, 1);
        expect(scope.verdesConflitantes[0][1]).not.toBeTruthy();
      });
    });
  });

  describe('selecionaAnelVerdesConflitantes', function () {
    beforeEach(function() {
      scope.objeto = {
        limiteGrupoSemaforico: 8,
        aneis: [
          {
            idJson: 1, ativo: true,
            estagios: [{idJson: 'e1'}],
            gruposSemaforicos: [{idJson: 'gs1'},{idJson: 'gs2'}]
          }
        ],
        estagios: [{idJson: 'e1'}, {idJson: 'e2'}],
        gruposSemaforicos: [
          {idJson: 'gs1', posicao: 1},
          {idJson: 'gs2', posicao: 2},
          {idJson: 'gs3', posicao: 3},
          {idJson: 'gs4', posicao: 4}
        ]
      };

      scope.aneis = scope.objeto.aneis;
      scope.selecionaAnelVerdesConflitantes(0);
      scope.$apply();
    });

    it('Ao selecionar determinado anel, os objetos "currentAnel", "currentGruposSemaforicos" e  "currentEstagios"' +
      ' devem ser inicializados',
      function() {
        expect(scope.currentAnel).toBe(scope.objeto.aneis[0]);
        expect(scope.currentGruposSemaforicos).toEqual([{idJson: 'gs1', posicao: 1}, {idJson: 'gs2', posicao: 2}]);
        expect(scope.currentEstagios).toEqual([{idJson: 'e1'}]);
      });
  });
});
