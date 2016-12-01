'use strict';

describe('Controller: RelatoriosLogcontroladoresCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var RelatoriosLogcontroladoresCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    RelatoriosLogcontroladoresCtrl = $controller('RelatoriosLogcontroladoresCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(RelatoriosLogcontroladoresCtrl.awesomeThings.length).toBe(3);
  });
});
