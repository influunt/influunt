'use strict';

describe('Directive: imposicao/liberar', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<imposicao/liberar></imposicao/liberar>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the imposicao/liberar directive');
  }));
});
