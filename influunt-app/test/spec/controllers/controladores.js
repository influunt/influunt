'use strict';

describe('Controller: ControladoresCtrl', function () {

  // helper functions
  var fakeInicializaWizard = function(scope, $q, objeto, fn) {
    spyOn(scope, 'inicializaWizard').and.callFake(function() {
      var deferred = $q.defer();
      deferred.resolve({});
      return deferred.promise;
    });

    scope.objeto = objeto;
    fn();
    scope.$apply();
  };

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresCtrl,
    scope,
    $q,
    Restangular,
    $httpBackend;

  beforeEach(inject(function ($controller, $rootScope, _$q_, _$httpBackend_, _Restangular_) {
    scope = $rootScope.$new();
    ControladoresCtrl = $controller('ControladoresCtrl', {
      $scope: scope
    });

    $q = _$q_;
    Restangular = _Restangular_;
    $httpBackend = _$httpBackend_;
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
      helpers = {"cidades":[{},{}],"fabricantes":[{},{},{}]};
      $httpBackend.expectGET('/helpers/controlador').respond(helpers);
      scope.inicializaWizard();
      $httpBackend.flush();
    });

    describe('inicializaWizard', function() {
      it('Deve retornar o objeto vazio.', function() {
        expect(scope.objeto).toEqual({});
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


    describe('InicializaAneis', function () {
      beforeEach(function() {
        var objeto = {"id": 15,"descricao": "teste","numeroSMEE": "asfd","firmware": "asdf","latitude": -19.181655,"longitude": -51.399921000000006,"dataCriacao": "23/06/2016 16:38:38","dataAtualizacao": "23/06/2016 16:38:38","idControlador": "2.000.0015","area": {"id": "cdbb59a8-d4e1-4bcf-9cdf-062e0324d3e3","descricao": 2,"dataCriacao": "19/06/2016 16:11:29","dataAtualizacao": "19/06/2016 16:11:29","cidade": {"id": "584a3016-2739-4ea5-802b-9c2e702772cc","nome": "Belo Horizonte","dataCriacao": "19/06/2016 16:10:28","dataAtualizacao": "19/06/2016 16:10:28","areas": [{"id": "3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5","descricao": 1},{"id": "cdbb59a8-d4e1-4bcf-9cdf-062e0324d3e3","descricao": 2}]},"limites": []},"modelo": {"id": "125b88c3-f536-4f2f-a112-f284f7cf250e","descricao": "fabricante 2","dataCriacao": "19/06/2016 21:16:13","dataAtualizacao": "19/06/2016 21:16:13","fabricante": {"id": "1748a734-e9b4-478d-9786-04a60a6a65d8","nome": "fabricante 2","dataCriacao": "19/06/2016 21:16:13","dataAtualizacao": "19/06/2016 21:16:13","modelos": [{"id": "125b88c3-f536-4f2f-a112-f284f7cf250e","descricao": "fabricante 2","configuracao": {"id": "2ae77195-ee3e-4f88-b4b2-5d49e70f6780","descricao": "opa 1","limiteEstagio": 4,"limiteGrupoSemaforico": 4,"limiteAnel": 4,"limiteDetectorPedestre": 4,"limiteDetectorVeicular": 8,"dataCriacao": "19/06/2016 16:11:54","dataAtualizacao": "19/06/2016 16:11:54"}}]},"configuracao": {"id": "2ae77195-ee3e-4f88-b4b2-5d49e70f6780","descricao": "opa 1","limiteEstagio": 4,"limiteGrupoSemaforico": 4,"limiteAnel": 4,"limiteDetectorPedestre": 4,"limiteDetectorVeicular": 8,"dataCriacao": "19/06/2016 16:11:54","dataAtualizacao": "19/06/2016 16:11:54"}},"aneis": [],"gruposSemaforicos": []};
        fakeInicializaWizard(scope, $q, objeto, scope.inicializaAneis);
      });

      it('Deve iniciar a tela com o primeiro anel selecionado', function() {
        expect(scope.currentAnelId).toBe(0);
        expect(scope.currentAnel).toBe(scope.aneis[0]);
      });

      it('Deve criar a uma quantidade de aneis igual ao limite de aneis do modelo', function() {
        expect(scope.objeto.aneis.length).toBe(scope.objeto.modelo.configuracao.limiteAnel);
        expect(scope.aneis.length).toBe(scope.objeto.modelo.configuracao.limiteAnel);
      });

      it('Os aneis devem ser nomeados com o padrao idControlador + indice do anel', function() {
        expect(scope.objeto.aneis[0].id_anel).toEqual('2.000.0015-1');
      });
    });

    describe('InicializaAssociacao', function() {
      beforeEach(function() {
        var objeto = {"id": 15,"descricao": "teste","numeroSMEE": "asfd","firmware": "asdf","latitude": -19.181655,"longitude": -51.399921000000006,"dataCriacao": "23/06/2016 16:38:38","dataAtualizacao": "23/06/2016 16:40:20","idControlador": "2.000.0015","area": {"id": "cdbb59a8-d4e1-4bcf-9cdf-062e0324d3e3","descricao": 2,"dataCriacao": "19/06/2016 16:11:29","dataAtualizacao": "19/06/2016 16:11:29","cidade": {"id": "584a3016-2739-4ea5-802b-9c2e702772cc","nome": "Belo Horizonte","dataCriacao": "19/06/2016 16:10:28","dataAtualizacao": "19/06/2016 16:10:28","areas": [{"id": "3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5","descricao": 1},{"id": "cdbb59a8-d4e1-4bcf-9cdf-062e0324d3e3","descricao": 2}]},"limites": []},"modelo": {"id": "125b88c3-f536-4f2f-a112-f284f7cf250e","descricao": "fabricante 2","dataCriacao": "19/06/2016 21:16:13","dataAtualizacao": "19/06/2016 21:16:13","fabricante": {"id": "1748a734-e9b4-478d-9786-04a60a6a65d8","nome": "fabricante 2","dataCriacao": "19/06/2016 21:16:13","dataAtualizacao": "19/06/2016 21:16:13","modelos": [{"id": "125b88c3-f536-4f2f-a112-f284f7cf250e","descricao": "fabricante 2","configuracao": {"id": "2ae77195-ee3e-4f88-b4b2-5d49e70f6780","descricao": "opa 1","limiteEstagio": 4,"limiteGrupoSemaforico": 4,"limiteAnel": 4,"limiteDetectorPedestre": 4,"limiteDetectorVeicular": 8,"dataCriacao": "19/06/2016 16:11:54","dataAtualizacao": "19/06/2016 16:11:54"}}]},"configuracao": {"id": "2ae77195-ee3e-4f88-b4b2-5d49e70f6780","descricao": "opa 1","limiteEstagio": 4,"limiteGrupoSemaforico": 4,"limiteAnel": 4,"limiteDetectorPedestre": 4,"limiteDetectorVeicular": 8,"dataCriacao": "19/06/2016 16:11:54","dataAtualizacao": "19/06/2016 16:11:54"}},"aneis": [{"id": "24b5760d-06be-49c3-9d3d-79f018aaad3e","ativo": false,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 0,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20","movimentos": [],"gruposSemaforicos": []},{"id": "80dd0686-4d0b-4a54-8203-c65fa58ffc46","numeroSMEE": "1","descricao": "1","ativo": true,"posicao": 1,"latitude": -19.9580671,"longitude": -44.05085180000003,"quantidadeGrupoPedestre": 1,"quantidadeGrupoVeicular": 1,"quantidadeDetectorPedestre": 1,"quantidadeDetectorVeicular": 1,"dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20","movimentos": [{"id": "591a0bca-b198-44af-8162-8fae7f1cd2e4","imagem": {"id": "1d2b3685-dc83-47fd-8d20-816f0d0f55a1","filename": "13406967_1003062549749489_543993289383831132_n.jpg","contentType": "image/jpeg","dataCriacao": "23/06/2016 16:40:14","dataAtualizacao": "23/06/2016 16:40:14"},"estagio": {"id": "d9200048-07e7-4f03-976c-e221800ee98b","demandaPrioritaria": false,"dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20"},"dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20"},{"id": "7e9a0949-de81-4426-b30b-63a544247112","imagem": {"id": "9b94a76a-d793-493f-bf2f-0c69223900f3","filename": "Screen Shot 2016-06-13 at 23.45.32.png","contentType": "image/png","dataCriacao": "23/06/2016 16:40:14","dataAtualizacao": "23/06/2016 16:40:14"},"estagio": {"id": "e8071330-8977-44c5-b8b9-eefd45e1d2c0","demandaPrioritaria": false,"dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20"},"dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20"}],"gruposSemaforicos": [{"id": "2bf3505e-2cfe-4c10-9461-eae0644f08cd","dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20","posicao": 2,"estagioGrupoSemaforicos": []},{"id": "b3d34743-2fee-4303-b96f-19ad621113dd","dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20","posicao": 1,"estagioGrupoSemaforicos": []}]},{"id": "85a6a3f2-49d4-4277-a439-13466075b98f","ativo": false,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 0,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20","movimentos": [],"gruposSemaforicos": []},{"id": "e50534f1-4376-497b-9624-6c83849adcdd","ativo": false,"quantidadeGrupoPedestre": 0,"quantidadeGrupoVeicular": 0,"quantidadeDetectorPedestre": 0,"quantidadeDetectorVeicular": 0,"dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20","movimentos": [],"gruposSemaforicos": []}],"gruposSemaforicos": [{"id": "2bf3505e-2cfe-4c10-9461-eae0644f08cd","dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20","posicao": 2,"estagioGrupoSemaforicos": []},{"id": "b3d34743-2fee-4303-b96f-19ad621113dd","dataCriacao": "23/06/2016 16:40:20","dataAtualizacao": "23/06/2016 16:40:20","posicao": 1,"estagioGrupoSemaforicos": []}]};
        fakeInicializaWizard(scope, $q, objeto, scope.inicializaAssociacao);
      });

      it('Deve carregar o scope.aneis com a lista de aneis ativos', function() {
        expect(scope.aneis.length).toBe(1);
      });

      it('Os grupos semaforicos deverão estar ordenados por posicao', function() {
        expect(scope.aneis[0].gruposSemaforicos[0].posicao).toBe(1);
        expect(scope.aneis[0].gruposSemaforicos[1].posicao).toBe(2);
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

    describe('InicializaAneis', function () {
      beforeEach(function() {
        spyOn(scope, 'inicializaWizard').and.callFake(function() {
          var deferred = $q.defer();
          deferred.resolve({});
          return deferred.promise;
        });

        scope.objeto = {idControlador: '1234567', aneis: [{}, {}], modelo: {configuracao: {limiteAnel: 4}}};
        scope.inicializaAneis();
        scope.$apply();
      });

      it('Deve inicializar os campos de id_anel e posicao dos aneis', function() {
        expect(scope.objeto.aneis[0].id_anel).toBeDefined();
        expect(scope.objeto.aneis[0].posicao).toBeDefined();
      });
    });
  });

  describe('Funcoes auxiliares', function () {
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

    describe('relacionaImagemAoEstagio', function () {
      it('Deve atribuir uma imagem ao movimento.', function() {
        var movimento = {estagio: {}};
        var imagem = 'img';
        scope.relacionaImagemAoEstagio(movimento, null, imagem);
        expect(movimento.estagio.imagem).toBe(imagem);
      });
    });

    describe('relacionaImagemAoEstagio', function () {
      var imagem;
      beforeEach(function() {
        scope.currentAnel = {};
        imagem = {id: 1};
      });

      it('Deve criar um array de movimentos no estagio, caso nao exista', function() {
        scope.associaImagemAoMovimento(null, {id: 1});
        expect(scope.currentAnel.movimentos).toBeDefined();
      });

      it('Deve adicionar mais um elemento à lista de movimentos, caso este já esteja definido', function() {
        scope.currentAnel.movimentos = [];
        scope.associaImagemAoMovimento(null, {id: 1});
        expect(scope.currentAnel.movimentos).toBeDefined();
      });

      it('Deve atribuir uma imagem ao estagio.', function() {
        scope.associaImagemAoMovimento(null, {id: 1});
        expect(scope.currentAnel.movimentos[0].imagem).toEqual(imagem);
      });
    });

    describe('mensagemValidacaoForm', function() {
      it('Deve construir um objeto contendo uma lista de mensagens de validacao', function() {
        scope.validacoes = {};
        var res = {
          data: [
            {message: 'mag_1'},
            {message: 'mag_2'}
          ]
        };

        scope.mensagemValidacaoForm(res);
        expect(scope.validacoes.alerts.length).toBe(2);
      });
    });
  });
});
