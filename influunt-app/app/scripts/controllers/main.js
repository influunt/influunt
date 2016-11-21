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

      var checkRoleForMenus, atualizaDadosDinamicos, registerWatchers, getControlador, exibirAlerta,
          statusControladoresWatcher, alarmesEFalhasWatcher, trocaPlanoWatcher, onlineOfflineWatcher;

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
        pahoProvider.connect()
          .then(function() {
            pahoProvider.register(eventosDinamicos.STATUS_CONTROLADORES, statusControladoresWatcher);
            pahoProvider.register(eventosDinamicos.ALARMES_FALHAS, alarmesEFalhasWatcher);
            pahoProvider.register(eventosDinamicos.TROCA_PLANO, trocaPlanoWatcher);
            pahoProvider.register(eventosDinamicos.CONTROLADOR_ONLINE, onlineOfflineWatcher);
            pahoProvider.register(eventosDinamicos.CONTROLADOR_OFFLINE, onlineOfflineWatcher);
          });
      };

      onlineOfflineWatcher = function(payload) {
        var mensagem = JSON.parse(payload);
        $scope.statusObj.onlines = $scope.statusObj.onlines || {};

        return getControlador(mensagem.idControlador)
          .then(function(controlador) {
            var isOnline = mensagem.tipoMensagem === 'CONTROLADOR_ONLINE';
            $scope.statusObj.onlines[mensagem.idControlador] = isOnline;
            atualizaDadosDinamicos();

            var msg = isOnline ?
              'controladores.mapaControladores.alertas.controladorOnline' :
              'controladores.mapaControladores.alertas.controladorOffline';

            msg = $filter('translate')(msg, {CONTROLADOR: controlador.CLC});
            exibirAlerta(msg, !isOnline);
          });
      };

      trocaPlanoWatcher = function(payload) {
        var mensagem = JSON.parse(payload);

        return getControlador(mensagem.idControlador)
          .then(function(controlador) {
            var posicaoAnel = parseInt(mensagem.conteudo.anel.posicao);
            var anel = _.find(controlador.aneis, {posicao: posicaoAnel});

            $scope.statusObj.imposicaoPlanos = $scope.statusObj.imposicaoPlanos || {};
            $scope.statusObj.modosOperacoes  = $scope.statusObj.modosOperacoes || {};

            $scope.statusObj.modosOperacoes[mensagem.idControlador] = _.get(mensagem, 'conteudo.plano.modoOperacao');
            $scope.statusObj.imposicaoPlanos[mensagem.idControlador] = _.get(mensagem, 'conteudo.planoImposto');
            atualizaDadosDinamicos();

            var msg = $filter('translate')(
              'controladores.mapaControladores.alertas.trocaPlanoAnelEControlador',
              {ANEL: anel.CLA, CONTROLADOR: controlador.CLC}
            );
            exibirAlerta(msg);
          });
      };

      alarmesEFalhasWatcher = function(payload) {
        var mensagem = JSON.parse(payload);
        $scope.statusObj.erros = $scope.statusObj.erros || {};

        if (_.get(mensagem, 'conteudo.tipoEvento.tipoEventoControlador') === 'FALHA') {
          return getControlador(mensagem.idControlador)
            .then(function(controlador) {
              var falha = {
                clc: controlador.CLC,
                data: mensagem.carimboDeTempo,
                endereco: controlador.nomeEndereco,
                id: mensagem.idControlador,
                motivoFalha: _.get(mensagem, 'conteudo.descricaoEvento')
              };

              $scope.statusObj.erros.data = $scope.statusObj.erros.data || [];
              $scope.statusObj.erros.data.push(falha);
              atualizaDadosDinamicos();


              var msg = $filter('translate')('controladores.mapaControladores.alertas.controladorEmFalha', {CONTROLADOR: controlador.CLC});
              if (mensagem.conteudo && _.isArray(mensagem.conteudo.params)) {
                var posicaoAnel = mensagem.conteudo.params[0];
                var anel = _.find(controlador.aneis, {posicao: posicaoAnel});
                msg = $filter('translate')('controladores.mapaControladores.alertas.anelEmFalha', {ANEL: anel.CLA});
              }

              exibirAlerta(msg, true);
            })
            .finally(influuntBlockui.unblock);
        }
      };

      statusControladoresWatcher = function(payload) {
        var mensagem = JSON.parse(payload);
        $scope.statusObj.status = $scope.statusObj.status || {};

        return getControlador(mensagem.idControlador)
          .then(function(controlador) {
            $scope.statusObj.status[mensagem.idControlador] = _.get(mensagem, 'conteudo.status');
            atualizaDadosDinamicos();

            var msg = $filter('translate')(
              'controladores.mapaControladores.alertas.mudancaStatusControlador',
              {CONTROLADOR: controlador.CLC}
            );
            exibirAlerta(msg);
          });
      };

      atualizaDadosDinamicos = function() {
        $scope.dadosStatus = _.countBy(_.values($scope.statusObj.status), _.identity);
        $scope.dadosOnlines = _.countBy(_.values($scope.statusObj.onlines), _.identity);
        $scope.modosOperacoes = _.countBy(_.values($scope.statusObj.modosOperacoes), _.identity);
        $scope.planosImpostos = _.countBy(_.values($scope.statusObj.imposicaoPlanos), _.identity);
        $scope.errosControladores = _.orderBy($scope.statusObj.erros.data, 'data', 'desc');
      };

      exibirAlerta = function(msg, isPrioritario) {
        if ($scope.eventos.exibirAlertas || isPrioritario) {
          toast.warn(msg);
        }
      };

      getControlador = function(idControlador) {
        return Restangular.one('controladores', idControlador).get({}, {'x-prevent-block-ui': true});
      };

      $scope.getUsuario = function() {
        return JSON.parse(localStorage.usuario);
      };

      $http.get('/json/menus.json').then(function(res) {
        $scope.menus = res.data;
        checkRoleForMenus();
      });
    }]);
