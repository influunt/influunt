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


      $scope.$watchGroup(['objeto.endereco', 'objeto.enderecos[0].localizacao', 'objeto.enderecos[1].localizacao'], function(){
        if(!!$scope.objeto.endereco && !!$scope.objeto.endereco.address_components) {
          $scope.objeto.enderecos[0].localizacao = $scope.objeto.endereco.address_components[0].long_name;
        }

      	if($scope.objeto.enderecos[0].localizacao && $scope.objeto.enderecos[1].localizacao) {
          $scope.objeto.nomeEndereco = $scope.objeto.enderecos[0].localizacao + ' com ' + $scope.objeto.enderecos[1].localizacao;
      	} else {
          $scope.objeto.nomeEndereco = '';
        }
      });

    }]);
