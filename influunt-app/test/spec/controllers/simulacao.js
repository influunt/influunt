'use strict';

describe('Controller: SimulacaoCtrl', function () {

  var SimulacaoCtrl,
    scope,
    httpBackend,
    controlador,
    stateParams;

  beforeEach(inject(function ($controller, $rootScope, $httpBackend, $stateParams) {
    scope = $rootScope.$new();
    SimulacaoCtrl = $controller('SimulacaoCtrl', {
      $scope: scope
    });

    httpBackend = $httpBackend;
    stateParams = $stateParams;

    controlador = {
      "id": "6a6b7ebe-f985-4eb9-abe2-672a78bd381e",
      "nomeEndereco": "R. Bela Cintra, nº 999. ref.: 2 anéis 3 estágios modo manual",
      "aneis": [
        {
          "id": "9011e40d-c778-4ad5-aa94-60cda8855a12",
          "posicao": 2,
          "detectores": [
            {
              "id": "368e8a9a-8716-4fb9-8bc2-049fe6598b85",
              "tipo": "VEICULAR",
              "posicao": 2,
              "monitorado": true
            }
          ],
          "planos": [
            {
              "id": "fa67fb95-c8c3-4c33-bed4-90dc5f7d70f9",
              "posicao": 1,
              "descricao": "PLANO 1",
              "modoOperacao": "TEMPO_FIXO_ISOLADO"
            }
          ]
        },
        {
          "id": "929a5844-9642-40a3-8c9a-5cdc02b349b0",
          "posicao": 1,
          "detectores": [
            {
              "id": "e88e4ee0-190b-4b53-8f80-7b4f71bd64b0",
              "tipo": "VEICULAR",
              "posicao": 1,
              "monitorado": true
            }
          ],
          "planos": [
            {
              "id": "04d6c68f-ad24-4615-9466-21fc1bcf945b",
              "posicao": 5,
              "descricao": "PLANO 5",
              "modoOperacao": "TEMPO_FIXO_ISOLADO"
            },
            {
              "id": "245995cd-97e4-494f-b7d8-7d2c18053071",
              "posicao": 1,
              "descricao": "PLANO 1",
              "modoOperacao": "TEMPO_FIXO_ISOLADO"
            }
          ]
        }
      ]
    };
  }));

  it('init() deve setar dados do controlador da simulação', function() {
    httpBackend.expectGET('/controladores/'+ controlador.id +'/simulacao').respond(controlador);
    scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
    scope.$state.go('app.simulacao', { idControlador: controlador.id });
    scope.init();
    httpBackend.flush();
    scope.$apply();

    expect(scope.velocidades).toBeDefined();
    expect(scope.parametrosSimulacao).toBeDefined();
    expect(scope.controlador).toBeDefined();
  });

  it('deve adicionar novo disparo de detector ao preencher o anterior', function() {
    scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
    var detector = controlador.aneis[0].detectores[0];
    scope.parametrosSimulacao.disparoDetectores[0].detector = detector;
    scope.parametrosSimulacao.disparoDetectores[0].disparo = moment().format();
    scope.$apply();
    expect(scope.parametrosSimulacao.disparoDetectores.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.disparoDetectores[1]).length).toBe(0);
  });

  it('deve adicionar nova imposição de plano ao preencher o anterior', function() {
    scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
    var plano = controlador.aneis[0].planos[0];
    scope.parametrosSimulacao.imposicaoPlanos[0].plano = plano;
    scope.parametrosSimulacao.imposicaoPlanos[0].disparo = moment().format();
    scope.$apply();
    expect(scope.parametrosSimulacao.imposicaoPlanos.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.imposicaoPlanos[1]).length).toBe(0);
  });

  // it('deve escolher controlador de acordo com parametro na query string', function() {
  //   httpBackend.expectGET('/controladores/simulacao').respond(controladores);
  //   scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
  //   scope.$state.go('app.simulacao', { idControlador: controladores.data[0].id });
  //   scope.init();
  //   httpBackend.flush();
  //   scope.$apply();

  //   expect(scope.parametrosSimulacao.idControlador).toBe(controladores.data[0].id);
  //   expect(scope.detectores).toBeDefined();
  //   expect(scope.detectores.length).toBe(2);
  //   expect(scope.planos).toBeDefined();
  //   expect(scope.planos.length).toBe(2);
  // });
});
