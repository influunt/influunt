'use strict';

describe('Controller: RelatorioTabelaHorariaCtrl', function () {

  var RelatorioTabelaHorariaCtrl,
      scope,
      httpBackend,
      controladores;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $httpBackend) {
    scope = $rootScope.$new();
    RelatorioTabelaHorariaCtrl = $controller('RelatorioTabelaHorariaCtrl', { $scope: scope });
    httpBackend = $httpBackend;
    controladores = ControladoresBasicos.get();
  }));

  it('deve carregar a lista de controladores ao iniciar', function () {
    httpBackend.expectGET('/controladores').respond(controladores);
    scope.init();
    httpBackend.flush();
    expect(scope.lista).toBeDefined();
    expect(scope.lista.length).toEqual(controladores.data.length);
  });

  it('não deve gerar relatório sem parâmetros', function () {
    expect(scope.podeGerar()).toBeFalsy();
  });

  it('não deve gerar relatório sem data', function () {
    scope.relatorioParams = { controladorId: '1234' };
    scope.$apply();
    expect(scope.podeGerar()).toBeFalsy();
  });

  it('não deve gerar relatório sem controladorId', function () {
    scope.relatorioParams = { data: moment().format() };
    scope.$apply();
    expect(scope.podeGerar()).toBeFalsy();
  });

  it('deve gerar relatório se os parâmetros estiverem setados', function () {
    scope.relatorioParams = { data: moment().format(), controladorId: '1234' };
    scope.$apply();
    expect(scope.podeGerar()).toBeTruthy();
  });

  it('deve buscar os dados do relatório na API', function () {
    var controladorId = '1234',
        data = moment();
    scope.relatorioParams = { data: data, controladorId: controladorId };
    var relatorioData = [{ horario: "08:00:00", plano: "PLANO 1", numeroPlano: "1", modoOperacaoPlano: "ISOLADO", subarea: "Paulista", tipoEvento: "NORMAL" }];
    var queryString = '?controladorId=' + controladorId + '&data=' + data.format();
    httpBackend.expectGET('/relatorios/tabela_horaria' + queryString).respond(relatorioData);
    scope.getDadosRelatorio();
    httpBackend.flush();
    scope.$apply();
    expect(scope.relatorio).toBeDefined();
    expect(scope.relatorio.length).toBe(1);
    expect(scope.relatorio[0].horario).toBe("08:00:00");
    expect(scope.relatorio[0].plano).toBe("PLANO 1");
  });

});
