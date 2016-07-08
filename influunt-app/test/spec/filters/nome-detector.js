'use strict';

describe('Filter: nomeDetector', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var nomeDetector;
  beforeEach(inject(function ($filter) {
    nomeDetector = $filter('nomeDetector');
  }));

  it('Não retornará nada caso não exista um objeto de parametro', function() {
    expect(nomeDetector()).toBeUndefined();
  });

  it('Se o objeto for do tipo pedestre e posicao 1, deverá retornar DP1', function() {
    var obj = {tipo: 'PEDESTRE', posicao: 1};
    expect(nomeDetector(obj)).toBe('DP1');
  });

  it('Se o objeto for do tipo veicular e posicao 1, deverá retornar DV1', function() {
    var obj = {tipo: 'VEICULAR', posicao: 1};
    expect(nomeDetector(obj)).toBe('DV1');
  });
});
