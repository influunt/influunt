'use strict';

describe('Service: influuntUuid', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var influuntUuid;
  beforeEach(inject(function (_influuntUuid_) {
    influuntUuid = _influuntUuid_;
  }));

  it('should do something', function () {
    expect(!!influuntUuid).toBe(true);
  });

});
