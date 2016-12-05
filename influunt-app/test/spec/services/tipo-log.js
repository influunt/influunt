'use strict';

describe('Service: TIPO_LOG', function () {

  // instantiate service
  var tipoLog;
  beforeEach(inject(function (_TIPO_LOG_) {
    tipoLog = _TIPO_LOG_;
  }));

  it('deve existir um objeto', function () {
    expect(!!tipoLog).toBe(true);
  });

});
