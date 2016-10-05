'use strict';

describe('Service: telasSemLogin', function () {

  // instantiate service
  var telasSemLogin;
  beforeEach(inject(function (_TELAS_SEM_LOGIN_) {
    telasSemLogin = _TELAS_SEM_LOGIN_;
  }));

  it('deve existir um objeto', function () {
    expect(!!telasSemLogin).toBe(true);
  });

  it('deve existir uma constante login', function () {
    var index = telasSemLogin.indexOf('login');
    expect(index).toEqual(0);
  });

  it('não deve existir uma constante teste', function () {
    var index = telasSemLogin.indexOf('teste');
    expect(index).toEqual(-1);
  });


});
