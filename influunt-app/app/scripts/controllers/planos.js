'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:PlanosCtrl
 * @description
 * # PlanosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('PlanosCtrl', ['$scope', '$state', 'Restangular',
    function ($scope, $state, Restangular) {

      $scope.min = 10;
      $scope.max = 100;

      /**
       * Inicializa a tela de planos. Carrega os dados básicos da tela.
       */
      $scope.init = function() {
        var id = $state.params.id;
        Restangular.one('controladores', id).get().then(function(res) {
          $scope.objeto = res;
          $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);

          $scope.objeto.aneis.forEach(function(anel) {
            anel.planos = anel.planos || [{ posicao: 1 }];

            // @todo temporario. A posicao do estagio deverá vir da API.
            anel.estagios.forEach(function(estagio, index) {
              estagio.posicao = index + 1;
            });

          });

          $scope.selecionaAnel(0);
        });
      };

      /**
       * Adiciona um novo plano à lista de planos dos aneis. Após a criação do novo
       * plano, este deverá ser colocado em edição imediatamente.
       */
      $scope.adicionaPlano = function() {
        $scope.objeto.aneis.forEach(function(anel) {
          var posicao = anel.planos.length + 1;
          var plano = {posicao: posicao};
          anel.planos.push(plano);
        });

        // Depois de criar um novo conjunto de planos, deve coloca-lo em edição
        // imediatamente.
        $scope.selecionaPlano($scope.currentAnel.planos.length - 1);
      };

      $scope.selecionaAnel = function(index) {
        $scope.currentAnelIndex = index;
        $scope.currentAnel = $scope.objeto.aneis[index];
        $scope.selecionaPlano(0);
        $scope.selecionaEstagio(0);
      };

      $scope.selecionaPlano = function(index) {
        $scope.currentPlanoIndex = index;
        $scope.currentPlano = $scope.currentAnel.planos[index];
      };

      $scope.selecionaEstagio = function(index) {
        $scope.currentEstagioIndex = index;
        $scope.currentEstagio = $scope.currentAnel.estagios[index];
      };

    }]);
