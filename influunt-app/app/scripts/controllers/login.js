'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('LoginCtrl', ['$scope', 'Restangular', '$state', 'SweetAlert',
    function LoginCtrl ($scope, Restangular, $state, SweetAlert) {
      $scope.credenciais = {};

      $scope.submitLogin = function(formValido) {
        $scope.submited = true;
        if (!formValido) {
          return false;
        }

        Restangular.all('login').post($scope.credenciais)
          .then(function() {
            $state.go('app.main');
          })
          .catch(function(err) {
            if (err.status === 401) {
              err.data.forEach(function(error) {
                // @todo       por algum motivo o toaster não está funcionando para
                // respostas do login.
                // window.alert(error.message);
                SweetAlert.swal({
                  type: 'warning',
                  title: 'Atenção',
                  text: error.message,
                  timer: 2000,
                  showConfirmButton: false
                });
              });
            }
          });
      };

    }]);
