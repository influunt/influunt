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

      var checkRoleForMenus, atualizaDadosDinamicos, registerWatchers, getControlador, logout, loadAlarmesEFalhas,
          atualizaOnlineOffline, atualizaStatusLogicos, atualizaModoOperacao, atualizaStatusFisicos, isChartEmpty;

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
              logout();
            }
          });
      };

      $scope.loadDashboard = function() {
        _.set($scope.$root, 'eventos.exibirTodosAlertas', JSON.parse(localStorage.exibirAlertas || 'false'));

        Restangular.one('monitoramento', 'status_controladores')
          .get()
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
          });
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

      isChartEmpty = function(chartData) {
        return _.chain(chartData).map('value').sum().value() === 0;
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
        $timeout(function() {
          atualizaOnlineOffline();
          atualizaModoOperacao();
          atualizaStatusFisicos();
          atualizaStatusLogicos();

          $scope.planosImpostos = _.countBy(_.values($scope.statusObj.imposicaoPlanos), _.identity);
          $scope.errosControladores = _
            .chain($scope.statusObj.erros)
            .reject(function(erro) { return erro.tipo.match(/^REMOCAO/); })
            .orderBy('data', 'desc')
            .value();
        });
      };

      atualizaModoOperacao = function() {
        $scope.modosOperacoesPorAneis = _
          .chain($scope.statusObj.modosOperacoes)
          .values()
          .reduce(function(result, obj) {
            result[obj.modoOperacao] = result[obj.modoOperacao] || 0;
            result[obj.modoOperacao]++;
            return result;
          }, {})
          .value();

        $scope.modosOperacoesChart = [
          {
            label: $filter('translate')('planos.modosOperacao.TEMPO_FIXO_COORDENADO'),
            value: _.get($scope.modosOperacoesPorAneis, 'TEMPO_FIXO_COORDENADO') || 0
          },
          {
            label: $filter('translate')('planos.modosOperacao.TEMPO_FIXO_ISOLADO'),
            value: _.get($scope.modosOperacoesPorAneis, 'TEMPO_FIXO_ISOLADO') || 0
          },
          {
            label: $filter('translate')('planos.modosOperacao.ATUADO'),
            value: _.get($scope.modosOperacoesPorAneis, 'ATUADO') || 0
          },
          {
            label: $filter('translate')('planos.modosOperacao.INTERMITENTE'),
            value: _.get($scope.modosOperacoesPorAneis, 'INTERMITENTE') || 0
          },
          {
            label: $filter('translate')('planos.modosOperacao.MANUAL'),
            value: _.get($scope.modosOperacoesPorAneis, 'MANUAL') || 0
          },
          {
            label: $filter('translate')('planos.modosOperacao.APAGADO'),
            value: _.get($scope.modosOperacoesPorAneis, 'APAGADO') || 0
          }
        ];

        $scope.isModosOperacoesChartEmpty = isChartEmpty($scope.modosOperacoesChart);
      };

      atualizaStatusFisicos = function() {
        $scope.statusFisicoControladores = _
          .chain($scope.statusObj.status)
          .values()
          .map('statusDevice')
          .countBy(_.identity)
          .value();

        $scope.statusFisicoAneis = _.chain($scope.statusObj.status)
          .values()
          .map(function(obj) {
            return _.values(obj.statusAneis);
          })
          .flatten()
          .countBy(_.identity)
          .value();

        $scope.dadosStatusChart = [
          {
            label: $filter('translate')('main.operacaoNormal'),
            value: $scope.statusFisicoAneis.NORMAL || 0
          },
          {
            label: $filter('translate')('main.operandoComFalhas'),
            value: $scope.statusFisicoAneis.COM_FALHA || 0
          },
          {
            label: $filter('translate')('main.amareloIntermitentePorfalha'),
            value: $scope.statusFisicoAneis.AMARELO_INTERMITENTE_POR_FALHA || 0
          },
          {
            label: $filter('translate')('main.apagadoPorFalha'),
            value: $scope.statusFisicoAneis.APAGADO_POR_FALHA || 0
          },
          {
            label: $filter('translate')('main.manual'),
            value: $scope.statusFisicoAneis.MANUAL || 0
          }
        ];

        $scope.isDadosStatusChartEmpty = isChartEmpty($scope.dadosStatusChart);
      };

      atualizaStatusLogicos = function() {
        $scope.statusLogicoControladores = _.chain($scope.statusObj.statusControladoresLogicos).values().countBy(_.identity).value();
        $scope.statusLogicoAneis = _
          .chain($scope.statusObj.statusControladoresLogicos)
          .map(function(value, key) {
             return {
               status: value,
               quantidadeAneis: $scope.statusObj.aneisPorControlador[key]
             };
           })
           .reduce(function(result, obj) {
             result[obj.status] = result[obj.status] || 0;
             result[obj.status] += obj.quantidadeAneis;
             return result;
           }, {})
           .value();

        $scope.statusLogicosChart = [
          {
            label: $filter('translate')('EM_CONFIGURACAO'),
            value: $scope.statusLogicoControladores.EM_CONFIGURACAO || 0
          },
          {
            label: $filter('translate')('CONFIGURADO'),
            value: $scope.statusLogicoControladores.CONFIGURADO || 0
          },
          {
            label: $filter('translate')('SINCRONIZADO'),
            value: $scope.statusLogicoControladores.SINCRONIZADO || 0
          },
          {
            label: $filter('translate')('EDITANDO'),
            value: $scope.statusLogicoControladores.EDITANDO || 0
          }
        ];

        $scope.isStatusLogicosChartEmpty = isChartEmpty($scope.statusLogicosChart);
      };

      atualizaOnlineOffline = function() {
        $scope.dadosOnlines = _.countBy(_.values($scope.statusObj.onlines), _.identity);
        $scope.aneisOnlines = _
          .chain($scope.statusObj.onlines)
          .map(function(value, key) {
             return {
               isOnline: value,
               quantidadeAneis: $scope.statusObj.aneisPorControlador[key]
             };
           })
           .reduce(function(result, obj) {
             result[obj.isOnline] = result[obj.isOnline] || 0;
             result[obj.isOnline] += obj.quantidadeAneis;
             return result;
           }, {})
           .value();

        $scope.onlineOfflineChart = [
          {
            label: $filter('translate')('main.online'),
            value: $scope.dadosOnlines['true'] || 0
          },
          {
            label: $filter('translate')('main.offline'),
            value: $scope.dadosOnlines['false'] || 0
          }
        ];

        $scope.isOnlineOfflineChartEmpty = isChartEmpty($scope.onlineOfflineChart);
      };

      getControlador = function(idControlador) {
        return Restangular.one('controladores', idControlador).get({}, {'x-prevent-block-ui': true});
      };

      logout = function() {
        Restangular.one('logout', localStorage.token).remove()
          .catch(function(err) {
            if (err.status === 401) {
              err.data.forEach(function(error) {
                influuntAlert.alert($filter('translate')('geral.atencao'), error.message);
              });
            }
          })
        .finally(function() {
          localStorage.removeItem('token');
          $state.go('login');
          influuntBlockui.unblock();
        });
      };

      loadAlarmesEFalhas = function() {
        $scope.$root.alarmesAtivados = {};
        var usuarioId = $scope.getUsuario().id;
        return Restangular.one('usuarios', usuarioId).all('alarmes_e_falhas').getList()
          .then(function(res) {
            _.each(res, function(obj) {
              $scope.$root.alarmesAtivados[obj.chave] = true;
            });
          }).finally(influuntBlockui.unblock);
      };

      $scope.getUsuario = function() {
        return JSON.parse(localStorage.usuario);
      };

      $scope.$watch('$root.eventos.exibirTodosAlertas', function(exibirAlertas) {
        if (!_.isUndefined(exibirAlertas)) {
          localStorage.setItem('exibirAlertas', exibirAlertas);
        }
      });

      $scope.$on('IdleStart', function() {
        $('#modal-idle-warning').modal('show');
      });

      $scope.$on('IdleEnd', function() {
        $('#modal-idle-warning').modal('hide');
      });

      $scope.$on('IdleTimeout', function() {
        $('.modal').modal('hide');
        Idle.unwatch();
        logout();

        influuntAlert.alert(
          $filter('translate')('geral.mensagens.sessaoExpirada.titulo'),
          $filter('translate')('geral.mensagens.sessaoExpirada.mensagem')
        );
      });

      $scope.$on('influuntApp.mqttConnectionRecovered', function() {
        $scope.loadDashboard();
        loadAlarmesEFalhas();
      });

      $http.get('/json/menus.json').then(function(res) {
        $scope.menus = res.data;
        checkRoleForMenus();
      });

      $scope.loadDashboard();
      loadAlarmesEFalhas();

      $scope.$root.$on('$stateChangeSuccess', registerWatchers);
    }]);
