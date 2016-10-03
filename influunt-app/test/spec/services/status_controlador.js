'use strict';

describe('Service: STATUS_CONTROLADOR', function () {

  // load the service's module
  // beforeEach(module('influuntApp'));

  // instantiate service
  var statusControlador;
  beforeEach(inject(function (_STATUS_CONTROLADOR_) {
    statusControlador = _STATUS_CONTROLADOR_;
  }));

  it('deve existir um objeto', function () {
    expect(!!statusControlador).toBe(true);
  });

});
