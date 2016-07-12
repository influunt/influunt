'use strict';

describe('Controller: ControladoresAssociacaoDetectoresCtrl', function () {

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

    describe('inicializaAssociacaoDetectores', function () {
      beforeEach(function() {
        var objeto = {id: '1bd3f7e0-1ead-4674-bae1-8afd505f7357',aneis: [{id: '4346cc6e-33bf-4028-bb3c-a90a1c272aaf',ativo: true,posicao: 1,quantidadeGrupoPedestre: 2,quantidadeGrupoVeicular: 0,quantidadeDetectorPedestre: 3,quantidadeDetectorVeicular: 3,detectores: [{id: 1, tipo: 'VEICULAR'},{id: 2, tipo: 'VEICULAR'},{id: 3, tipo: 'VEICULAR'},{id: 4, tipo: 'PEDESTRE'},{id: 5, tipo: 'PEDESTRE'},{id: 6, tipo: 'PEDESTRE'},],estagios: [{id: '7c0ca652-a56a-4294-929e-33e8105440fb',demandaPrioritaria: false,origemDeTransicoesProibidas: [{id: '61927ee0-f633-471d-a4bf-841db1c73c2a',origem: {id: '7c0ca652-a56a-4294-929e-33e8105440fb'},destino: {id: 'be4520b4-710d-4c53-a39a-5cd7d2de509b'},alternativo: {id: 'd5497f5c-5c5c-4bb1-8b2b-47402ac7d9b0'}}],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: []},{id: '7d179366-6aaf-41fc-bfbe-63208e98fd6d',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: []},{id: 'be4520b4-710d-4c53-a39a-5cd7d2de509b',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [{id: '61927ee0-f633-471d-a4bf-841db1c73c2a',origem: {id: '7c0ca652-a56a-4294-929e-33e8105440fb'},destino: {id: 'be4520b4-710d-4c53-a39a-5cd7d2de509b'},alternativo: {id: 'd5497f5c-5c5c-4bb1-8b2b-47402ac7d9b0'}}],alternativaDeTransicoesProibidas: []},{id: 'd5497f5c-5c5c-4bb1-8b2b-47402ac7d9b0',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [{id: '61927ee0-f633-471d-a4bf-841db1c73c2a',origem: {id: '7c0ca652-a56a-4294-929e-33e8105440fb'},destino: {id: 'be4520b4-710d-4c53-a39a-5cd7d2de509b'},alternativo: {id: 'd5497f5c-5c5c-4bb1-8b2b-47402ac7d9b0'}}]},{id: 'e2dad23d-9fd8-4a0f-8580-763e40fc719e',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: []}],gruposSemaforicos: [{id: '6733e9e5-00da-4e2e-a060-3e47504b3285',tipo: 'PEDESTRE',posicao: 1,descricao: 'teste',anel: {id: '4346cc6e-33bf-4028-bb3c-a90a1c272aaf',ativo: false,quantidadeGrupoPedestre: 0,quantidadeGrupoVeicular: 0,quantidadeDetectorPedestre: 0,quantidadeDetectorVeicular: 0,estagios: [],gruposSemaforicos: []},verdesConflitantes: [{id: '87994d25-f095-4a01-b1d1-f1b6c54e7ac4',tipo: 'PEDESTRE',posicao: 2,descricao: 'tste',anel: {id: '4346cc6e-33bf-4028-bb3c-a90a1c272aaf',ativo: false,quantidadeGrupoPedestre: 0,quantidadeGrupoVeicular: 0,quantidadeDetectorPedestre: 0,quantidadeDetectorVeicular: 0,estagios: [],gruposSemaforicos: []},verdesConflitantes: [],estagioGrupoSemaforicos: []}],estagioGrupoSemaforicos: [{id: '1a7239a1-f24c-40fd-aa0e-955c2393bb71',ativo: true,estagio: {id: 'be4520b4-710d-4c53-a39a-5cd7d2de509b',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [{id: '61927ee0-f633-471d-a4bf-841db1c73c2a',origem: {id: '7c0ca652-a56a-4294-929e-33e8105440fb'},destino: {id: 'be4520b4-710d-4c53-a39a-5cd7d2de509b'},alternativo: {id: 'd5497f5c-5c5c-4bb1-8b2b-47402ac7d9b0'}}],alternativaDeTransicoesProibidas: []},grupoSemaforico: {id: '6733e9e5-00da-4e2e-a060-3e47504b3285'}},{id: '451dc67b-0ab5-41ed-a516-6fdede7336e6',ativo: true,estagio: {id: 'd5497f5c-5c5c-4bb1-8b2b-47402ac7d9b0',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [{id: '61927ee0-f633-471d-a4bf-841db1c73c2a',origem: {id: '7c0ca652-a56a-4294-929e-33e8105440fb'},destino: {id: 'be4520b4-710d-4c53-a39a-5cd7d2de509b'},alternativo: {id: 'd5497f5c-5c5c-4bb1-8b2b-47402ac7d9b0'}}]},grupoSemaforico: {id: '6733e9e5-00da-4e2e-a060-3e47504b3285'}},{id: '4d820905-c135-4b75-8d2f-4cf052878fc3',ativo: true,estagio: {id: '7c0ca652-a56a-4294-929e-33e8105440fb',demandaPrioritaria: false,origemDeTransicoesProibidas: [{id: '61927ee0-f633-471d-a4bf-841db1c73c2a',origem: {id: '7c0ca652-a56a-4294-929e-33e8105440fb'},destino: {id: 'be4520b4-710d-4c53-a39a-5cd7d2de509b'},alternativo: {id: 'd5497f5c-5c5c-4bb1-8b2b-47402ac7d9b0'}}],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: []},grupoSemaforico: {id: '6733e9e5-00da-4e2e-a060-3e47504b3285'}},{id: '615b7d06-e518-4938-944f-357b24d2c61f',ativo: true,estagio: {id: '7d179366-6aaf-41fc-bfbe-63208e98fd6d',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: []},grupoSemaforico: {id: '6733e9e5-00da-4e2e-a060-3e47504b3285'}},{id: 'b4ffc306-ca4d-40ae-b614-3d80b6fbcaee',ativo: true,estagio: {id: 'e2dad23d-9fd8-4a0f-8580-763e40fc719e',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: []},grupoSemaforico: {id: '6733e9e5-00da-4e2e-a060-3e47504b3285'}}]},{id: '87994d25-f095-4a01-b1d1-f1b6c54e7ac4',tipo: 'PEDESTRE',posicao: 2,descricao: 'tste',anel: {id: '4346cc6e-33bf-4028-bb3c-a90a1c272aaf',ativo: false,quantidadeGrupoPedestre: 0,quantidadeGrupoVeicular: 0,quantidadeDetectorPedestre: 0,quantidadeDetectorVeicular: 0,estagios: [],gruposSemaforicos: []},verdesConflitantes: [{id: '6733e9e5-00da-4e2e-a060-3e47504b3285',tipo: 'PEDESTRE',posicao: 1,descricao: 'teste',anel: {id: '4346cc6e-33bf-4028-bb3c-a90a1c272aaf',ativo: false,quantidadeGrupoPedestre: 0,quantidadeGrupoVeicular: 0,quantidadeDetectorPedestre: 0,quantidadeDetectorVeicular: 0,estagios: [],gruposSemaforicos: []},verdesConflitantes: [],estagioGrupoSemaforicos: []}],estagioGrupoSemaforicos: [{id: '3f66e512-4343-44bc-9e73-33715092c6fc',ativo: true,estagio: {id: 'be4520b4-710d-4c53-a39a-5cd7d2de509b',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [{id: '61927ee0-f633-471d-a4bf-841db1c73c2a',origem: {id: '7c0ca652-a56a-4294-929e-33e8105440fb'},destino: {id: 'be4520b4-710d-4c53-a39a-5cd7d2de509b'},alternativo: {id: 'd5497f5c-5c5c-4bb1-8b2b-47402ac7d9b0'}}],alternativaDeTransicoesProibidas: []},grupoSemaforico: {id: '87994d25-f095-4a01-b1d1-f1b6c54e7ac4'}},{id: '93507a90-6425-4fc9-bc08-2de64af9d3a2',ativo: true,estagio: {id: '7d179366-6aaf-41fc-bfbe-63208e98fd6d',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: []},grupoSemaforico: {id: '87994d25-f095-4a01-b1d1-f1b6c54e7ac4'}},{id: 'd62f69f2-cd78-4036-af04-cbb05da860f3',ativo: true,estagio: {id: 'd5497f5c-5c5c-4bb1-8b2b-47402ac7d9b0',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: [{id: '61927ee0-f633-471d-a4bf-841db1c73c2a',origem: {id: '7c0ca652-a56a-4294-929e-33e8105440fb'},destino: {id: 'be4520b4-710d-4c53-a39a-5cd7d2de509b'},alternativo: {id: 'd5497f5c-5c5c-4bb1-8b2b-47402ac7d9b0'}}]},grupoSemaforico: {id: '87994d25-f095-4a01-b1d1-f1b6c54e7ac4'}},{id: 'fc2478c6-213d-4957-9ef1-ab5a7973a116',ativo: true,estagio: {id: 'e2dad23d-9fd8-4a0f-8580-763e40fc719e',demandaPrioritaria: false,origemDeTransicoesProibidas: [],destinoDeTransicoesProibidas: [],alternativaDeTransicoesProibidas: []},grupoSemaforico: {id: '87994d25-f095-4a01-b1d1-f1b6c54e7ac4'}}]}]}]};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAssociacaoDetectores);
      });

      it('Deverá criar n detectores, onde n é igual a soma da quantidadeDetectorVeicular e quantidadeDetectorPedestre',
        function () {
          var numeroDetectores = scope.aneis[0].quantidadeDetectorPedestre + scope.aneis[0].quantidadeDetectorVeicular;
          expect(numeroDetectores).toBe(scope.aneis[0].detectores.length);
        });
    });
  });

  describe('Funções auxiliares', function () {
    describe('toggleAssociacaoDetector', function () {
      var e1, e2, d1;
      beforeEach(function() {
        e1 = {id: 'E1'};
        e2 = {id: 'E2'};
        d1 = {id: 'D1'};
      });

      it('Dado o estágio E1 e detector D1 sejam associados, o D1 deverá ter um atributo estagio contendo E1',
        function() {
          scope.toggleAssociacaoDetector(e1, d1);

          expect(d1.estagio).toBeDefined();
          expect(d1.estagio).toEqual(e1);
        });

      it('Dado que o detector D1 tenha o estágio E2 associado, ao associar D1 com E1, o estágio deverá ser substituído' +
        'por este último', function() {
          d1.estagio = e2;
          scope.toggleAssociacaoDetector(e1, d1);

          expect(d1.estagio).toBeDefined();
          expect(d1.estagio).toEqual(e1);
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
