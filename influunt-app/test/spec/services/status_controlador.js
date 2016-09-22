'use strict';

describe('Service: STATUSCONTROLADOR', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var STATUSCONTROLADOR;
  beforeEach(inject(function (_STATUSCONTROLADOR_) {
    STATUSCONTROLADOR = _STATUSCONTROLADOR_;
  }));

  it('should do something', function () {
    expect(!!STATUSCONTROLADOR).toBe(true);
  });

});
