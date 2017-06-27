'use strict';

describe('Controller: ControladoresAneisCtrl', function () {

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

    describe('Configuração válida', function() {
      beforeEach(inject(function($q, CETLocalizacaoService) {
        var deferred = $q.defer();
        spyOn(CETLocalizacaoService, 'getLatLng').and.returnValue(deferred.promise);
        deferred.resolve({lat: 1, lng: 2});

        var endereco1       = {idJson: 1, localizacao: 'Av Bandeirantes', localizacao2: 'Av Afonso Penna'};
        var enderecoVazio   = {idJson: 2, localizacao: ''};

        var objeto = {
          aneis: [{idJson: 1, endereco: endereco1, posicao: 1}, {idJson: 2, endereco: enderecoVazio}],
          estagios: [],
          todosEnderecos: [endereco1, enderecoVazio],
          numeroSMEE: 2
        };
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAneis);
      }));

      it('Deve iniciar a tela com o primeiro anel selecionado', function() {
        scope.currentEndereco = {localizacao: 'Av Bandeirantes', localizacao2: 'Av Afonso Penna'};

        scope.$apply();
        expect(scope.currentAnelIndex).toBe(0);
        expect(scope.currentAnel).toBe(scope.aneis[0]);
        expect(scope.controladorLocalizacao).toBe(scope.aneis[0].localizacao);
      });

      it('Deve iniciar o primeiro anel com SMEE do dados básicos', function () {
        expect(scope.currentAnel.numeroSMEE).toBe(2);
      });


      it('Cada anel ativo do controlador deverá ter um endereco', function() {
        expect(scope.aneis[0].endereco).toBeDefined();
      });

      it('Deve criar o texto em "nomeEndereco" associando os nomes do "Av Bandeirantes" "x" "Av Afonso Penna"', function() {
        var anel = scope.objeto.aneis[0];
        expect(anel.localizacao).toBe('Av Bandeirantes x Av Afonso Penna');
        expect(scope.controladorLocalizacao).toBe('Av Bandeirantes x Av Afonso Penna');
      });

      it('no segundo anel o "nomeEndereco" deve ser vazio', function() {
        var anel = scope.objeto.aneis[1];
        anel.localizacao = '';
        anel.endereco.localizacao = 'endereco 1';
        anel.endereco.localizacao2 = 'endereco 2';
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
      scope.adicionarEstagio({}, {id: 1, idJson: 'imagem-1'}, 'anel-1');
      expect(scope.currentAnel.estagios.length).toBe(1);
      var estagio = _.find(scope.objeto.estagios, {imagem: {idJson: 'imagem-1'}});
      expect(estagio.posicao).toBe(1);
    });
  });

  describe('removerEstagio', function () {
    beforeEach(function() {
      scope.objeto = {
        aneis: [{idJson: 'anel-1', estagios: [{idJson: 'estagio-1'}]}],
        estagios: [{idJson: 'estagio-1', imagem: {id: 'imagem-1', idJson: 'imagem-1'}}],
        imagens: [{id: 'imagem-1', idJson: 'imagem-1'}],
      };
      scope.currentAnel = scope.objeto.aneis[0];
      $httpBackend.expectDELETE('/controladores/remover_planos_tabelas_horarias').respond({});
      scope.removerEstagio({id: 'imagem-1'});
      scope.$apply();
    });

    it('Deve excluir o registro de imagem', function() {
      expect(scope.objeto.imagens.length).toBe(0);
    });
  });

  describe('ativarProximoAnel', function () {
    beforeEach(function() {
      scope.objeto = {
        todosEnderecos: [{idJson: 1, localizacao: 'Av Bandeirantes', localizacao2: 'Av Afonsopena'}],
        aneis: [{idJson: 'anel-1', ativo: true, posicao: 1},
                {idJson: 'anel-2', ativo: false},
                {idJson: 'anel-3', ativo: false}]
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
      expect(scope.objeto.aneis[2].endereco).toBeDefined();
    });

    it('O array de aneis ativos de ser atualizado.', function() {
      expect(scope.aneis.length).toBe(2);
    });
  });

  describe('desativarUltimoAnel', function () {
    beforeEach(function() {
      scope.objeto = {
        aneis: [
          {idJson: 'anel-1',ativo: true, posicao: 1, enderecos: [{idJson: 'e1'},{idJson: 'e2'}]},
          {
            idJson: 'anel-2',
            ativo: true,
            endereco: {idJson: 'e3'},
            estagios: [{idJson: 'e1'}],
            gruposSemaforicos: [{idJson: 'gs1'}],
            detectores: [{idJson: 'd1'}],
            planos: [{idJson: 'p1'}]
          },
          {idJson: 'anel-3',ativo: false}
        ],
        todosEnderecos: [{idJson: 'e1'},{idJson: 'e2'},{idJson: 'e3'},{idJson: 'e4'}],
        estagios: [{idJson: 'e1'}],
        gruposSemaforicos: [{idJson: 'gs1'}],
        detectores: [{idJson: 'd1'}],
        planos: [{idJson: 'p1'}]
      };
      scope.aneis = scope.objeto.aneis;
      scope.desativarUltimoAnel();
      scope.$apply();
    });

    it('Deve desativar o ultimo anel com "ativo" true', function() {
      expect(scope.objeto.aneis[1].ativo).not.toBeTruthy();
    });

    it('O anel deverá ter quaisquer configurações anteriores deletadas', function() {
      expect(scope.objeto.aneis[1].ativo).toBeFalsy();
      expect(scope.objeto.aneis[1].estagios).toEqual([]);
      expect(scope.objeto.aneis[1].gruposSemaforicos).toEqual([]);
      expect(scope.objeto.aneis[1].detectores).toEqual([]);
      expect(scope.objeto.aneis[1].planos).toEqual([]);
      expect(scope.objeto.aneis[1].endereco.idJson).toBeDefined();
    });

    it('Não deve permitir desativar o primeiro anel', function() {
      scope.desativarUltimoAnel();
      expect(scope.objeto.aneis[0].ativo).toBeTruthy();
    });
  });

  describe('bugs', function() {
    describe('estágios sem imagens', function () {
      describe('Quando a API retorna um estágio sem imagem', function () {
        beforeEach(function() {
          var objeto = {
            aneis: [
              {
                idJson: 'anel-1',
                ativo: true,
                endereco: {idJson: 'e3'},
                estagios: [{idJson: 'e1'}, {idJson: 'e2'}],
                gruposSemaforicos: [{idJson: 'gs1'}],
                detectores: [{idJson: 'd1'}],
                planos: [{idJson: 'p1'}]
              },
              {idJson: 'anel-3',ativo: false}
            ],
            todosEnderecos: [{idJson: 'e1'},{idJson: 'e2'},{idJson: 'e3'},{idJson: 'e4'}],
            estagios: [{idJson: 'e1', imagem: { idJson: 'imagem-1'}}, {idJson: 'e2'}],
            imagens: [{idJson: 'imagem-1'}]
          };

          WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAneis);
          scope.$apply();
        });

        it('Os estágios sem imagens devem ser removidos da lista de estágios', function () {
          expect(scope.imagensDeEstagios.length).toBe(1);
        });
      });

      describe('Quado o app produz estágios sem imagem', function () {
        beforeEach(function() {
          var objeto = {
            aneis: [
              {
                idJson: 'anel-1',
                ativo: true,
                endereco: {idJson: 'e3'},
                estagios: [{idJson: 'e1'}, {idJson: 'e2'}],
                gruposSemaforicos: [{idJson: 'gs1'}],
                detectores: [{idJson: 'd1'}],
                planos: [{idJson: 'p1'}]
              }
            ],
            todosEnderecos: [{idJson: 'e1'},{idJson: 'e2'},{idJson: 'e3'},{idJson: 'e4'}],
            estagios: [{idJson: 'e1', imagem: { idJson: 'imagem-1'}}, {idJson: 'e2'}],
            imagens: [{idJson: 'imagem-1'}]
          };

          WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaAneis);
          scope.$apply();
        });

        it('Deve remover os estágios sem imagens antes de enviar à API', function () {
          scope.beforeSubmitForm();
          scope.$apply();

          expect(scope.objeto.aneis[0].estagios.length).toBe(1);
          expect(scope.objeto.estagios.length).toBe(1);
        });
      });
    });
  });
});
