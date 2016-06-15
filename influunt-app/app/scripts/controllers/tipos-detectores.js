'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:TiposDetectoresCtrl
 * @description
 * # TiposDetectoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('TiposDetectoresCtrl', ['$controller', '$scope',
    function ($controller, $scope) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('tipos_detectores');
    }]);
