'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresAssociacaoDetectoresCtrl
 * @description
 * # ControladoresAssociacaoDetectoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresAssociacaoDetectoresCtrl', ['$scope', '$state', '$controller', 'assertControlador',
    function ($scope, $state, $controller, assertControlador) {
      $controller('ControladoresCtrl', {$scope: $scope});

      // métodos privados.
      var criarDetectores;

      /**
       * Pré-condições para acesso à tela de associcao de detectores: Somente será possivel
       * continuar nesta tela se o controlador já possuir estágios e aneis.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertAssociacaoDetectores = function() {
        var valid = assertControlador.hasAneis($scope.objeto) && assertControlador.hasEstagios($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.transicoes_proibidas', {id: $scope.objeto.id});
        }

        return valid;
      };

      /**
       * { function_description }
       *
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.inicializaAssociacaoDetectores = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertAssociacaoDetectores()) {
            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
            $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});

            $scope.aneis.forEach(function(anel) {
              anel.detectores = [];
              anel.detectores =  criarDetectores(anel.quantidadeDetectorPedestre, 'PEDESTRE');
              anel.detectores = _.concat(anel.detectores, criarDetectores(anel.quantidadeDetectorVeicular, 'VEICULAR'));

              // @todo CODIGO DUPLICADO!!! ESTE BLOCO DEVERÁ SER REMOVIDO ASSIM QUE A API RETORNAR A POSICAO DOS ESTAGIOS.
              anel.estagios.forEach(function(estagio, index) {
                estagio.posicao = index + 1;
              });

              anel.estagios = _.orderBy(anel.estagios, ['posicao'], ['asc']);
            });

            $scope.selecionaAnel(0);
          }
        });
      };

      $scope.associaDetector = function(estagio, detector) {
        console.log(estagio, detector);
      };

      criarDetectores = function(qtde, tipo) {
        return _.times(qtde, Number).map(function(i) {
          return {
            posicao: i + 1,
            tipo: tipo
          };
        });
      };

    }]);
