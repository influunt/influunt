'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ConfiguracaoControladoresCtrl
 * @description
 * # ConfiguracaoControladoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ConfiguracaoControladoresCtrl', ['$controller', '$scope',
    function ($controller, $scope) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('configuracoes_controladores');
    }]);
