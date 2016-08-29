'use strict';

describe('Controller: ControladoresConfiguracaoGruposCtrl', function () {
  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ControladoresConfiguracaoGruposCtrl,
    scope,
    $q,
    $httpBackend,
    $state,
    helpers;

  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$state_, _$q_) {
    scope = $rootScope.$new();
    ControladoresConfiguracaoGruposCtrl = $controller('ControladoresConfiguracaoGruposCtrl', {$scope: scope});

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

  describe('assert', function () {
    it('O controlador deve ter ao meno um anel e um estagio.', function() {
      scope.objeto = {aneis: [{idJson: 1, estagios: [{idJson: 'e1'}]}], estagios: [{idJson: 'e1'}]};
      var result = scope.assert();
      expect(result).toBeTruthy();
    });

    it('Um controlador que não tenha ao menos um anel deve ser considerado inválido', function() {
      scope.objeto = {aneis: []};
      var result = scope.assert();
      expect(result).not.toBeTruthy();
    });
  });

  describe('inicializaConfiguracaoGrupos', function () {
    describe('Configuração inválida', function () {
      it('Deve interromper o "inicializaConfiguracaoGrupos" se o controlador estiver inválido', function() {
        var objeto = {};
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaConfiguracaoGrupos);
        expect(scope.currentAnelIndex).not.toBeDefined();
      });
    });

    describe('Configuração válida', function () {
      beforeEach(function() {
        var objeto = {
          aneis: [{idJson: 1, ativo: true, estagios: [{idJson: 'e1'}]}],
          estagios: [{idJson: 'e1'}, {idJson: 'e2'}]
        };

        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaConfiguracaoGrupos);
      });

      it('Deve iniciar a tela com o primeiro anel selecionado', function() {
        expect(scope.currentAnelIndex).toBe(0);
        expect(scope.currentAnel).toBe(scope.aneis[0]);
      });

      it('Deve iniciar a variavel de currentEstagios', function() {
        expect(scope.currentEstagios.length).toBe(scope.currentAnel.estagios.length);
      });
    });

    describe('Configuração inicializada', function () {
      beforeEach(function() {
        var objeto = {
          aneis: [{idJson: 1, ativo: true, gruposSemaforicos: [{idJson: 'gs1'}], estagios: [{idJson: 'e1'}]}],
          estagios: [{idJson: 'e1'}],
          gruposSemaforicos: [{idJson: 'gs1'}, {idJson: 'gs2'}]
        };
        WizardControladores.fakeInicializaWizard(scope, $q, objeto, scope.inicializaConfiguracaoGrupos);
      });

      it('deve atualizar o objeto currentGruposSemaforicos com a lista de grupos do anel', function() {
        expect(scope.currentGruposSemaforicos.length).toBe(1);
      });
    });
  });

  describe('adicionaGrupoSemaforico', function () {
    beforeEach(function() {
      scope.objeto = {
        aneis: [
          {idJson: 1, ativo: true, estagios: [{idJson: 'e1'}]},
          {idJson: 2, ativo: true, estagios: [{idJson: 'e2'}]},
          {idJson: 3, ativo: true, estagios: [{idJson: 'e3'}]}
        ],
        estagios: [{idJson: 'e1'}, {idJson: 'e2'}]
      };

      scope.currentAnel = scope.objeto.aneis[0];
      scope.aneis = scope.objeto.aneis;
      scope.adicionaGrupoSemaforico();
    });

    it('Deve adicionar um objeto de grupo semafórico na lista de "gruposSemaforicos" do objeto', function() {
      expect(scope.objeto.gruposSemaforicos.length).toBe(1);
    });

    it('Deve adicionar uma referencia do objeto criado dentro do anel corrente.', function() {
      expect(scope.currentAnel.gruposSemaforicos.length).toBe(1);
      expect(scope.currentAnel.gruposSemaforicos[0].idJson).toBe(scope.objeto.gruposSemaforicos[0].idJson);
    });

    it('A lista de currentGruposSemaforicos deve ser atualizada', function() {
      expect(scope.currentGruposSemaforicos.length).toBe(1);
    });

    it('A posicao dos grupos semaforicos deve ser a partir do somatorio dos grupos de todos os aneis', function() {
      scope.currentAnel = scope.objeto.aneis[1];
      scope.adicionaGrupoSemaforico();
      expect(scope.objeto.gruposSemaforicos[1].posicao).toBe(2);
    });
  });

  describe('removeGrupo', function () {
    var deferred;
    beforeEach(inject(function(influuntAlert) {
      scope.objeto = {
        aneis: [
          {idJson: 1, ativo: true, gruposSemaforicos: [{idJson: 'gs1'}], estagios: [{idJson: 'e1'}]},
          {idJson: 2, ativo: true, estagios: [{idJson: 'e2'}], gruposSemaforicos: [{idJson: 'gs2'}]},
          {idJson: 3, ativo: true, estagios: [{idJson: 'e3'}]}
        ],
        estagios: [{idJson: 'e1'}, {idJson: 'e2'}],
        gruposSemaforicos: [{idJson: 'gs1'}, {idJson: 'gs2'}]
      };

      scope.currentAnel = scope.objeto.aneis[0];
      scope.aneis = scope.objeto.aneis;
      deferred = $q.defer();
      spyOn(influuntAlert, 'delete').and.returnValue(deferred.promise);

      scope.removeGrupo(0);
    }));

    describe('Remover um grupo ainda não enviado à API', function () {
      it('Deve remover o objeto da lista de "gruposSemaforicos" do objeto', function() {
        deferred.resolve(true);
        scope.$apply();
        expect(scope.objeto.gruposSemaforicos.length).toBe(1);
      });

      it('Deve remover a referencia do objeto do anel corrente', function() {
        deferred.resolve(true);
        scope.$apply();
        expect(scope.currentAnel.gruposSemaforicos.length).toBe(0);
      });
    });

    describe('Remover um grupo semafórico enviado à API', function () {
      beforeEach(function() {
        scope.objeto.gruposSemaforicos.forEach(function(gs, index) {
          gs.id = 'gs' + (index + 1);
        });
      });

      it('O objeto da lista de grupos semafóricos deverá permanecer na lista, com "_destroy" = true', function() {
        deferred.resolve(true);
        scope.$apply();
        expect(scope.objeto.gruposSemaforicos.length).toBe(2);

        var grupo = _.find(scope.objeto.gruposSemaforicos, {idJson: scope.currentAnel.gruposSemaforicos[0].idJson});
        expect(grupo._destroy).toBeTruthy();
      });

      it('A referencia ao objeto deletado deverá ser mantida', function() {
        deferred.resolve(true);
        scope.$apply();
        expect(scope.currentAnel.gruposSemaforicos.length).toBe(1);
      });
    });

    it('A lista de currentGruposSemaforicos deve ser atualizada', function() {
      deferred.resolve(true);
      scope.$apply();
      expect(scope.currentGruposSemaforicos.length).toBe(0);
    });

    it('Deve manter o conteudo intacto se o usuário não confirmar a remoção do objeto.', function() {
      deferred.resolve(false);
      scope.$apply();
      expect(scope.objeto.gruposSemaforicos.length).toBe(2);
    });

    it('Caso algum grupo semaforico seja removido, as posicoes dos posteriores deverão ser atualizadas', function() {
      deferred.resolve(true);
      scope.$apply();
      var grupo = _.find(scope.objeto.gruposSemaforicos, {idJson: scope.objeto.aneis[1].gruposSemaforicos[0].idJson});
      expect(grupo.posicao).toBe(1);
    });
  });

  describe('selecionaAnelGruposSemaforicos', function () {
    beforeEach(function() {
      scope.objeto = {
        aneis: [
          {ativo: true, estagios: [{idJson: 'e1'}], gruposSemaforicos: [{idJson: 'gs1'}]},
          {ativo: true, estagios: [{idJson: 'e2'}, {idJson: 'e3'}], gruposSemaforicos: [{idJson: 'gs2'}, {idJson: 'gs3'}]}
        ],
        estagios: [{idJson: 'e1'}, {idJson: 'e2'}, {idJson: 'e3'}],
        gruposSemaforicos: [{idJson: 'gs1'}, {idJson: 'gs2'}, {idJson: 'gs3'}]
      };

      scope.aneis = _.filter(scope.objeto.aneis, 'ativo');
      scope.selecionaAnelGruposSemaforicos(0);
    });

    it('Deve atualizar o "currentAnel"', function() {
      expect(scope.currentAnelIndex).toBe(0);
      expect(scope.currentAnel).toBe(scope.objeto.aneis[0]);
    });
    it('Deve atualizar o "currentEstagios"', function() {
      expect(scope.currentEstagios.length).toBe(1);
    });
    it('Deve atualizar o "currentGruposSemaforicos"', function() {
      expect(scope.currentGruposSemaforicos.length).toBe(1);
    });
  });

  describe('podeAdicionarGrupoSemaforico', function () {
    beforeEach(function() {
      scope.objeto = {
        limiteGrupoSemaforico: 1,
        aneis: [
          {ativo: true, estagios: [{idJson: 'e1'}], gruposSemaforicos: [{idJson: 'gs1'}]},
        ],
        estagios: [{idJson: 'e1'}],
        gruposSemaforicos: [{idJson: 'gs1'}]
      };

      scope.currentAnel = scope.objeto.aneis[0];
      scope.aneis = _.filter(scope.objeto.aneis, 'ativo');
    });

    it('Não deve permitir adicionar outro grupo semaforico se a quantidade limite for alcancada', function() {
      expect(scope.podeAdicionarGrupoSemaforico()).not.toBeTruthy();
    });

    it('Deve permitir adicionar outro grupo semaforico se a quantidade limite não for alcancada', function() {
      scope.objeto.limiteGrupoSemaforico = 10;
      expect(scope.podeAdicionarGrupoSemaforico()).toBeTruthy();
    });
  });
});
