'use strict';

describe('Controller: CrudCtrl', function () {

  // ad the controller's module
  beforeEach(module('influuntApp', function(RestangularProvider) {
    RestangularProvider.setBaseUrl('');
  }));

  var CrudCtrl,
    scope,
    resourceList,
    resourceObj,
    httpBackend,
    $q;

  beforeEach(inject(function ($controller, $rootScope, $httpBackend, _$q_) {
    scope = $rootScope.$new();
    CrudCtrl = $controller('CrudCtrl', {
      $scope: scope,
      $state: {
        params: {id: 'id'},
        go: function(){}
      }
    });

    $q = _$q_;

    httpBackend = $httpBackend;

    resourceList = {
      data: [
        {attr1: 'attr1'},
        {attr2: 'attr2'}
      ]
    };

    resourceObj = {id: 'id', attr1: 'attr1'};
    scope.inicializaNovoCrud('resource');
  }));

  it('Quando o index é executado, deve ser retornada uma lista de objetos do resource', function() {
    var url = '/resource?page=0&per_page=30&sort=&sort_type=desc'
    httpBackend.expectGET(url).respond(resourceList);
    scope.index();
    httpBackend.flush();
    expect(scope.lista.length).toBe(2);
  });

  it('Quando o show é executado, deve retornar um objeto do resource', function() {
    httpBackend.expectGET('/resource/id').respond(resourceObj);
    scope.show();
    httpBackend.flush();
    expect(typeof scope.objeto).toBe('object');
  });

  it('Quando o show é executado mas não retorna nada, o objeto não deve ser definido', function() {
    httpBackend.expectGET('/resource/id').respond(null);
    scope.show();
    httpBackend.flush();
    expect(scope.objeto).not.toBeDefined();
  });

  it('Deve criar um objeto vazio ao executar a rota de new', function() {
    scope.new();
    expect(scope.objeto).toEqual({});
  });

  it('Não deverá salvar dado algum caso o formulário seja inválido', function() {
    spyOn(scope, 'create');
    spyOn(scope, 'update');

    var formValido = false;
    scope.save(formValido);

    expect(scope.create).not.toHaveBeenCalled();
    expect(scope.update).not.toHaveBeenCalled();
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
