'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:AreasCtrl
 * @description
 * # AreasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('AreasCtrl', ['$controller', '$scope',
    function ($controller, $scope) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('areas');
    }]);
