'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:AreasCtrl
 * @description
 * # AreasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('FaixasDeValoresCtrl', ['$controller', '$scope', '$filter', '$state', 'Restangular', 'influuntBlockui', 'toast', 'PermissionStrategies', 'handleValidations',
    function ($controller, $scope, $filter, $state, Restangular, influuntBlockui, toast, PermissionStrategies, handleValidations) {

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
          .catch(function(err) {
            if (err.status === 422) {
              $scope.errors = handleValidations.handle(err.data);
              console.log($scope.errors)
            } else {
              toast.error($filter('translate')('geral.mensagens.default_erro'));
              throw new Error(JSON.stringify(err));
            }
          })
          .finally(function() {
            influuntBlockui.unblock();
          });
      };


    }]);
