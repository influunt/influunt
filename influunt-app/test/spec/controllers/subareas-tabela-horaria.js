'use strict';

describe('Controller: SubareasTabelaHorariaCtrl', function () {
  var SubareasTabelaHorariaCtrl,
      scope,
      $httpBackend,
      $q,
      $state,
      $controller,
      controlador,
      controladorId,
      subareaId;

  beforeEach(inject(function (_$controller_, $rootScope, _$httpBackend_, _$q_, _$state_) {
    $httpBackend = _$httpBackend_;
    $q = _$q_;
    $state = _$state_;
    scope = $rootScope.$new();
    $controller = _$controller_;
    SubareasTabelaHorariaCtrl = $controller('SubareasTabelaHorariaCtrl', { $scope: scope });
  }));

  var beforeEachFn = function(objControlador) {
    controladorId = objControlador.getControladorId();
    subareaId = objControlador.getSubareaId();
    controlador = objControlador.get();

    $state.go('app.subareas_tabela_horaria', { id: subareaId });
    scope.$apply();
    SubareasTabelaHorariaCtrl = $controller('SubareasTabelaHorariaCtrl', { $scope: scope });

    $httpBackend.expectGET('/subareas/' + subareaId + '/tabela_horaria').respond(controlador);
    scope.init();
    $httpBackend.flush();
    scope.$apply();
  };

  describe('init()', function() {
    it('deve iniciar o objeto vazio se a API não retornar a tabela horária', function() {
      subareaId = '1234';
      $state.go('app.subareas_tabela_horaria', { id: subareaId });
      scope.$apply();
      SubareasTabelaHorariaCtrl = $controller('SubareasTabelaHorariaCtrl', { $scope: scope });
      $httpBackend.expectGET('/subareas/' + subareaId + '/tabela_horaria').respond('');
      scope.init();
      $httpBackend.flush();
      scope.$apply();

      expect(scope.objeto).toBeDefined();
      expect(scope.objeto.versoesTabelasHorarias).toBeDefined();
      expect(scope.objeto.versoesTabelasHorarias).toEqual([]);
      expect(scope.objeto.tabelasHorarias).toBeDefined();
      expect(scope.objeto.tabelasHorarias).toEqual([]);
      expect(scope.objeto.eventos).toBeDefined();
      expect(scope.objeto.eventos).toEqual([]);
      expect(scope.objeto.subareaId).toEqual(subareaId);
    });

    it('deve iniciar o objeto com a resposta da API', function() {
      beforeEachFn(ControladorComPlanos);

      expect(scope.objeto).toBeDefined();
      expect(scope.objeto.versoesTabelasHorarias).toBeDefined();
      expect(scope.objeto.versoesTabelasHorarias).toEqual(ControladorComPlanos.get().versoesTabelasHorarias);
      expect(scope.objeto.tabelasHorarias).toBeDefined();
      expect(scope.objeto.tabelasHorarias).toEqual(ControladorComPlanos.get().tabelasHorarias);
      expect(scope.objeto.eventos).toBeDefined();
      expect(scope.objeto.eventos).toEqual(ControladorComPlanos.get().eventos);
      expect(scope.objeto.subareaId).toEqual(ControladorComPlanos.getSubareaId());
    });
  });

  describe('submitForm', function () {
    var Restangular;
    beforeEach(inject(function(_Restangular_) {
      Restangular = _Restangular_;
      beforeEachFn(ControladorComPlanos);
      scope.submitForm();
    }));

    it('Deve exibir mensagens de validação em caso de inconsistencia.', inject(function(handleValidations) {
      spyOn(handleValidations, 'buildValidationMessages');
      $httpBackend.expectPOST('/subareas/' + subareaId + '/tabela_horaria').respond(422, {});
      $httpBackend.flush();
      scope.$apply();

      expect(handleValidations.buildValidationMessages).toHaveBeenCalled();
      expect(scope.errorsUibAlert).toBeDefined();
    }));

    it('Deve tratar os demais erros que não sejam validações da API', function() {
      spyOn(console, 'error');
      $httpBackend.expectPOST('/subareas/' + subareaId + '/tabela_horaria').respond(500, {});
      $httpBackend.flush();
      scope.$apply();

      expect(console.error).toHaveBeenCalled();
    });
  });

});
