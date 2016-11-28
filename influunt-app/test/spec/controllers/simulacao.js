'use strict';

describe('Controller: SimulacaoCtrl', function () {

  var SimulacaoCtrl,
    scope,
    httpBackend,
    stateParams;

  var setParametros = function(){
    scope.parametrosSimulacao = {
      velocidade: 1,
      disparoDetectores: [{}],
      imposicaoPlanos: [{}],
      imposicaoModos: [{}],
      liberacaoImposicoes: [{}],
      falhasControlador: [{}],
      alarmesControlador: [{}]
    };
    return ControladorSimulacao.get().controlador;
  };
  
  beforeEach(inject(function ($controller, $rootScope, $httpBackend, $stateParams) {
    scope = $rootScope.$new();
    SimulacaoCtrl = $controller('SimulacaoCtrl', {
      $scope: scope
    });

    httpBackend = $httpBackend;
    stateParams = $stateParams;
  }));

  it('init() deve setar dados do controlador da simulação', function() {
    httpBackend.expectGET('/controladores/'+ ControladorSimulacao.getControladorId() +'/simulacao').respond(ControladorSimulacao.get());
    scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
    scope.$state.go('app.simulacao', { id: ControladorSimulacao.getControladorId() });
    scope.$apply();
    scope.init();
    httpBackend.flush();

    expect(scope.velocidades).toBeDefined();
    expect(scope.parametrosSimulacao).toBeDefined();
    expect(scope.controlador).toBeDefined();
  });

  it('deve adicionar novo disparo de detector ao preencher o anterior', function() {
    var controlador = setParametros();
    var detector = controlador.aneis[0].detectores[0];
    scope.parametrosSimulacao.disparoDetectores[0].detector = detector;
    scope.parametrosSimulacao.disparoDetectores[0].disparo = moment().format();
    scope.$apply();
    expect(scope.parametrosSimulacao.disparoDetectores.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.disparoDetectores[1]).length).toBe(0);
  });

  it('deve adicionar nova imposição de plano ao preencher o anterior', function() {
    var controlador = setParametros();
    var plano = controlador.aneis[0].planos[0];
    scope.parametrosSimulacao.imposicaoPlanos[0].plano = plano;
    scope.parametrosSimulacao.imposicaoPlanos[0].anel = controlador.aneis[0];
    scope.parametrosSimulacao.imposicaoPlanos[0].disparo = moment().format();
    scope.parametrosSimulacao.imposicaoPlanos[0].duracao = 1;
    scope.$apply();
    expect(scope.parametrosSimulacao.imposicaoPlanos.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.imposicaoPlanos[1]).length).toBe(0);
  });
  
  it('deve adicionar nova imposição de modo ao preencher o anterior', function() {
    var controlador = setParametros();
    scope.parametrosSimulacao.imposicaoModos[0].modo = 'INTERMITENTE';
    scope.parametrosSimulacao.imposicaoModos[0].anel = controlador.aneis[0];
    scope.parametrosSimulacao.imposicaoModos[0].disparo = moment().format();
    scope.parametrosSimulacao.imposicaoModos[0].duracao = 1;
    scope.$apply();
    expect(scope.parametrosSimulacao.imposicaoModos.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.imposicaoModos[1]).length).toBe(0);
  });

  it('deve adicionar novo disparo de alarme ao preencher o anterior', function() {
    setParametros();
    var alarme = ControladorSimulacao.get().alarmes[0];
    scope.parametrosSimulacao.alarmesControlador[0].alarme = alarme;
    scope.parametrosSimulacao.alarmesControlador[0].disparo = moment().format();
    scope.$apply();
    expect(scope.parametrosSimulacao.alarmesControlador.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.alarmesControlador[1]).length).toBe(0);
  });

  it('deve adicionar nova falha ao preencher o horário se não precisar de parâmetro', function() {
    setParametros();
    var falha = ControladorSimulacao.getFalhaSemParametro();
    scope.parametrosSimulacao.falhasControlador[0].falha = falha;
    scope.parametrosSimulacao.falhasControlador[0].disparo = moment().format();
    scope.$apply();
    expect(scope.parametrosSimulacao.falhasControlador.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.falhasControlador[1]).length).toBe(0);
  });

  it('deve adicionar nova falha somente após preencher o horário e parâmetro', function() {
    setParametros();
    var falha = ControladorSimulacao.getFalhaComParametro();
    scope.parametrosSimulacao.falhasControlador[0].falha = falha;
    scope.parametrosSimulacao.falhasControlador[0].disparo = moment().format();
    scope.$apply();
    expect(scope.parametrosSimulacao.falhasControlador.length).toBe(1);
    scope.parametrosSimulacao.falhasControlador[0].parametro = { param: 1 };
    scope.$apply();
    expect(scope.parametrosSimulacao.falhasControlador.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.falhasControlador[1]).length).toBe(0);
  });
});
