'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('MainCtrl', ['$scope', '$controller', '$http',
    function MainCtrl($scope, $controller, $http) {
      // Herda todo o comportamento de breadcrumbs.
      $controller('BreadcrumbsCtrl', {$scope: $scope});
      $controller('DatatablesCtrl', {$scope: $scope});

      $http.get('/json/menus.json').then(function(res) {
        $scope.menus = res.data;
      });
    }
  ]);
