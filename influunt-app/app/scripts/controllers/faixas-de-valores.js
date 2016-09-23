'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:AreasCtrl
 * @description
 * # AreasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('FaixasDeValoresCtrl', ['$controller', '$scope', '$filter', '$state', 'Restangular', 'influuntBlockui', 'toast',
    function ($controller, $scope, $filter, $state, Restangular, influuntBlockui, toast) {

      var resourceName = 'faixas_de_valores';

      $scope.init = function() {
        return Restangular.one(resourceName, '').get()
          .then(function(res) {
            $scope.objeto = res;
            console.log('objeto: ', res)
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.save = function() {
        return Restangular.one(resourceName, '').customPUT($scope.objeto)
          .then(function(res) {
            $state.go('app.main');
          })
          .finally(function() {
            influuntBlockui.unblock();
            toast.success($filter('translate')('geral.mensagens.salvo_com_sucesso'));
          });
      };

    }]);
