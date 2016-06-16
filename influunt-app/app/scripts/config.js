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

        .state('app.wizard', {
          url: '/wizard',
          templateUrl: 'views/controladores/dados.html',
          controller: 'ControladoresCtrl',
          controllerAs: 'controladores',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.controladores', {
          url: '/controladores',
          templateUrl: 'views/controladores/wizard/wizard.html',
          controller: 'ControladoresCtrl',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.controladores.dados_basicos', {
          url: '/dados-basicos',
          templateUrl: 'views/controladores/wizard/dados-basicos.html',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.controladores.aneis', {
          url: '/aneis',
          templateUrl: 'views/controladores/wizard/aneis.html',
          data: {
            title: 'controladores.titulo'
          }
        })

        .state('app.controladores.verdes_conflitantes', {
          url: '/verdes-conflitantes',
          templateUrl: 'views/controladores/wizard/verdes-conflitantes.html',
          data: {
            title: 'controladores.titulo'
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
