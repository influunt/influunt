'use strict';

describe('Controller: RelatoriosPlanosCtrl', function () {

  var RelatoriosPlanosCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    RelatoriosPlanosCtrl = $controller('RelatoriosPlanosCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

});
