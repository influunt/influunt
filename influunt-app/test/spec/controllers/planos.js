'use strict';

describe('Controller: PlanosCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var PlanosCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    PlanosCtrl = $controller('PlanosCtrl', {
      $scope: scope
    });
  }));

});
