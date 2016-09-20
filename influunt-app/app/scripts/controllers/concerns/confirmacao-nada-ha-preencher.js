'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:CrudCtrl
 * @description
 * # CrudCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ConfirmacaoNadaHaPreencherCtrl', ['$scope',
    function ($scope) {
      var confirmacaoNadaHaPreencher = {};

      $scope.inicializaConfirmacaoNadaHaPreencher = function(){
        $scope.confirmacao = {};
        $scope.aneis.forEach(function(anel) {
          confirmacaoNadaHaPreencher[anel.posicao] = confirmacaoNadaHaPreencher[anel.posicao] || $scope.possuiInformacoesPreenchidas(anel);
        });
      };

      $scope.confirmacaoNadaHaPreencher = function(){
        confirmacaoNadaHaPreencher[$scope.currentAnel.posicao] = !confirmacaoNadaHaPreencher[$scope.currentAnel.posicao];
      };

      $scope.podeSalvar = function() {
        return _.values(confirmacaoNadaHaPreencher).every(function(e) {return e;});
      };

      $scope.possuiInformacoesPreenchidas = function() {
        throw new Error('Método possuiInformacoesPreenchidas não implementado classe filho.');
      };
      
      $scope.verificaConfirmacaoNadaHaPreencher = function(){
        if($scope.confirmacao){
          if($scope.possuiInformacoesPreenchidas()){
            confirmacaoNadaHaPreencher[$scope.currentAnel.posicao] = true;
            $scope.confirmacao[$scope.currentAnel.posicao] = false;
          }else{
            confirmacaoNadaHaPreencher[$scope.currentAnel.posicao] = false;
            $scope.confirmacao[$scope.currentAnel.posicao] = false;
          }
        }
      };
  }]);
