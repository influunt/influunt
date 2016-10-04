'use strict';

describe('Service: removerPlanosTabelasHorarias', function () {

  var removerPlanosTabelasHorarias;
  beforeEach(inject(function (_removerPlanosTabelasHorarias_) {
    removerPlanosTabelasHorarias = _removerPlanosTabelasHorarias_;
  }));

  it('verifica se envia a requisicao se tiver versão Plano', function () {
    var scope = {objeto: {versoesPlanos: [{idJson: 'vp1'}], versoesTabelasHorarias: []}};
    expect(removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto)).toBeTruthy();
    expect(scope.objeto.enviadoRemoverPlanosETabelaHoraria).toBeTruthy();

    expect(removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto)).toBeFalsy();
  });
  
  it('verifica se envia a requisicao se tiver versão tabela horaria', function () {
    var scope = {objeto: {versoesPlanos: [], versoesTabelasHorarias: [{idJson: 'vth1'}]}};
    expect(removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto)).toBeTruthy();
    expect(scope.objeto.enviadoRemoverPlanosETabelaHoraria).toBeTruthy();

    expect(removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto)).toBeFalsy();
  });

  it('não envia a requisao caso não tenha versoesPlanos e não tenha versoesTabelasHorarias', function () {
    var scope = {objeto: {versoesPlanos: [], versoesTabelasHorarias: []}};
    expect(removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto)).toBeFalsy();
    expect(scope.objeto.enviadoRemoverPlanosETabelaHoraria).toBeFalsy();

    expect(removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto)).toBeFalsy();
  });

});
