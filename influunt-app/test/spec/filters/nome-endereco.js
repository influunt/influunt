'use strict';

describe('Filter: nomeEndereco', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var nomeEndereco;
  beforeEach(inject(function ($filter) {
    nomeEndereco = $filter('nomeEndereco');
  }));

  it('should return the input prefixed with "nomeEndereco filter:"', function () {
    var text = 'angularjs';
    expect(nomeEndereco(text)).toBe('nomeEndereco filter: ' + text);
  });

});
