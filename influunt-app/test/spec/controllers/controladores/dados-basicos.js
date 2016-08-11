'use strict';

describe('Controller: ControladoresDadosBasicosCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp'));

  var ControladoresDadosBasicosCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ControladoresDadosBasicosCtrl = $controller('ControladoresDadosBasicosCtrl', {
      $scope: scope
    });

    scope.objeto = {todosEnderecos: [{}, {}]};
  }));

  it('Deve conter as definições de funções do ControladorCtrl', function() {
    expect(scope.inicializaWizard).toBeDefined();
  });

  it('Deve criar o texto em "nomeEndereco" associando os nomes do "endereco 1" "com" "endereco 2"', function() {
    scope.objeto.todosEnderecos[0].localizacao = 'endereco 1';
    scope.objeto.todosEnderecos[1].localizacao = 'endereco 2';
    scope.$apply();
    expect(scope.objeto.nomeEndereco).toBe('endereco 1 com endereco 2');
  });

  it('"nomeEndereco" deve ser vazio se não houver "endereco 1" ou "endereco 2"', function() {
    scope.objeto.todosEnderecos[1].localizacao = 'endereco 2';
    scope.$apply();
    expect(scope.objeto.nomeEndereco).toBe('');
  });

});
