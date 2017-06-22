'use strict';

describe('Controller: RelatoriosStatusCtrl', function () {

  var RelatoriosStatusCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    RelatoriosStatusCtrl = $controller('RelatoriosStatusCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

});
