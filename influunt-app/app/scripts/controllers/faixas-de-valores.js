'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:AreasCtrl
 * @description
 * # AreasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('FaixasDeValoresCtrl', ['$controller', '$scope', '$filter', '$state', 'Restangular', 'influuntBlockui', 'toast', 'PermissionStrategies',
    function ($controller, $scope, $filter, $state, Restangular, influuntBlockui, toast, PermissionStrategies) {

      var resourceName = 'faixas_de_valores';

      $scope.PermissionStrategies = PermissionStrategies;

      $scope.init = function() {
        return Restangular.one(resourceName, '').get()
          .then(function(res) {
            $scope.objeto = res;
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.save = function() {
        return Restangular.one(resourceName, '').customPUT($scope.objeto)
          .then(function() {
            $state.go('app.main');
          })
          .finally(function() {
            influuntBlockui.unblock();
            toast.success($filter('translate')('geral.mensagens.salvo_com_sucesso'));
          });
      };


    }]);
