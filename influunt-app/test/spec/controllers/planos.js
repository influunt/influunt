'use strict';

describe('Controller: PlanosCtrl', function () {
  var PlanosCtrl,
      scope,
      $httpBackend,
      $q,
      $state,
      $controller,
      controlador,
      timeline,
      controladorId,
      anelId;

  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  beforeEach(inject(function (_$controller_, $rootScope, _$httpBackend_, _$q_, _$state_) {
    $httpBackend = _$httpBackend_;
    $q = _$q_;
    $state = _$state_;
    scope = $rootScope.$new();
    $controller = _$controller_;
  }));

  var beforeEachFn = function(objControlador) {
    controladorId = objControlador.getControladorId();
    anelId = objControlador.getAnelAtivoId();
    controlador = objControlador.get();

    // Inicializando, por padrão, o planos em modo de visualizacao. Isto é necessário para acessar os dados
    // em `data`, nas rotas.
    $state.go('app.planos', {id: controladorId});
    scope.$apply();

    PlanosCtrl = $controller('PlanosCtrl', { $scope: scope });

    timeline = [];
    $httpBackend.expectGET('/controladores/' + controladorId).respond(controlador);
    $httpBackend.expectGET('/planos/' + anelId + '/timeline').respond(timeline);
    scope.init();
    $httpBackend.flush();
  };

  describe('init - controlador mínimo', function () {
    beforeEach(function() { beforeEachFn(ControladorBasico); });

    it('Deve salvar o plano em objeto', function() {
      expect(scope.objeto).toBeDefined();
    });

    it('Deve montar os valores minimos para o diagrama de intervalos', function() {
      expect(scope.valoresMinimos).toBeDefined();
    });

    it('Deve criar um objeto de versão planos para cada anel ativo', function() {
      var aneis = _.filter(scope.objeto.aneis, 'ativo');
      aneis.forEach(function(anel) {
        expect(anel.versaoPlano).toBeDefined();
      });

      expect(scope.objeto.versoesPlanos.length).toBe(aneis.length);
    });

    it('Deve criar n planos por anel, sendo n igual ao limite de planos do controlador', function() {
      var aneis = _.filter(scope.objeto.aneis, 'ativo');
      aneis.forEach(function(anel) {
        expect(anel.planos).toBeDefined();
        expect(anel.planos.length).toBe(scope.objeto.limitePlanos);
      });
    });
  });

  describe('init - controlador com planos cadastrados', function () {
    beforeEach(function() { beforeEachFn(ControladorComPlanos); });

    it('Deverá preencher os n planos por anel, mantendo aqueles que já haviam sido configurados', function() {
      var aneis = _.filter(scope.objeto.aneis, 'ativo');
      aneis.forEach(function(anel) {
        var planosConfigurados = _.filter(scope.objeto.planos, 'configurado');

        expect(anel.planos).toBeDefined();
        expect(anel.planos.length).toBe(scope.objeto.limitePlanos);
        expect(planosConfigurados.length).toBe(controlador.planos.length);
        expect(_.map(planosConfigurados, 'id')).toEqual(_.map(controlador.planos, 'id'));
      });
    });
  });

  describe('editarPlano - função de edição de planos não ATIVOS', function () {
    beforeEach(function() {
      beforeEachFn(ControladorComPlanos);
    });

    it('Deve redirecionar à tela de edicao de planos', function() {
      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(200, null);
      scope.editarPlano(controladorId);
      $httpBackend.flush();
      expect($state.current.name).toBe('app.planos_edit');
    });

    it('O usuário deve ser alertado que não pode editar o plano, se for o caso', inject(function(toast, influuntAlert) {
      spyOn(toast, 'clear');
      spyOn(influuntAlert, 'alert');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
      scope.editarPlano(controladorId);
      $httpBackend.flush();

      expect(toast.clear).toHaveBeenCalled();
      expect(influuntAlert.alert).toHaveBeenCalled();
    }));

    it('Os demais erros devem ser tratados via toast.error', inject(function(toast) {
      spyOn(toast, 'error');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403);
      scope.editarPlano(controladorId);
      $httpBackend.flush();

      expect(toast.error).toHaveBeenCalled();
    }));
  });

  describe('clonarPlanos - função de edição de planos ATIVOS', function () {
    beforeEach(function() {
      beforeEachFn(ControladorComPlanos);
    });

    it('Deve redirecionar à tela de edicao de planos', function() {
      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(200, null);
      $httpBackend.expectGET('/controladores/' + controladorId + '/editar_planos').respond(controlador);
      scope.clonarPlanos(controladorId);
      $httpBackend.flush();
      expect($state.current.name).toBe('app.planos_edit');
    });

    it('O usuário deve ser alertado que não pode editar o plano, se for o caso', inject(function(toast, influuntAlert) {
      spyOn(toast, 'clear');
      spyOn(influuntAlert, 'alert');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
      scope.clonarPlanos(controladorId);
      $httpBackend.flush();

      expect(toast.clear).toHaveBeenCalled();
      expect(influuntAlert.alert).toHaveBeenCalled();
    }));

    it('Os demais erros devem ser tratados via toast.error', inject(function(toast) {
      spyOn(toast, 'error');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403);
      scope.clonarPlanos(controladorId);
      $httpBackend.flush();

      expect(toast.error).toHaveBeenCalled();
    }));
  });
});
