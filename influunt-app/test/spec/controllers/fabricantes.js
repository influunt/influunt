'use strict';

describe('Controller: FabricantesCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var FabricantesCtrl, scope;

  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();

    scope.objeto = {};
    FabricantesCtrl = $controller('FabricantesCtrl', {$scope: scope});
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });

  it('Deve acrescentar um novo modelo à fabricante', function() {
    scope.adicionarModelo();
    expect(scope.objeto.modelos.length).toBe(1);
  });

  it('Qualquer ação de remover modelos não existentes na lista deverá ser interrompida.', function() {
    // tentativa de remover elemento de uma lista inexistente.
    scope.removerModelo(0);
    expect(scope.objeto.modelos).toBeUndefined();

    // tentativa de remover elemento com indice maior que a quantidade de elementos criados.
    scope.adicionarModelo();
    scope.removerModelo(10);
    expect(scope.objeto.modelos.length).toBe(1);

    // tentativa de remover elemento com indice maior que a quantidade de elementos criados.
    scope.removerModelo(-1);
    expect(scope.objeto.modelos.length).toBe(1);
  });

  it('Deve remover um modelo da lista do fabricante', function() {
    scope.adicionarModelo();
    scope.removerModelo(0);
    expect(scope.objeto.modelos.length).toBe(0);
  });

});
