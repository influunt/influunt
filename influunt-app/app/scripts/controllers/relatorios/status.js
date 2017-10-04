'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:RelatoriosStatusCtrl
 * @description
 * # RelatoriosStatusCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('RelatoriosStatusCtrl', ['$controller', '$scope', '$state', 'Restangular', 'influuntBlockui',
    function ($controller, $scope, $state, Restangular, influuntBlockui) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('relatorios', 'controladores_status');
      $scope.relatorio = {tipoRelatorio: ''};

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

      /**
      * Relatorio de Controladores por Status
      */
      $scope.gerarRelatorioControladoresPorStatusCSV = function() {
        $scope.pesquisa.tipoRelatorio = 'CSV';
        var pesquisa = _.cloneDeep($scope.pesquisa);
        pesquisa.per_page = null;
        pesquisa.page = null;
        return Restangular.all('relatorios').withHttpConfig({ responseType: 'arraybuffer' }).customGET('controladores_status', $scope.buildQuery(pesquisa))
          .then(function(res) {
            var blob = new Blob([res], {type: 'text/csv;charset=utf-8'});
            saveAs(blob, 'controladores_status.csv');
            $scope.lista = res.data;
          })
          .then(function() {
            $scope.pesquisa.tipoRelatorio = '';
            $scope.index();
          })
          .finally(influuntBlockui.unblock);
      };

  }]);
