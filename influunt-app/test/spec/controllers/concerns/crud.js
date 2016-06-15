'use strict';

describe('Controller: CrudCtrl', function () {
  var CrudCtrl,
    scope,
    resourceList,
    resourceObj,
    httpBackend,
    $q,
    toast;

  // ad the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider, $provide) {
    RestangularProvider.setBaseUrl('');
    $provide.factory('toast', function() {
      return {
        error: function() {},
        success: function() {},
      }
    });
  }));


  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $httpBackend, _$q_, _toast_) {
    scope = $rootScope.$new();
    CrudCtrl = $controller('CrudCtrl', {
      $scope: scope,
      $state: {
        params: {id: 'id'},
        go: function(){}
      }
    });

    $q = _$q_;

    toast = _toast_;
    spyOn(toast, 'error');

    httpBackend = $httpBackend;

    resourceList = [
      {attr1: 'attr1'},
      {attr2: 'attr2'}
    ];

    resourceObj = {id: 'id', attr1: 'attr1'};
    scope.inicializaNovoCrud('resource');
  }));

  it('Quando o index é executado, deve ser retornada uma lista de objetos do resource', function() {
    httpBackend.expectGET('/resource').respond(resourceList);
    scope.index();
    httpBackend.flush();
    expect(scope.lista.length).toBe(2);
  });

  it('Quando o index é executado e houver um erro na api, uma mensagem de erro deve ser apresentada', function() {
    httpBackend.expectGET('/resource').respond(500, {});
    scope.index();
    httpBackend.flush();
    expect(toast.error).toHaveBeenCalled();
  });

  it('Quando o show é executado, deve retornar um objeto do resource', function() {
    httpBackend.expectGET('/resource/id').respond(resourceObj);
    scope.show();
    httpBackend.flush();
    expect(typeof scope.objeto).toBe('object');
  });

  it('Deve criar um objeto vazio ao executar a rota de new', function() {
    scope.new();
    expect(scope.objeto).toEqual({});
  });

  it('Deve executar o método de "create" quando se salva um objeto sem id', function() {
    spyOn(scope, 'create').and.callFake(function() {
      var deferred = $q.defer();
      deferred.resolve('teste');
      return deferred.promise;
    });

    scope.objeto = {attr: 1};
    scope.save();
    expect(scope.create).toHaveBeenCalled();
  });

  it('Deve executar o método "update" quando se salva um objeto com id', function() {
    spyOn(scope, 'update').and.callFake(function() {
      var deferred = $q.defer();
      deferred.resolve('teste');
      return deferred.promise;
    });

    scope.objeto = {id: 'teste', attr: 1};
    scope.save();
    expect(scope.update).toHaveBeenCalled();
  });

  afterEach(function() {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });
});
