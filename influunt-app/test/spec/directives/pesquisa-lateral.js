'use strict';

describe('Directive: pesquisaLateral', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<pesquisa-lateral></pesquisa-lateral>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the pesquisaLateral directive');
  }));
});
