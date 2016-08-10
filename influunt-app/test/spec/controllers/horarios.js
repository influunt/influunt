'use strict';

describe('Controller: HorariosCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var HorariosCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    HorariosCtrl = $controller('HorariosCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

});
