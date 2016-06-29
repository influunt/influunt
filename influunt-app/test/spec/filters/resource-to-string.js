'use strict';

describe('Filter: resourceToString', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var resourceToString;
  beforeEach(inject(function ($filter) {
    resourceToString = $filter('resourceToString');
  }));

  it('should return the input prefixed with "resourceToString filter:"', function () {
    var text = 'angularjs';
    expect(resourceToString(text)).toBe('resourceToString filter: ' + text);
  });

});
