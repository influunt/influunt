'use strict';

describe('Controller: MainCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var MainCtrl,
    scope;

  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    MainCtrl = $controller('MainCtrl', {
      $scope: scope
    });
  }));

  it('Deve possuir as definições do controller de breadcrumbs', function() {
    expect(scope.udpateBreadcrumbs).toBeDefined();
  });

  it('Deve possuir as definições do controller de datatables', function() {
    expect(scope.datatables.options).toBeDefined();
  });

  it('deve carregar os menus a partir da lista fornecida', inject(function ($httpBackend) {
    $httpBackend.expectGET('/json/menus.json').respond({});
    $httpBackend.flush();

    expect(scope.menus).toBeDefined();
  }));
});
