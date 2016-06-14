'use strict';

describe('Controller: AreasCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var AreasCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AreasCtrl = $controller('AreasCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(AreasCtrl.awesomeThings.length).toBe(3);
  });
});
