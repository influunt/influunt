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
    scope.parametrosSimulacao.imposicaoPlanos[0].duracao = 5;
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

  it('deve iniciar e parar a simulação apropriadamente', function() {
    var params = '{"simulacaoId":"367b1257-e917-48ed-863a-bcf698a48986","controladorId":"567d1171-d35c-4fdf-8120-8f44ef0f6092","tempoCicloAnel":[],"aneis":[{"estagios":[{"posicao":3,"imagem":"api/v1/imagens/65e3d8ff-928f-42a8-8a43-924ea67c57c7/thumb"},{"posicao":2,"imagem":"api/v1/imagens/43e479b7-9871-47de-b833-acc0537a5e81/thumb"},{"posicao":1,"imagem":"api/v1/imagens/4e9300f5-9a24-4e63-974d-920fce3609ef/thumb"}],"numero":1,"tiposGruposSemaforicos":["VEICULAR","PEDESTRE","VEICULAR","PEDESTRE","VEICULAR","PEDESTRE","VEICULAR","PEDESTRE"]},{"estagios":[{"posicao":2,"imagem":"api/v1/imagens/89b3b0d8-3f1b-48f7-b7ab-2d04004b6563/thumb"},{"posicao":3,"imagem":"api/v1/imagens/0ed86340-a10b-4043-908d-c73e4690a00a/thumb"},{"posicao":1,"imagem":"api/v1/imagens/ceaf6c44-0250-4d48-99b9-0dc4da042745/thumb"}],"numero":2,"tiposGruposSemaforicos":["VEICULAR","PEDESTRE","VEICULAR","PEDESTRE","VEICULAR","PEDESTRE"]},{"estagios":[],"numero":3,"tiposGruposSemaforicos":[]},{"estagios":[],"numero":4,"tiposGruposSemaforicos":[]}],"detectores":[{"tipo":"VEICULAR","anel":1,"posicao":1},{"tipo":"PEDESTRE","anel":1,"posicao":1}]}';
    setParametros();
    httpBackend.expectPOST('/simulacao').respond(params);
    scope.submitForm();
    scope.$apply();
    httpBackend.flush();
    expect(scope.simulacao).toBeDefined();


    httpBackend.expectPOST('/simulacao/parar');
    spyOn(scope.simulacao.state, 'destroy');
    scope.pararSimulacao();
    httpBackend.flush();
    expect(scope.simulacao.state.destroy).toHaveBeenCalled();
  });
});
