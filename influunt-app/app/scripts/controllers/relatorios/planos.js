'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:RelatoriosPlanosCtrl
 * @description
 * # RelatoriosPlanosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('RelatoriosPlanosCtrl', ['$controller', '$scope', '$state', 'Restangular', 'influuntBlockui',
    function ($controller, $scope, $state, Restangular, influuntBlockui) {
        // Herda todo o comportamento do crud basico.
        $controller('CrudCtrl', {$scope: $scope});
        $scope.inicializaNovoCrud('relatorios', 'planos');
        $scope.relatorio = {tipoRelatorio: ''};
        $scope.arrayEstagios = [];

        $scope.pesquisa = {
          tipoRelatorio: '',
          campos: [
            {
              nome: 'filtrarPor',
              label: 'relatorios.filtarPor',
              tipo: 'select',
              options: ['Subarea', 'Agrupamento']
            },
            {
              nome: 'subareaAgrupamento',
              label: 'relatorios.subareaAgrupamento',
              tipo: 'texto'
            }
          ]
        };

        $scope.afterIndex = function() {
          $scope.arrayEstagios = _.chain($scope.lista).map(function(plano){return plano.estagios.length}).max().times().value();
        };

      /**
      * Relatorio de Planos
      */
      $scope.gerarRelatorioPlanosCSV = function() {
        $scope.pesquisa.tipoRelatorio = 'CSV';
        var pesquisa = _.cloneDeep($scope.pesquisa);
        return Restangular.all('relatorios').withHttpConfig({ responseType: 'arraybuffer' }).customGET('planos', $scope.buildQuery(pesquisa))
          .then(function(res) {
            var blob = new Blob([res], {type: 'text/csv;charset=utf-8'});
            saveAs(blob, 'planos.csv');
            $scope.lista = res.data;
          })
          .then(function() {
            $scope.pesquisa.tipoRelatorio = '';
            $scope.index();
          })
          .finally(influuntBlockui.unblock);
      };

  }]);
