'use strict';

describe('Service: mapaProvider', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var mapaProvider;
  beforeEach(inject(function (_mapaProvider_) {
    mapaProvider = _mapaProvider_;
  }));

  it('should do something', function () {
    expect(!!mapaProvider).toBe(true);
  });

});
