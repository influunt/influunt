'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:AlarmesEFalhasCtrl
 * @description
 * # AlarmesEFalhasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('AlarmesEFalhasCtrl', ['$scope', 'Restangular', '$filter', 'toast', 'influuntBlockui',
    function ($scope, Restangular, $filter, toast, influuntBlockui) {

      $scope.objeto = {};
      $scope.init = function() {
        return Restangular.all('monitoramento').all('alarmes_e_falhas').customGET()
          .then(function(res) {
            $scope.alarmesEFalhas = res.plain();
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.toggleAllChecked = function(isChecked) {
        return _
          .chain($scope.alarmesEFalhas)
          .map()
          .flatten()
          .map('tipo')
          .each(function(v) { $scope.alarmesAtivados[v] = isChecked; })
          .value();
      };

      $scope.save = function() {
        return Restangular
          .one('usuarios', $scope.getUsuario().id)
          .all('alarmes_e_falhas')
          .post($scope.alarmesAtivados)
          .then(function(response) {
            toast.success($filter('translate')('geral.mensagens.salvo_com_sucesso'));
            $scope.$root.alarmesAtivados = {};
            _.each(response, function(obj) {
              $scope.$root.alarmesAtivados[obj.chave] = true;
            });
          })
          .finally(influuntBlockui.unblock);
      };

    }]);
