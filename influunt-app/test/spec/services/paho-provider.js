'use strict';

describe('Service: pahoProvider', function () {

  // instantiate service
  var pahoProvider;
  beforeEach(inject(function (_pahoProvider_) {
    pahoProvider = _pahoProvider_;
  }));

  it('should do something', function () {
    expect(!!pahoProvider).toBe(true);
  });

});
