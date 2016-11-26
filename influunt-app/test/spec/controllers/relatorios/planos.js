'use strict';

describe('Controller: RelatoriosPlanosCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

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

  it('should attach a list of awesomeThings to the scope', function () {
    expect(RelatoriosPlanosCtrl.awesomeThings.length).toBe(3);
  });
});
