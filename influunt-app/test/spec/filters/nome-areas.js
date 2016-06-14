'use strict';

describe('Filter: nomeAreas', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var nomeAreas;
  beforeEach(inject(function ($filter) {
    nomeAreas = $filter('nomeAreas');
  }));

  it('Não deve retornar nada caso não haja lista de áreas', function() {
    expect(nomeAreas()).not.toBeDefined();
  });

  it('Deve retornar uma string contendo três elementos de lista HTML', function() {
    var areas = [
      {descricao: 'area 1'},
      {descricao: 'area 2'},
      {descricao: 'area 3'}
    ];

    var result = nomeAreas(areas);
    var resultHTML = $(result);

    expect(result).toBeDefined();
    expect(resultHTML.length).toBe(3);
  });

});
