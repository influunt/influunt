'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('LoginCtrl', ['$scope', 'Restangular', '$state', '$filter', 'influuntAlert', 'toast', '$location', 'influuntBlockui',
    function LoginCtrl ($scope, Restangular, $state, $filter, influuntAlert, toast, $location, influuntBlockui) {
      $scope.credenciais = {};

      $scope.submitLogin = function(formValido) {
        $scope.submited = true;
        if (!formValido) {
          return false;
        }

        Restangular.all('login').post($scope.credenciais)
          .then(function(res) {
            localStorage.setItem('usuario', JSON.stringify(_.pick(res, ['id', 'login', 'email', 'root', 'permissoes'])));
            $state.go('app.main');
          })
          .catch(function(err) {
            if (err.status === 401) {
              err.data.forEach(function(error) {
                influuntAlert.alert($filter('translate')('geral.atencao'), error.message);
              });
            }
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.submitRecuperarSenha = function(formValido) {
        $scope.submited = true;
        if (!formValido) {
          return false;
        }

        Restangular.all('recuperar_senha').post($scope.credenciais)
          .then(function() {
            toast.success($filter('translate')('login.recuperar_senha_sucesso'));
            $state.go('login');
          })
          .catch(function(err) {
            if (err.status === 422) {
              $scope.error = err.data;
            }
          })
          .finally(influuntBlockui.unblock);

      };

      $scope.checarTokenValido = function() {
        var token = $location.search().token;
        if(angular.isDefined(token)) {
          return Restangular.one('checar_token_valido', token).customGET()
            .catch(function(err) {
              toast.clear();
              toast.error(err.data);
              $state.go('login');
            })
            .finally(influuntBlockui.unblock);
        } else {
          $state.go('login');
        }
      };

      $scope.submitRedefinirSenha = function(formValido) {
        $scope.submited = true;
        if (!formValido) {
          return false;
        }

        var token = $location.search().token;
        $scope.credenciais.token = token;
        Restangular.all('redefinir_senha').post($scope.credenciais)
          .then(function() {
            toast.success($filter('translate')('login.redefinir_senha_sucesso'));
            $state.go('login');
          })
          .catch(function(err) {
            $scope.error = err.data;
          })
          .finally(influuntBlockui.unblock);
      };

    }]);
