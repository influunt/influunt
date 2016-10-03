'use strict';

describe('Directive: influuntMetis', function () {

  // load the directive's module
  // beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('Deve criar uma instancia de um menu metis', inject(function($compile, $timeout) {
    element = angular.element('<ul id="side-menu" influunt-metis><li></li><li></li></ul>');
    element = $compile(element)(scope);
    $timeout.flush();
    scope.$apply();
    expect(element).toBeDefined();
  }));
});
