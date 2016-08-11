'use strict';

describe('Filter: estagioForaSequencia', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var estagioForaSequencia, estagios, sequencia;
  beforeEach(inject(function ($filter) {
    estagioForaSequencia = $filter('estagioForaSequencia');
    estagios = [{idJson: 1}, {idJson: 2}, {idJson: 3}];
    sequencia = [{estagio: {idJson: 2}},{estagio: {idJson: 3}}];
  }));

  it('Deve retornar somente os estágios com idJson não presentes na sequencia de estagios informada', function() {
    var result = estagioForaSequencia(estagios, sequencia);
    var expectation = [{idJson: 1}];
    expect(result).toEqual(expectation);

  });
  it('Deve ser undefined, caso a sequencia de estagios esteja nula', function() {
    sequencia = null;
    var result = estagioForaSequencia(estagios, sequencia);
    expect(result).not.toBeDefined();
  });
  it('Deve ser undefined, caso a lista de estagios esteja nula', function() {
    estagios = null;
    var result = estagioForaSequencia(estagios, sequencia);
    expect(result).not.toBeDefined();
  });

});
