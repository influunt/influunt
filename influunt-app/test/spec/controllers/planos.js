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

  describe('bugs', function () {
    var deferred;
    beforeEach(inject(function(influuntAlert, $q) {
      beforeEachFn(ControladorComPlanos);
      // Mocks a call for confirm method and calls the promise function.
      deferred = $q.defer();
      spyOn(influuntAlert, 'delete').and.returnValue(deferred.promise);
    }));

    it('Adicionar, remover e trocar modo de operação', function() {
      var estagioPlano = _.find(scope.objeto.estagiosPlanos, {idJson: scope.currentPlano.estagiosPlanos[0].idJson});
      expect(scope.currentPlano.estagiosPlanos.length).toBe(3);

      scope.adicionarEstagioPlano(estagioPlano);
      expect(scope.currentPlano.estagiosPlanos.length).toBe(4);

      var estagioPlano = _.find(scope.objeto.estagiosPlanos, {idJson: scope.currentPlano.estagiosPlanos[0].idJson});
      scope.removerEstagioPlano(estagioPlano);
      deferred.resolve(true);
      scope.$apply();
      expect(scope.currentPlano.estagiosPlanos.length).toBe(3);

      scope.currentPlano.modoOperacao = 'ATUADO';
      scope.limpaDadosPlano();

      expect(scope.currentPlano.estagiosPlanos.length).toBe(3);
    });
  });

  describe('erros ao salvar', function () {
    beforeEach(function() {
      beforeEachFn(ControladorComPlanos);
    });

    it('Badge de erro deve aparecer na posição correta', function() {
      scope.selecionaAnelPlanos(0);
      scope.errors = {'aneis':[{'versoesPlanos':[{'planos':[{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']},null,{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']}]}],'all':[{'planos':[{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']},null,{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']}]}]}]};

      expect(scope.planoTemErro(0)).toBe(true);
      expect(scope.planoTemErro(1)).toBe(false);
      expect(scope.planoTemErro(2)).toBe(true);
    });
  });



  describe('erros ao salvar anel com plano exclusivo', function () {
    beforeEach(function() {
      beforeEachFn(ControladorComPlanoExclusivo);
    });

    it('Badge de erro deve aparecer na posição correta', function() {
      scope.selecionaAnelPlanos(0);
      scope.errors = {'aneis':[{'versoesPlanos':[{'planos':[null, {'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']},null,{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']}]}],'all':[{'planos':[{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']},null,{'ultrapassaTempoCiclo':['A soma dos tempos dos estágios ({temposEstagios}s) é diferente do tempo de ciclo ({tempoCiclo}s).']}]}]}]};

      expect(scope.planoTemErro(0)).toBe(false);
      expect(scope.planoTemErro(1)).toBe(true);
      expect(scope.planoTemErro(2)).toBe(false);
      expect(scope.planoTemErro(3)).toBe(true);
    });
  });
});
