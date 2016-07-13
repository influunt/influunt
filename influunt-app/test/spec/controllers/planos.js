'use strict';

describe('Controller: PlanosCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var PlanosCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    PlanosCtrl = $controller('PlanosCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(PlanosCtrl.awesomeThings.length).toBe(3);
  });
});
