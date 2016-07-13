'use strict';

describe('Service: assertControlador', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var assertControlador;
  beforeEach(inject(function (_assertControlador_) {
    assertControlador = _assertControlador_;
  }));

  it('should do something', function () {
    expect(!!assertControlador).toBe(true);
  });

});
