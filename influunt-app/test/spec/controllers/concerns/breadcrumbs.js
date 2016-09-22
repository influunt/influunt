'use strict';

describe('Controller: BreadcrumbsCtrl', function () {

  // load the controller's module
  beforeEach(module('influuntApp', function($provide) {
    var breadcrumbs = function() {
      return {
        path: function() {
          return [];
        }
      };
    };

    $provide.factory('breadcrumbs', breadcrumbs);
  }));

  var BreadcrumbsCtrl,
    scope,
    $state,
    httpBackend,
    controlador;


  beforeEach(inject(function ($controller, $rootScope, _$state_, $httpBackend) {
    $state = _$state_;
    scope = $rootScope.$new();
    BreadcrumbsCtrl = $controller('BreadcrumbsCtrl', {
      $scope: scope
    });

    httpBackend = $httpBackend;
    controlador = { nomeEndereco: "Av Teste 1" };

  }));

  it('Deve atualizar os breadcrumbs sempre que a rota atual for alterada', function() {
    spyOn(scope, 'udpateBreadcrumbs');

    var state = $state.get('app.main');
    scope.$broadcast('$stateChangeSuccess', state);
    expect(scope.udpateBreadcrumbs).toHaveBeenCalled();
  });

  it('Deve utilizar o state "current" se não for passado um state por parametro', function() {
    $state.current = {
      data: {
        title: 'teste'
      }
    };

    scope.udpateBreadcrumbs();
    expect(scope.pageTitle).toBe($state.current.data.title);
  });

  it('O titulo da página deve ser igual ao campo de titulo parametrizado no objeto "data" do state', function() {
    var state = {
      data: {
        title: 'teste'
      }
    };

    scope.udpateBreadcrumbs(state);
    expect(scope.pageTitle).toBe(state.data.title);
  });

  it('Deve buscar o endereço do controlador caso esteja no controller correto', function() {
    var state = {
      controller: 'Controladores'
    };

    httpBackend.expectGET('/controladores').respond(controlador);
    scope.setControladorEndereco(state);
    httpBackend.flush();
    expect(scope.controladorLocalizacao).toEqual(controlador.nomeEndereco);
  });

  it('Deve atualizar o titulo da pagina para "geral.titulo_padrao" se não houver titulo parametrizado', function() {
    var state = {
      name: 'app.main'
    };

    scope.udpateBreadcrumbs(state);
    expect(scope.pageTitle).toBe('geral.titulo_padrao');
  });
});
