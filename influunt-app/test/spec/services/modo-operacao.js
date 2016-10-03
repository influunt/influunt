'use strict';

describe('Service: modoOperacaoService', function () {

  // instantiate service
  var modoOperacaoService;
  beforeEach(inject(function (_modoOperacaoService_) {
    modoOperacaoService = _modoOperacaoService_;
  }));

  describe('getCssClass', function () {
    it('retorna o nome kebabCase\'d do modo de operação a partir do status numerico', function() {
      expect(modoOperacaoService.getCssClass(0)).toBe('apagado');
    });

    it('Não deverá retornar uma classe se o status não existir', function() {
      expect(modoOperacaoService.getCssClass(100)).not.toBeDefined();
    });
  });

  describe('getModoById', function () {
    it('Deve retornar a constant uppercase\'d do modo de operação pelo status numerico', function() {
      expect(modoOperacaoService.getModoById(0)).toBe('APAGADO');
    });

    it('Não deverá retornar uma constante se o status não existir', function() {
      expect(modoOperacaoService.getModoById(100)).not.toBeDefined();
    });
  });

  describe('getModoIdByName', function () {
    it('Deve retornar o status numerico do modo de operacao a partir da constante uppercase\'d', function() {
      expect(modoOperacaoService.getModoIdByName('APAGADO')).toBe(0);
    });

    it('Deve retornar -1 se for pesquisada uma constante não reconhecida', function() {
      expect(modoOperacaoService.getModoIdByName('ABC')).toBe(-1);
    });
  });

});
