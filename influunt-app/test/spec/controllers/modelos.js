'use strict';

describe('Controller: ModelosCtrl', function () {

  var ModelosCtrl,
    scope;

  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ModelosCtrl = $controller('ModelosCtrl', {
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

  it('Deve ter listar os limites', function() {
    var modelo = {
      limiteEstagio: 2,
      limiteGrupoSemaforico: 4,
      limiteAnel: 6,
      limiteDetectorPedestre: 8,
      limiteDetectorVeicular: 10,
      limiteTabelasEntreVerdes: 2,
      limitePlanos: 2
    };
    var texto = '<ul><li>modelos.limiteEstagio: 2</li><li>modelos.limiteGrupoSemaforico: 4</li><li>modelos.limiteAnel: 6</li><li>modelos.limiteDetectorPedestre: 8</li><li>modelos.limiteDetectorVeicular: 10</li><li>modelos.limiteTabelasEntreVerdes: 2</li><li>modelos.limitePlanos: 2</li></ul>';
    expect(scope.limitesList(modelo)).toBe(texto);
  });
});
