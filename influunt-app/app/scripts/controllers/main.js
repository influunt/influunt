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
                           'Restangular', 'influuntBlockui', 'PermissionsService', 'pahoProvider', 'toast',
                           'eventosDinamicos',
    function MainCtrl($scope, $state, $filter, $controller, $http, $timeout, influuntAlert,
                      Restangular, influuntBlockui, PermissionsService, pahoProvider, toast,
                      eventosDinamicos) {
      // Herda todo o comportamento de breadcrumbs.
      $controller('BreadcrumbsCtrl', {$scope: $scope});

      var checkRoleForMenus, atualizaDadosDinamicos, registerWatchers, statusConexaoControladoresWatcher;

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
            $scope.statusObj = res;
            atualizaDadosDinamicos();
            registerWatchers();
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
      checkRoleForMenus = function() {
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


      registerWatchers = function() {
        pahoProvider.register(eventosDinamicos.STATUS_CONEXAO_CONTROLADORES, statusConexaoControladoresWatcher);
        // pahoProvider.register(eventosDinamicos.ALARMES_FALHAS, errosControladoresWatcher);
        // pahoProvider.register(eventosDinamicos.IMPOSICAO_PLANO_CONTROLADOR, imposicaoPlanoControladorWatcher);
        // pahoProvider.register(eventosDinamicos.MODOS_CONTROLADORES, modoControladoresWatcher);
        // pahoProvider.register(eventosDinamicos.STATUS_CONTROLADORES, statusControladoresWatcher);
      };

      statusConexaoControladoresWatcher = function(payload) {
        console.log($scope.statusObj);
        var response = JSON.parse(payload);
        $scope.statusObj.onlines[response.idControlador] = response.conectado;
        atualizaDadosDinamicos();

        if (!response.conectado) {
          toast.warn('O controlador ' + response.idControlador + ' acabou de se desconectar.', 0);
        }
      };

      atualizaDadosDinamicos = function() {
        $scope.dadosStatus = _.countBy(_.values($scope.statusObj.status), _.identity);
        $scope.dadosOnlines = _.countBy(_.values($scope.statusObj.onlines), _.identity);
        $scope.modosOperacoes = _.countBy(_.values($scope.statusObj.modosOperacoes), _.identity);
        $scope.planosImpostos = _.countBy(_.values($scope.statusObj.imposicaoPlanos), _.identity);
        $scope.errosControladores = $scope.statusObj.erros.data;
      };

      $scope.getUsuario = function() {
        return JSON.parse(localStorage.usuario);
      };

      $http.get('/json/menus.json').then(function(res) {
        $scope.menus = res.data;
        checkRoleForMenus();
      });
    }]);
