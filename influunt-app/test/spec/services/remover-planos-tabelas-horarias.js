'use strict';

describe('Service: removerPlanosTabelasHorarias', function () {

  var removerPlanosTabelasHorarias;
  beforeEach(inject(function (_removerPlanosTabelasHorarias_) {
    removerPlanosTabelasHorarias = _removerPlanosTabelasHorarias_;
  }));

  it('verifica se envia a requisicao se tiver versão Plano', function () {
    var scope = {objeto: {versoesPlanos: [{idJson: 'vp1'}], versoesTabelasHorarias: []}};
    expect(true, removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto));
    expect(true, scope.objeto.enviadoRemoverPlanosETabelaHoraria);

    expect(false, removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto));
  });
  
  it('verifica se envia a requisicao se tiver versão tabela horaria', function () {
    var scope = {objeto: {versoesPlanos: [], versoesTabelasHorarias: [{idJson: 'vth1'}]}};
    expect(true, removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto));
    expect(true, scope.objeto.enviadoRemoverPlanosETabelaHoraria);

    expect(false, removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto));
  });

  it('não envia a requisao caso não tenha versoesPlanos e não tenha versoesTabelasHorarias', function () {
    var scope = {objeto: {versoesPlanos: [], versoesTabelasHorarias: []}};
    expect(false, removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto));
    expect(false, scope.objeto.enviadoRemoverPlanosETabelaHoraria);

    expect(false, removerPlanosTabelasHorarias.deletarPlanosTabelasHorariosNoServidor(scope.objeto));
  });

});
