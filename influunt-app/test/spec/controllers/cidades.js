'use strict';

describe('Controller: CidadesCtrl', function () {
  // load the controller's module
  beforeEach(module('influuntApp'));

  var CidadesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CidadesCtrl = $controller('CidadesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('deve criar um objeto de cidade quando a rota de new for chamada', function() {
    scope.new();
    expect(scope.cidade).toBeDefined();
  });
});
