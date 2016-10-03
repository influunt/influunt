'use strict';

describe('Controller: AuditoriasCtrl', function () {

  // load the controller's module
  // beforeEach(module('influuntApp'));

  var AuditoriasCtrl,
    scope;

  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AuditoriasCtrl = $controller('AuditoriasCtrl', {
      $scope: scope
    });
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });


});
