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
  .module('influuntApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ui.router',
    'ngSanitize',
    'ngTouch',
    'oc.lazyLoad',
    'ui.bootstrap',
  ])
  .config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider',
    function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

      $ocLazyLoadProvider.config({
        // Set to true if you want to see what and when is dynamically loaded
        debug: false
      });

      $urlRouterProvider.otherwise("/index/main");

      $stateProvider
        .state('index', {
          abstract: true,
          url: "/index",
          templateUrl: "views/common/content.html",
          controller: 'MainCtrl',
          controllerAs: 'main'
        })
        .state('index.main', {
          url: "/main",
          templateUrl: "views/main.html",
          data: { pageTitle: 'Example view' },
        })
        .state('index.minor', {
          url: "/minor",
          templateUrl: "views/minor.html",
          data: { pageTitle: 'Example view' }
        });
    }])
    .run(['$rootScope', '$state', function($rootScope, $state) {
      $rootScope.$state = $state;
    }]);
