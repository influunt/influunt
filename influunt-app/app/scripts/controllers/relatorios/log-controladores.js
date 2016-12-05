'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:RelatoriosLogcontroladoresCtrl
 * @description
 * # RelatoriosLogcontroladoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('RelatoriosLogControladoresCtrl', ['$controller', '$scope', '$state', 'Restangular', 'influuntBlockui', 'TIPO_LOG',
    function ($controller, $scope, $state, Restangular, influuntBlockui, TIPO_LOG) {
      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('relatorios', 'log_controladores');
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
            nome: 'tipoLog',
            label: 'relatorios.filtarPor',
            tipo: 'select',
            options: TIPO_LOG
          }
        ]
      };

      /**
      * Relatorio de Logs
      */
      $scope.gerarRelatorioLogCSV = function() {
        $scope.pesquisa.tipoRelatorio = 'CSV';
        var pesquisa = _.cloneDeep($scope.pesquisa);
        return Restangular.all('relatorios').withHttpConfig({ responseType: 'arraybuffer' }).customGET('log_controladores', $scope.buildQuery(pesquisa))
          .then(function(res) {
            var blob = new Blob([res], {type: 'text/csv;charset=utf-8'});
            saveAs(blob, 'log_controladores.csv');
            $scope.lista = res.data;
          })
          .then(function() {
            $scope.pesquisa.tipoRelatorio = '';
            $scope.index();
          })
          .finally(influuntBlockui.unblock);
      };

  }]);
