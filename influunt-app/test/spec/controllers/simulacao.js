'use strict';

describe('Controller: SimulacaoCtrl', function () {

  var SimulacaoCtrl,
    scope,
    httpBackend,
    stateParams;

  beforeEach(inject(function ($controller, $rootScope, $httpBackend, $stateParams) {
    scope = $rootScope.$new();
    SimulacaoCtrl = $controller('SimulacaoCtrl', {
      $scope: scope
    });

    httpBackend = $httpBackend;
    stateParams = $stateParams;

  }));

  it('init() deve setar dados do controlador da simulação', function() {
    httpBackend.expectGET('/controladores/'+ ControladorSimulacao.id +'/simulacao').respond(ControladorSimulacao);
    scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
    scope.$state.go('app.simulacao', { id: ControladorSimulacao.id });
    scope.$apply();
    scope.init();
    httpBackend.flush();

    expect(scope.velocidades).toBeDefined();
    expect(scope.parametrosSimulacao).toBeDefined();
    expect(scope.controlador).toBeDefined();
  });

  it('deve adicionar novo disparo de detector ao preencher o anterior', function() {
    scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
    var detector = ControladorSimulacao.aneis[0].detectores[0];
    scope.parametrosSimulacao.disparoDetectores[0].detector = detector;
    scope.parametrosSimulacao.disparoDetectores[0].disparo = moment().format();
    scope.$apply();
    expect(scope.parametrosSimulacao.disparoDetectores.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.disparoDetectores[1]).length).toBe(0);
  });

  it('deve adicionar nova imposição de plano ao preencher o anterior', function() {
    scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
    var plano = ControladorSimulacao.aneis[0].planos[0];
    scope.parametrosSimulacao.imposicaoPlanos[0].plano = plano;
    scope.parametrosSimulacao.imposicaoPlanos[0].disparo = moment().format();
    scope.$apply();
    expect(scope.parametrosSimulacao.imposicaoPlanos.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.imposicaoPlanos[1]).length).toBe(0);
  });
});
