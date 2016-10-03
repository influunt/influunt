'use strict';

describe('Directive: select2', function () {

  // load the directive's module
  // beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('Deve criar uma instancia de um objeto select2', inject(function ($compile) {

    scope.lista = [{name: 'A'}, {name: 'B'}, {name: 'C'}];
    element = '<select select2 name="controladores" class="form-control" data-ng-options="option.name for option in lista" data-ng-model="objeto" multiple="multiple"></select>';
    element = angular.element(element);
    element = $compile(element)(scope);
    scope.$apply();

    expect(element).toBeDefined();
  }));
});
