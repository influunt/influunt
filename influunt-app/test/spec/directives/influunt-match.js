'use strict';

describe('Directive: influuntMatch', function () {

  var validTemplate = '<input ng-model="confirmation" influunt-match="original"></input>';
  var compiled;

  var element,
    scope;

  beforeEach(inject(function ($rootScope, $compile) {
    scope = $rootScope.$new();
    scope.credentials = {};
    element = angular.element('<form name="formTest"><input type="password" name="pass" ng-model="credentials.pass"><input type="password" name="confirm" ng-model="credentials.confirm" influunt-match="credentials.pass"></form>');
    element = $compile(element)(scope);
    scope.$apply();
  }));

  it('Deve acusar um erro de validação quando os campos não forem iguais', function() {
    scope.credentials.pass = 'test';
    scope.credentials.confirm = 'test1';
    scope.$apply();
    expect(Object.keys(scope.formTest.confirm.$error)).toContain('influuntMatch');
    expect(scope.formTest.confirm.$error.influuntMatch).toBeTruthy();

    scope.credentials.confirm = 'test';
    scope.$apply();
    expect(Object.keys(scope.formTest.confirm.$error)).not.toContain('influuntMatch');
  });


});
