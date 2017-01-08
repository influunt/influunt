'use strict';

describe('Controller: ImporConfigCtrl', function() {
  var ImporConfigCtrl,
    scope,
    $httpBackend,
    $state,
    $controller;

  beforeEach(inject(function(_$controller_, $rootScope, _$httpBackend_, _$state_) {
    $httpBackend = _$httpBackend_;
    $state = _$state_;
    scope = $rootScope.$new();
    $controller = _$controller_;
    ImporConfigCtrl = $controller('ImporConfigCtrl', { $scope: scope });
  }));

  describe('query automática via query string', function() {
    it('deve buscar na API o status do anel passado na query string', function() {
      $httpBackend.expectGET('/controladores/imposicao?page=0&per_page=30&sort_type=asc&status_eq=NORMAL').respond({ data: [] });
      $httpBackend.expectGET('/monitoramento/status_aneis').respond({});
      $state.go('app.impor_config', { status: 'NORMAL' });
      _.set(scope, 'pesquisa.filtro.status.tipoCampo', 'select');
      scope.$apply();
      scope.index();
      $httpBackend.flush();
    });

    it('deve buscar todos os controladores se query string não tiver status', function() {
      $httpBackend.expectGET('/controladores/imposicao?page=0&per_page=30&sort_type=asc').respond({ data: [] });
      $httpBackend.expectGET('/monitoramento/status_aneis').respond({});
      $state.go('app.impor_config');
      scope.$apply();
      scope.index();
      $httpBackend.flush();
    });
  });
});
