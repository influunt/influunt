'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ModeloControladoresCtrl
 * @description
 * # ModeloControladoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ModelosControladoresCtrl', ['$controller', '$scope', 'Restangular',
    function ($controller, $scope, Restangular) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('modelos_controladores');
			
      /**
       * Recupera a lista de cidades que podem ser relacionadas à área.
       */
      $scope.beforeShow = function() {

        Restangular.all('fabricantes').getList().then(function(res) {
          $scope.fabricantes = res;
        });
				
        Restangular.all('configuracoes_controladores').getList().then(function(res) {
          $scope.configuracoes = res;
        });
      };
			
    }]);
