'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:RelatorioTabelaHorariaCtrl
 * @description
 * # RelatorioTabelaHorariaCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('RelatorioTabelaHorariaCtrl', ['$controller', '$scope', '$state', 'Restangular', 'influuntBlockui',
    function ($controller, $scope, $state, Restangular, influuntBlockui) {

      var loadControladores, getParams, parseData;

      $scope.relatorioParams = {};

      $scope.init = function() {
        loadControladores();
      };

      loadControladores = function() {
        return Restangular.all('controladores').customGET(null, {})
          .then(function(res) {
            $scope.lista = _.orderBy(res.data, 'CLC');
          })
          .finally(influuntBlockui.unblock);
      };

      getParams = function() {
        return {
          controladorId: $scope.relatorioParams.controladorId,
          data: parseData($scope.relatorioParams.data)
        };
      };

      parseData = function (data) {
        return moment.isMoment(data) ? data.format() : moment((new Date (data).toLocaleString()), "MM-DD-YYYY HH:mm").format();
      };

      /**
      * Relatorio de Controladores por Status
      */
      $scope.getDadosRelatorio = function() {
        var params = getParams();
        return Restangular.all('relatorios').customGET('tabela_horaria', params)
          .then(function(res) {
            $scope.relatorio = res.plain();
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.getRelatorioTabelaHorariaCSV = function() {
        var params = getParams();
        params.tipoRelatorio = 'CSV';

        return Restangular.all('relatorios')
          .withHttpConfig({ responseType: 'arraybuffer' })
          .customGET('tabela_horaria', params)
          .then(function(res) {
            var blob = new Blob([res], { type: 'text/csv;charset=utf-8' });
            saveAs(blob, 'tabela_horaria_'+ $scope.relatorioParams.data.format('DD-MM-YYYY') +'.csv');
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.podeGerar = function() {
        return _.get($scope.relatorioParams, 'controladorId') && _.get($scope.relatorioParams, 'data');
      };

  }]);
