'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('MainCtrl', ['$scope', '$state', '$filter', '$controller', '$http', 'influuntAlert', 'Restangular', 'influuntBlockui',
    function MainCtrl($scope, $state, $filter, $controller, $http, influuntAlert, Restangular, influuntBlockui) {
      // Herda todo o comportamento de breadcrumbs.
      $controller('BreadcrumbsCtrl', {$scope: $scope});

      $scope.pagination = {
        current: 1,
        maxSize: 5
      };

      $scope.sair = function() {
        influuntAlert
          .confirm(
            $filter('translate')('geral.mensagens.sair'),
            $filter('translate')('geral.mensagens.confirma_saida')
          )
          .then(function(confirmado) {
            if (confirmado) {
              Restangular.one('logout', localStorage.token).remove()
                .then(function() {
                  localStorage.removeItem('token');
                  $state.go('login');
                })
                .catch(function(err) {
                  if (err.status === 401) {
                    err.data.forEach(function(error) {
                      influuntAlert.alert($filter('translate')('geral.atencao'), error.message);
                    });
                  }
                })
                .finally(influuntBlockui.unblock);
            }
          });
      };

      $scope.loadDashboard = function() {
        Restangular.one('monitoramento', 'status_controladores').get()
          .then(function(res) {
            $scope.dados_status = _.countBy(_.values(res.status), _.identity);
            $scope.dados_onlines = _.countBy(_.values(res.onlines), _.identity);
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

      $scope.carregarControladores = function(onlines) {
        var rota = onlines ? 'controladores_onlines' : 'controladores_offlines';
        Restangular.one('monitoramento', rota).get()
          .then(function(res) {
            $scope.pagination.current = 1;
            $scope.controladores = res.data;
            $scope.online = onlines;
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

      $scope.detalheControlador = function() {
        var controladorId = $state.params.id;
        Restangular.one('monitoramento/detalhe_controlador', controladorId).get()
          .then(function(res) {
            $scope.controlador = res;
          })
          .catch(function(err) {
            if (err.status === 401) {
              err.data.forEach(function(error) {
                influuntAlert.alert($filter('translate')('geral.atencao'), error.message);
              });
            }
          });
      };


      $scope.getUsuario = function() {
        return JSON.parse(localStorage.usuario);
      };

      $http.get('/json/menus.json').then(function(res) {
        $scope.menus = res.data;
      });
    }]);
