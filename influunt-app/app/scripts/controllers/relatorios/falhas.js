'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:RelatoriosFalhasCtrl
 * @description
 * # RelatoriosFalhasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('RelatoriosFalhasCtrl', ['$controller', '$scope', '$state', 'Restangular', 'influuntBlockui',
    function ($controller, $scope, $state, Restangular, influuntBlockui) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('relatorios', 'controladores_falhas');
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
          },
          {
            nome: 'tipoFalha',
            label: 'relatorios.tipoFalha',
            tipo: 'texto'
          },
        ]
      };

      /**
      * Relatorio de Controladores por Status
      */
      $scope.gerarRelatorioControladoresPorFalhasCSV = function() {
        console.log("OLA MUNDO !!!")
        $scope.pesquisa.tipoRelatorio = 'CSV';
        var pesquisa = _.cloneDeep($scope.pesquisa);
        pesquisa.per_page = null;
        pesquisa.page = null;
        return Restangular.all('relatorios').withHttpConfig({ responseType: 'arraybuffer' }).customGET('controladores_falhas', $scope.buildQuery(pesquisa))
          .then(function(res) {
            var blob = new Blob([res], {type: 'text/csv;charset=utf-8'});
            saveAs(blob, 'controladores_falhas.csv');
            $scope.lista = res.data;
          })
          .then(function() {
            $scope.pesquisa.tipoRelatorio = '';
            $scope.index();
          })
          .finally(influuntBlockui.unblock);
      };

  }]);
