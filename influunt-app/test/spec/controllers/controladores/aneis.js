'use strict';

describe('Controller: ControladoresAneisCtrl', function () {

  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresAneisCtrl,
    scope,
    $httpBackend,
    $q,
    $state,
    helpers;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$q_, _$state_) {
    scope = $rootScope.$new();
    ControladoresAneisCtrl = $controller('ControladoresAneisCtrl', { $scope: scope });

    $httpBackend = _$httpBackend_;
    $q = _$q_;
    $state = _$state_;

    helpers = {cidades:[{},{}],fabricantes:[{},{},{}]};
    $httpBackend.expectGET('/helpers/controlador').respond(helpers);
    scope.inicializaWizard();
    $httpBackend.flush();
  }));


  it('Deve conter as definições de funções do ControladorCtrl', function() {
    expect(scope.inicializaWizard).toBeDefined();
  });

  describe('assertAneis', function () {
    it('Um controlador que não tenha ao menos um anel deve ser considerado inválido', function() {
      scope.objeto = {aneis: []};
      var result = scope.assertAneis();
      expect(result).not.toBeTruthy();
    });

    it('Se houver ao menos um anel, este controlador deverá ser considerado válido', function() {
      scope.objeto = {aneis: [{}]};
      var result = scope.assertAneis();
      expect(result).toBeTruthy();
    });
  });

  describe('inicializaAneis', function () {
    describe('Configuração inválida', function () {
      it('Deve interromper o "inicializaAneis" se o controlador estiver inválido', function() {
        var objeto = {};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAneis);
        expect(scope.currentAnelIndex).not.toBeDefined();
      });
    });

    describe('Configuração válida', function () {
      beforeEach(function() {
        var objeto = {
          aneis: [{idJson: 1, enderecos: []}],
          estagios: []
        };
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAneis);
      });

      it('Deve iniciar a tela com o primeiro anel selecionado', function() {
        expect(scope.currentAnelIndex).toBe(0);
        expect(scope.currentAnel).toBe(scope.aneis[0]);
      });

      it('Cada anel ativo do controlador deverá ter ao menos dois enderecos', function() {
        expect(scope.aneis[0].enderecos.length).toBe(2);
      });

      it('Deve criar o texto em "nomeEndereco" associando os nomes do "endereco 1" "com" "endereco 2"', function() {
        var anel = scope.objeto.aneis[0];
        anel.enderecos[0].localizacao = 'endereco 1';
        anel.enderecos[1].localizacao = 'endereco 2';
        scope.$apply();
        expect(anel.localizacao).toBe('endereco 1 com endereco 2');
      });

      it('"nomeEndereco" deve ser vazio se não houver "endereco 1" ou "endereco 2"', function() {
        var anel = scope.objeto.aneis[0];
        anel.enderecos[0].localizacao = 'endereco 1';
        expect(anel.localizacao).toBe('');
      });
    });
  });

  describe('adicionarEstagio', function () {
    beforeEach(function() {
      scope.objeto = {aneis: [{idJson: 'anel-1'}]};
      scope.currentAnel = scope.objeto.aneis[0];
      scope.$apply();
    });

    it('Deve adicionar uma nova imagem à lista de estágios', function() {
      scope.adicionarEstagio({}, {id: 1, idJson: 'imagem-1'});
      expect(scope.currentAnel.estagios.length).toBe(1);
    });
  });

  describe('removerEstagio', function () {
    beforeEach(function() {
      scope.objeto = {
        aneis: [{idJson: 'anel-1', estagios: [{idJson: 'estagio-1'}]}],
        estagios: [{idJson: 'estagio-1', imagem: {idJson: 'imagem-1'}}],
        imagens: [{idJson: 'imagem-1'}],
      };
      scope.currentAnel = scope.objeto.aneis[0];
      scope.removerEstagio({idJson: 'imagem-1'});
      scope.$apply();
    });

    it('Deve excluir o registro de imagem', function() {
      expect(scope.objeto.imagens.length).toBe(0);
    });
  });

  describe('ativarProximoAnel', function () {
    beforeEach(function() {
      scope.objeto = {
        aneis: [{idJson: 'anel-1', ativo: true}, {idJson: 'anel-2', ativo: false}, {idJson: 'anel-3', ativo: false}]
      };
      scope.aneis = scope.objeto.aneis;
      scope.ativarProximoAnel();
      scope.$apply();
    });

    it('O primeiro anel desativado deve mudar para o status "ativo" true', function() {
      expect(scope.objeto.aneis[1].ativo).toBeTruthy();
      expect(scope.objeto.aneis[2].ativo).not.toBeTruthy();
    });

    it('Os dados de endereco do novo anel devem ser inicializados', function() {
      expect(scope.objeto.aneis[2].enderecos.length).toBe(2);
    });

    it('O array de aneis ativos de ser atualizado.', function() {
      expect(scope.aneisAtivos.length).toBe(2);
      console.log(JSON.stringify(scope.objeto));
    });
  });

  describe('desativarUltimoAnel', function () {
    beforeEach(function() {
      scope.objeto = {
        "aneis": [
          {"idJson": "anel-1","ativo": true,"enderecos": [{"idJson": "e1"},{"idJson": "e2"}]},
          {"idJson": "anel-2","ativo": true,"enderecos": [{"idJson": "e3"},{"idJson": "e4"}]},
          {"idJson": "anel-3","ativo": false}
        ],
        "todosEnderecos": [{"idJson": "e1"},{"idJson": "e2"},{"idJson": "e3"},{"idJson": "e4"}]
      };
      scope.aneis = scope.objeto.aneis;
      scope.desativarUltimoAnel();
      scope.$apply();
    });

    it('Deve desativar o ultimo anel com "ativo" true', function() {
      expect(scope.objeto.aneis[1].ativo).not.toBeTruthy();
    });

    it('O anel desativado não deverá ter registro de enderecos.', function() {
      expect(scope.objeto.aneis[1].enderecos).not.toBeDefined();
    });

    it('Não deve permitir desativar o primeiro anel', function() {
      scope.desativarUltimoAnel();
      expect(scope.objeto.aneis[0].ativo).toBeTruthy();
    });
  });
});
