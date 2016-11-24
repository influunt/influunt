'use strict';

/**
 * @ngdoc function
 * @name influuntApp:ImporConfigCtrl
 * @description
 * # ImporConfigCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ImporConfigCtrl', ['$scope', '$controller', 'Restangular', 'influuntBlockui', 'pahoProvider', 'eventosDinamicos',
    function ($scope, $controller, Restangular, influuntBlockui, pahoProvider, eventosDinamicos) {

      var setData;

      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('controladores');

      $scope.pesquisa = {
        campos: [
          {
            nome: 'nomeEndereco',
            label: 'controladores.nomeEndereco',
            tipo: 'texto',
            resource: 'controladores'
          }
        ]
      };

      $scope.pagination = {
        perPage: 10,
        current: 1
      };
      $scope.esconderPerPage = true;

      $scope.aneisSelecionados = [];
      $scope.dataSincronizar = {};
      $scope.dataImposicaoModo = {};

      $scope.index = function() {
        var query = $scope.buildQuery($scope.pesquisa);
        return Restangular.all('controladores').customGET('imposicao', query)
          .then(function(res) {
            console.log('res: ', res)
            setData(res);
          })
          .finally(influuntBlockui.unblock);
      };

      setData = function(response) {
        $scope.lista = _.chain(response.data)
          .map('aneis')
          .flatten()
          .value();

        $scope.idsTransacoes = {};
        _.each($scope.lista, function(anel) {
          $scope.idsTransacoes[anel.controlador.id] = null;
        });

        console.log('lista: ', $scope.lista)

        $scope.pagination.totalItems = $scope.lista.length;
      };

      $scope.selecionaAnel = function(anelId) {
        $scope.aneisSelecionados.push(anelId);
      };

      $scope.desselecionaAnel = function(anelId) {
        _.pull($scope.aneisSelecionados, anelId);
      };

      $scope.sincronizar = function() {
        var resource;
        if ($scope.dataSincronizar.tipo === 'pacotePlanos') {
          resource = 'pacote_plano';
        } else {
          resource = 'configuracao_completa';
        }

        return Restangular.one('imposicoes').customPOST($scope.aneisSelecionados, resource)
          .then(function(response) {
            console.log(response)

            _.each(response.plain(), function(transacaoId, controladorId) {
              $scope.idsTransacoes[controladorId] = transacaoId;
            });

          })
          .finally(influuntBlockui.unblock);
      }

    }]);
