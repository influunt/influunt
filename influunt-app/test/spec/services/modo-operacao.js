'use strict';

describe('Service: modoOperacao', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var modoOperacao;
  beforeEach(inject(function (_modoOperacao_) {
    modoOperacao = _modoOperacao_;
  }));

  it('should do something', function () {
    expect(!!modoOperacao).toBe(true);
  });

});
