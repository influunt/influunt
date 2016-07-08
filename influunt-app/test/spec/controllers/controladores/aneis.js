'use strict';

describe('Controller: ControladoresAneisCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresAneisCtrl,
    scope,
    $httpBackend,
    $q,
    $state;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$q_, _$state_) {
    scope = $rootScope.$new();
    ControladoresAneisCtrl = $controller('ControladoresAneisCtrl', {
      $scope: scope
      // place here mocked dependencies
    });

    $httpBackend = _$httpBackend_;
    $q = _$q_;
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

    describe('InicializaAneis', function () {
      beforeEach(function() {
        var objeto = {'id':'69e52a0f-bd25-4b56-810e-750ba003b9b6','descricao':'teste','numeroSMEE':'teste','numeroSMEEConjugado1':'123','numeroSMEEConjugado2':'123','numeroSMEEConjugado3':'123','firmware':'1232','latitude':-19.9809899,'longitude':-44.03494549999999,'dataCriacao':'24/06/2016 18:40:45','dataAtualizacao':'24/06/2016 18:40:45','CLC':'1.000.0999','area':{'id':'41648354-f83a-4680-8571-621630e16008','descricao':1,'dataCriacao':'24/06/2016 11:38:32','dataAtualizacao':'24/06/2016 11:38:32','cidade':{'id':'15b83474-f4bf-499b-a41d-a12f04429cb4','nome':'Belo Horizonte','dataCriacao':'24/06/2016 11:38:24','dataAtualizacao':'24/06/2016 11:38:24','areas':[{'id':'41648354-f83a-4680-8571-621630e16008','descricao':1},{'id':'ed522abb-85c4-425d-a65d-d5947cd46888','descricao':2}]},'limites':[]},'modelo':{'id':'cb0489e1-a0ad-4145-a2cf-7cca7e19f8f2','descricao':'opa 2 fab2','dataCriacao':'24/06/2016 11:39:59','dataAtualizacao':'24/06/2016 11:39:59','fabricante':{'id':'b195beea-4f3b-4614-83f3-2da0f7d2874b','nome':'fabricante 2','dataCriacao':'24/06/2016 11:39:59','dataAtualizacao':'24/06/2016 11:39:59','modelos':[{'id':'cb0489e1-a0ad-4145-a2cf-7cca7e19f8f2','descricao':'opa 2 fab2','configuracao':{'id':'f3e4293d-e406-4f0d-a2cb-5ddae54e3a72','descricao':'opa 2','limiteEstagio':8,'limiteGrupoSemaforico':8,'limiteAnel':8,'limiteDetectorPedestre':8,'limiteDetectorVeicular':32,'dataCriacao':'24/06/2016 11:39:07','dataAtualizacao':'24/06/2016 11:39:07'}},{'id':'dd1b1175-a2ea-4dde-aa0d-5ec80ad886c7','descricao':'opa 1 fab2','configuracao':{'id':'f20a1b3a-c43b-43da-a9ba-6e2d7da4afc9','descricao':'opa 1','limiteEstagio':4,'limiteGrupoSemaforico':4,'limiteAnel':4,'limiteDetectorPedestre':4,'limiteDetectorVeicular':16,'dataCriacao':'24/06/2016 11:38:58','dataAtualizacao':'24/06/2016 11:38:58'}}]},'configuracao':{'id':'f3e4293d-e406-4f0d-a2cb-5ddae54e3a72','descricao':'opa 2','limiteEstagio':8,'limiteGrupoSemaforico':8,'limiteAnel':8,'limiteDetectorPedestre':8,'limiteDetectorVeicular':32,'dataCriacao':'24/06/2016 11:39:07','dataAtualizacao':'24/06/2016 11:39:07'}},'aneis':[{'id':'02022a0a-fe52-4580-bf68-004ad0bf2340','ativo':false,'posicao':3,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 18:40:45','dataAtualizacao':'24/06/2016 18:40:45','estagios':[],'gruposSemaforicos':[]},{'id':'0a38f42d-a3bf-4611-941d-bb3ff3ade8c3','ativo':false,'posicao':8,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 18:40:45','dataAtualizacao':'24/06/2016 18:40:45','estagios':[],'gruposSemaforicos':[]},{'id':'162a59a1-b0ff-4bf1-9f18-d86071f60060','ativo':false,'posicao':4,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 18:40:45','dataAtualizacao':'24/06/2016 18:40:45','estagios':[],'gruposSemaforicos':[]},{'id':'251eebf4-ec8d-4516-8577-c66b41b5ea5f','ativo':false,'posicao':2,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 18:40:45','dataAtualizacao':'24/06/2016 18:40:45','estagios':[],'gruposSemaforicos':[]},{'id':'799c1329-af11-43da-b6c3-4e1df36b3a5c','ativo':false,'posicao':6,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 18:40:45','dataAtualizacao':'24/06/2016 18:40:45','estagios':[],'gruposSemaforicos':[]},{'id':'940dbe1c-e74a-4ac6-8ddf-014a28785a5c','ativo':false,'posicao':5,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 18:40:45','dataAtualizacao':'24/06/2016 18:40:45','estagios':[],'gruposSemaforicos':[]},{'id':'aa24a196-8d2d-4dac-a28b-6097f59536c1','ativo':false,'posicao':1,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 18:40:45','dataAtualizacao':'24/06/2016 18:40:45','estagios':[],'gruposSemaforicos':[]},{'id':'d9136a28-f275-4288-ab97-3ffd5ce1ca11','ativo':false,'posicao':7,'quantidadeGrupoPedestre':0,'quantidadeGrupoVeicular':0,'quantidadeDetectorPedestre':0,'quantidadeDetectorVeicular':0,'dataCriacao':'24/06/2016 18:40:45','dataAtualizacao':'24/06/2016 18:40:45','estagios':[],'gruposSemaforicos':[]}],'gruposSemaforicos':[]};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAneis);
      });

      it('Deve iniciar a tela com o primeiro anel selecionado', function() {
        expect(scope.currentAnelIndex).toBe(0);
        expect(scope.currentAnel).toBe(scope.aneis[0]);
      });

      it('Deve criar a uma quantidade de aneis igual ao limite de aneis do modelo', function() {
        expect(scope.objeto.aneis.length).toBe(scope.objeto.modelo.configuracao.limiteAnel);
        expect(scope.aneis.length).toBe(scope.objeto.modelo.configuracao.limiteAnel);
      });

      it('Os aneis devem ser nomeados com o padrao idControlador + indice do anel', function() {
        expect(scope.objeto.aneis[0].idAnel).toEqual('1.000.0999-1');
      });
    });
  });

  describe('Wizard para edição de contrladores', function () {
    var helpers, id, objeto;
    beforeEach(inject(function($state) {
      id = 'id';
      $state.params.id = id;

      helpers = {cidades:[{},{}],fabricantes:[{},{},{}]};
      objeto = {id: 1, area: {id: 'area', cidade: {id: 'cidade'}}, modelo: {id: 'modelo', fabricante: {id: 'fab1'}}};
      $httpBackend.expectGET('/controladores/' + id).respond(objeto);
      $httpBackend.expectGET('/helpers/controlador').respond(helpers);
      scope.inicializaWizard();
      $httpBackend.flush();
    }));

    describe('InicializaAneis', function () {
      beforeEach(function() {
        spyOn(scope, 'inicializaWizard').and.callFake(function() {
          var deferred = $q.defer();
          deferred.resolve({});
          return deferred.promise;
        });
      });

      it('Deve inicializar os campos de idAnel e posicao dos aneis', function() {
        scope.objeto = {idControlador: '1234567', aneis: [{}, {}], modelo: {configuracao: {limiteAnel: 4}}};
        scope.inicializaAneis();
        scope.$apply();

        expect(scope.objeto.aneis[0].idAnel).toBeDefined();
        expect(scope.objeto.aneis[0].posicao).toBeDefined();
      });

      it('Não deve acessar a tela de configuracao de aneis se ao menos um anel não for declarado para o controlador',
        function() {
          scope.objeto = {idControlador: '1234567', aneis: [], modelo: {configuracao: {limiteAnel: 4}}};
          scope.inicializaAneis();
          scope.$apply();

          expect($state.current.name).not.toBe('app.wizard_controladores.aneis');
        });
    });
  });

  describe('Funções auxiliares', function () {
    describe('desativaProximosAneis', function () {
      beforeEach(function() {
        scope.aneis = [{posicao: 1,ativo: true},{posicao: 2,ativo: true},{posicao: 3,ativo: true}];
      });

      it('Deve desativar os aneis 2 e 3, caso o anel 1 seja desativado', function() {
        var currentAnel = scope.aneis[0];
        scope.desativaProximosAneis(currentAnel);
        expect(scope.aneis[1].ativo).toBe(false);
        expect(scope.aneis[2].ativo).toBe(false);
      });
    });
  });

});
