'use strict';

describe('Filter: resourceToString', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var resourceToString, resource;
  beforeEach(inject(function ($filter) {
    resourceToString = $filter('resourceToString');
    resource = {
      nome: 'nome-resource',
      other: 'other-resource'
    };
  }));

  it('Não deve retornar nada caso não haja um resource definido', function() {
    expect(resourceToString()).not.toBeDefined();
  });

  it('Deve retornar por padrao o nome do resource', function() {
    expect(resourceToString(resource)).toBe('nome-resource');
  });

  it('Deve retornar qualquer outro campo, caso este seja definido.', function() {
    expect(resourceToString(resource, 'other')).toBe('other-resource');
  });

});
