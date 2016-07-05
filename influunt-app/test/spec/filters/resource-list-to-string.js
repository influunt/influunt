'use strict';

describe('Filter: resourceListToString', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));
  var resourceListToString;

  // initialize a new instance of the filter before each test
  var resourceListToString, resources;
  beforeEach(inject(function ($filter) {
    resourceListToString = $filter('resourceListToString');
    resources = [{
      descricao: 'descricao-resource1',
      other: 'other-resource1'
    },
    {
      descricao: 'descricao-resource2',
      other: 'other-resource2'
    },
    {
      descricao: 'descricao-resource3',
      other: 'other-resource3'
    }];
  }));

  it('Não deve retornar nada caso não haja resource definido', function() {
    expect(resourceListToString()).not.toBeDefined();
  });

  it('Deve retornar uma string contendo três elementos de lista HTML', function() {
    var result = resourceListToString(resources);
    var resultHTML = $(result);

    expect(result).toBeDefined();
    expect(resultHTML.length).toBe(3);
  });

  it('Deve retornar a descrição do resource por padrão', function() {
    expect(resourceListToString(resources)).toMatch(/descricao-resource1/);
    expect(resourceListToString(resources)).toMatch(/descricao-resource2/);
    expect(resourceListToString(resources)).toMatch(/descricao-resource3/);
  });

  it('Deve retornar o valor de qualquer outro parâmetro, se informado.', function() {
    expect(resourceListToString(resources, 'other')).toMatch(/other-resource1/);
    expect(resourceListToString(resources, 'other')).toMatch(/other-resource2/);
    expect(resourceListToString(resources, 'other')).toMatch(/other-resource3/);
  });

});
