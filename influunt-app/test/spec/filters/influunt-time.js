'use strict';

describe('Filter: influuntTime', function () {

  // initialize a new instance of the filter before each test
  var influuntTime, date;
  beforeEach(inject(function ($filter) {
    influuntTime = $filter('influuntTime');
  }));

  it('Deve retornar "-----" se não hover data', function() {
    expect(influuntTime()).toBe('-----');
  });

  it('Por padrão, deverá entregar as datas formatadas por "HH:mm:ss"', function() {
    expect(influuntTime('00:00:00.000')).toBe('00:00:00');
  });

});
