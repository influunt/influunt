'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('LoginCtrl', ['$scope', 'Restangular', '$state', '$filter', 'influuntAlert', 'toast', '$location', 'influuntBlockui', 'PermissionsService',
    function LoginCtrl ($scope, Restangular, $state, $filter, influuntAlert, toast, $location, influuntBlockui, PermissionsService) {

      var getTokenFromLocation, saveUsuario;

      $scope.credenciais = {};
      $scope.browsersRequired = [{chrome: '51'}];
      $scope.browsersRejected = ['msie', 'msedge'];

      $scope.submitLogin = function(formValido) {
        $scope.submited = true;
        if (!formValido) {
          return false;
        }

        Restangular.all('login').post($scope.credenciais)
          .then(function(res) {
            PermissionsService.setUsuario(res);
            PermissionsService.loadPermissions()
              .then(function() {
                $state.go('app.main');
              });
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
        var token = getTokenFromLocation();
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

        var token = getTokenFromLocation();
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

      getTokenFromLocation = function() {
        var resultMatch = location.search.match(/token=\w+/);
        var token;
        if(_.isArray(resultMatch)) {
          token = resultMatch[0].split('=')[1];
        }
        return token;
      };

      saveUsuario = function(res) {
        var usuario = _.pick(res, ['id', 'login', 'email', 'root', 'permissoes']);
        if (res.area) {
          usuario.area = { idJson: res.area.idJson };
        }
        localStorage.setItem('usuario', JSON.stringify(usuario));
      };

    }]);
