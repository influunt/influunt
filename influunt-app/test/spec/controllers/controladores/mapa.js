'use strict';

describe('Controller: ControladoresMapaCtrl', function () {

  // load the controller's module
  // beforeEach(module('influuntApp'));

  var ControladoresMapaCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ControladoresMapaCtrl = $controller('ControladoresMapaCtrl', {$scope: scope});

    scope.objeto = {todosEnderecos: [{}, {}]};
  }));
});
