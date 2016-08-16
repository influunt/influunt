'use strict';

describe('Filter: markersAneisPopup', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var markersAneisPopup;
  beforeEach(inject(function ($filter) {
    markersAneisPopup = $filter('markersAneisPopup');
  }));

  it('should return the input prefixed with "markersAneisPopup filter:"', function () {
    var text = 'angularjs';
    expect(markersAneisPopup(text)).toBe('markersAneisPopup filter: ' + text);
  });

});
