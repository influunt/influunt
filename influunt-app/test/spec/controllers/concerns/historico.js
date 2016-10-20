'use strict';

describe('Controller: HistoricoCtrl', function () {

  var HistoricoCtrl,
      scope,
      $httpBackend,
      $q,
      $state,
      $controller,
      controlador,
      controladorId,
      anelId,
      resourceName;

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
    resourceName = 'planos';

    HistoricoCtrl = $controller('HistoricoCtrl', { $scope: scope });

    scope.inicializaResourceHistorico(resourceName);
    scope.objeto = controlador;
    scope.$apply();
  };

  describe('editar - função de edição de resources não ATIVOS', function () {
    beforeEach(function() {
      beforeEachFn(ControladorComPlanos);
    });

    it('Deve redirecionar à tela de edicao do resource', function() {
      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(200, null);
      scope.editar(controladorId);
      $httpBackend.flush();
      expect($state.current.name).toBe('app.' + resourceName + '_edit');
    });

    it('O usuário deve ser alertado que não pode editar o resource, se for o caso', inject(function(toast, influuntAlert) {
      spyOn(toast, 'clear');
      spyOn(influuntAlert, 'alert');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
      scope.editar(controladorId);
      $httpBackend.flush();

      expect(toast.clear).toHaveBeenCalled();
      expect(influuntAlert.alert).toHaveBeenCalled();
    }));

    it('Os demais erros devem ser tratados via toast.error', inject(function(toast) {
      spyOn(toast, 'error');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403);
      scope.editar(controladorId);
      $httpBackend.flush();

      expect(toast.error).toHaveBeenCalled();
    }));
  });

  describe('clonar - função de edição de resources ATIVOS', function () {
    beforeEach(function() { beforeEachFn(ControladorComPlanos); });

    it('Deve redirecionar à tela de edicao de resources', function() {
      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(200, null);
      $httpBackend.expectGET('/controladores/' + controladorId + '/editar_' + resourceName).respond(controlador);
      scope.clonar(controladorId);
      $httpBackend.flush();
      expect($state.current.name).toBe('app.' + resourceName + '_edit');
    });

    it('O usuário deve ser alertado que não pode editar o resource, se for o caso', inject(function(toast, influuntAlert) {
      spyOn(toast, 'clear');
      spyOn(influuntAlert, 'alert');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
      scope.clonar(controladorId);
      $httpBackend.flush();

      expect(toast.clear).toHaveBeenCalled();
      expect(influuntAlert.alert).toHaveBeenCalled();
    }));

    it('Os demais erros devem ser tratados via toast.error', inject(function(toast) {
      spyOn(toast, 'error');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403);
      scope.clonar(controladorId);
      $httpBackend.flush();

      expect(toast.error).toHaveBeenCalled();
    }));
  });

  describe('cancelar', function () {
    var deferred, Restangular;
    beforeEach(inject(function(influuntAlert, $q, _Restangular_) {
      Restangular = _Restangular_;
      deferred = $q.defer();
      beforeEachFn(ControladorComPlanos);
      spyOn(influuntAlert, 'delete').and.returnValue(deferred.promise);
    }));

    it('Não deve executar nada se o usuário não confirmar o cancelamento', function() {
      spyOn(Restangular, 'one');
      scope.cancelar(scope.objeto[resourceName][0]);
      deferred.resolve(false);
      scope.$apply();
      expect(Restangular.one).not.toHaveBeenCalled();
    });

    it('Deve excluir a copia local de do resource se este não for sincronizado à API', function() {
      scope.objeto[resourceName] = [{}];
      spyOn(Restangular, 'one');
      scope.cancelar(scope.objeto[resourceName][0]);
      deferred.resolve(true);
      scope.$apply();
      expect(Restangular.one).not.toHaveBeenCalled();
      expect(scope.$state.current.name).toBe('app.controladores');
    });

    it('Deve enviar à API uma request de cancelamento de edição se uma versão já sincronizada for encontrada', inject(function(toast) {
      spyOn(toast, 'success');
      var obj = _.chain(scope.objeto[resourceName]).filter('id').last().value();
      $httpBackend.expectDELETE('/' + resourceName + '/' + obj.id + '/cancelar_edicao').respond({});
      scope.cancelar(obj);
      deferred.resolve(true);
      $httpBackend.flush();
      scope.$apply();
      expect(toast.success).toHaveBeenCalled();
      expect(scope.$state.current.name).toBe('app.controladores');
    }));
  });

  describe('submit', function () {
    var Restangular;
    beforeEach(inject(function(_Restangular_) {
      Restangular = _Restangular_;
      beforeEachFn(ControladorComPlanos);
      scope.submit(scope.objeto).then(function(res) { scope.objeto = res; }).catch(function(err) { scope.errors = err; });
    }));

    it('Deve atualizar o controlador com a resposta da API em caso de sucesso.', function() {
      var resposta = {id: 'controlador_id'};
      $httpBackend.expectPOST('/' + resourceName).respond(200, resposta);
      $httpBackend.flush();
      scope.$apply();

      expect(scope.objeto.id).toEqual(resposta.id);
    });

    it('Deve exibir mensagens de validação em caso de inconsistencia.', inject(function(handleValidations) {
      spyOn(handleValidations, 'buildValidationMessages');
      $httpBackend.expectPOST('/' + resourceName).respond(422, {});
      $httpBackend.flush();
      scope.$apply();

      expect(handleValidations.buildValidationMessages).toHaveBeenCalled();
    }));

    it('Deve tratar os demais erros que não sejam validações da API', function() {
      spyOn(console, 'error');
      $httpBackend.expectPOST('/' + resourceName).respond(500, {});
      $httpBackend.flush();
      scope.$apply();

      expect(console.error).toHaveBeenCalled();
    });
  });

});
