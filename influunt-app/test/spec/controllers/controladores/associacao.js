'use strict';

describe('Controller: ControladoresAssociacaoCtrl', function () {

  var ControladoresAssociacaoCtrl,
    scope,
    $q,
    $httpBackend,
    $state,
    helpers;

  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$state_, _$q_) {
    scope = $rootScope.$new();
    ControladoresAssociacaoCtrl = $controller('ControladoresAssociacaoCtrl', {$scope: scope});

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

  describe('assertAssociacoes', function () {
    it('O controlador deve ter ao menos as configurações mínimas até verdes conflitantes.', function() {
      scope.objeto = {
        aneis: [
          {
            idJson: 1,
            gruposSemaforicos: [{idJson: 'gs1'}],
            verdesConflitantes: [{idJson: 'vc1'}],
            estagios: [{idJson: 'e1'}]}
        ],
        estagios: [
          {idJson: 'e1', id: 'e1', anel: {idJson: 1}}
        ],
        gruposSemaforicos: [
          {idJson: 'gs1'}
        ],
        verdesConflitantes: [{idJson: 'vc1'}]
      };

      var result = scope.assertAssociacoes();
      expect(result).toBeTruthy();
    });

    it('Um controlador que não tenha ao menos um anel deve ser considerado inválido', function() {
      scope.objeto = {aneis: []};
      var result = scope.assertAssociacoes();
      expect(result).not.toBeTruthy();
    });
  });

  describe('inicializaAssociacao', function () {
    describe('Configuração inválida', function () {
      it('Deve interromper o "inicializaAssociacao" se o controlador estiver inválido', function() {
        var objeto = {};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAssociacao);
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
              gruposSemaforicos: [{idJson: 'gs1'}],
              verdesConflitantes: [{idJson: 'vc1'}],
              estagios: [{idJson: 'e1'}, {idJson: 'e2'}]}
          ],
          estagios: [
            {idJson: 'e1', id: 'e1', anel: {idJson: 1}},
            {idJson: 'e2', id: 'e2', anel: {idJson: 1}}
          ],
          gruposSemaforicos: [
            {idJson: 'gs1', estagiosGruposSemaforicos: [{idJson: 'egs1'}]},
            {idJson: 'gs2', estagiosGruposSemaforicos: [{idJson: 'egs2'}]},
            {idJson: 'gs3'}
          ],
          estagiosGruposSemaforicos: [
            {idJson: 'egs1', estagio: {idJson: 'e1'}, grupoSemaforico: {idJson: 'gs1'}},
            {idJson: 'egs2', estagio: {idJson: 'e1'}, grupoSemaforico: {idJson: 'gs2'}}
          ],
          verdesConflitantes: [{idJson: 'vc1'}]
        };

        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAssociacao);
      });

      it('Deve inicializaros "estagiosRelacionados" do grupo', function() {
        expect(scope.objeto.gruposSemaforicos[0].estagiosRelacionados.e1).toBeTruthy();
      });
    });
  });

  describe('associaEstagiosGrupoSemaforico', function () {
    var grupo, estagio;
    beforeEach(function() {
      scope.objeto = {
        aneis: [
          {
            idJson: 1,
            ativo: true,
            gruposSemaforicos: [{idJson: 'gs1'}],
            verdesConflitantes: [{idJson: 'vc1'}],
            estagios: [{idJson: 'e1'}, {idJson: 'e2'}]}
        ],
        estagios: [
          {idJson: 'e1', anel: {idJson: 1}},
          {idJson: 'e2', anel: {idJson: 1}}
        ],
        gruposSemaforicos: [
          {idJson: 'gs1'},
          {idJson: 'gs2'},
          {idJson: 'gs3'}
        ],
        verdesConflitantes: [{idJson: 'vc1'}]
      };
      scope.aneis = scope.objeto.aneis;

      grupo = scope.objeto.gruposSemaforicos[0];
      estagio = scope.objeto.estagios[0];
      scope.associaEstagiosGrupoSemaforico(grupo, estagio);
    });

    describe('ativação da relação estágio x grupo', function () {
      it('Deve criar a relação no objeto, em "estagiosGruposSemaforicos"', function() {
        var expectation = {grupoSemaforico: {idJson: 'gs1'}, estagio: {idJson: 'e1'}};
        expect(scope.objeto.estagiosGruposSemaforicos[0].grupoSemaforico).toEqual(expectation.grupoSemaforico);
        expect(scope.objeto.estagiosGruposSemaforicos[0].estagio).toEqual(expectation.estagio);
      });
      it('Deve referenciar a relação em grupo, em "estagiosGruposSemaforicos"', function() {
        expect(scope.objeto.gruposSemaforicos[0].estagiosGruposSemaforicos[0].idJson)
          .toBe(scope.objeto.estagiosGruposSemaforicos[0].idJson);
      });
      it('Deve referenciar a relação em estágio, em "estagiosGruposSemaforicos"', function() {
        expect(scope.objeto.estagios[0].estagiosGruposSemaforicos[0].idJson)
          .toBe(scope.objeto.estagiosGruposSemaforicos[0].idJson);
      });
    });

    describe('desativação da relação estagio x grupo', function () {
      beforeEach(function() {
        scope.associaEstagiosGrupoSemaforico(grupo, estagio);
      });

      it('Deve remover a relação no objeto, em "estagiosGruposSemaforicos"', function() {
        expect(scope.objeto.estagiosGruposSemaforicos[0]).not.toBeDefined();
        expect(scope.objeto.estagiosGruposSemaforicos[0]).not.toBeDefined();
      });
      it('Deve remover a referencia em grupo, em "estagiosGruposSemaforicos"', function() {
        expect(scope.objeto.gruposSemaforicos[0].estagiosGruposSemaforicos[0]).not.toBeDefined();
      });
      it('Deve remover a referencia em estágio, em "estagiosGruposSemaforicos"', function() {
        expect(scope.objeto.estagios[0].estagiosGruposSemaforicos[0]).not.toBeDefined();
      });
    });
  });

  describe('sortableOptions -> stop', function () {
    it('Deve atualizar as posições dos estágios com a troca de posição destes elemento', function() {
      var estagios = [{idJson: 1}, {idJson: 2}, {idJson: 3}, {idJson: 4}, {idJson: 5}];
      scope.objeto = {estagios: estagios};
      scope.currentEstagios = [estagios[0], estagios[1], estagios[2]];
      scope.currentAnel = {estagios: [{idJson: 1}, {idJson: 2}, {idJson: 3}]};

      // simulando a troca de posições entre o primeiro e o segundo estágios e a chamada da
      // funcao pelo plugin sortable.
      scope.currentEstagios.splice(0, 1);
      scope.currentEstagios.splice(1, 0, estagios[0]);
      scope.sortableOptions.stop();
      scope.$apply();

      expect(scope.currentEstagios[0].posicao).toBe(1);
      expect(scope.currentEstagios[0].idJson).toBe(2);
      expect(scope.currentEstagios[1].posicao).toBe(2);
      expect(scope.currentEstagios[1].idJson).toBe(1);
      expect(scope.currentEstagios[2].posicao).toBe(3);
      expect(scope.currentEstagios[2].idJson).toBe(3);
    });
  });
  
  describe('getErros', function () {
    beforeEach(inject(function($timeout, handleValidations) {
      scope.objeto = {
        aneis: [
          {
            idJson: 1,
            estagios: [
              {idJson: 'E1A1'},
              {idJson: 'E2A1'},
              {idJson: 'E3A1'}
            ],
            gruposSemaforicos: [
              {idJson: 'G1A1'},
              {idJson: 'G2A1'},
              {idJson: 'G3A1'}
            ]
          },
          {
            idJson: 2,
            estagios: [
              {idJson: 'E1A2'},
              {idJson: 'E2A2'}
            ],
            gruposSemaforicos: [
              {idJson: 'G1A1'},
              {idJson: 'G2A2'}
            ]
          }
        ],
        estagios: [
          {idJson: 'E1A1', anel: {idJson: 1}},
          {idJson: 'E2A1', anel: {idJson: 1}, demandaPrioritaria: true},
          {idJson: 'E3A1', anel: {idJson: 1}},
          {idJson: 'E1A2', anel: {idJson: 2}},
          {idJson: 'E2A2', anel: {idJson: 2}}
        ],
        gruposSemaforicos: [
          {idJson: 'G1A1', anel: {idJson: 1}},
          {idJson: 'G2A1', anel: {idJson: 1}},
          {idJson: 'G3A1', anel: {idJson: 1}}
        ]
      };
      var error = [
        {
          'root':'Controlador',
          'message':'Esse grupo semafórico não pode estar associado a um estágio de demanda prioritária e a outro estágio ao mesmo tempo.',
          'path':'aneis[0].gruposSemaforicos[2].naoEstaAssociadoAEstagioDemandaPrioritariaEOutroEstagio'
        },
        {
          'root':'Controlador',
          'message':'Estágio de demanda prioritária deve ser associado a um grupo semafórico veicular.',
          'path':'aneis[0].estagios[1].umGrupoSemaforicoVeicularEmDemandaPrioritaria'
        },
        {
          'root':'Controlador',
          'message':'Existem grupos semafóricos conflitantes associados a esse estágio.',
          'path':'aneis[0].estagios[0].naoDevePossuirGruposSemaforicosConflitantes'
        },
        {
          "root": "Controlador",
          "message": "O anel ativo deve ter somente um estágio de demanda prioritária.",
          "path": "aneis[0].somenteUmEstagioDeDemandaPrioritaria"
        }
      ];
      scope.errors = handleValidations.buildValidationMessages(error, scope.objeto);
      scope.selecionaAnel(0);
      scope.$apply();
      $timeout.flush();
    }));

    it('Deve ter erro para o estágio 1', function() {
      expect(scope.estagioTemErro(0, 0)).toBeTruthy();
      expect(scope.errors.aneis[0].estagios[0].naoDevePossuirGruposSemaforicosConflitantes[0]).toBe('Existem grupos semafóricos conflitantes associados a esse estágio.');
    });

    it('Deve ter erro para o estágio 2', function() {
      expect(scope.estagioTemErro(0, 1)).toBeTruthy();
      expect(scope.errors.aneis[0].estagios[1].umGrupoSemaforicoVeicularEmDemandaPrioritaria[0]).toBe('Estágio de demanda prioritária deve ser associado a um grupo semafórico veicular.');
    });

    it('Não deve ter erro para o estágio 3', function() {
      expect(scope.estagioTemErro(0, 2)).toBeFalsy();
    });

    it('Não deve ter erro para os grupos semaforicos 1, 2', function() {
      expect(scope.grupoSemaforicoTemErro(0, {idJson: 'G1A1'})).toBeFalsy();
      expect(scope.grupoSemaforicoTemErro(0, {idJson: 'G2A1'})).toBeFalsy();
    });

    it('Deve ter erro no anel', function() {
      expect(scope.getErrosAneis(scope.errors.aneis[0]).lenght > 0).toBeFalsy();
      expect(scope.getErrosAneis(scope.errors.aneis[0])[0]).toBe('O anel ativo deve ter somente um estágio de demanda prioritária.');
    });

    it('Deve ter erro para o grupo semaforico 3', function() {
      var grupo = {idJson: 'G3A1'};
      expect(scope.grupoSemaforicoTemErro(0, grupo)).toBeTruthy();
      expect(scope.getErrosGrupoSemaforico(0, grupo).naoEstaAssociadoAEstagioDemandaPrioritariaEOutroEstagio[0]).toBe('Esse grupo semafórico não pode estar associado a um estágio de demanda prioritária e a outro estágio ao mesmo tempo.');
    });
    
  });
  
});
