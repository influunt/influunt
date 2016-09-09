'use strict';

describe('Directive: sortableTh', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<sortable-th></sortable-th>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the sortableTh directive');
  }));
});
