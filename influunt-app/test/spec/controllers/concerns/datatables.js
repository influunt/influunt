'use strict';

describe('Controller: DatatablesCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var DatatablesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DatatablesCtrl = $controller('DatatablesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('Deve definir as opções dos datatables', function() {
    expect(scope.dtOptions).toBeDefined();
  });
});
