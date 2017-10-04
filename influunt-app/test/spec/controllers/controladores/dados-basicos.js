'use strict';

describe('Controller: ControladoresDadosBasicosCtrl', function () {

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

  it('Deve criar o texto em "nomeEndereco" associando os nomes do "endereco 1" "x" "endereco 2"',
    inject(function($q, CETLocalizacaoService) {
      scope.objeto.todosEnderecos[0].localizacao = 'endereco 1';
      scope.objeto.todosEnderecos[0].localizacao2 = 'endereco 2';
      scope.enderecoControladorIndex = 0;

      var deferred = $q.defer();
      spyOn(CETLocalizacaoService, 'atualizaLatLngPorEndereco').and.returnValue(deferred.promise);
      deferred.resolve({lat: 1, lng: 2});

      scope.$apply();
      expect(CETLocalizacaoService.atualizaLatLngPorEndereco).toHaveBeenCalled();
      expect(scope.objeto.nomeEndereco).toBe('endereco 1 x endereco 2');
    }));

  it('Deve criar o texto em "nomeEndereco" mesmo quando o endereço não está na posição 0',
    inject(function($q, CETLocalizacaoService) {
      scope.objeto.todosEnderecos[1].localizacao = 'endereco 1';
      scope.objeto.todosEnderecos[1].localizacao2 = 'endereco 2';
      scope.enderecoControladorIndex = 1;

      var deferred = $q.defer();
      spyOn(CETLocalizacaoService, 'atualizaLatLngPorEndereco').and.returnValue(deferred.promise);
      deferred.resolve({lat: 1, lng: 2});

      scope.$apply();
      expect(scope.objeto.nomeEndereco).toBe('endereco 1 x endereco 2');
    }));
});
