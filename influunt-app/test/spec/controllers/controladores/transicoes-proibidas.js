'use strict';

describe('Controller: ControladoresTransicoesProibidasCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var ControladoresTransicoesProibidasCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ControladoresTransicoesProibidasCtrl = $controller('ControladoresTransicoesProibidasCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ControladoresTransicoesProibidasCtrl.awesomeThings.length).toBe(3);
  });
});
