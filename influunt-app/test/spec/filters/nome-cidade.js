'use strict';

describe('Filter: nomeCidade', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var nomeCidade;
  beforeEach(inject(function ($filter) {
    nomeCidade = $filter('nomeCidade');
  }));

  it('Não deve retornar nada caso não haja uma cidade definida', function() {
    expect(nomeCidade()).not.toBeDefined();
  });

  it('Deve retornar o nome da cidade quando houver uma cidade', function() {
    var cidade = {
      nome: "cidade A"
    };

    expect(nomeCidade(cidade)).toBe(cidade.nome);
  });

});
