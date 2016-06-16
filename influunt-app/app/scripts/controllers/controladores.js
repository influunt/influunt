'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresCtrl
 * @description
 * # ControladoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresCtrl', ['$controller', '$scope', 'Restangular',
    function ($controller, $scope, Restangular) {

      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('controladores');
      $scope.hideRemoveCoordenada = true;

      /**
       * Carrega as listas de dependencias dos controladores. Atua na tela de crud.
       */
      $scope.beforeShow = function() {
        Restangular.all('areas').getList().then(function(res) {
          $scope.areas = res;
        });
      };

      /**
       * Inicializa o objeto de coordenadas do controlador, caso este ainda não
       * tenha sido definido. Atua na tela de crud.
       */
      $scope.afterShow = function() {
        var coordenadaDefault = {
          latitude: null,
          longitude: null
        };

        $scope.objeto.coordenada = $scope.objeto.coordenada || coordenadaDefault;
        $scope.coordenada = $scope.objeto.coordenada;
      };

      $scope.initWizard = function() {
        Restangular
          .all('helpers')
          .all('controlador')
          .customGET()
          .then(function(res) {
            $scope.data = res;
          });

        $scope.objeto = {};
        $scope.helpers = {};
      };

      $scope.submitDadosBasicos = function() {
        $scope
          .save()
          .then(function() {
            $state.go('app.wizard_controladores.aneis');
          })
          .catch(function() {

          });
      };
    }]);
