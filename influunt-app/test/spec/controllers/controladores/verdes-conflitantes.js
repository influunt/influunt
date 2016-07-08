'use strict';

describe('Controller: ControladoresVerdesConflitantesCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresVerdesConflitantesCtrl,
    scope,
    $q,
    $httpBackend,
    $state;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$state_, _$q_) {
    scope = $rootScope.$new();
    ControladoresVerdesConflitantesCtrl = $controller('ControladoresVerdesConflitantesCtrl', {
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

    describe('inicializaVerdesConflitantes', function() {
      beforeEach(function() {
        scope.objeto = {idControlador: '1234567', aneis: [{}], modelo: {configuracao: {limiteAnel: 4}}};
        WizardControladores.fakeInicializaWizard(scope, $q, scope.objeto, scope.inicializaVerdesConflitantes);
        scope.$apply();
      });

      it('Não deve acessar a tela de verdes conflitantes caso não haja ao menos um grupo semaforico declarado',
        function () {
          expect($state.current.name).not.toBe('app.wizard_controladores.verdes_conflitantes');
        });
    });
  });

  describe('Funções auxiliares', function () {
    describe('toggleVerdeConflitante', function () {
      beforeEach(function() {
        var objeto = {"id":"ce3ee3dd-7f5e-4357-9663-f47f37c150f8","localizacao":"area 1","numeroSMEE":"area 1","numeroSMEEConjugado1":"12","numeroSMEEConjugado2":"123","numeroSMEEConjugado3":"123","firmware":"321","latitude":-19.9513211,"longitude":-43.921468600000026,"dataCriacao":"28/06/2016 23:31:32","dataAtualizacao":"28/06/2016 23:32:27","CLC":"1.000.0999","area":{"id":"6a2e0afd-616b-40eb-a834-60e33195b009","descricao":1,"dataCriacao":"28/06/2016 19:35:03","dataAtualizacao":"28/06/2016 19:35:03","cidade":{"id":"6db983c9-8b03-42f7-ac8b-f19d9b0b92d1","nome":"Belo Horizonte","dataCriacao":"28/06/2016 19:34:54","dataAtualizacao":"28/06/2016 19:34:54","areas":[{"id":"6a2e0afd-616b-40eb-a834-60e33195b009","descricao":1},{"id":"b9a85f3a-ac49-436b-a121-9af51db9b9a8","descricao":2}]},"limites":[]},"modelo":{"id":"820c1ede-f516-4377-9754-f8339f1f3071","descricao":"fab 1 opa","dataCriacao":"28/06/2016 19:36:07","dataAtualizacao":"28/06/2016 19:36:07","fabricante":{"id":"ecedfd9f-4649-470d-a10a-acaf10350da1","nome":"fab 1","dataCriacao":"28/06/2016 19:36:07","dataAtualizacao":"28/06/2016 19:36:07","modelos":[{"id":"820c1ede-f516-4377-9754-f8339f1f3071","descricao":"fab 1 opa","configuracao":{"id":"eeff04ca-ed6e-4ead-9b6a-0ba84b4adbb0","descricao":"opa","limiteEstagio":8,"limiteGrupoSemaforico":8,"limiteAnel":8,"limiteDetectorPedestre":8,"limiteDetectorVeicular":8,"dataCriacao":"28/06/2016 19:35:52","dataAtualizacao":"28/06/2016 19:35:52"}}]},"configuracao":{"id":"eeff04ca-ed6e-4ead-9b6a-0ba84b4adbb0","descricao":"opa","limiteEstagio":8,"limiteGrupoSemaforico":8,"limiteAnel":8,"limiteDetectorPedestre":8,"limiteDetectorVeicular":8,"dataCriacao":"28/06/2016 19:35:52","dataAtualizacao":"28/06/2016 19:35:52"}},"aneis":[{"id":"16a2cf30-3957-4d99-a6bf-bdec16e5891f","ativo":false,"quantidadeGrupoPedestre":0,"quantidadeGrupoVeicular":0,"quantidadeDetectorPedestre":0,"quantidadeDetectorVeicular":0,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27","estagios":[],"gruposSemaforicos":[]},{"id":"19883586-ff33-4585-a004-311dcb3aada0","ativo":false,"quantidadeGrupoPedestre":0,"quantidadeGrupoVeicular":0,"quantidadeDetectorPedestre":0,"quantidadeDetectorVeicular":0,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27","estagios":[],"gruposSemaforicos":[]},{"id":"41bfb905-3950-414c-9ee6-147b13bc70f8","ativo":false,"quantidadeGrupoPedestre":0,"quantidadeGrupoVeicular":0,"quantidadeDetectorPedestre":0,"quantidadeDetectorVeicular":0,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27","estagios":[],"gruposSemaforicos":[]},{"id":"578ac07c-68ce-4e72-8368-424b8ef506a2","ativo":false,"quantidadeGrupoPedestre":0,"quantidadeGrupoVeicular":0,"quantidadeDetectorPedestre":0,"quantidadeDetectorVeicular":0,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27","estagios":[],"gruposSemaforicos":[]},{"id":"aba2b4c3-de65-4baa-b064-1375199e40b8","ativo":false,"quantidadeGrupoPedestre":0,"quantidadeGrupoVeicular":0,"quantidadeDetectorPedestre":0,"quantidadeDetectorVeicular":0,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27","estagios":[],"gruposSemaforicos":[]},{"id":"d7c5dc59-3f83-4c66-ac05-4dcfb5429de7","ativo":false,"quantidadeGrupoPedestre":0,"quantidadeGrupoVeicular":0,"quantidadeDetectorPedestre":0,"quantidadeDetectorVeicular":0,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27","estagios":[],"gruposSemaforicos":[]},{"id":"efc70dee-08d1-4bf2-b38d-5143dde2a84e","ativo":false,"quantidadeGrupoPedestre":0,"quantidadeGrupoVeicular":0,"quantidadeDetectorPedestre":0,"quantidadeDetectorVeicular":0,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27","estagios":[],"gruposSemaforicos":[]},{"id":"fabea98b-bd33-480b-8b10-48332849fe9b","numeroSMEE":"teste","ativo":true,"posicao":1,"latitude":-19.9513211,"longitude":-43.921468600000026,"quantidadeGrupoPedestre":2,"quantidadeGrupoVeicular":0,"quantidadeDetectorPedestre":0,"quantidadeDetectorVeicular":0,"dataCriacao":"28/06/2016 23:31:32","dataAtualizacao":"28/06/2016 23:32:27","estagios":[{"id":"6b95029b-bba8-4186-9875-fd62b8f0d1de","imagem":{"id":"4b1a186f-df95-4cf0-8bf6-9fe01cb89bfe","filename":"13406967_1003062549749489_543993289383831132_n.jpg","contentType":"image/jpeg","dataCriacao":"28/06/2016 23:31:52","dataAtualizacao":"28/06/2016 23:31:52"},"demandaPrioritaria":false,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27"},{"id":"f6d285ef-83a3-4498-a4da-d8c1d2c6d7ff","imagem":{"id":"8e58963d-2b83-43ec-82eb-8609b2016bfd","filename":"12321359_986438248070903_1173574894423312875_n.jpg","contentType":"image/jpeg","dataCriacao":"28/06/2016 23:31:52","dataAtualizacao":"28/06/2016 23:31:52"},"demandaPrioritaria":false,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27"}],"gruposSemaforicos":[{"id":"5225d0bc-5b41-4b40-8647-73a985e54997","dataCriacao":"28/06/2016 23:31:55","dataAtualizacao":"28/06/2016 23:32:27","tipo":"PEDESTRE","posicao":1,"descricao":"veicular g1","estagioGrupoSemaforicos":[{"id":"3e61befa-e09c-4e2e-8470-f0224f494712","dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27","ativo":true,"estagio":{"id":"6b95029b-bba8-4186-9875-fd62b8f0d1de","imagem":{"id":"4b1a186f-df95-4cf0-8bf6-9fe01cb89bfe","filename":"13406967_1003062549749489_543993289383831132_n.jpg","contentType":"image/jpeg","dataCriacao":"28/06/2016 23:31:52","dataAtualizacao":"28/06/2016 23:31:52"},"demandaPrioritaria":false,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27"},"grupoSemaforico":{"id":"5225d0bc-5b41-4b40-8647-73a985e54997"}}]},{"id":"82b80f5f-82b8-4905-865e-932e324a9796","dataCriacao":"28/06/2016 23:31:55","dataAtualizacao":"28/06/2016 23:32:27","tipo":"PEDESTRE","posicao":2,"descricao":"veicular g2","estagioGrupoSemaforicos":[{"id":"48030015-0332-4a56-a02b-c9fa54521462","dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27","ativo":true,"estagio":{"id":"f6d285ef-83a3-4498-a4da-d8c1d2c6d7ff","imagem":{"id":"8e58963d-2b83-43ec-82eb-8609b2016bfd","filename":"12321359_986438248070903_1173574894423312875_n.jpg","contentType":"image/jpeg","dataCriacao":"28/06/2016 23:31:52","dataAtualizacao":"28/06/2016 23:31:52"},"demandaPrioritaria":false,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27"},"grupoSemaforico":{"id":"82b80f5f-82b8-4905-865e-932e324a9796"}}]}]}],"gruposSemaforicos":[{"id":"5225d0bc-5b41-4b40-8647-73a985e54997","dataCriacao":"28/06/2016 23:31:55","dataAtualizacao":"28/06/2016 23:32:27","tipo":"PEDESTRE","posicao":1,"descricao":"veicular g1","estagioGrupoSemaforicos":[{"id":"3e61befa-e09c-4e2e-8470-f0224f494712","dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27","ativo":true,"estagio":{"id":"6b95029b-bba8-4186-9875-fd62b8f0d1de","imagem":{"id":"4b1a186f-df95-4cf0-8bf6-9fe01cb89bfe","filename":"13406967_1003062549749489_543993289383831132_n.jpg","contentType":"image/jpeg","dataCriacao":"28/06/2016 23:31:52","dataAtualizacao":"28/06/2016 23:31:52"},"demandaPrioritaria":false,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27"},"grupoSemaforico":{"id":"5225d0bc-5b41-4b40-8647-73a985e54997"}}]},{"id":"82b80f5f-82b8-4905-865e-932e324a9796","dataCriacao":"28/06/2016 23:31:55","dataAtualizacao":"28/06/2016 23:32:27","tipo":"PEDESTRE","posicao":2,"descricao":"veicular g2","estagioGrupoSemaforicos":[{"id":"48030015-0332-4a56-a02b-c9fa54521462","dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27","ativo":true,"estagio":{"id":"f6d285ef-83a3-4498-a4da-d8c1d2c6d7ff","imagem":{"id":"8e58963d-2b83-43ec-82eb-8609b2016bfd","filename":"12321359_986438248070903_1173574894423312875_n.jpg","contentType":"image/jpeg","dataCriacao":"28/06/2016 23:31:52","dataAtualizacao":"28/06/2016 23:31:52"},"demandaPrioritaria":false,"dataCriacao":"28/06/2016 23:32:27","dataAtualizacao":"28/06/2016 23:32:27"},"grupoSemaforico":{"id":"82b80f5f-82b8-4905-865e-932e324a9796"}}]}],"route":"controladores","reqParams":null,"restangularized":true,"fromServer":true,"parentResource":null,"restangularCollection":false};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaVerdesConflitantes);
      });

      it('se a caixa de verde conflitante clicada estiver desabilitada, os verdes conflitantes não serão alterados',
        function() {
          scope.toggleVerdeConflitante(0, 0, true);
          expect(scope.verdesConflitantes[0][0]).not.toBeTruthy();
        });

      describe('Ativação de um verde conflitante.', function () {
        it('Deve adicionar o id do grupo "y" nos verdes conflitantes do grupo "x"', function() {

          var gruposAneis = _.chain(scope.objeto.aneis).map('gruposSemaforicos').flatten().value();
          var grupo0 = _.find(gruposAneis, {posicao: 1});
          var grupo1 = _.find(gruposAneis, {posicao: 2});
          scope.toggleVerdeConflitante(0, 1);

          expect(grupo0.verdesConflitantes.length).toBe(1);
          expect(grupo0.verdesConflitantes[0].id).toBe(grupo1.id);
        });

        it('A matriz deve marcar a posicao 0x1 como verde conflitante.', function() {
          scope.toggleVerdeConflitante(0, 1);
          expect(scope.verdesConflitantes[0][1]).toBeTruthy();
        });

        it('se a posicao 0x1 é verde conflitante, a posicao 1x0 também deverá ser.', function() {
          scope.toggleVerdeConflitante(0, 1);
          expect(scope.verdesConflitantes[0][1]).toBeTruthy();
          expect(scope.verdesConflitantes[1][0]).toBeTruthy();
        });

        it('Dado que o elemento (x, y) foi marcado como verde conflitante, o elemento (y, x) ' +
          'também deverá ser marcado.', function () {
          var gruposAneis = _.chain(scope.objeto.aneis).map('gruposSemaforicos').flatten().value();
          var grupo0 = _.find(gruposAneis, {posicao: 1});
          var grupo1 = _.find(gruposAneis, {posicao: 2});
          scope.toggleVerdeConflitante(0, 1);

          expect(grupo0.verdesConflitantes.length).toBe(1);
          expect(grupo0.verdesConflitantes[0].id).toBe(grupo1.id);
          expect(grupo1.verdesConflitantes.length).toBe(1);
          expect(grupo1.verdesConflitantes[0].id).toBe(grupo0.id);
        });
      });

      describe('desativação de um verde conflitante.', function () {
        beforeEach(function() {
          scope.toggleVerdeConflitante(0, 1);
        });

        it('O grupo "x" não deve ter verdes conflitantes', function() {
          var gruposAneis = _.chain(scope.objeto.aneis).map('gruposSemaforicos').flatten().value();
          var grupo0 = _.find(gruposAneis, {posicao: 1});
          scope.toggleVerdeConflitante(0, 1);
          expect(grupo0.verdesConflitantes.length).toBe(0);
        });

        it('A matriz deve apontar a posicao 0x0 como um verde não conflitante', function () {
          scope.toggleVerdeConflitante(0, 1);
          expect(scope.verdesConflitantes[0][1]).not.toBeTruthy();
        });
      });
    });
  });
});
