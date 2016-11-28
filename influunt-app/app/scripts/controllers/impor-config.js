'use strict';

/**
 * @ngdoc function
 * @name influuntApp:ImporConfigCtrl
 * @description
 * # ImporConfigCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ImporConfigCtrl', ['$scope', '$controller', '$filter', 'Restangular', 'influuntBlockui', 'influuntAlert',
    function ($scope, $controller, $filter, Restangular, influuntBlockui, influuntAlert) {

      var setData, setAneisPlanosImpostos;

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
      $scope.aneisSelecionadosObj = [];
      $scope.dataSincronizar = {};
      $scope.dataImposicaoModo = {};
      $scope.isAnelChecked = {};
      $scope.statusTransacoes = {};

      $scope.index = function() {
        var query = $scope.buildQuery($scope.pesquisa);
        return Restangular.all('controladores').customGET('imposicao', query)
          .then(function(res) {
            setData(res);
            return Restangular.one('monitoramento', 'status_aneis').get();
          })
          .then(setAneisPlanosImpostos)
          .finally(influuntBlockui.unblock);
      };

      var filtraObjetosAneis = function() {
        $scope.aneisSelecionadosObj = _.filter($scope.lista, function(anel) {
          return $scope.aneisSelecionados.indexOf(anel.id) >= 0;
        });

        return $scope.aneisSelecionadosObj;
      };

      $scope.selecionaAnel = function(anelId) {
        $scope.aneisSelecionados.push(anelId);
        filtraObjetosAneis();
      };

      $scope.desselecionaAnel = function(anelId) {
        _.pull($scope.aneisSelecionados, anelId);
        filtraObjetosAneis();
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

        $scope.pagination.totalItems = $scope.lista.length;
      };

      setAneisPlanosImpostos = function(statusObj) {
        return _.map(statusObj.statusPlanos, function(status) {
          return _
            .chain($scope.lista)
            .find({controlador: {id: status.idControlador}, posicao: parseInt(status.anelPosicao)})
            .set('hasPlanoImposto', status.hasPlanoImposto)
            .set('modoOperacao', _.chain(status.modoOperacao).lowerCase().upperFirst().value())
            .set('inicio', status.inicio)
            .value();
        });
      };
    }]);


