'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresDadosBasicosCtrl
 * @description
 * # ControladoresDadosBasicosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresDadosBasicosCtrl', ['$scope', '$controller',
    function ($scope, $controller) {
      $controller('ControladoresCtrl', {$scope: $scope});

      $scope.$watch('objeto.todosEnderecos', function(){
        if(_.isArray($scope.objeto.todosEnderecos)){
          var completaEndereco = $scope.objeto.todosEnderecos[0].localizacao && ($scope.objeto.todosEnderecos[0].localizacao2 || $scope.objeto.todosEnderecos[0].alturaNumerica);

          if(completaEndereco) {
            if($scope.objeto.todosEnderecos[0].localizacao2){
              $scope.objeto.nomeEndereco = $scope.objeto.todosEnderecos[0].localizacao + ' com ' + $scope.objeto.todosEnderecos[0].localizacao2;
            }else{
              $scope.objeto.nomeEndereco = $scope.objeto.todosEnderecos[0].localizacao + ', nº ' + $scope.objeto.todosEnderecos[0].alturaNumerica;
            }
          } else {
            $scope.objeto.nomeEndereco = '';
          }
        }
      }, true);
      
      $scope.$watchGroup(['objeto.area.idJson', 'helpers.cidade.areas'], function (){
        $scope.currentSubareas = [];
        if ($scope.objeto && $scope.helpers && $scope.objeto.area && $scope.objeto.area.idJson){
          var area = _.find($scope.helpers.cidade.areas, {idJson: $scope.objeto.area.idJson});
          $scope.currentSubareas = area.subareas;
        }
        return $scope.currentSubareas;
      }, true);
    }]);
