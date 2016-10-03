'use strict';

describe('Filter: limitesListToString', function () {

  var limites;
  // load the filter's module
  // beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var limitesListToString;
  beforeEach(inject(function ($filter) {
    limitesListToString = $filter('limitesListToString');
    limites = [{
      latitude: 123,
      longitude: 456
    },
    {
      latitude: 231,
      longitude: 564
    },
    {
      latitude: 321,
      longitude: 654
    }];
  }));

  it('Não deve retornar nada caso não haja lista de limites definida', function() {
    expect(limitesListToString()).not.toBeDefined();
  });

  it('Deve retornar uma string contendo três elementos de lista HTML', function() {
    var result = limitesListToString(limites);
    var resultHTML = $(result);

    expect(result).toBeDefined();
    expect(resultHTML.length).toBe(3);
  });

  it('Deve retornar uma lista de pares ordenados (x, y) da coordenada', function() {
    expect(limitesListToString(limites)).toMatch(/(123; 456)/);
    expect(limitesListToString(limites)).toMatch(/(231; 564)/);
    expect(limitesListToString(limites)).toMatch(/(321; 654)/);
  });

});
