'use strict';

describe('Directive: influuntMap', function () {

  // load the directive's module
  beforeEach(module('influuntApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope, $compile) {
    scope = $rootScope.$new();

    scope.latitude = 42;
    scope.longitude = 42;

    element = '<div influunt-map latitude="latitude" longitude="longitude" />';
    element = $compile(element)(scope);
    scope.$digest();
  }));

  it('Deve criar um objeto de mapa', function() {
    expect(element.find('.leaflet-objects-pane').length).toBe(1);
  });
});
