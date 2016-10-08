'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('MainCtrl', ['$scope', '$state', '$filter', '$controller', '$http', '$timeout', 'influuntAlert',
                           'Restangular', 'influuntBlockui', 'PermissionsService',
    function MainCtrl($scope, $state, $filter, $controller, $http, $timeout, influuntAlert,
                      Restangular, influuntBlockui, PermissionsService) {
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
            $scope.dadosStatus = _.countBy(_.values(res.status), _.identity);
            $scope.dadosOnlines = _.countBy(_.values(res.onlines), _.identity);
            $scope.modosOperacoes = _.countBy(_.values(res.modosOperacoes), _.identity);
            $scope.planosImpostos = _.countBy(_.values(res.imposicaoPlanos), _.identity);
            $scope.errosControladores = res.erros.data;
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
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.menuVisible = {};
      var checkRoleForMenus = function() {
        return _.each($scope.menus, function(menu) {
          $scope.menuVisible[menu.name] = false;
          if (!_.isArray(menu.children)) {
            $scope.menuVisible[menu.name] = true;
            return true;
          }

          _.map(menu.children, function(subMenu) {
            var roleName = _.get($scope.$state.get(subMenu.route), 'data.permissions.only');
            PermissionsService.checkRole(roleName).then(function() { $scope.menuVisible[menu.name] = true; });
          });
        });
      };


      $scope.getUsuario = function() {
        return JSON.parse(localStorage.usuario);
      };

      $http.get('/json/menus.json').then(function(res) {
        $scope.menus = res.data;
        checkRoleForMenus();
      });
    }]);
