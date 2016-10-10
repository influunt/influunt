'use strict';

describe('Controller: RelatoriosCtrl', function () {

  var RelatoriosCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    RelatoriosCtrl = $controller('RelatoriosCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  xit('should attach a list of awesomeThings to the scope', function () {
    expect(RelatoriosCtrl.awesomeThings.length).toBe(3);
  });
});
