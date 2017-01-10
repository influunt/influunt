'use strict';

/**
 * @ngdoc function
 * @name influuntApp:ImporConfigCtrl
 * @description
 * # ImporConfigCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ImporConfigCtrl', ['$scope', '$controller', '$filter', 'Restangular', 'influuntBlockui', 'pahoProvider',
                                  'eventosDinamicos', '$location',
    function ($scope, $controller, $filter, Restangular, influuntBlockui, pahoProvider,
              eventosDinamicos, $location) {

      var setData, updateImposicoesEmAneis, filtraObjetosAneis, resolvePendingRequest;

      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('controladores');
      $scope.dadosControlador = {erros: ''};
      $scope.dadosTransacao = {tempoMaximoEspera: 60};

      $scope.pesquisa = {
        campos: [
          {
            nome: 'filtrarPor',
            label: 'relatorios.filtarPor',
            tipo: 'select',
            options: ['Subarea', 'Agrupamento']
          },
          {
            nome: 'subareaAgrupamento',
            label: 'relatorios.subareaAgrupamento',
            tipo: 'texto'
          },
          {
            nome: 'nomeDoEndereco',
            label: 'controladores.nomeEndereco',
            tipo: 'texto'
          },
          {
            nome: 'status',
            label: 'status.aneis',
            tipo: 'select',
            options: { NORMAL: 'NORMAL', COM_FALHA: 'COM_FALHA', AMARELO_INTERMITENTE_POR_FALHA: 'AMARELO_INTERMITENTE_POR_FALHA', APAGADO_POR_FALHA: 'APAGADO_POR_FALHA', MANUAL: 'MANUAL' }
          }
        ]
      };

      $scope.pagination = {
        perPage: 30,
        current: 1
      };

      $scope.esconderPerPage = true;

      $scope.aneisSelecionados = [];
      $scope.aneisSelecionadosObj = [];
      $scope.dataSincronizar = {};
      $scope.dataImposicaoModo = {};
      $scope.isAnelChecked = {};
      $scope.statusTransacoes = {};

      $scope.index = function() {
        if ($location.search().status) {
          _.set($scope.pesquisa, 'filtro.status.valor', $location.search().status);
          $location.search('status', null);
        }

        var query = $scope.buildQuery($scope.pesquisa);
        return Restangular.all('controladores').customGET('imposicao', query)
          .then(function(res) {
            setData(res);
            updateImposicoesEmAneis();
          });
      };

      $scope.selecionaAnel = function(anelId) {
        $scope.aneisSelecionados.push(anelId);
        filtraObjetosAneis();
      };

      $scope.desselecionaAnel = function(anelId) {
        _.pull($scope.aneisSelecionados, anelId);
        filtraObjetosAneis();
      };

      $scope.isAnelCheckedFilter = function(anel) {
        return $scope.isAnelChecked && anel && $scope.isAnelChecked[anel.id];
      };

      $scope.continuar = function(transacoesPendentes) {
        return resolvePendingRequest(_.first(transacoesPendentes), 'CONTINUE');
      };

      $scope.abortar = function(transacoesPendentes) {
        return resolvePendingRequest(_.first(transacoesPendentes), 'CANCEL');
      };

      $scope.lerDados = function(controladorId) {
        return Restangular.one('monitoramento/').customGET('erros_controladores/' + controladorId + '/historico_falha/0/60', null)
          .then(function(listaErros) {
            $scope.dadosControlador.erros = listaErros;
            return Restangular.one('controladores').customPOST({id: controladorId}, 'ler_dados');
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.limpaTransacoesAnteriores = function() {
        $scope.statusObj.transacoes = {};
      };

      resolvePendingRequest = function(transacaoId, acao) {
        return pahoProvider.connect().then(function() {
          var topic = eventosDinamicos.RESOLVE_PENDING_REQUEST.replace(':transacaoId', transacaoId);
          return pahoProvider.publish(topic, { transacaoId: transacaoId,  acao: acao });
        });
      };

      filtraObjetosAneis = function() {
        $scope.aneisSelecionadosObj = _.filter($scope.lista, function(anel) {
          return $scope.aneisSelecionados.indexOf(anel.id) >= 0;
        });

        return $scope.aneisSelecionadosObj;
      };

      setData = function(response) {
        $scope.lista = response.data;

        $scope.idsTransacoes = {};
        _.each($scope.lista, function(anel) {
          $scope.idsTransacoes[anel.controladorFisicoId] = null;
        });

        $scope.pagination.totalItems = $scope.lista.length;
      };

      updateImposicoesEmAneis = function(statusObj) {
        return Restangular.one('monitoramento', 'status_aneis').get()
          .then(function(statusObj) {
            _.set($scope, 'statusObj.status', statusObj.status);
            var statuses = statusObj.statusPlanos;
            return _.map(statuses, function(status) {
              return _
                .chain($scope.lista)
                .find({controladorFisicoId: status.idControlador, posicao: parseInt(status.anelPosicao)})
                .set('hasPlanoImposto', status.hasPlanoImposto)
                .set('modoOperacao', _.chain(status.modoOperacao).lowerCase().upperFirst().value())
                .set('saida', status.saida)
                .value();
            });
          })
        // console.log('statusObj:', statusObj)
        // debugger
        // $scope.statusAneis = statusObj.status;
        // var statuses = statusObj.statusPlanos;
        // return _.map(statuses, function(status) {
        //   return _
        //     .chain($scope.lista)
        //     .find({controladorFisicoId: status.idControlador, posicao: parseInt(status.anelPosicao)})
        //     .set('hasPlanoImposto', status.hasPlanoImposto)
        //     .set('modoOperacao', _.chain(status.modoOperacao).lowerCase().upperFirst().value())
        //     .set('saida', status.saida)
        //     .value();
        // });
      };

      $scope.$watch('statusObj.dadosControlador', function(dadosControlador) {
        if (_.isObject(dadosControlador)) {
          $scope.dadosControlador = $scope.dadosControlador || {};
          $scope.dadosControlador.conteudo = dadosControlador;
        }
      });

      $scope.$watch('statusObj.statusPlanos', function(statuses) {
        return _.isArray(statuses) && updateImposicoesEmAneis({statusPlanos: statuses});
      }, true);

      $scope.$watch('statusObj.onlines', function(onlines) {
        return _.each(onlines, function(value, key) {
          return _
            .chain($scope.lista)
            .filter({ controladorFisicoId: key })
            .each(function(anel) { anel.online = value; })
            .value();
        });
      }, true);

      $scope.$watch('statusObj.transacoes', function(transacoesPorControlador) {
        if (!_.isEmpty(transacoesPorControlador)) {
          $scope.transacoesPendentes = $scope.transacoesPendentes || [];
          if (transacoesPorControlador) {
            $scope.transacoesPendentes = _
              .chain(transacoesPorControlador)
              .values()
              .filter('isPending')
              .map('id')
              .uniq()
              .value();
          }
        }
      }, true);

      $scope.setCurrentAnel = function(anel) {
        $scope.currentAnel = anel;
      };

      $scope.statusAnel = function(anel) {
        return _.get($scope, 'statusObj.status["'+anel.controladorFisicoId+'"].statusAneis['+anel.posicao+']');
      };

      $scope.filtroStatus = function() {
        return _.get($scope.pesquisa, 'filtro.status.valor');
      };

      $scope.deveFiltrarPorStatus = function(anel) {
        return !$scope.filtroStatus() || $scope.statusAnel(anel) === $scope.filtroStatus();
      };
    }]);
