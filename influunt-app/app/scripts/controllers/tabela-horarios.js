'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:TabelaHorariosCtrl
 * @description
 * # HorariosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('TabelaHorariosCtrl', ['$controller', '$scope', '$state', '$timeout', 'Restangular', '$filter', 'toast',
                           'influuntAlert', 'influuntBlockui', 'geraDadosDiagramaIntervalo',
                           'handleValidations', 'TabelaHorariaService', 'HorariosService', 'planoService', 'SimulacaoService', 'breadcrumbs',
    function ($controller, $scope, $state, $timeout, Restangular, $filter, toast,
              influuntAlert, influuntBlockui, geraDadosDiagramaIntervalo,
              handleValidations, TabelaHorariaService, HorariosService, planoService, SimulacaoService, breadcrumbs) {


      $controller('HistoricoCtrl', {$scope: $scope});
      $scope.inicializaResourceHistorico('tabelas_horarias');

      $scope.somenteVisualizacao = $state.current.data.somenteVisualizacao;

      $scope.podeInicializar = false;

      /**
       * Inicializa a tela de tabela horario.
       */
      $scope.init = function() {
        var id = $state.params.id;
        Restangular.one('controladores', id).get()
          .then(function(res) {
            $scope.objeto = res;
            $scope.podeInicializar = true;
            breadcrumbs.setNomeEndereco($scope.objeto.nomeEndereco);
          });
      };

      $scope.clonarTabelaHoraria = function(controladorId) {
        return $scope.clonar(controladorId).finally(influuntBlockui.unblock);
      };

      $scope.editarTabelaHoraria = function(controladorId) {
        return $scope.editar(controladorId).finally(influuntBlockui.unblock);
      };

      $scope.cancelarEdicao = function() {
        return $scope.cancelar($scope.currentTabelaHoraria);
      };

      $scope.podeEditarControlador = function() {
        return planoService.podeEditarControlador($scope.objeto);
      };

      $scope.submitForm = function() {
        return $scope
          .submit($scope.objeto)
          .then(function(res) { $scope.objeto = res; })
          .catch(function(err) { $scope.errors = err; })
          .finally(influuntBlockui.unblock);
      };

      $scope.podeSimular = function(controlador) {
        return SimulacaoService.podeSimular(controlador);
      };
    }]);
