'use strict';

describe('Service: authInterceptor', function () {

  // instantiate service
  var authInterceptor;
  beforeEach(inject(function (_authInterceptor_) {
    authInterceptor = _authInterceptor_;
  }));

  it('should do something', function () {
    expect(!!authInterceptor).toBe(true);
  });

});
