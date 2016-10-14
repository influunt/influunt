'use strict';

describe('Controller: SimulacaoCtrl', function () {

  var SimulacaoCtrl,
    scope,
    httpBackend,
    controladores,
    stateParams;

  beforeEach(inject(function ($controller, $rootScope, $httpBackend, $stateParams) {
    scope = $rootScope.$new();
    SimulacaoCtrl = $controller('SimulacaoCtrl', {
      $scope: scope
    });

    httpBackend = $httpBackend;
    stateParams = $stateParams;

    controladores = {
      "data": [
        {
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
        },
        {
          "id": "8a8d866f-58b3-4460-a0d0-155dafe7c53d",
          "nomeEndereco": "R. Croata, nº 44. ref.: 1 anel 2 estágios",
          "aneis": [
            {
              "id": "6e6153f0-9df5-4e17-bd64-9f1ded9b3b2c",
              "posicao": 1,
              "detectores": [
                {
                  "id": "825e8554-ec51-4a7b-a85e-11f50f4a481f",
                  "tipo": "VEICULAR",
                  "posicao": 1,
                  "monitorado": true
                },
                {
                  "id": "fcfbdcc4-91f8-4c3d-a577-f78eaf6ddcc7",
                  "tipo": "VEICULAR",
                  "posicao": 2,
                  "monitorado": true
                }
              ],
              "planos": [
                {
                  "id": "dc67cd92-99f5-4a68-aef4-ab4f029cb287",
                  "posicao": 1,
                  "descricao": "PLANO 1",
                  "modoOperacao": "TEMPO_FIXO_ISOLADO"
                }
              ]
            }
          ]
        }
      ],
      "total": 2
    };
  }));

  it('init() deve setar dados inciais da simulação', function() {
    httpBackend.expectGET('/controladores/simulacao').respond(controladores);
    scope.init();
    httpBackend.flush();
    expect(scope.velocidades).toBeDefined();
    expect(scope.parametrosSimulacao).toBeDefined();
    expect(scope.controladores).toBeDefined();
  });

  it('deve atualizar detectores e planos ao escolher um controlador', function() {
    scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
    scope.controladores = controladores.data;
    scope.parametrosSimulacao.idControlador = scope.controladores[0].id;
    scope.$apply();
    expect(scope.detectores).toBeDefined();
    expect(scope.detectores.length).toBe(2);
    expect(scope.planos).toBeDefined();
    expect(scope.planos.length).toBe(2);
  });

  it('deve adicionar novo disparo de detector ao preencher o anterior', function() {
    scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
    var detector = controladores.data[0].aneis[0].detectores[0];
    scope.parametrosSimulacao.disparoDetectores[0].detector = detector;
    scope.parametrosSimulacao.disparoDetectores[0].disparo = moment().format();
    scope.$apply();
    expect(scope.parametrosSimulacao.disparoDetectores.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.disparoDetectores[1]).length).toBe(0);
  });

  it('deve adicionar nova imposição de plano ao preencher o anterior', function() {
    scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
    var plano = controladores.data[0].aneis[0].planos[0];
    scope.parametrosSimulacao.imposicaoPlanos[0].plano = plano;
    scope.parametrosSimulacao.imposicaoPlanos[0].disparo = moment().format();
    scope.$apply();
    expect(scope.parametrosSimulacao.imposicaoPlanos.length).toBe(2);
    expect(Object.keys(scope.parametrosSimulacao.imposicaoPlanos[1]).length).toBe(0);
  });

  it('deve escolher controlador de acordo com parametro na query string', function() {
    httpBackend.expectGET('/controladores/simulacao').respond(controladores);
    scope.parametrosSimulacao = { disparoDetectores: [{}], imposicaoPlanos: [{}] };
    scope.$state.go('app.simulacao', { idControlador: controladores.data[0].id });
    scope.init();
    httpBackend.flush();
    scope.$apply();

    expect(scope.parametrosSimulacao.idControlador).toBe(controladores.data[0].id);
    expect(scope.detectores).toBeDefined();
    expect(scope.detectores.length).toBe(2);
    expect(scope.planos).toBeDefined();
    expect(scope.planos.length).toBe(2);
  });
});
