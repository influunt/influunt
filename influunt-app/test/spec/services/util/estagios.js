'use strict';

describe('Service: utilEstagios', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var utilEstagios;
  beforeEach(inject(function (_utisEstagios_) {
    utilEstagios = _utisEstagios_;
  }));

  xit('should do something', function () {
    expect(!!utilEstagios).toBe(true);
  });

});
