'use strict';

describe('Service: geraDadosDiagramaIntervalo', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var geraDadosDiagramaIntervalo;
  beforeEach(inject(function (_geraDadosDiagramaIntervalo_) {
    geraDadosDiagramaIntervalo = _geraDadosDiagramaIntervalo_;
  }));

  it('should do something', function () {
    expect(!!geraDadosDiagramaIntervalo).toBe(true);
  });

});
