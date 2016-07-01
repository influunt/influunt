'use strict';

describe('Service: handleValidations', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var handleValidations;
  beforeEach(inject(function (_handleValidations_) {
    handleValidations = _handleValidations_;
  }));

  it('should do something', function () {
    expect(!!handleValidations).toBe(true);
  });

});
