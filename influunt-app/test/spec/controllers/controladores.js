'use strict';

describe('Controller: ControladoresCtrl', function () {

  var ControladoresCtrl,
    scope,
    $q,
    Restangular,
    $httpBackend,
    $state,
    helpers;

  beforeEach(inject(function ($controller, $rootScope, _$q_, _$httpBackend_, _Restangular_, _$state_) {
    scope = $rootScope.$new();
    ControladoresCtrl = $controller('ControladoresCtrl', {
      $scope: scope
    });

    $q = _$q_;
    Restangular = _Restangular_;
    $httpBackend = _$httpBackend_;
    $state = _$state_;
  }));

  describe('CRUD de contrladores', function () {
    it('Deve conter as definições das funções de CRUD', function() {
      expect(scope.index).toBeDefined();
      expect(scope.show).toBeDefined();
      expect(scope.new).toBeDefined();
      expect(scope.save).toBeDefined();
      expect(scope.confirmDelete).toBeDefined();
    });

    it('before show: carrega a lista de areas', function() {
      var areas = [{}, {}];
      $httpBackend.expectGET('/areas').respond(areas);
      scope.beforeShow();
      $httpBackend.flush();

      expect(scope.areas.length).toBe(2);
    });
  });

  describe('inicializaWizard', function () {
    beforeEach(function() {
      helpers = {
        cidades:[{idJson: 'c1'},{idJson: 'c2'}],
        fabricantes:[{idJson: 'f1'},{idJson: 'f2'},{idJson: 'f3'}]
      };
      $httpBackend.expectGET('/helpers/controlador').respond(helpers);
      scope.inicializaWizard();
      $httpBackend.flush();
    });

    describe('novo controlador', function () {
      it('Deve iniciar um objeto com a configuração padrão', function() {
        expect(scope.objeto.limiteEstagio).toBe(16);
        expect(scope.objeto.limiteGrupoSemaforico).toBe(16);
        expect(scope.objeto.limiteAnel).toBe(4);
        expect(scope.objeto.limiteDetectorPedestre).toBe(4);
        expect(scope.objeto.limiteDetectorVeicular).toBe(8);
        expect(scope.objeto.limiteTabelasEntreVerdes).toBe(2);
        expect(scope.objeto.todosEnderecos.length).toBe(1);
        expect(scope.objeto.endereco).toBeDefined();
      });

      it('Deve inicializar os objetos de helpers', function() {
        expect(scope.helpers).toBeDefined();
        expect(scope.objeto.cidades).toBeDefined();
        expect(scope.objeto.areas).toBeDefined();
      });
    });

    describe('controlador em edição', function () {
      var helpers, id, objeto;
      beforeEach(inject(function($state) {
        id = 'id';
        $state.params.id = id;

        helpers = {
          cidades: [{idJson: 'c1',areas: [{idJson: 'a1',cidade: {idJson: 'c1'}}]}],
          fabricantes: [{id: 'f1',modelos: [{id: 'm1',fabricante: {id: 'f1'}}]}]
        };

        objeto = {
          area: {
            idJson: 'a1',
          },
          areas: [{idJson: 'a1', cidade: { idJson: 'c1'}}],
          cidades: [
            {
              idJson: 'c1',
              areas: [{idJson: 'a1'}],
              nome: 'Cidade 1'
            }
          ],
          modelo: {
            id: 'm1',
            fabricante: {
              id: 'f1'
            }
          }
        };

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
    });
  });

  describe('selecionaAnel', function () {
    beforeEach(function() {
      scope.objeto = {
        aneis: [{idJson: 1, ativo: true, endereco: {idJson: 1}} , {idJson: 2, ativo: true}],
        todosEnderecos: [{idJson: 1, localizacao: "Av Paulista", localizacao2: "Av Pedro I"}]
      };

      scope.aneis = scope.objeto.aneis;
      scope.selecionaAnel(0);
    });

    it('Deve inicializar o objeto currentAnel com o indice de parametro', function() {
      expect(scope.currentAnel).toBe(scope.objeto.aneis[0]);
    });

    it('Deve retornar a localização do anel selecionado', function() {
      expect(scope.currentAnel.localizacao).toBe("Av Paulista com Av Pedro I");
    });
  });

  describe('selecionaGrupoSemaforico', function () {
    beforeEach(function() {
      scope.objeto = {
        aneis: [
          {idJson: 1, ativo: true, gruposSemaforicos: [{idJson: 'gs1'}]},
          {idJson: 2, ativo: true}
        ],
        gruposSemaforicos: [{idJson: 'gs1'}, {idJson: 'gs2'}]
      };
      scope.aneis = scope.objeto.aneis;
      scope.selecionaAnel(0);
      scope.selecionaGrupoSemaforico(scope.currentAnel.gruposSemaforicos[0], 0);
    });

    it('Inicializa as variaveis de controle do grupo semaforico selecionado.', function() {
      expect(scope.currentGrupoSemaforicoIndex).toBe(0);
      expect(scope.currentGrupoSemaforico).toEqual(scope.objeto.gruposSemaforicos[0]);
      expect(scope.currentGrupoSemaforicoIdentifier).toBe('00');
    });
  });

  describe('closeAlert', function () {
    beforeEach(function() {
      scope.errors = {
        aneis: [{
          general: {},
          other: {},
        }]
      };
    });

    it('Ao fechar o alert de aneis, deve limpar a lista de general notifications', function() {
      scope.closeAlert(0);
      expect(scope.errors.aneis[0].general).toBeUndefined();
    });

    it('Deve limpar uma lista customizada, se houver um segundo parametro informado', function() {
      scope.closeAlert(0, 'other');
      expect(scope.errors.aneis[0].other).toBeUndefined();
    });

    it('Deve manter o objeto de erros inalterado se um anel incorreto for informado', function() {
      var originalErrors = _.cloneDeep(scope.errors);
      scope.closeAlert(1);
      expect(scope.errors).toEqual(originalErrors);
    });
  });

  describe('submitForm', function () {

    var beforeEachFn = function(step, next, status, response) {
      $httpBackend.expectPOST('/controladores/' + step).respond(status, response);

      scope.submitForm(step, next);
      scope.$apply();

      $httpBackend.flush();
    };

    it('Se houver uma funcao "beforeSubmitForm" definida, ela sera executada', function() {
      scope.beforeSubmitForm = function() {};
      spyOn(scope, 'beforeSubmitForm');
      beforeEachFn('dados_basicos', 'app.wizard_controladores.aneis', 200, {});
      expect(scope.beforeSubmitForm).toHaveBeenCalled();
    });

    describe('Salvos com sucesso', function () {
      var response, step, next;
      beforeEach(function() {
        step = 'dados_basicos';
        next = 'app.wizard_controladores.aneis';
        response = {id: 1};
        beforeEachFn(step, next, 200, response);
      });

      it('Deve atualizar o objeto com a resposta do POST', function() {
        expect(scope.objeto.id).toEqual(response.id);
      });

      it('Deve limpar as variaveis de erro', function() {
        expect(scope.errors).toEqual({});
        expect(scope.messages).toEqual([]);
      });

      it('Deve redirecionar o usuário para a proxima rota.', function() {
        expect(scope.$state.current.name).toBe(next);
      });

      it('Deve mostrar a mensagem de "salvo com sucesso" se as rotas de origem e destino forem iguais', inject(function(toast) {
        spyOn(toast, 'success');
        beforeEachFn('aneis', next, 200, response);
        expect(toast.success).toHaveBeenCalled();
      }));
    });

    describe('Salvos com erro', function () {
      var response, step, next;
      beforeEach(function() {
        spyOn(scope, 'buildValidationMessages');
        step = 'dados_basicos';
        next = 'app.wizard_controladores.aneis';
        response = {id: 1};
        beforeEachFn(step, next, 422, response);
      });

      it('Deve preparar as mensagens de validação para exibi-las na tela', function() {
        expect(scope.buildValidationMessages).toHaveBeenCalled();
      });

      it('se houver uma função de "afterSubmitFormOnValidationError", ela deve ser chamada', function() {
        scope.afterSubmitFormOnValidationError = function() {};
        spyOn(scope, 'afterSubmitFormOnValidationError');
        beforeEachFn(step, next, 422, response);
        expect(scope.afterSubmitFormOnValidationError).toHaveBeenCalled();
      });

      it('Deve notificar via console.log erros de api', function() {
        spyOn(console, 'error');
        beforeEachFn(step, next, 500, response);
        expect(console.error).toHaveBeenCalled();
      });
    });
  });
});
