'use strict';

describe('Filter: influuntDate', function () {

  // initialize a new instance of the filter before each test
  var influuntDate, date;
  beforeEach(inject(function ($filter) {
    influuntDate = $filter('influuntDate');
    date = moment(0).utc();
  }));

  it('Deve retornar "-----" se não hover data', function() {
    expect(influuntDate()).toBe('-----');
  });

  it('Por padrão, deverá entregar as datas formatadas por "DD/MM/YYYY HH:mm:ss"', function() {
    expect(influuntDate(date)).toBe('01/01/1970 00:00:00');
  });

  it('Deve ser capaz de processar objetos strings, date e moment', function() {
    var format = 'DD/MM/YYYY';

    expect(influuntDate('01/01/1970', format)).toBe('01/01/1970');
    expect(influuntDate(date, format)).toBe('01/01/1970');
    expect(influuntDate(date.toDate(), format)).toBe(moment(0).format(format));
  });

  it('Possui os formatos "fromNow" e "fromHours', function() {
    expect(influuntDate(date, 'fromNow')).toMatch(/\d*\s?\w+/); // algo como '47 anos atrás'
    expect(influuntDate(date, 'fromHours')).toMatch(/\d*\s?\w+/); // algo como x segundos
  });

});
