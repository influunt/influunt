'use strict';

describe('Service: validaTransicao', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var validaTransicao;
  beforeEach(inject(function (_validaTransicao_) {
    validaTransicao = _validaTransicao_;
  }));

  it('should do something', function () {
    expect(!!validaTransicao).toBe(true);
  });

});
