'use strict';

describe('Controller: ControladoresCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var ControladoresCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ControladoresCtrl = $controller('ControladoresCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });
});
