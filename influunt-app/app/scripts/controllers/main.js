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
                           'eventosDinamicos', 'audioNotifier', 'Idle', 'alarmesDinamicoService',
    function MainCtrl($scope, $state, $filter, $controller, $http, $timeout, influuntAlert,
                      Restangular, influuntBlockui, PermissionsService,
                      eventosDinamicos, audioNotifier, Idle, alarmesDinamicoService) {
      // Herda todo o comportamento de breadcrumbs.
      $controller('BreadcrumbsCtrl', {$scope: $scope});
      Idle.watch();

      var checkRoleForMenus, atualizaDadosDinamicos, registerWatchers, getControlador, logout, loadAlarmesEFalhas;
      var LIMITE_ALARMES_FALHAS = 10;

      $scope.pagination = {
        current: 1,
        maxSize: 5
      };

      $scope.eventos = {
        exibirAlertas: false
      };

      $scope.sair = function() {
        influuntAlert
          .confirm(
            $filter('translate')('geral.mensagens.sair'),
            $filter('translate')('geral.mensagens.confirma_saida')
          )
          .then(function(confirmado) {
            if (confirmado) {
              logout();
            }
          });
      };

      $scope.loadDashboard = function() {
        Restangular.one('monitoramento', 'status_controladores')
          .get({limite_alarmes_falhas: LIMITE_ALARMES_FALHAS})
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
        if ($state.current.name !== 'app.mapa_controladores') {
          var alarmes =  alarmesDinamicoService($scope.statusObj);
          alarmes.onEventTriggered(atualizaDadosDinamicos);
          alarmes.setListaControladores(null);
          alarmes.onClickToast(null);
          alarmes.registerWatchers();
        }
      };

      atualizaDadosDinamicos = function() {
        $scope.dadosStatus = _.countBy(_.values($scope.statusObj.status), _.identity);
        $scope.dadosOnlines = _.countBy(_.values($scope.statusObj.onlines), _.identity);
        $scope.modosOperacoes = _.countBy(_.values($scope.statusObj.modosOperacoes), _.identity);
        $scope.planosImpostos = _.countBy(_.values($scope.statusObj.imposicaoPlanos), _.identity);
        $scope.errosControladores = _.orderBy($scope.statusObj.erros, 'data', 'desc');
      };

      getControlador = function(idControlador) {
        return Restangular.one('controladores', idControlador).get({}, {'x-prevent-block-ui': true});
      };

      logout = function() {
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
      };

      loadAlarmesEFalhas = function() {
        $scope.$root.alarmesAtivados = {};
        var usuarioId = $scope.getUsuario().id;
        return Restangular.one('usuarios', usuarioId).all('alarmes_e_falhas').getList()
          .then(function(res) {
            _.each(res, function(obj) {
              $scope.$root.alarmesAtivados[obj.chave] = true;
            });
          });
      };

      $scope.getUsuario = function() {
        return JSON.parse(localStorage.usuario);
      };

      $scope.$on('IdleStart', function() {
        $('#modal-idle-warning').modal('show');
      });

      $scope.$on('IdleEnd', function() {
        $('#modal-idle-warning').modal('hide');
      });

      $scope.$on('IdleTimeout', function() {
        $('#modal-idle-warning').modal('hide');
        Idle.unwatch();
        logout();

        influuntAlert.alert(
          $filter('translate')('geral.mensagens.sessaoExpirada.titulo'),
          $filter('translate')('geral.mensagens.sessaoExpirada.mensagem')
        );
      });

      $http.get('/json/menus.json').then(function(res) {
        $scope.menus = res.data;
        checkRoleForMenus();
      });

      $scope.loadDashboard();
      loadAlarmesEFalhas();

      $scope.$root.$on('$stateChangeSuccess', registerWatchers);

    }]);
