'use strict';

describe('Service: telasSemLogin', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var telasSemLogin;
  beforeEach(inject(function (_telasSemLogin_) {
    telasSemLogin = _telasSemLogin_;
  }));

  it('should do something', function () {
    expect(!!telasSemLogin).toBe(true);
  });

});
