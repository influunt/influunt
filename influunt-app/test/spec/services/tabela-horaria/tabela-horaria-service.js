'use strict';

describe('Service: TabelaHorariaService', function () {

  // load the service's module
  // beforeEach(module('influuntApp'));

  // instantiate service
  var TabelaHorariaService;
  beforeEach(inject(function (_TabelaHorariaService_) {
    TabelaHorariaService = _TabelaHorariaService_;
  }));

  it('tipo evento corrente deveria ser NORMAL', function () {
    TabelaHorariaService.initialize('NORMAL');
    expect(TabelaHorariaService.isCurrentTipoEventoNormal()).toBe(true);
    expect(TabelaHorariaService.isCurrentTipoEventoEspecial()).not.toBe(true);
    expect(TabelaHorariaService.isCurrentTipoEventoNaoRecorrente()).not.toBe(true);
  });

  it('tipo evento corrente deveria ser ESPECIAL', function () {
    TabelaHorariaService.initialize('ESPECIAL_RECORRENTE');
    expect(TabelaHorariaService.isCurrentTipoEventoNormal()).not.toBe(true);
    expect(TabelaHorariaService.isCurrentTipoEventoEspecial()).toBe(true);
    expect(TabelaHorariaService.isCurrentTipoEventoNaoRecorrente()).not.toBe(true);
  });

  it('tipo evento corrente deveria ser NAO RECORRENTE', function () {
    TabelaHorariaService.initialize('ESPECIAL_NAO_RECORRENTE');
    expect(TabelaHorariaService.isCurrentTipoEventoNormal()).not.toBe(true);
    expect(TabelaHorariaService.isCurrentTipoEventoEspecial()).not.toBe(true);
    expect(TabelaHorariaService.isCurrentTipoEventoNaoRecorrente()).toBe(true);
  });

});
