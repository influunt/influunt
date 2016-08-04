'use strict';

xdescribe('Controller: ControladoresTransicoesProibidasCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresTransicoesProibidasCtrl,
    scope,
    $q,
    $httpBackend,
    $state;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$state_, _$q_) {
    scope = $rootScope.$new();
    ControladoresTransicoesProibidasCtrl = $controller('ControladoresTransicoesProibidasCtrl', {
      $scope: scope
      // place here mocked dependencies
    });

    $httpBackend = _$httpBackend_;
    $state = _$state_;
    $q = _$q_;
  }));

  describe('Wizard para novo controlador', function() {
    var helpers;
    beforeEach(function() {
      helpers = {cidades:[{},{}],fabricantes:[{},{},{}]};
      $httpBackend.expectGET('/helpers/controlador').respond(helpers);
      scope.inicializaWizard();
      $httpBackend.flush();
    });

    describe('inicializaTransicoesProibidas', function () {
      beforeEach(function() {
        scope.objeto = {idControlador: '1234567', aneis: [{}], modelo: {configuracao: {limiteAnel: 4}}};
        WizardControladores.fakeInicializaWizard(scope, $q, scope.objeto, scope.inicializaTransicoesProibidas);
        scope.$apply();
      });

      it('Não deve acessar a tela de transicoes proibidas caso não haja aneis e estagios.',
        function () {
          expect($state.current.name).not.toBe('app.wizard_controladores.transicoes_proibidas');
        });
    });
  });

  describe('Funções auxiliares', function () {
    describe('toggleTransicaoProibida', function () {
      beforeEach(function() {
        var objeto = {'id': 'ce3ee3dd-7f5e-4357-9663-f47f37c150f8','localizacao': 'area 1','numeroSMEE': 'area 1','numeroSMEEConjugado1': '12','numeroSMEEConjugado2': '123','numeroSMEEConjugado3': '123','firmware': '321','latitude': -19.9513211,'longitude': -43.921468600000026,'dataCriacao': '28/06/2016 23:31:32','dataAtualizacao': '28/06/2016 23:32:27','CLC': '1.000.0999','area': {'id': '6a2e0afd-616b-40eb-a834-60e33195b009','descricao': 1,'dataCriacao': '28/06/2016 19:35:03','dataAtualizacao': '28/06/2016 19:35:03','cidade': {'id': '6db983c9-8b03-42f7-ac8b-f19d9b0b92d1','nome': 'Belo Horizonte','dataCriacao': '28/06/2016 19:34:54','dataAtualizacao': '28/06/2016 19:34:54','areas': [{'id': '6a2e0afd-616b-40eb-a834-60e33195b009','descricao': 1},{'id': 'b9a85f3a-ac49-436b-a121-9af51db9b9a8','descricao': 2}]},'limites': []},'modelo': {'id': '820c1ede-f516-4377-9754-f8339f1f3071','descricao': 'fab 1 opa','dataCriacao': '28/06/2016 19:36:07','dataAtualizacao': '28/06/2016 19:36:07','fabricante': {'id': 'ecedfd9f-4649-470d-a10a-acaf10350da1','nome': 'fab 1','dataCriacao': '28/06/2016 19:36:07','dataAtualizacao': '28/06/2016 19:36:07','modelos': [{'id': '820c1ede-f516-4377-9754-f8339f1f3071','descricao': 'fab 1 opa','configuracao': {'id': 'eeff04ca-ed6e-4ead-9b6a-0ba84b4adbb0','descricao': 'opa','limiteEstagio': 8,'limiteGrupoSemaforico': 8,'limiteAnel': 8,'limiteDetectorPedestre': 8,'limiteDetectorVeicular': 8,'dataCriacao': '28/06/2016 19:35:52','dataAtualizacao': '28/06/2016 19:35:52'}}]},'configuracao': {'id': 'eeff04ca-ed6e-4ead-9b6a-0ba84b4adbb0','descricao': 'opa','limiteEstagio': 8,'limiteGrupoSemaforico': 8,'limiteAnel': 8,'limiteDetectorPedestre': 8,'limiteDetectorVeicular': 8,'dataCriacao': '28/06/2016 19:35:52','dataAtualizacao': '28/06/2016 19:35:52'}},'aneis': [{'id': '16a2cf30-3957-4d99-a6bf-bdec16e5891f','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': '19883586-ff33-4585-a004-311dcb3aada0','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': '41bfb905-3950-414c-9ee6-147b13bc70f8','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': '578ac07c-68ce-4e72-8368-424b8ef506a2','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'aba2b4c3-de65-4baa-b064-1375199e40b8','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'd7c5dc59-3f83-4c66-ac05-4dcfb5429de7','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'efc70dee-08d1-4bf2-b38d-5143dde2a84e','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'fabea98b-bd33-480b-8b10-48332849fe9b','numeroSMEE': 'teste','ativo': true,'posicao': 1,'latitude': -19.9513211,'longitude': -43.921468600000026,'quantidadeGrupoPedestre': 2,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:31:32','dataAtualizacao': '28/06/2016 23:32:27','estagios': [{'id': '6b95029b-bba8-4186-9875-fd62b8f0d1de','imagem': {'id': '4b1a186f-df95-4cf0-8bf6-9fe01cb89bfe','filename': '13406967_1003062549749489_543993289383831132_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},{'id': 'f6d285ef-83a3-4498-a4da-d8c1d2c6d7ff','imagem': {'id': '8e58963d-2b83-43ec-82eb-8609b2016bfd','filename': '12321359_986438248070903_1173574894423312875_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'}],'gruposSemaforicos': [{'id': '5225d0bc-5b41-4b40-8647-73a985e54997','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 1,'descricao': 'veicular g1','estagioGrupoSemaforicos': [{'id': '3e61befa-e09c-4e2e-8470-f0224f494712','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': '6b95029b-bba8-4186-9875-fd62b8f0d1de','imagem': {'id': '4b1a186f-df95-4cf0-8bf6-9fe01cb89bfe','filename': '13406967_1003062549749489_543993289383831132_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '5225d0bc-5b41-4b40-8647-73a985e54997'}}]},{'id': '82b80f5f-82b8-4905-865e-932e324a9796','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 2,'descricao': 'veicular g2','estagioGrupoSemaforicos': [{'id': '48030015-0332-4a56-a02b-c9fa54521462','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': 'f6d285ef-83a3-4498-a4da-d8c1d2c6d7ff','imagem': {'id': '8e58963d-2b83-43ec-82eb-8609b2016bfd','filename': '12321359_986438248070903_1173574894423312875_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '82b80f5f-82b8-4905-865e-932e324a9796'}}]}]}],'gruposSemaforicos': [{'id': '5225d0bc-5b41-4b40-8647-73a985e54997','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 1,'descricao': 'veicular g1','estagioGrupoSemaforicos': [{'id': '3e61befa-e09c-4e2e-8470-f0224f494712','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': '6b95029b-bba8-4186-9875-fd62b8f0d1de','imagem': {'id': '4b1a186f-df95-4cf0-8bf6-9fe01cb89bfe','filename': '13406967_1003062549749489_543993289383831132_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '5225d0bc-5b41-4b40-8647-73a985e54997'}}]},{'id': '82b80f5f-82b8-4905-865e-932e324a9796','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 2,'descricao': 'veicular g2','estagioGrupoSemaforicos': [{'id': '48030015-0332-4a56-a02b-c9fa54521462','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': 'f6d285ef-83a3-4498-a4da-d8c1d2c6d7ff','imagem': {'id': '8e58963d-2b83-43ec-82eb-8609b2016bfd','filename': '12321359_986438248070903_1173574894423312875_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '82b80f5f-82b8-4905-865e-932e324a9796'}}]}],'route': 'controladores','reqParams': null,'restangularized': true,'fromServer': true,'parentResource': null,'restangularCollection': false};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaTransicoesProibidas);
      });

      it('se a intercessão e1xe1 for clicada, o metodo deverá ser interrompido sem causar alterações', function() {
        var estagio1 = scope.currentAnel.estagios[0];
        scope.toggleTransicaoProibida(estagio1, estagio1, true);

        expect(estagio1.origemDeTransicoesProibidas).not.toBeDefined();
        expect(estagio1.destinoDeTransicoesProibidas).not.toBeDefined();
      });

      describe('Ativação/desativação da intercessão dos estágios E1-E2', function() {
        var estagio1, estagio2;
        beforeEach(function() {
          estagio1 = scope.currentAnel.estagios[0];
          estagio2 = scope.currentAnel.estagios[1];
          scope.toggleTransicaoProibida(estagio1, estagio2, false);
        });

        it('O estagio e1 será origem de uma transicao proibida para e2 em "origemDeTransicoesProibidas" do estagio1', function() {
          expect(estagio1.origemDeTransicoesProibidas[0].origem.id).toBe(estagio1.id);
        });

        it('O estágio e1 será origem de uma transicao proibida para e2 em "destinoDeTransicoesProibidas" do estagio2', function() {
          expect(estagio2.destinoDeTransicoesProibidas[0].origem.id).toBe(estagio1.id);
        });

        it('O estagio e2 será destino de uma transição proibida para e1 em "origemDeTransicoesProibidas do estagio1"', function() {
          expect(estagio1.origemDeTransicoesProibidas[0].destino.id).toBe(estagio2.id);
        });

        it('O estagio e2 será destino de uma transição proibida para e1 em "destinoDeTransicoesProibidas" do estagio2', function() {
          expect(estagio2.destinoDeTransicoesProibidas[0].destino.id).toBe(estagio2.id);
        });

        it('O objeto de transicoesProibidasDoAnel deverá ter um atributo igual a "`e1.posicao`_`e2.posicao`"', function() {
          var transicao = 'E' + estagio1.posicao + '-E' + estagio2.posicao;
          var transicaoNoAnel = scope.currentAnel.transicoesProibidas[transicao];
          expect(transicaoNoAnel).toBeDefined();
          expect(transicaoNoAnel.id).toEqual(estagio1.origemDeTransicoesProibidas[0].id);
        });

        it('Quando o usuario desativa a intercessão E1-E2, os campos de origem e destino de ambos deverão ser removidos', function() {
          scope.toggleTransicaoProibida(estagio1, estagio2, false);
          expect(estagio1.origemDeTransicoesProibidas.length).toBe(0);
          expect(estagio2.destinoDeTransicoesProibidas.length).toBe(0);
          expect(estagio1.origemDeTransicoesProibidas.length).toBe(0);
          expect(estagio2.destinoDeTransicoesProibidas.length).toBe(0);
        });

        it('Quando o usuario desativa a intercessão E1-E2, o atributo "`e1.id`_`e2.id`" de transicoesProibidasDoAnel deverá ser removido', function() {
          scope.toggleTransicaoProibida(estagio1, estagio2, false);
          var transicao = 'E' + estagio1.posicao + '-E' + estagio2.posicao;
          expect(scope.currentAnel.transicoesProibidas[transicao]).not.toBeDefined();
        });
      });
    });

    describe('marcarTransicaoAlternativa', function () {
      var estagio1, estagio2, estagio3, estagio4;
      describe('Dado que a transicacao E1-E2 receba o estágio E3 como alternativo', function () {
        beforeEach(function() {
          var objeto = {'id': 'ce3ee3dd-7f5e-4357-9663-f47f37c150f8','localizacao': 'area 1','numeroSMEE': 'area 1','numeroSMEEConjugado1': '12','numeroSMEEConjugado2': '123','numeroSMEEConjugado3': '123','firmware': '321','latitude': -19.9513211,'longitude': -43.921468600000026,'dataCriacao': '28/06/2016 23:31:32','dataAtualizacao': '28/06/2016 23:32:27','CLC': '1.000.0999','area': {'id': '6a2e0afd-616b-40eb-a834-60e33195b009','descricao': 1,'dataCriacao': '28/06/2016 19:35:03','dataAtualizacao': '28/06/2016 19:35:03','cidade': {'id': '6db983c9-8b03-42f7-ac8b-f19d9b0b92d1','nome': 'Belo Horizonte','dataCriacao': '28/06/2016 19:34:54','dataAtualizacao': '28/06/2016 19:34:54','areas': [{'id': '6a2e0afd-616b-40eb-a834-60e33195b009','descricao': 1},{'id': 'b9a85f3a-ac49-436b-a121-9af51db9b9a8','descricao': 2}]},'limites': []},'modelo': {'id': '820c1ede-f516-4377-9754-f8339f1f3071','descricao': 'fab 1 opa','dataCriacao': '28/06/2016 19:36:07','dataAtualizacao': '28/06/2016 19:36:07','fabricante': {'id': 'ecedfd9f-4649-470d-a10a-acaf10350da1','nome': 'fab 1','dataCriacao': '28/06/2016 19:36:07','dataAtualizacao': '28/06/2016 19:36:07','modelos': [{'id': '820c1ede-f516-4377-9754-f8339f1f3071','descricao': 'fab 1 opa','configuracao': {'id': 'eeff04ca-ed6e-4ead-9b6a-0ba84b4adbb0','descricao': 'opa','limiteEstagio': 8,'limiteGrupoSemaforico': 8,'limiteAnel': 8,'limiteDetectorPedestre': 8,'limiteDetectorVeicular': 8,'dataCriacao': '28/06/2016 19:35:52','dataAtualizacao': '28/06/2016 19:35:52'}}]},'configuracao': {'id': 'eeff04ca-ed6e-4ead-9b6a-0ba84b4adbb0','descricao': 'opa','limiteEstagio': 8,'limiteGrupoSemaforico': 8,'limiteAnel': 8,'limiteDetectorPedestre': 8,'limiteDetectorVeicular': 8,'dataCriacao': '28/06/2016 19:35:52','dataAtualizacao': '28/06/2016 19:35:52'}},'aneis': [{'id': '16a2cf30-3957-4d99-a6bf-bdec16e5891f','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': '19883586-ff33-4585-a004-311dcb3aada0','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': '41bfb905-3950-414c-9ee6-147b13bc70f8','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': '578ac07c-68ce-4e72-8368-424b8ef506a2','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'aba2b4c3-de65-4baa-b064-1375199e40b8','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'd7c5dc59-3f83-4c66-ac05-4dcfb5429de7','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'efc70dee-08d1-4bf2-b38d-5143dde2a84e','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'fabea98b-bd33-480b-8b10-48332849fe9b','numeroSMEE': 'teste','ativo': true,'posicao': 1,'latitude': -19.9513211,'longitude': -43.921468600000026,'quantidadeGrupoPedestre': 2,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:31:32','dataAtualizacao': '28/06/2016 23:32:27','estagios': [{'id': 'estagio-1'},{'id': 'estagio-2'},{'id': 'estagio-3'},{'id': 'estagio-4'}],'gruposSemaforicos': [{'id': '5225d0bc-5b41-4b40-8647-73a985e54997','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 1,'descricao': 'veicular g1','estagioGrupoSemaforicos': [{'id': '3e61befa-e09c-4e2e-8470-f0224f494712','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': '6b95029b-bba8-4186-9875-fd62b8f0d1de','imagem': {'id': '4b1a186f-df95-4cf0-8bf6-9fe01cb89bfe','filename': '13406967_1003062549749489_543993289383831132_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '5225d0bc-5b41-4b40-8647-73a985e54997'}}]},{'id': '82b80f5f-82b8-4905-865e-932e324a9796','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 2,'descricao': 'veicular g2','estagioGrupoSemaforicos': [{'id': '48030015-0332-4a56-a02b-c9fa54521462','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': 'f6d285ef-83a3-4498-a4da-d8c1d2c6d7ff','imagem': {'id': '8e58963d-2b83-43ec-82eb-8609b2016bfd','filename': '12321359_986438248070903_1173574894423312875_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '82b80f5f-82b8-4905-865e-932e324a9796'}}]}]}],'gruposSemaforicos': [{'id': '5225d0bc-5b41-4b40-8647-73a985e54997','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 1,'descricao': 'veicular g1','estagioGrupoSemaforicos': [{'id': '3e61befa-e09c-4e2e-8470-f0224f494712','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': '6b95029b-bba8-4186-9875-fd62b8f0d1de','imagem': {'id': '4b1a186f-df95-4cf0-8bf6-9fe01cb89bfe','filename': '13406967_1003062549749489_543993289383831132_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '5225d0bc-5b41-4b40-8647-73a985e54997'}}]},{'id': '82b80f5f-82b8-4905-865e-932e324a9796','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 2,'descricao': 'veicular g2','estagioGrupoSemaforicos': [{'id': '48030015-0332-4a56-a02b-c9fa54521462','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': 'f6d285ef-83a3-4498-a4da-d8c1d2c6d7ff','imagem': {'id': '8e58963d-2b83-43ec-82eb-8609b2016bfd','filename': '12321359_986438248070903_1173574894423312875_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '82b80f5f-82b8-4905-865e-932e324a9796'}}]}],'route': 'controladores','reqParams': null,'restangularized': true,'fromServer': true,'parentResource': null,'restangularCollection': false};
          WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaTransicoesProibidas);
          estagio1 = scope.currentAnel.estagios[0];
          estagio2 = scope.currentAnel.estagios[1];
          estagio3 = scope.currentAnel.estagios[2];
          estagio4 = scope.currentAnel.estagios[3];
          scope.toggleTransicaoProibida(estagio1, estagio2, false);
          scope.marcarTransicaoAlternativa(
            {origem: {id: estagio1.id}, destino: {id: estagio2.id}, alternativo: {id: estagio3.id}}
          );
        });

        it('O estágio E1 deve ter um objeto em origemDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E3',
          function() {
            expect(estagio1.origemDeTransicoesProibidas[0].origem.id).toBe(estagio1.id);
            expect(estagio1.origemDeTransicoesProibidas[0].destino.id).toBe(estagio2.id);
            expect(estagio1.origemDeTransicoesProibidas[0].alternativo.id).toBe(estagio3.id);
          });
        it('O estágio E2 deve ter um objeto em destinoDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E3',
          function() {
            expect(estagio2.destinoDeTransicoesProibidas[0].origem.id).toBe(estagio1.id);
            expect(estagio2.destinoDeTransicoesProibidas[0].destino.id).toBe(estagio2.id);
            expect(estagio2.destinoDeTransicoesProibidas[0].alternativo.id).toBe(estagio3.id);
          });
        it('O estágio E3 deve ter um objeto em alternativaDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E3',
          function() {
            expect(estagio3.alternativaDeTransicoesProibidas[0].origem.id).toBe(estagio1.id);
            expect(estagio3.alternativaDeTransicoesProibidas[0].destino.id).toBe(estagio2.id);
            expect(estagio3.alternativaDeTransicoesProibidas[0].alternativo.id).toBe(estagio3.id);
          });
        it('O estagio E4 não deverá ter objetos em nenhum dos campos de transicoesProibidas',
          function() {
            expect(estagio4.origemDeTransicoesProibidas).not.toBeDefined();
            expect(estagio4.destinoDeTransicoesProibidas).not.toBeDefined();
            expect(estagio4.alternativaDeTransicoesProibidas).not.toBeDefined();
          });

        describe('Dado que a alternativa de E1-E2 seja alterada para E4', function () {
          beforeEach(function() {
            scope.marcarTransicaoAlternativa(
              {origem: {id: estagio1.id}, destino: {id: estagio2.id}, alternativo: {id: estagio4.id}}
            );
          });

          it('O estágio E1 deve ter um objeto em origemDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E4',
            function() {
              expect(estagio1.origemDeTransicoesProibidas[0].origem.id).toBe(estagio1.id);
              expect(estagio1.origemDeTransicoesProibidas[0].destino.id).toBe(estagio2.id);
              expect(estagio1.origemDeTransicoesProibidas[0].alternativo.id).toBe(estagio4.id);
            });
          it('O estágio E2 deve ter um objeto em destinoDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E4',
            function() {
              expect(estagio2.destinoDeTransicoesProibidas[0].origem.id).toBe(estagio1.id);
              expect(estagio2.destinoDeTransicoesProibidas[0].destino.id).toBe(estagio2.id);
              expect(estagio2.destinoDeTransicoesProibidas[0].alternativo.id).toBe(estagio4.id);
            });
          it('O estagio E3 não deverá ter objetos em nenhum dos campos de transicoesProibidas',
            function() {
              expect(estagio3.origemDeTransicoesProibidas).not.toBeDefined();
              expect(estagio3.destinoDeTransicoesProibidas).not.toBeDefined();
              expect(estagio3.alternativaDeTransicoesProibidas[0]).not.toBeDefined();
            });
          it('O estágio E4 deve ter um objeto em alternativaDeTransicoesProibidas, com origem = E1, destino = E2 e alternativo = E4',
            function() {
              expect(estagio4.alternativaDeTransicoesProibidas[0].origem.id).toBe(estagio1.id);
              expect(estagio4.alternativaDeTransicoesProibidas[0].destino.id).toBe(estagio2.id);
              expect(estagio4.alternativaDeTransicoesProibidas[0].alternativo.id).toBe(estagio4.id);
            });
        });

        describe('Dado que a alternativa de E1-E2 seja apagada', function () {
          beforeEach(function() {
            scope.marcarTransicaoAlternativa(
              {origem: {id: estagio1.id}, destino: {id: estagio2.id}, alternativo: null}
            );
          });

          it('O estágio E1 deve ter um objeto em origemDeTransicoesProibidas, com origem = E1 e destino = E2',
            function() {
              expect(estagio1.origemDeTransicoesProibidas[0].origem.id).toBe(estagio1.id);
              expect(estagio1.origemDeTransicoesProibidas[0].destino.id).toBe(estagio2.id);
              expect(estagio1.origemDeTransicoesProibidas[0].alternativo).toBe(null);
            });
          it('O estágio E2 deve ter um objeto em destinoDeTransicoesProibidas, com origem = E1 e destino = E2',
            function() {
              expect(estagio2.destinoDeTransicoesProibidas[0].origem.id).toBe(estagio1.id);
              expect(estagio2.destinoDeTransicoesProibidas[0].destino.id).toBe(estagio2.id);
              expect(estagio2.destinoDeTransicoesProibidas[0].alternativo).toBe(null);
            });
          it('O estagio E3 não deverá ter objetos em nenhum dos campos de transicoesProibidas',
            function() {
              expect(estagio3.origemDeTransicoesProibidas).not.toBeDefined();
              expect(estagio3.destinoDeTransicoesProibidas).not.toBeDefined();
              expect(estagio3.alternativaDeTransicoesProibidas[0]).not.toBeDefined();
            });
          it('O estagio E4 não deverá ter objetos em nenhum dos campos de transicoesProibidas',
            function() {
              expect(estagio4.origemDeTransicoesProibidas).not.toBeDefined();
              expect(estagio4.destinoDeTransicoesProibidas).not.toBeDefined();
              expect(estagio4.alternativaDeTransicoesProibidas).not.toBeDefined();
            });
        });
      });
    });

    describe('filterEstagiosAlternativos', function () {
      var estagio1, estagio2, estagio3;
      beforeEach(function() {
        var objeto = {'id': 'ce3ee3dd-7f5e-4357-9663-f47f37c150f8','localizacao': 'area 1','numeroSMEE': 'area 1','numeroSMEEConjugado1': '12','numeroSMEEConjugado2': '123','numeroSMEEConjugado3': '123','firmware': '321','latitude': -19.9513211,'longitude': -43.921468600000026,'dataCriacao': '28/06/2016 23:31:32','dataAtualizacao': '28/06/2016 23:32:27','CLC': '1.000.0999','area': {'id': '6a2e0afd-616b-40eb-a834-60e33195b009','descricao': 1,'dataCriacao': '28/06/2016 19:35:03','dataAtualizacao': '28/06/2016 19:35:03','cidade': {'id': '6db983c9-8b03-42f7-ac8b-f19d9b0b92d1','nome': 'Belo Horizonte','dataCriacao': '28/06/2016 19:34:54','dataAtualizacao': '28/06/2016 19:34:54','areas': [{'id': '6a2e0afd-616b-40eb-a834-60e33195b009','descricao': 1},{'id': 'b9a85f3a-ac49-436b-a121-9af51db9b9a8','descricao': 2}]},'limites': []},'modelo': {'id': '820c1ede-f516-4377-9754-f8339f1f3071','descricao': 'fab 1 opa','dataCriacao': '28/06/2016 19:36:07','dataAtualizacao': '28/06/2016 19:36:07','fabricante': {'id': 'ecedfd9f-4649-470d-a10a-acaf10350da1','nome': 'fab 1','dataCriacao': '28/06/2016 19:36:07','dataAtualizacao': '28/06/2016 19:36:07','modelos': [{'id': '820c1ede-f516-4377-9754-f8339f1f3071','descricao': 'fab 1 opa','configuracao': {'id': 'eeff04ca-ed6e-4ead-9b6a-0ba84b4adbb0','descricao': 'opa','limiteEstagio': 8,'limiteGrupoSemaforico': 8,'limiteAnel': 8,'limiteDetectorPedestre': 8,'limiteDetectorVeicular': 8,'dataCriacao': '28/06/2016 19:35:52','dataAtualizacao': '28/06/2016 19:35:52'}}]},'configuracao': {'id': 'eeff04ca-ed6e-4ead-9b6a-0ba84b4adbb0','descricao': 'opa','limiteEstagio': 8,'limiteGrupoSemaforico': 8,'limiteAnel': 8,'limiteDetectorPedestre': 8,'limiteDetectorVeicular': 8,'dataCriacao': '28/06/2016 19:35:52','dataAtualizacao': '28/06/2016 19:35:52'}},'aneis': [{'id': '16a2cf30-3957-4d99-a6bf-bdec16e5891f','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': '19883586-ff33-4585-a004-311dcb3aada0','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': '41bfb905-3950-414c-9ee6-147b13bc70f8','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': '578ac07c-68ce-4e72-8368-424b8ef506a2','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'aba2b4c3-de65-4baa-b064-1375199e40b8','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'd7c5dc59-3f83-4c66-ac05-4dcfb5429de7','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'efc70dee-08d1-4bf2-b38d-5143dde2a84e','ativo': false,'quantidadeGrupoPedestre': 0,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','estagios': [],'gruposSemaforicos': []},{'id': 'fabea98b-bd33-480b-8b10-48332849fe9b','numeroSMEE': 'teste','ativo': true,'posicao': 1,'latitude': -19.9513211,'longitude': -43.921468600000026,'quantidadeGrupoPedestre': 2,'quantidadeGrupoVeicular': 0,'quantidadeDetectorPedestre': 0,'quantidadeDetectorVeicular': 0,'dataCriacao': '28/06/2016 23:31:32','dataAtualizacao': '28/06/2016 23:32:27','estagios': [{'id': '6b95029b-bba8-4186-9875-fd62b8f0d1de','imagem': {'id': '4b1a186f-df95-4cf0-8bf6-9fe01cb89bfe','filename': '13406967_1003062549749489_543993289383831132_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},{'id': 'f6d285ef-83a3-4498-a4da-d8c1d2c6d7ff','imagem': {'id': '8e58963d-2b83-43ec-82eb-8609b2016bfd','filename': '12321359_986438248070903_1173574894423312875_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},{'id': 'f6d285ef-83a3-4498-a4da-d8c1d2c6d7ee'}],'gruposSemaforicos': [{'id': '5225d0bc-5b41-4b40-8647-73a985e54997','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 1,'descricao': 'veicular g1','estagioGrupoSemaforicos': [{'id': '3e61befa-e09c-4e2e-8470-f0224f494712','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': '6b95029b-bba8-4186-9875-fd62b8f0d1de','imagem': {'id': '4b1a186f-df95-4cf0-8bf6-9fe01cb89bfe','filename': '13406967_1003062549749489_543993289383831132_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '5225d0bc-5b41-4b40-8647-73a985e54997'}}]},{'id': '82b80f5f-82b8-4905-865e-932e324a9796','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 2,'descricao': 'veicular g2','estagioGrupoSemaforicos': [{'id': '48030015-0332-4a56-a02b-c9fa54521462','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': 'f6d285ef-83a3-4498-a4da-d8c1d2c6d7ff','imagem': {'id': '8e58963d-2b83-43ec-82eb-8609b2016bfd','filename': '12321359_986438248070903_1173574894423312875_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '82b80f5f-82b8-4905-865e-932e324a9796'}}]}]}],'gruposSemaforicos': [{'id': '5225d0bc-5b41-4b40-8647-73a985e54997','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 1,'descricao': 'veicular g1','estagioGrupoSemaforicos': [{'id': '3e61befa-e09c-4e2e-8470-f0224f494712','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': '6b95029b-bba8-4186-9875-fd62b8f0d1de','imagem': {'id': '4b1a186f-df95-4cf0-8bf6-9fe01cb89bfe','filename': '13406967_1003062549749489_543993289383831132_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '5225d0bc-5b41-4b40-8647-73a985e54997'}}]},{'id': '82b80f5f-82b8-4905-865e-932e324a9796','dataCriacao': '28/06/2016 23:31:55','dataAtualizacao': '28/06/2016 23:32:27','tipo': 'PEDESTRE','posicao': 2,'descricao': 'veicular g2','estagioGrupoSemaforicos': [{'id': '48030015-0332-4a56-a02b-c9fa54521462','dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27','ativo': true,'estagio': {'id': 'f6d285ef-83a3-4498-a4da-d8c1d2c6d7ff','imagem': {'id': '8e58963d-2b83-43ec-82eb-8609b2016bfd','filename': '12321359_986438248070903_1173574894423312875_n.jpg','contentType': 'image/jpeg','dataCriacao': '28/06/2016 23:31:52','dataAtualizacao': '28/06/2016 23:31:52'},'demandaPrioritaria': false,'dataCriacao': '28/06/2016 23:32:27','dataAtualizacao': '28/06/2016 23:32:27'},'grupoSemaforico': {'id': '82b80f5f-82b8-4905-865e-932e324a9796'}}]}],'route': 'controladores','reqParams': null,'restangularized': true,'fromServer': true,'parentResource': null,'restangularCollection': false};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaTransicoesProibidas);

        estagio1 = scope.currentAnel.estagios[0];
        estagio2 = scope.currentAnel.estagios[1];
        estagio3 = scope.currentAnel.estagios[2];
        scope.toggleTransicaoProibida(estagio1, estagio2, false);
        scope.marcarTransicaoAlternativa({origem: estagio1, destino: estagio2, alternativo: estagio3});
      });

      it('Em uma lista de estágios (E1, E2, E3), a transicao E1-E2 pode ter somente E3 como alternativa',
        inject(function($filter) {
          var filter = $filter('filter')(
            scope.currentAnel.estagios,
            scope.filterEstagiosAlternativos(estagio1, estagio2)
          );
          expect(filter.length).toBe(1);
        }));
    });
  });
});
