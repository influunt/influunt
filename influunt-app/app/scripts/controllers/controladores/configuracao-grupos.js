'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresConfiguracaoGrupoCtrl
 * @description
 * # ControladoresConfiguracaoGrupoCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresConfiguracaoGruposCtrl', ['$scope', '$controller', '$state', '$filter', 'assertControlador', 'influuntAlert',
    function ($scope, $controller, $state, $filter, assertControlador, influuntAlert) {
      $controller('ControladoresCtrl', {$scope: $scope});

      var atualizaPosicaoGrupos;

      /**
       * Pré-condições para acesso à tela.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assert = function() {
        var valid = assertControlador.hasAneis($scope.objeto) && assertControlador.hasEstagios($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.aneis', {id: $scope.objeto.id});
        }

        return valid;
      };

      $scope.inicializaConfiguracaoGrupos = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assert()) {
            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
            $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
            $scope.objeto.gruposSemaforicos = _.orderBy($scope.objeto.gruposSemaforicos, ['posicao']);

            $scope.selecionaAnelGruposSemaforicos(0);
          }
        });
      };

      $scope.adicionaGrupoSemaforico = function() {
        var obj = { 
          idJson: UUID.generate(),
          anel: {idJson: $scope.currentAnel.idJson}, 
          tipo: 'VEICULAR',
          faseVermelhaApagadaAmareloIntermitente: false,
          // tempoVerdeSeguranca: $scope.objeto.
        };

        $scope.objeto.gruposSemaforicos = $scope.objeto.gruposSemaforicos || [];
        $scope.currentAnel.gruposSemaforicos = $scope.currentAnel.gruposSemaforicos || [];

        $scope.objeto.gruposSemaforicos.push(obj);
        $scope.currentAnel.gruposSemaforicos.push({ idJson: obj.idJson });

        $scope.atualizaGruposSemaforicos();
        return atualizaPosicaoGrupos();
      };

      $scope.removeGrupo = function(index) {
        influuntAlert.delete().then(function(confirmado) {
          if (confirmado) {

            var jsonId = $scope.currentAnel.gruposSemaforicos[index].idJson;
            var i = _.findIndex($scope.objeto.gruposSemaforicos, {idJson: jsonId});

            $scope.currentAnel.gruposSemaforicos.splice(index, 1);
            $scope.objeto.gruposSemaforicos.splice(i, 1);

            $scope.atualizaGruposSemaforicos();
            return atualizaPosicaoGrupos();
          }
        });
      };

      $scope.selecionaAnelGruposSemaforicos = function(index) {
        $scope.selecionaAnel(index);
        $scope.atualizaEstagios();
        $scope.atualizaGruposSemaforicos();
      };

      $scope.podeAdicionarGrupoSemaforico = function(){
        if(!$scope.objeto){
          return true;
        }
        return $scope.objeto.gruposSemaforicos.length < $scope.objeto.limiteGrupoSemaforico;
      };

      $scope.atualizaTempoVerdeSeguranca = function(grupo){
        grupo.tempoVerdeSeguranca = grupo.tipo === 'VEICULAR' ? $scope.objeto.verdeSegurancaVeicularMin : $scope.objeto.verdeSegurancaPedestreMin;
      };

      /**
       * atualiza as posições dos grupos.
       *
       * @return     {<type>}  { description_of_the_return_value }
       */
      atualizaPosicaoGrupos = function() {
        var posicao = 0;
        _.chain($scope.aneis)
          .map('gruposSemaforicos')
          .flatten()
          .map('idJson')
          .each(function(idJson) {
            var obj = _.find($scope.objeto.gruposSemaforicos, {idJson: idJson});
            obj.posicao = ++posicao;
          })
          .value();
      };

    }]);
