'use strict';

describe('Filter: checkboxFilter', function () {

  var input;

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var checkboxFilter;
  beforeEach(inject(function ($filter) {
    checkboxFilter = $filter('checkboxFilter');
    input = [{id: 1, valor: 'test 1'}, {id: 2, valor: 'test 2'}, {id: 3, valor: 'test 3'}];
  }));

  it('Deve retornar a lista completa caso nenhum filtro seja realizado', function() {
    expect(checkboxFilter(input).length).toBe(3);
  });

  it('Deve filtrar o input pelos id\'s informados', function() {
    var filtro = {1: true, 2: true};
    expect(checkboxFilter(input, filtro).length).toBe(2);
  });

  it('Campos selecionados e removidos não devem ser selecionados', function() {
    var filtro = {1: true, 2: false};
    expect(checkboxFilter(input, filtro).length).toBe(1);
  });

  it('Não deve retornar nada, caso não haja dados inseridos', function() {
    expect(checkboxFilter()).toBeUndefined();
  });

});
