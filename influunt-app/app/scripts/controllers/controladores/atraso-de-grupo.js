'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresEntreVerdesCtrl
 * @description
 * # ControladoresEntreVerdesCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresAtrasoDeGrupoCtrl', ['$scope', '$state', '$controller', 'assertControlador',
    function ($scope, $state, $controller, assertControlador) {

      $scope.isAtrasoDeGrupo = true;
      var inicializaTransicoes;
      var confirmacaoNadaHaPreencher;

      $controller('ControladoresCtrl', {$scope: $scope});
      /**
       * Garante que o controlador tem as condições mínimas para acessar a tela de atraso de grupo.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertAtrasoDeGrupo = function() {
        var valid = assertControlador.hasAneis($scope.objeto) &&
                    assertControlador.hasEstagios($scope.objeto) &&
                    assertControlador.hasTransicoes($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.verdes_conflitantes', {id: $scope.objeto.id});
        }

        return valid;
      };

      /**
       * Inicializa a tela de atraso de grupo: Carrega os dados necessários, ordena os aneis e estágios a partir
       * das posições.
       *
       * @return     {<type>}  { description_of_the_return_value }
       */

      $scope.setAtributos = function() {
        $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
        $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
        $scope.objeto.gruposSemaforicos = _.orderBy($scope.objeto.gruposSemaforicos, ['posicao']);
        $scope.atrasoGrupoMin = $scope.objeto.atrasoGrupoMin;
        $scope.atrasoGrupoMax = $scope.objeto.atrasoGrupoMax;
        inicializaTransicoes();
      };

      $scope.inicializaAtrasoDeGrupo = function(index) {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertAtrasoDeGrupo()) {
            $scope.selecionaAnel(index);
            $scope.atualizaGruposSemaforicos();
            $scope.selecionaGrupoSemaforico($scope.currentGruposSemaforicos[0], 0);
            
            $scope.setAtributos();
          }
        });
      };

      inicializaTransicoes = function() {
        var allTransicoes = _.union($scope.objeto.transicoes, $scope.objeto.transicoesComPerdaDePassagem);
        _.forEach(allTransicoes, function(transicao) {
          if (typeof transicao.atrasoDeGrupo === 'undefined') {
            transicao.atrasoDeGrupo = { idJson: UUID.generate(), atrasoDeGrupo: $scope.atrasoGrupoMin };
            $scope.objeto.atrasosDeGrupo.push(transicao.atrasoDeGrupo);
          } else {
            var atrasoDeGrupo = _.find($scope.objeto.atrasosDeGrupo, { idJson: transicao.atrasoDeGrupo.idJson });
            transicao.atrasoDeGrupo = atrasoDeGrupo;
          }
        });
      };

      $scope.selecionaAnelAtrasoDeGrupo = function(index) {
        $scope.inicializaAtrasoDeGrupo(index);
      };
      
      $scope.confirmacaoNadaHaPreencher = function(){
        confirmacaoNadaHaPreencher = !confirmacaoNadaHaPreencher;
      };

      $scope.podeSalvar = function() {
        if($scope.objeto && !!$scope.objeto.atrasosDeGrupo){
          $scope.totalNaoPreenchido = _.filter($scope.objeto.atrasosDeGrupo, {atrasoDeGrupo: '0'}).length;
          $scope.totalNaoPreenchido = $scope.totalNaoPreenchido + _.filter($scope.objeto.atrasosDeGrupo, {atrasoDeGrupo: 0}).length;
          if($scope.totalNaoPreenchido < $scope.objeto.atrasosDeGrupo.length){
            confirmacaoNadaHaPreencher = false;
            return true;
          }
          return confirmacaoNadaHaPreencher;
        }
        return false;
      };

    }]);
