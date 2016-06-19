'use strict';

describe('Directive: ichecks', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope,
    icheck;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  beforeEach(inject(function ($compile) {
    element = $compile(angular.element('<input type="checkbox" ichecks ng-model="test">'))(scope);
    scope.$apply();
  }));

  it('Deve criar um elemento ichecks sempre que o atributo icheck for adicionado a um checkbox', function() {
    expect(element).toBeDefined();
  });

  it('Deve trocar o estado da variavel settada em ng-model sempre que o usuário clicar no icheck', function() {
    element.click();
    expect(scope.test).toBe(true);

    element.click();
    expect(scope.test).toBe(false);
  });
});
