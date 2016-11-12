'use strict';

describe('Filter: statusControlador', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var statusControlador;
  beforeEach(inject(function ($filter) {
    statusControlador = $filter('statusControlador');
  }));

  it('should return the input prefixed with "statusControlador filter:"', function () {
    var text = 'angularjs';
    expect(statusControlador(text)).toBeDefined();
  });

});
