'use strict';

describe('Filter: resourceToString', function () {

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


describe('Filter: resourceToLink', function () {
  // initialize a new instance of the filter before each test
  var resourceToLink, resources;
  beforeEach(inject(function ($filter) {
    resourceToLink = $filter('resourceToLink');
    resources = {
      descricao: 'descricao-resource1',
      other: 'other-resource1'
    };
  }));

  it('Não deve retornar nada caso não haja resource definido', function() {
    expect(resourceToLink()).not.toBeDefined();
  });

  it('Deve retornar uma string contendo com o link HTML do elemento', function() {
    var result = resourceToLink(resources);
    var resultHTML = $(result);

    expect(result).toBeDefined();
    expect(resultHTML.length).toBe(1);
  });

  it('Deve retornar a descrição do resource por padrão', function() {
    expect(resourceToLink(resources)).toMatch(/descricao-resource1/);
  });

  it('Deve retornar o valor de qualquer outro parâmetro, se informado.', function() {
    expect(resourceToLink(resources, 'other')).toMatch(/other-resource1/);
  });

  it('Deve retornar um link para o estado passado como parametro.', function() {
    var resource = {
      other: 'texto do link',
      controlador_id: '1234'
    }
    var link = resourceToLink(resource, 'other', 'app.controladores_show', 'controlador_id', 'id');
    expect(link).toBe('<a href="#/app/controladores/1234">texto do link</a>');
  });
});
