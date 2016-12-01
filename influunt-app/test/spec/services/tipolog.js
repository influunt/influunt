'use strict';

describe('Service: tipoLog', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var tipoLog;
  beforeEach(inject(function (_tipoLog_) {
    tipoLog = _tipoLog_;
  }));

  it('should do something', function () {
    expect(!!tipoLog).toBe(true);
  });

});
