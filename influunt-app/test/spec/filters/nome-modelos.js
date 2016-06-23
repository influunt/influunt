'use strict';

describe('Filter: nomeModelos', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var nomeModelos;
  beforeEach(inject(function ($filter) {
    nomeModelos = $filter('nomeModelos');
  }));

  it('Não deve retornar nada caso não haja lista de modelos', function() {
    expect(nomeModelos()).not.toBeDefined();
  });

  it('Deve retornar uma string contendo três elementos de lista HTML', function() {
    var modelos = [
      {descricao: 'modelo 1'},
      {descricao: 'modelo 2'},
      {descricao: 'modelo 3'}
    ];

    var result = nomeModelos(modelos);
    var resultHTML = $(result);

    expect(result).toBeDefined();
    expect(resultHTML.length).toBe(3);
  });

});
