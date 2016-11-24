'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:RelatoriosCtrl
 * @description
 * # RelatoriosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('RelatoriosCtrl', ['$scope', '$controller', 'tipoAcao', 'Restangular', 'influuntBlockui',
    function ($scope, $controller, tipoAcao, Restangular, influuntBlockui) {

      $scope.tipoAcao = tipoAcao;
      $scope.relatorio = {tipoRelatorio: 'PDF'};

      var generateReport;

      /**
       * Recupera a lista de usuarios.
       */
      $scope.carregarUsuarios = function() {
        Restangular.all('usuarios').customGET()
        .then(function(res) {
          $scope.usuarios = res.data;
        })
        .finally(influuntBlockui.unblock);
      };

      /**
      * Relatorio de Auditoria
      */
      $scope.gerarRelatorioAuditoria = function() {
        if (moment.isMoment($scope.relatorio['change.eventTime_start']) &&
            $scope.relatorio['change.eventTime_start'].isValid()) {
          $scope.relatorio['change.eventTime_start'] = $scope.relatorio['change.eventTime_start'].format('DD/MM/YYYY HH:mm:ss');
        }

        if (moment.isMoment($scope.relatorio['change.eventTime_end']) &&
            $scope.relatorio['change.eventTime_end'].isValid()) {
          $scope.relatorio['change.eventTime_end'] = $scope.relatorio['change.eventTime_end'].format('DD/MM/YYYY') + ' 23:59:59';
        }

        return generateReport('relatorio_auditoria', 'auditoria', $scope.relatorio);
      };

      /**
      * Relatorio de Controladores por Falhas
      */
      $scope.gerarRelatorioControladoresPorFalhas = function() {
        return generateReport('relatorio_controladores_falhas', 'controladores_falhas', $scope.relatorio);
      };

      /**
      * Relatorio de Controladores com alteracao de entreverdes no periodo
      */
      $scope.gerarRelatorioControladoresEntreverdes = function() {
        if (moment.isMoment($scope.relatorio.dataAtualizacao_start) &&
            $scope.relatorio.dataAtualizacao_start.isValid()) {
          $scope.relatorio.dataAtualizacao_start = $scope.relatorio.dataAtualizacao_start.format('DD/MM/YYYY HH:mm:ss');
        }

        if (moment.isMoment($scope.relatorio.dataAtualizacao_end) &&
            $scope.relatorio.dataAtualizacao_end.isValid()) {
          $scope.relatorio.dataAtualizacao_end = $scope.relatorio.dataAtualizacao_end.format('DD/MM/YYYY') + ' 23:59:59';
        }

        return generateReport('relatorio_controladores_entreverdes', 'controladores_entreverdes', $scope.relatorio);
      };

      generateReport = function(reportName, url, relatorio) {
        return Restangular.all('relatorios').withHttpConfig({ responseType: 'arraybuffer' }).customGET(url, relatorio)
          .then(function(res) {
            var blob;
            if (relatorio.tipoRelatorio === 'PDF') {
              blob = new Blob([res], {type: 'application/pdf'});
              saveAs(blob, reportName + '.pdf');
            } else if (relatorio.tipoRelatorio === 'CSV') {
              blob = new Blob([res], {type: 'text/csv;charset=utf-8'});
              saveAs(blob, reportName + '.csv');
            }
          })
          .finally(influuntBlockui.unblock);

      };

  }]);
