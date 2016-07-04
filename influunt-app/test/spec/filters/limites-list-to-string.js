'use strict';

describe('Filter: limitesListToString', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var limitesListToString;
  beforeEach(inject(function ($filter) {
    limitesListToString = $filter('limitesListToString');
  }));

  it('should return the input prefixed with "limitesListToString filter:"', function () {
    var text = 'angularjs';
    expect(limitesListToString(text)).toBe('limitesListToString filter: ' + text);
  });

});
