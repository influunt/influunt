'use strict';

describe('Filter: resourceListToString', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var resourceListToString;
  beforeEach(inject(function ($filter) {
    resourceListToString = $filter('resourceListToString');
  }));

  it('should return the input prefixed with "resourceListToString filter:"', function () {
    var text = 'angularjs';
    expect(resourceListToString(text)).toBe('resourceListToString filter: ' + text);
  });

});
