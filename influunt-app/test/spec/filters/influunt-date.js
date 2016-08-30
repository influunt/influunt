'use strict';

describe('Filter: influuntDate', function () {

  // load the filter's module
  beforeEach(module('influuntApp'));

  // initialize a new instance of the filter before each test
  var influuntDate;
  beforeEach(inject(function ($filter) {
    influuntDate = $filter('influuntDate');
  }));

  // xit('should return the input prefixed with "influuntDate filter:"', function () {
  //   var text = 'angularjs';
  //   expect(influuntDate(text)).toBe('influuntDate filter: ' + text);
  // });

});
