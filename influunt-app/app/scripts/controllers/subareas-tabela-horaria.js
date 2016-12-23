'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:SubAreasCtrl
 * @description
 * # SubAreasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('SubareasTabelaHorariaCtrl', ['$controller', '$scope', 'Restangular', 'influuntBlockui', '$state', '$q', 'handleValidations',
    function ($controller, $scope, Restangular, influuntBlockui, $state, $q, handleValidations) {

      var loadTabelaHoraria;

      // Tabela Horária
      $scope.init = function() {
        $scope.subareaId = $scope.$state.params.id;
        loadTabelaHoraria()
          .then(function(response) {
            if (response) {
              $scope.objeto = response.plain();
            } else {
              $scope.objeto = {
                versoesTabelasHorarias: [],
                tabelasHorarias: [],
                eventos: []
              };
            }
            $scope.objeto.subareaId = $scope.subareaId;
            $scope.podeInicializar = true;
          });
      };

      loadTabelaHoraria = function() {
        return Restangular.all('subareas/' + $scope.subareaId).customGET('tabela_horaria');
      };

      var sortEventos = function(refObjeto) {
        return angular.isDefined(refObjeto.versoesTabelasHorarias) &&
          _.each(refObjeto.versoesTabelasHorarias, function(versao) {
            var orderers = ['NORMAL', 'ESPECIAL_RECORRENTE', 'ESPECIAL_NAO_RECORRENTE'];
            var tabelaHoraria = _.find($scope.objeto.tabelasHorarias, {idJson: versao.tabelaHoraria.idJson});
            var idsEventos = _.map(tabelaHoraria.eventos, 'idJson');
            tabelaHoraria.eventos = _
              .chain(refObjeto.eventos)
              .filter(function(ev) { return idsEventos.indexOf(ev.idJson) >= 0; })
              .sortBy([function(ev) { return orderers.indexOf(ev.tipo); }, 'posicao'])
              .orderBy('posicao')
              .map(function(ev) { return { idJson: ev.idJson }; })
              .value();
          });
      };

      var submit = function(refObjeto) {
        sortEventos(refObjeto);

        return Restangular.all('subareas/' + $scope.subareaId).customPOST(refObjeto, 'tabela_horaria')
          .then(function(res) {
            $state.go('app.subareas');
            return res;
          })
          .catch(function(res) {
            $scope.errorsUibAlert = _.chain(res.data)
            .map(function(erro) { return erro.root + ': ' + erro.message; })
            .uniq()
            .value();

            return $q.reject(handleValidations.buildValidationMessages(res.data, refObjeto));
          });
      };

      $scope.submitForm = function() {
        return submit($scope.objeto)
          .then(function(res) { $scope.objeto = res; })
          .catch(function(err) { $scope.errors = err; });
      };

    }]);
