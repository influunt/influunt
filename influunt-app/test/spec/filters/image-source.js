'use strict';

describe('Filter: imageSource', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var imageSource;
  beforeEach(inject(function ($filter) {
    imageSource = $filter('imageSource');
  }));

  it('should return the input prefixed with "imageSource filter:"', function () {
    var text = 'angularjs';
    expect(imageSource(text)).toBe('imageSource filter: ' + text);
  });

});
