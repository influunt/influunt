'use strict';

describe('Filter: estagioForaSequencia', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var estagioForaSequencia;
  beforeEach(inject(function ($filter) {
    estagioForaSequencia = $filter('estagioForaSequencia');
  }));

  it('should return the input prefixed with "estagioForaSequencia filter:"', function () {
    var text = 'angularjs';
    expect(estagioForaSequencia(text)).toBe('estagioForaSequencia filter: ' + text);
  });

});
