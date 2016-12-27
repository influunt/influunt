'use strict';

describe('Controller: TabelaHorariosCtrl', function () {
  var TabelaHorariosCtrl,
      scope,
      $httpBackend,
      $q,
      $state,
      $controller,
      controlador,
      controladorId;

  beforeEach(inject(function (_$controller_, $rootScope, _$httpBackend_, _$q_, _$state_) {
    $httpBackend = _$httpBackend_;
    $q = _$q_;
    $state = _$state_;
    scope = $rootScope.$new();
    $controller = _$controller_;
    TabelaHorariosCtrl = $controller('TabelaHorariosCtrl', { $scope: scope });
  }));

  var beforeEachFn = function(objControlador) {
    controladorId = objControlador.getControladorId();
    controlador = objControlador.get();

    // Inicializando, por padrão, o planos em modo de visualizacao. Isto é necessário para acessar os dados
    // em `data`, nas rotas.
    $state.go('app.tabelas_horarias', {id: controladorId});
    scope.$apply();
    TabelaHorariosCtrl = $controller('TabelaHorariosCtrl', { $scope: scope });

    $httpBackend.expectGET('/controladores/' + controladorId).respond(controlador);
    scope.init();
    $httpBackend.flush();
    scope.$apply();
  };

  describe('editarTabelaHoraria - função de edição de tabela horaria não ATIVA', function () {
    beforeEach(function() {
      beforeEachFn(ControladorComPlanos);
    });

    it('Deve redirecionar à tela de edicao de tabela horária', function() {
      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(200, null);
      scope.editarTabelaHoraria(controladorId);
      $httpBackend.flush();
      expect($state.current.name).toBe('app.tabelas_horarias_edit');
    });

    it('O usuário deve ser alertado que não pode editar o plano, se for o caso', inject(function(toast, influuntAlert) {
      spyOn(toast, 'clear');
      spyOn(influuntAlert, 'alert');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
      scope.editarTabelaHoraria(controladorId);
      $httpBackend.flush();

      expect(toast.clear).toHaveBeenCalled();
      expect(influuntAlert.alert).toHaveBeenCalled();
    }));

    it('Os demais erros devem ser tratados via toast.error', inject(function(toast, influuntAlert) {
      spyOn(toast, 'clear');
      spyOn(influuntAlert, 'alert');
      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
      scope.editarTabelaHoraria(controladorId);
      $httpBackend.flush();

      expect(toast.clear).toHaveBeenCalled();
      expect(influuntAlert.alert).toHaveBeenCalled();
    }));
  });

  describe('clonarTabelaHoraria - função de edição de tabela horaria ATIVA', function () {
    beforeEach(function() { beforeEachFn(ControladorComPlanos); });

    it('Deve redirecionar à tela de edicao de tabela horaria', function() {
      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(200, null);
      $httpBackend.expectGET('/controladores/' + controladorId + '/editar_tabelas_horarias').respond(controlador);
      scope.clonarTabelaHoraria(controladorId);
      $httpBackend.flush();
      expect($state.current.name).toBe('app.tabelas_horarias_edit');
    });

    it('O usuário deve ser alertado que não pode editar a tabela horaria, se for o caso', inject(function(influuntAlert) {
      spyOn(influuntAlert, 'alert');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403, [{message: 'teste'}]);
      scope.clonarTabelaHoraria(controladorId);
      $httpBackend.flush();

      expect(influuntAlert.alert).toHaveBeenCalled();
    }));

    it('Os demais erros devem ser tratados via toast.error', inject(function(toast) {
      spyOn(toast, 'error');

      $httpBackend.expectGET('/controladores/' + controladorId + '/pode_editar').respond(403);
      scope.clonarTabelaHoraria(controladorId);
      $httpBackend.flush();

      expect(toast.error).toHaveBeenCalled();
    }));
  });

  describe('cancelarEdicao', function () {
    var deferred, Restangular;
    beforeEach(inject(function(influuntAlert, $q, _Restangular_) {
      Restangular = _Restangular_;
      deferred = $q.defer();
      beforeEachFn(ControladorComPlanos);
      spyOn(influuntAlert, 'delete').and.returnValue(deferred.promise);
    }));

    it('Não deve executar nada se o usuário não confirmar o cancelamento', function() {
      scope.currentTabelaHoraria = {};
      spyOn(Restangular, 'one');
      scope.cancelarEdicao();
      deferred.resolve(false);
      scope.$apply();
      expect(Restangular.one).not.toHaveBeenCalled();
      expect(scope.$state.current.name).toBe('app.tabelas_horarias');
    });

    it('Deve excluir a copia local de plano se este não for sincronizado à API', function() {
      scope.currentTabelaHoraria = {};
      spyOn(Restangular, 'one');
      scope.cancelarEdicao();
      deferred.resolve(true);
      scope.$apply();
      expect(Restangular.one).not.toHaveBeenCalled();
      expect(scope.$state.current.name).toBe('app.controladores');
    });

    it('Deve enviar à API uma request de cancelamento de edição se uma versão já sincronizada for encontrada', inject(function(toast) {
      spyOn(toast, 'success');
      var tabelaHoraria = _.chain(scope.objeto.tabelasHorarias).filter('id').last().value();
      scope.currentTabelaHoraria = tabelaHoraria;
      $httpBackend.expectDELETE('/tabelas_horarias/' + tabelaHoraria.id + '/cancelar_edicao').respond({});
      scope.cancelarEdicao();
      deferred.resolve(true);
      $httpBackend.flush();
      scope.$apply();
      expect(toast.success).toHaveBeenCalled();
      expect(scope.$state.current.name).toBe('app.controladores');
    }));
  });

  describe('submitForm', function () {
    var Restangular;
    beforeEach(inject(function(_Restangular_) {
      Restangular = _Restangular_;
      beforeEachFn(ControladorComPlanos);
      scope.submitForm();
    }));

    it('Deve atualizar o controlador com a resposta da API em caso de sucesso.', function() {
      var resposta = {id: 'controlador_id'};
      $httpBackend.expectPOST('/tabelas_horarias').respond(200, resposta);
      $httpBackend.flush();
      scope.$apply();

      expect(scope.objeto.id).toEqual(resposta.id);
    });

    it('Deve exibir mensagens de validação em caso de inconsistencia.', inject(function(handleValidations) {
      spyOn(handleValidations, 'buildValidationMessages');
      $httpBackend.expectPOST('/tabelas_horarias').respond(422, {});
      $httpBackend.flush();
      scope.$apply();

      expect(handleValidations.buildValidationMessages).toHaveBeenCalled();
    }));

    it('Deve tratar os demais erros que não sejam validações da API', function() {
      spyOn(console, 'error');
      $httpBackend.expectPOST('/tabelas_horarias').respond(500, {});
      $httpBackend.flush();
      scope.$apply();

      expect(console.error).toHaveBeenCalled();
    });
  });

});
