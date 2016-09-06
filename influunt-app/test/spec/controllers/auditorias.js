'use strict';

describe('Controller: AuditoriasCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var AuditoriasCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AuditoriasCtrl = $controller('AuditoriasCtrl', {
      $scope: scope
    });
  }));


});
