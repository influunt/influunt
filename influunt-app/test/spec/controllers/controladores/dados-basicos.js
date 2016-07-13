'use strict';

describe('Controller: ControladoresDadosBasicosCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var ControladoresDadosBasicosCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ControladoresDadosBasicosCtrl = $controller('ControladoresDadosBasicosCtrl', {
      $scope: scope
    });
  }));

  it('Deve conter as definições de funções do ControladorCtrl', function() {
    expect(scope.inicializaWizard).toBeDefined();
  });

});
