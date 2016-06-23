'use strict';

describe('Controller: ModelosControladoresCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var ModelosControladoresCtrl, scope, $httpBackend, $q;

  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$q_) {
    scope = $rootScope.$new();
    $q = _$q_;
    $httpBackend = _$httpBackend_;

    scope.objeto = {};
    ModelosControladoresCtrl = $controller('ModelosControladoresCtrl', {$scope: scope});
  }));

  it('Deve conter as definições das funções de CRUD', function() {
    expect(scope.index).toBeDefined();
    expect(scope.show).toBeDefined();
    expect(scope.new).toBeDefined();
    expect(scope.save).toBeDefined();
    expect(scope.confirmDelete).toBeDefined();
  });

  it('Deve carregar a lista de fabricante e configuracoes de controladores antes de exibir dados', function() {
    var fabricantes = [{nome: 'fab1'}, {nome: 'fab2'}];
    var configuracoes = [{nome: 'conf1'}, {nome: 'fab2'}];

    $httpBackend.expectGET('/fabricantes').respond(fabricantes);
    $httpBackend.expectGET('/configuracoes_controladores').respond(configuracoes);
    scope.beforeShow();
    $httpBackend.flush();

    expect(scope.fabricantes.length).toBe(2);
    expect(scope.configuracoes.length).toBe(2);
  });
});
