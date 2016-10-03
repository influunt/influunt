'use strict';

describe('Directive: influuntMatch', function () {

  var validTemplate = '<input ng-model="confirmation" influunt-match="original"></input>';
  var compiled;

  var element,
    compile,
    scope;

  beforeEach(inject(function ($rootScope, $compile) {
    scope = $rootScope.$new();
    compile = $compile;
  }));

  it('does not throw when no ngModel controller is found', function() {
    var naTemplate = '<div match="original"></div>';
    compiled = compile(naTemplate)(scope);
    scope.$digest();
  });


  xit('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<influunt-match></influunt-match>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the influuntMatch directive');
  }));
});
