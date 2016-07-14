'use strict';

describe('Directive: influuntMetis', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<influunt-metis></influunt-metis>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the influuntMetis directive');
  }));
});
