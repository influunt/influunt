'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresDadosBasicosCtrl
 * @description
 * # ControladoresDadosBasicosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresDadosBasicosCtrl', ['$scope', '$controller', '$filter', 'influuntBlockui',
    function ($scope, $controller, $filter, influuntBlockui) {
      $controller('ControladoresCtrl', {$scope: $scope});

      $scope.inicializaWizardDadosBasicos = function() {
        return $scope.inicializaWizard().finally(influuntBlockui.unblock);
      };

      $scope.$watch('objeto.todosEnderecos', function(todosEnderecos) {
        if (!_.isArray(todosEnderecos)) { return false; }
        $scope.objeto.nomeEndereco = $filter('nomeEndereco')(todosEnderecos[0]);
      }, true);

      $scope.$watchGroup(['objeto.area.idJson', 'helpers.cidade.areas'], function (){
        $scope.currentSubareas = [];
        if ($scope.objeto && $scope.helpers && $scope.objeto.area && $scope.objeto.area.idJson){
          var area = _.find($scope.helpers.cidade.areas, {idJson: $scope.objeto.area.idJson});
          $scope.currentSubareas = area.subareas;
        }
        return $scope.currentSubareas;
      }, true);

      $scope.$watch('objeto.modelo', function(modelo) {
        $scope.modeloControlador = modelo;
      });

    }]);
