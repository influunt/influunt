'use strict';

describe('Directive: ichecks', function () {

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
    $timeout.flush();
    scope.$apply();

    expect(element.is(':disabled')).toBe(true);
  }));

  it('Deve criar um checkbox enabled se isDisabled for false', inject(function($compile, $timeout) {
    element = angular.element('<input type="checkbox" ichecks ng-model="test" is-disabled="false">');
    element = $compile(element)(scope);
    $timeout.flush();
    scope.$apply();

    expect(element.is(':disabled')).toBe(false);
  }));

  it('Deve trocar o estado da variavel settada em ng-model sempre que o usuário clicar no icheck', function() {
    element.trigger('click');
    expect(scope.test).toBe(true);
  });
  
  it('Testa a função checked do icheck', inject(function($compile, $timeout) {
    scope.checked = function(){};
    scope.unchecked = function(){};
    spyOn(scope, 'checked');
    spyOn(scope, 'unchecked');
    element = angular.element('<input type="checkbox" ichecks ng-model="test" if-checked="checked()" if-unchecked="unchecked()" >');
    element = $compile(element)(scope);
    $timeout.flush();
    scope.$apply();
    
    $(element).iCheck('check');
    $timeout.flush();
    expect(scope.checked).toHaveBeenCalled();

    $(element).iCheck('uncheck');
    $timeout.flush();
    expect(scope.unchecked).toHaveBeenCalled();
  }));
});
