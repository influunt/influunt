'use strict';

describe('Directive: influuntMap', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope, $compile) {
    scope = $rootScope.$new();

    var element = '<div influunt-map latitude="latitude" longitude="longitude" />';
    element = $compile(element)(scope);
    scope.$digest();
  }));

  xit('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<influunt-map></influunt-map>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the influuntMap directive');
  }));
});
