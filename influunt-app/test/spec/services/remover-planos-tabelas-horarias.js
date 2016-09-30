'use strict';

describe('Service: removerPlanosTabelasHorarias', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var removerPlanosTabelasHorarias;
  beforeEach(inject(function (_removerPlanosTabelasHorarias_) {
    removerPlanosTabelasHorarias = _removerPlanosTabelasHorarias_;
  }));

  xit('should do something', function () {
    expect(!!removerPlanosTabelasHorarias).toBe(true);
  });

});
