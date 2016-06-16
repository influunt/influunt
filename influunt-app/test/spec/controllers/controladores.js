'use strict';

describe('Controller: ControladoresCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var ControladoresCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ControladoresCtrl = $controller('ControladoresCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ControladoresCtrl.awesomeThings.length).toBe(3);
  });
});
