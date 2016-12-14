'use strict';

/**
 * @ngdoc function
 * @name influuntApp:ImporConfigCtrl
 * @description
 * # ImporConfigCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ImporConfigCtrl', ['$scope', '$controller', '$filter', 'Restangular',
                                  'influuntBlockui', 'pahoProvider', 'eventosDinamicos',
    function ($scope, $controller, $filter, Restangular,
              influuntBlockui, pahoProvider, eventosDinamicos) {

      var setData, updateImposicoesEmAneis, filtraObjetosAneis;

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
        var query = $scope.buildQuery($scope.pesquisa);
        return Restangular.all('controladores').customGET('imposicao', query)
          .then(function(res) {
            setData(res);
            return Restangular.one('monitoramento', 'status_aneis').get();
          })
          .then(updateImposicoesEmAneis)
          .finally(influuntBlockui.unblock);
      };

      filtraObjetosAneis = function() {
        $scope.aneisSelecionadosObj = _.filter($scope.lista, function(anel) {
          return $scope.aneisSelecionados.indexOf(anel.id) >= 0;
        });

        return $scope.aneisSelecionadosObj;
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

      var resolvePendingRequest = function(transacaoId, acao) {
        return pahoProvider.connect().then(function() {
          return pahoProvider.publish(eventosDinamicos.RESOLVE_PENDING_REQUEST, {
            transacaoId: transacaoId,  acao: acao
          });
        });
      };

      $scope.continuar = function(transacoesPendentes) {
        return resolvePendingRequest(_.first(transacoesPendentes), 'CONTINUE');
      };

      $scope.abortar = function(transacoesPendentes) {
        return resolvePendingRequest(_.first(transacoesPendentes), 'CANCEL');
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
        var statuses = statusObj.statusPlanos;
        return _.map(statuses, function(status) {
          return _
            .chain($scope.lista)
            .find({controladorFisicoId: status.idControlador, posicao: parseInt(status.anelPosicao)})
            .set('hasPlanoImposto', status.hasPlanoImposto)
            .set('modoOperacao', _.chain(status.modoOperacao).lowerCase().upperFirst().value())
            .set('inicio', status.inicio || moment().format('DD/MM/YYYY HH:mm:ss'))
            .value();
        });
      };

      $scope.lerDados = function(controladorId) {
        return Restangular.one('monitoramento/').customGET('erros_controladores/' + controladorId + '/historico_falha/0/60', null)
          .then(function(listaErros) {
            $scope.dadosControlador.erros = listaErros;
            return Restangular.one('controladores').customPOST({id: controladorId}, 'ler_dados');
          })
          .finally(influuntBlockui.unblock);
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

      $scope.$watch('statusObj.transacoes', function(transacoesPorControlador) {
        $scope.transacoesPendentes = $scope.transacoesPendentes || [];
        if (transacoesPorControlador) {
          $scope.transacoesPendentes = _
            .chain(transacoesPorControlador)
            .values()
            .filter({status: 'PENDING'})
            .map('id')
            .uniq()
            .value();
        }
      }, true);
    }]);
