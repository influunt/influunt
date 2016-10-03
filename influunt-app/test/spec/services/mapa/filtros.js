'use strict';

describe('Service: filtrosMapa', function () {

  // load the service's module
  // beforeEach(module('influuntApp'));

  // instantiate service
  var filtrosMapa;
  beforeEach(inject(function (_filtrosMapa_) {
    filtrosMapa = _filtrosMapa_;
  }));

  it('should do something', function () {
    expect(!!filtrosMapa).toBe(true);
  });

});
