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

      var atualizaPosicaoGrupos, removeGrupoSalvo, removeGrupoLocal;

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
          faseVermelhaApagadaAmareloIntermitente: true,
          tempoVerdeSeguranca: $scope.objeto.verdeSegurancaVeicularMin
        };

        $scope.objeto.gruposSemaforicos = $scope.objeto.gruposSemaforicos || [];
        $scope.currentAnel.gruposSemaforicos = $scope.currentAnel.gruposSemaforicos || [];

        $scope.objeto.gruposSemaforicos.push(obj);
        $scope.currentAnel.gruposSemaforicos.push({ idJson: obj.idJson });

        $scope.atualizaGruposSemaforicos();
        return atualizaPosicaoGrupos();
      };

      $scope.removeGrupo = function(grupo) {
        influuntAlert.delete().then(function(confirmado) {
          if (confirmado) {
            if (grupo.id) {
              removeGrupoSalvo(grupo);
            } else {
              removeGrupoLocal(grupo);
            }

            $scope.atualizaGruposSemaforicos();
            return atualizaPosicaoGrupos();
          }
        });
      };

      /**
       * Remove grupos que já foram enviados à API.
       *
       * @param      {<type>}  grupo   The grupo
       */
      removeGrupoSalvo = function(grupo) {
        grupo._destroy = true;
        grupo.posicao = Infinity;
      };

      /**
       * Remove grupos que ainda não foram enviados à API.
       *
       * @param      {<type>}  index   The index
       * @param      {<type>}  i       { parameter_description }
       */
      removeGrupoLocal = function(grupo) {
        var index = _.findIndex($scope.currentAnel.gruposSemaforicos, { idJson: grupo.idJson });
        $scope.currentAnel.gruposSemaforicos.splice(index, 1);
        index = _.findIndex($scope.objeto.gruposSemaforicos, { idJson: grupo.idJson });
        $scope.objeto.gruposSemaforicos.splice(index, 1);
      };

      $scope.selecionaAnelGruposSemaforicos = function(index) {
        $scope.selecionaAnel(index);
        $scope.atualizaEstagios();
        $scope.atualizaGruposSemaforicos();
      };

      $scope.podeAdicionarGrupoSemaforico = function(){
        return $scope.objeto && $scope.objeto.gruposSemaforicos.length < $scope.objeto.limiteGrupoSemaforico;
      };

      $scope.atualizaTempoVerdeSeguranca = function(grupo){
        if (grupo.tipo === 'VEICULAR'){
          grupo.disableSelectFase = false;
          grupo.tempoVerdeSeguranca = $scope.objeto.verdeSegurancaVeicularMin;
          grupo.faseVermelhaApagadaAmareloIntermitente = true;
        } else {
          grupo.tempoVerdeSeguranca = $scope.objeto.verdeSegurancaPedestreMin;
          grupo.faseVermelhaApagadaAmareloIntermitente = false;
          grupo.disableSelectFase = true;
        }
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
          .compact()
          .map(function(idJson) { return _.find($scope.objeto.gruposSemaforicos, {idJson: idJson}); })
          .orderBy(['posicao'])
          .each(function(obj) {

            if (!obj._destroy) {
              obj.posicao = ++posicao;
            }
          })
          .value();
      };

    }]);
