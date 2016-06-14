'use strict';

describe('Controller: CrudCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var CrudCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CrudCtrl = $controller('CrudCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(CrudCtrl.awesomeThings.length).toBe(3);
  });
});
