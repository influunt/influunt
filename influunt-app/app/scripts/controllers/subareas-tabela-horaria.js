'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:SubAreasCtrl
 * @description
 * # SubAreasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('SubareasTabelaHorariaCtrl', ['$controller', '$scope', 'Restangular',
    function ($controller, $scope, Restangular) {

      $controller('HistoricoCtrl', {$scope: $scope});

      // Tabela Horária
      $scope.init = function() {
        var subareaId = $scope.$state.params.id;
        $scope.inicializaResourceHistorico('subareas/' + subareaId);
        return Restangular.one('subareas', subareaId).customGET('tabela_horaria')
          .then(function(response) {
            if (response) {
              $scope.objeto = response.plain();
            } else {
              $scope.objeto = {
                versoesTabelasHorarias: [],
                tabelasHorarias: [],
                eventos: []
              };
            }
            $scope.podeInicializar = true;
          });
      };

      $scope.submitForm = function() {
        return $scope.submit($scope.objeto, 'app.subareas', 'tabela_horaria')
          .catch(function(err) { $scope.errors = err; });
      };

    }]);
