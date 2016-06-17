'use strict';

describe('Service: validacoesAneis', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var validacoesAneis;
  beforeEach(inject(function (_validacoesAneis_) {
    validacoesAneis = _validacoesAneis_;
  }));

  it('should do something', function () {
    expect(!!validacoesAneis).toBe(true);
  });

});
