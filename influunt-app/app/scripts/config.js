'use strict';

/**
 * @ngdoc overview
 * @name influuntApp
 * @description
 * # influuntApp
 *
 * Main module of the application.
 */
angular
  .module('influuntApp')
  .config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {

      $urlRouterProvider.otherwise('/app/main');

      $stateProvider
        .state('login', {
          url: '/login',
          templateUrl: 'views/login/signin.html',
          controller: 'LoginCtrl',
          controllerAs: 'login'
        })
        .state('app', {
          abstract: true,
          url: '/app',
          templateUrl: 'views/common/content_top_navigation.html',
          controller: 'MainCtrl',
          controllerAs: 'main',
          data: {
            title: 'geral.dashboard',
            redirectTo: 'app.main'
          }
        })
        .state('app.main', {
          url: '/main',
          templateUrl: 'views/main.html'
        })

        // Crud Cidades.
        .state('app.cidades', {
          url: '/cidades',
          templateUrl: 'views/cidades/index.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo'
          }
        })

        .state('app.cidades_new', {
          url: '/cidades/new',
          templateUrl: 'views/cidades/new.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo'
          }
        })

        .state('app.cidades_edit', {
          url: '/cidades/:id/edit',
          templateUrl: 'views/cidades/edit.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo'
          }
        })

        .state('app.cidades_show', {
          url: '/cidades/:id',
          templateUrl: 'views/cidades/show.html',
          controller: 'CidadesCtrl',
          controllerAs: 'cidades',
          data: {
            title: 'cidades.titulo'
          }
        })

        // Crud areas.
        .state('app.areas', {
          url: '/areas',
          templateUrl: 'views/areas/index.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo'
          }
        })

        .state('app.areas_new', {
          url: '/areas/new',
          templateUrl: 'views/areas/new.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo'
          }
        })

        .state('app.areas_edit', {
          url: '/areas/:id/edit',
          templateUrl: 'views/areas/edit.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo'
          }
        })

        .state('app.areas_show', {
          url: '/areas/:id',
          templateUrl: 'views/areas/show.html',
          controller: 'AreasCtrl',
          controllerAs: 'areas',
          data: {
            title: 'areas.titulo'
          }
        })

        // Crud controladores.
        .state('app.controladores', {
          url: '/controladores',
          templateUrl: 'views/controladores/index.html',
          controller: 'ControladoresCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.controladores_new', {
          url: '/controladores/new',
          templateUrl: 'views/controladores/new.html',
          controller: 'ControladoresCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.controladores_edit', {
          url: '/controladores/:id/edit',
          templateUrl: 'views/controladores/edit.html',
          controller: 'ControladoresCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.controladores_show', {
          url: '/controladores/:id',
          templateUrl: 'views/controladores/show.html',
          controller: 'ControladoresCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'controladores.titulo'
          }
        })

        // Crud tipos detectores.
        .state('app.tipos_detectores', {
          url: '/tipos_detectores',
          templateUrl: 'views/tipos_detectores/index.html',
          controller: 'TiposDetectoresCtrl',
          controllerAs: 'tipos_detectores',
          data: {
            title: 'tipos_detectores.titulo'
          }
        })

        .state('app.tipos_detectores_new', {
          url: '/tipos_detectores/new',
          templateUrl: 'views/tipos_detectores/new.html',
          controller: 'TiposDetectoresCtrl',
          controllerAs: 'tipos_detectores',
          data: {
            title: 'tipos_detectores.titulo'
          }
        })

        .state('app.tipos_detectores_edit', {
          url: '/tipos_detectores/:id/edit',
          templateUrl: 'views/tipos_detectores/edit.html',
          controller: 'TiposDetectoresCtrl',
          controllerAs: 'tipos_detectores',
          data: {
            title: 'tipos_detectores.titulo'
          }
        })

        .state('app.tipos_detectores_show', {
          url: '/tipos_detectores/:id',
          templateUrl: 'views/tipos_detectores/show.html',
          controller: 'TiposDetectoresCtrl',
          controllerAs: 'tipos_detectores',
          data: {
            title: 'tipos_detectores.titulo'
          }

        .state('app.dados', {
          url: '/dados',
          templateUrl: 'views/dados.html',
          data: {
            title: 'Example view'
          }
        })

        // Rotas de teste. Podem ser removidas em breve.
        .state('index', {
          abstract: true,
          url: '/index',
          templateUrl: 'views/common/content.html',
          controller: 'MainCtrl',
          controllerAs: 'main'
        })
        .state('index.main', {
          url: '/main',
          templateUrl: 'views/main.html',
          data: {pageTitle: 'Example view'}
        })
        .state('index.minor', {
          url: '/minor',
          templateUrl: 'views/minor.html',
          data: {pageTitle: 'Example view'}
        });
    }])
    .run(['$rootScope', '$state', function($rootScope, $state) {
      $rootScope.$state = $state;
    }]);
