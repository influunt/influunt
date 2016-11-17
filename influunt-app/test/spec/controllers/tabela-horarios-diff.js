'use strict';

describe('Controller: TabelaHorariosDiffCtrl', function () {
  var TabelaHorariosDiffCtrl,
      scope,
      $httpBackend,
      $q,
      $state,
      $controller,
      controlador,
      controladorId,
      versaoConfigurada;

  beforeEach(inject(function (_$controller_, $rootScope, _$httpBackend_, _$q_, _$state_) {
    $httpBackend = _$httpBackend_;
    $q = _$q_;
    $state = _$state_;
    scope = $rootScope.$new();
    $controller = _$controller_;
  }));

  var beforeEachFn = function(objControlador) {
    controladorId = objControlador.getControladorId();
    versaoConfigurada = objControlador.getVersaoTabelaHorariaConfigurada();
    controlador = objControlador.get();

    $state.go('app.tabelas_horarias_diff', { id: controladorId, versaoIdJson: versaoConfigurada.idJson });
    scope.$apply();
    TabelaHorariosDiffCtrl = $controller('TabelaHorariosDiffCtrl', { $scope: scope });

    $httpBackend.expectGET('/controladores/' + controladorId).respond(controlador);
    scope.init();
    $httpBackend.flush();
    scope.$apply();

    expect(scope.versaoBase).toBeDefined();
    expect(scope.versaoOrigem).toBeDefined();
    expect(scope.versaoBase.id).toBe(versaoConfigurada.id);
    expect(scope.versaoOrigem.tabelaHoraria.idJson).toBe(scope.versaoBase.tabelaHorariaOrigem.idJson);
  };

  describe('construção do diff', function() {
    beforeEach(function() {
      beforeEachFn(ControladorVariasVersoesTabelaHoraria);
    });

    it('deve construir o diff de eventos normais por default', function() {
      expect(scope.currentTipoEvento).toBe('NORMAL');
      expect(_.filter(scope.eventosOrigem, { status: 'removido' }).length).toBe(0);
      expect(_.filter(scope.eventosOrigem, { status: 'inalterado' }).length).toBe(4);
      expect(_.filter(scope.eventosBase, { status: 'adicionado' }).length).toBe(0);
      expect(_.filter(scope.eventosBase, { status: 'inalterado' }).length).toBe(4);
    });

    it('deve construir o diff de eventos recorrentes', function() {
      scope.selecionaTipoEvento(1);
      scope.$apply();

      expect(scope.currentTipoEvento).toBe('ESPECIAL_RECORRENTE');
      expect(_.filter(scope.eventosOrigem, { status: 'removido' }).length).toBe(1);
      expect(_.filter(scope.eventosOrigem, { status: 'inalterado' }).length).toBe(1);
      expect(_.filter(scope.eventosBase, { status: 'adicionado' }).length).toBe(2);
      expect(_.filter(scope.eventosBase, { status: 'inalterado' }).length).toBe(1);
    });

    it('deve construir o diff de eventos não recorrentes', function() {
      scope.selecionaTipoEvento(2);
      scope.$apply();

      expect(scope.currentTipoEvento).toBe('ESPECIAL_NAO_RECORRENTE');
      expect(_.filter(scope.eventosOrigem, { status: 'removido' }).length).toBe(2);
      expect(_.filter(scope.eventosOrigem, { status: 'inalterado' }).length).toBe(0);
      expect(_.filter(scope.eventosBase, { status: 'adicionado' }).length).toBe(2);
      expect(_.filter(scope.eventosBase, { status: 'inalterado' }).length).toBe(0);
    });
  });

});
