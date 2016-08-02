'use strict';

describe('Service: influuntAlert', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var influuntAlert;
  beforeEach(inject(function (_influuntAlert_) {
    influuntAlert = _influuntAlert_;
  }));

  it('should do something', function () {
    expect(!!influuntAlert).toBe(true);
  });

});
