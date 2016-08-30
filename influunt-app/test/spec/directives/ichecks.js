'use strict';

describe('Directive: ichecks', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  beforeEach(inject(function ($compile) {
    element = angular.element('<input type="checkbox" ichecks ng-model="test">');
    element = $compile(element)(scope);
    scope.$apply();
  }));

  it('Deve criar um elemento ichecks sempre que o atributo icheck for adicionado a um checkbox', function() {
    expect(element).toBeDefined();
  });

  it('Deve criar um checkbox disabled se isDisabled for true', inject(function($compile, $timeout) {
    element = angular.element('<input type="checkbox" ichecks ng-model="test" is-disabled="true">');
    element = $compile(element)(scope);
    scope.$apply();

    $timeout.flush();
    $timeout.verifyNoPendingTasks();
    $timeout(function() {
      expect(element.is(':disabled')).toBe(true);
    }, 1000);
  }));

  it('Deve criar um checkbox enabled se isDisabled for false', inject(function($compile, $timeout) {
    element = angular.element('<input type="checkbox" ichecks ng-model="test" is-disabled="false">');
    element = $compile(element)(scope);
    scope.$apply();

    $timeout.flush();
    $timeout.verifyNoPendingTasks();

    expect(element.is(':disabled')).toBe(false);
  }));

  it('Deve trocar o estado da variavel settada em ng-model sempre que o usuário clicar no icheck', function() {
    element.trigger('click');
    expect(scope.test).toBe(true);
  });
});
