'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresRevisaoCtrl
 * @description
 * # ControladoresRevisaoCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
 .controller('ControladoresRevisaoCtrl', ['$scope', '$state', '$controller', 'assertControlador',
    function ($scope, $state, $controller, assertControlador) {
      $controller('ControladoresCtrl', {$scope: $scope});


      //controladores.dadosBasicos
      /**
       * Pré-condições para acesso à tela de revisao: Somente será possível acessar esta
       * tela se o objeto possuir estágios. Os estágios são informados no passo anterior, o
       * passo de aneis.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertRevisao = function() {
        var valid = assertControlador.hasAneis($scope.objeto) && assertControlador.hasEstagios($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.revisao', {id: $scope.objeto.id});
        }
        return valid;
      };

      /**
       * Carrega os dados basicos na tela de revisao : xxxxxxxx.
       *
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.inicializaRevisao = function() {
        console.log(" Revisao "+ $scope.assertRevisao());
         /*return $scope.inicializaWizard().then(function() {
            if ($scope.assertRevisao()) {
             $scope.objeto.cidade = "TESTE";
             console.log("TEste");
            }

         });*/
      };



  }]);
