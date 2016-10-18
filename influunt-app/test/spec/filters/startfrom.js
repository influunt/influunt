'use strict';

describe('Filter: startFrom', function () {

  // initialize a new instance of the filter before each test
  var startFrom, collection;
  beforeEach(inject(function ($filter) {
    startFrom = $filter('startFrom');
    collection = [1,2,3,4,5,6,7,8,9,0];
  }));

  it('Retorna null se não houver uma coleção', function() {
    expect(startFrom()).toBe(null);
  });

  it('Deve devolver a coleção completa caso não haja parametro de inicio para a funcao', function() {
    expect(startFrom(collection).length).toBe(10);
  });

  it('Deve devolver o segmento da coleção após o número indicado pelo indice de inicio', function() {
    expect(startFrom(collection, 3)).toEqual([4, 5,6,7,8,9,0]);
  });
});
