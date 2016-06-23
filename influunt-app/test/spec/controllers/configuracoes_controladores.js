'use strict';

describe('Controller: ConfiguracaoControladoresCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var ConfiguracaoControladoresCtrl, scope;

  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ConfiguracaoControladoresCtrl = $controller('ConfiguracaoControladoresCtrl', {$scope: scope});
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });
});
