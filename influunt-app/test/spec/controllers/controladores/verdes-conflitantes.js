'use strict';

describe('Controller: ControladoresVerdesConflitantesCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var ControladoresVerdesConflitantesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ControladoresVerdesConflitantesCtrl = $controller('ControladoresVerdesConflitantesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ControladoresVerdesConflitantesCtrl.awesomeThings.length).toBe(3);
  });
});
