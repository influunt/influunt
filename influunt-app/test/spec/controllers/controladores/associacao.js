'use strict';

describe('Controller: ControladoresAssociacaoCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var ControladoresAssociacaoCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ControladoresAssociacaoCtrl = $controller('ControladoresAssociacaoCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ControladoresAssociacaoCtrl.awesomeThings.length).toBe(3);
  });
});
