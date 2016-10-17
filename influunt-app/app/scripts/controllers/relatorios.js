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
          $scope.relatorio['change.eventTime_end'] = $scope.relatorio['change.eventTime_end'].format('DD/MM/YYYY HH:mm:ss');
        }

        Restangular.all('relatorios').withHttpConfig({ responseType: 'arraybuffer' }).customGET('auditoria', $scope.relatorio)
        .then(function(res) {
          if($scope.relatorio.tipoRelatorio === 'PDF') {
            var blob = new Blob([res], {type: 'application/pdf'});
            saveAs(blob, 'relatorio_auditoria.pdf');
          } else if ($scope.relatorio.tipoRelatorio === 'CSV') {
            var blob = new Blob([res], {type: 'text/csv;charset=utf-8'});
            saveAs(blob, 'relatorio_auditoria.csv');
          }
        })
        .finally(influuntBlockui.unblock);
      };

      /**
      * Relatorio de Controladores por Status
      */
      $scope.gerarRelatorioControladoresPorStatus = function() {
        Restangular.all('relatorios').withHttpConfig({ responseType: 'arraybuffer' }).customGET('controladores_status', $scope.relatorio)
        .then(function(res) {
          if($scope.relatorio.tipoRelatorio === 'PDF') {
            var blob = new Blob([res], {type: 'application/pdf'});
            saveAs(blob, 'relatorio_controladores_status.pdf');
          } else if ($scope.relatorio.tipoRelatorio === 'CSV') {
            var blob = new Blob([res], {type: 'text/csv;charset=utf-8'});
            saveAs(blob, 'relatorio_controladores_status.csv');
          }
        })
        .finally(influuntBlockui.unblock);
      };

      /**
      * Relatorio de Controladores por Falhas
      */
      $scope.gerarRelatorioControladoresPorFalhas = function() {
        Restangular.all('relatorios').withHttpConfig({ responseType: 'arraybuffer' }).customGET('controladores_falhas', $scope.relatorio)
        .then(function(res) {
          if($scope.relatorio.tipoRelatorio === 'PDF') {
            var blob = new Blob([res], {type: 'application/pdf'});
            saveAs(blob, 'relatorio_controladores_falhas.pdf');
          } else if ($scope.relatorio.tipoRelatorio === 'CSV') {
            var blob = new Blob([res], {type: 'text/csv;charset=utf-8'});
            saveAs(blob, 'relatorio_controladores_falhas.csv');
          }
        })
        .finally(influuntBlockui.unblock);
      };

      /**
      * Relatorio de Controladores com alteracao de entreverdes no periodo
      */
      $scope.gerarRelatorioControladoresEntreverdes = function() {
        if (moment.isMoment($scope.relatorio['dataAtualizacao_start']) &&
            $scope.relatorio['dataAtualizacao_start'].isValid()) {
          $scope.relatorio['dataAtualizacao_start'] = $scope.relatorio['dataAtualizacao_start'].format('DD/MM/YYYY HH:mm:ss');
        }

        if (moment.isMoment($scope.relatorio['dataAtualizacao_end']) &&
            $scope.relatorio['dataAtualizacao_end'].isValid()) {
          $scope.relatorio['dataAtualizacao_end'] = $scope.relatorio['dataAtualizacao_end'].format('DD/MM/YYYY HH:mm:ss');
        }

        Restangular.all('relatorios').withHttpConfig({ responseType: 'arraybuffer' }).customGET('controladores_entreverdes', $scope.relatorio)
        .then(function(res) {
          if($scope.relatorio.tipoRelatorio === 'PDF') {
            var blob = new Blob([res], {type: 'application/pdf'});
            saveAs(blob, 'relatorio_controladores_entreverdes.pdf');
          } else if ($scope.relatorio.tipoRelatorio === 'CSV') {
            var blob = new Blob([res], {type: 'text/csv;charset=utf-8'});
            saveAs(blob, 'relatorio_controladores_entreverdes.csv');
          }
        })
        .finally(influuntBlockui.unblock);
      };

  }]);
