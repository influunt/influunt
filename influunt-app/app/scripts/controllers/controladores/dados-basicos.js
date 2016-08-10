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

      $scope.$watchGroup(['objeto.todosEnderecos[0].localizacao', 'objeto.todosEnderecos[1].localizacao'], function(){
        var compeletaEndereco = _.isArray($scope.objeto.todosEnderecos) &&
          $scope.objeto.todosEnderecos[0].localizacao &&
          $scope.objeto.todosEnderecos[1].localizacao;

        if(compeletaEndereco) {
          $scope.objeto.nomeEndereco = $scope.objeto.todosEnderecos[0].localizacao + ' com ' + $scope.objeto.todosEnderecos[1].localizacao;
        } else {
          $scope.objeto.nomeEndereco = '';
        }
      });

    }]);
