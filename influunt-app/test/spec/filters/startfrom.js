'use strict';

describe('Filter: startFrom', function () {

  // load the filter's module
  // beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var startFrom;
  beforeEach(inject(function ($filter) {
    startFrom = $filter('startFrom');
  }));

  xit('should return the input prefixed with "startFrom filter:"', function () {
    var text = 'angularjs';
    expect(startFrom(text)).toBe('startFrom filter: ' + text);
  });

});
