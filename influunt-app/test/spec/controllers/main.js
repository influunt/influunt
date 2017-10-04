'use strict';

describe('Controller: MainCtrl', function () {

  var MainCtrl,
    scope;

  beforeEach(inject(function ($controller, $rootScope, $httpBackend) {
    scope = $rootScope.$new();
    MainCtrl = $controller('MainCtrl', {
      $scope: scope
    });

    $httpBackend.expectGET('/json/menus.json').respond({});
    $httpBackend.expectGET(/\/monitoramento\/status_controladores\?fim_intervalo\=\d+\&inicio_intervalo\=\d+/).respond({});
    $httpBackend.expectGET('/usuarios/alarmes_e_falhas').respond([]);
    $httpBackend.flush();
  }));

  it('Deve possuir as definições do controller de breadcrumbs', function() {
    expect(scope.udpateBreadcrumbs).toBeDefined();
  });

  it('deve carregar os menus a partir da lista fornecida', function () {
    expect(scope.menus).toBeDefined();
  });

  describe('Função de sair', function () {
    var deferred;
    beforeEach(inject(function(influuntAlert, $q) {
      // Mocks a call for confirm method and calls the promise function.
      deferred = $q.defer();
      localStorage.setItem('token', 123);
      spyOn(influuntAlert, 'confirm').and.returnValue(deferred.promise);
    }));

    it('Se o usuário confirmar a saída, sua credencial removida', inject(function ($httpBackend) {
      $httpBackend.expectDELETE('/logout/123').respond({});
      scope.sair();
      deferred.resolve(true);
      $httpBackend.flush();
      scope.$apply();
      expect(localStorage.token).not.toBeDefined();
    }));

    it('Se o usuário cancelar, sua credencial permanecerá válida', function() {
      scope.sair();
      deferred.resolve(false);
      scope.$apply();
      expect(localStorage.token).toBeDefined();
    });

  });
});
