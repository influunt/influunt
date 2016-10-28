'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:HistoricoCtrl
 * @description
 * # HistoricoCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('HistoricoCtrl', ['$scope', '$state', '$filter', '$q', 'Restangular', 'influuntBlockui', 'toast', 'influuntAlert', 'handleValidations',
    function HistoricoCtrl($scope, $state, $filter, $q, Restangular, influuntBlockui, toast, influuntAlert, handleValidations) {

      var handleErroEditar, sortPlanos, sortEventos;
      var resourceName = null;

      $scope.inicializaResourceHistorico = function(param) {
        resourceName = param;
      };

      $scope.editar = function(controladorId) {
        return Restangular.one('controladores', controladorId).all('pode_editar').customGET()
          .then(function() {
            $state.go('app.' + resourceName + '_edit', { id: controladorId });
          })
          .catch(handleErroEditar);
      };

      $scope.clonar = function(controladorId) {
        return Restangular
          .one('controladores', controladorId).all('pode_editar').customGET()
          .then(function() {
            return Restangular.one('controladores', controladorId).all('editar_' + resourceName + '').customGET();
          })
          .then(function() {
            $state.go('app.' + resourceName + '_edit', { id: controladorId });
          })
          .catch(handleErroEditar);
      };

      $scope.cancelar = function(obj) {
        influuntAlert.delete().then(function(confirmado) {
          if (confirmado) {
            if (obj && obj.id) {
              return Restangular.one(resourceName, obj.id).all('cancelar_edicao').customDELETE()
                .then(function() {
                  toast.success($filter('translate')('geral.mensagens.removido_com_sucesso'));
                  $state.go('app.controladores');
                })
                .catch(handleErroEditar)
                .finally(influuntBlockui.unblock);
            } else {
              $state.go('app.controladores')
            }
          }
        });
      };

      $scope.submit = function(refObjeto) {
        // planos são ordenados antes de submeter o form
        // para que os erros voltem ordenados da API.
        // @todo: Testar se isso pode causar problemas no submit de tabelas horárias.
        sortPlanos(refObjeto);
        sortEventos(refObjeto);

        return Restangular.all(resourceName).post(refObjeto)
          .then(function(res) {
            influuntBlockui.unblock();
            $state.go('app.controladores');
            return res;
          })
          .catch(function(res) {
            influuntBlockui.unblock();
            if (res.status !== 422) {
              console.error(res);
              return $q.reject(res);
            }

            return $q.reject(handleValidations.buildValidationMessages(res.data, refObjeto));
          });
      };

      handleErroEditar = function(err) {
        if (err.status === 403 && _.get(err, 'data.[0].message')) {
          toast.clear();
          influuntAlert.alert('Controlador', err.data[0].message);
        } else {
          toast.error($filter('translate')('geral.mensagens.default_erro'));
        }
      };

      sortEventos = function(refObjeto) {
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

      sortPlanos = function(refObjeto) {
        return angular.isDefined(refObjeto.versoesPlanos) &&
          _.forEach(refObjeto.versoesPlanos, function(versaoPlano) {
            versaoPlano.planos = _
              .chain(refObjeto.planos)
              .filter(function(plano) { return plano.anel.idJson === versaoPlano.anel.idJson; })
              .orderBy('posicao')
              .map(function(plano) { return { idJson: plano.idJson }; })
              .value();
        });
      };

    }]);
