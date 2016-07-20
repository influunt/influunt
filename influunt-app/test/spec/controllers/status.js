'use strict';

describe('Controller: StatusCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var StatusCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    StatusCtrl = $controller('StatusCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  
});
