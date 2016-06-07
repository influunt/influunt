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

      $urlRouterProvider.otherwise('/index/main');

      $stateProvider
        .state('login', {
          url: '/login',
          templateUrl: 'views/login/signin.html',
          controller: 'LoginCtrl',
          controllerAs: 'login'
        })
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
