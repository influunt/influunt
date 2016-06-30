'use strict';

describe('Controller: PermissoesCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var PermissoesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    PermissoesCtrl = $controller('PermissoesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(PermissoesCtrl.awesomeThings.length).toBe(3);
  });
});
