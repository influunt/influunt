'use strict';

/**
 * @ngdoc function
 * @name influuntApp:HorariosCtrl
 * @description
 * # HorariosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('HorariosCtrl', ['$scope', '$controller',
    function ($scope, $controller) {
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('horarios');

    }]);
