'use strict';

describe('Directive: passwordMatch', function () {

  var element,
    scope;

  beforeEach(inject(function ($compile, $rootScope) {
    scope = $rootScope.$new();
    scope.data = {};

    element = '<form name="teste"><input name="pass" ng-model="data.myModel" password-match="data.myMatcher"></form>';
    element = $compile(element)(scope);
  }));

  it('Deve invalidar o campo se o ng-model for diferente do matcher.', function() {
    scope.data.myModel = 'myModel';
    scope.data.myMatcher = 'myMatcher';
    scope.$apply();

    expect(scope.teste.pass.$error.passwordMatch).toBeTruthy();
  });

  it('Deve validar o campo se o ng-model for igual ao matcher.', function() {
    scope.data.myModel = 'myModel';
    scope.data.myMatcher = 'myModel';
    scope.$apply();

    expect(scope.teste.pass.$error.passwordMatch).not.toBeTruthy();
  });
});
