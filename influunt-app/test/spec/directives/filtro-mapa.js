'use strict';

describe('Directive: filtroMapa', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<filtro-mapa></filtro-mapa>');
    element = $compile(element)(scope);
    expect(element).toBeDefined();
  }));
});