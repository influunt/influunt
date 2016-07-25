'use strict';

describe('Service: influuntBlockui', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var influuntBlockui;
  beforeEach(inject(function (_influuntBlockui_) {
    influuntBlockui = _influuntBlockui_;
  }));

  it('should do something', function () {
    expect(!!influuntBlockui).toBe(true);
  });

});
