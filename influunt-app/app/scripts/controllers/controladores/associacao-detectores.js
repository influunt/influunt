'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresAssociacaoDetectoresCtrl
 * @description
 * # ControladoresAssociacaoDetectoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresAssociacaoDetectoresCtrl', ['$scope', '$state', '$controller', '$filter', 'assertControlador', 'influuntAlert', 'Restangular', 'toast', 'influuntBlockui',
    function ($scope, $state, $controller, $filter, assertControlador, influuntAlert, Restangular, toast, influuntBlockui) {
      $controller('ControladoresCtrl', {$scope: $scope});
      $controller('ConfirmacaoNadaHaPreencherCtrl', {$scope: $scope});

      var atualizaPosicoesDetectores, atualizaEstagiosComDetector, excluirDetectorNoCliente,
      abreModalConfiguracaoDetector;

      /**
       * Pré-condições para acesso à tela de associcao de detectores: Somente será possivel
       * continuar nesta tela se o controlador já possuir estágios e aneis.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertAssociacaoDetectores = function() {
        var valid = assertControlador.hasAneis($scope.objeto) && assertControlador.hasEstagios($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.transicoes_proibidas', {id: $scope.objeto.id});
        }

        return valid;
      };

      /**
       * { function_description }
       *
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.inicializaAssociacaoDetectores = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertAssociacaoDetectores()) {
            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
            $scope.objeto.estagios = _.orderBy($scope.objeto.estagios, ['posicao']);
            $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});

            $scope.selecionaAnelAssociacaoDetectores(0);
            atualizaEstagiosComDetector();

            $scope.podeDetectorPedestre = _.filter($scope.objeto.gruposSemaforicos, {tipo: 'PEDESTRE'}).length > 0;
            $scope.podeDetectorVeicular = _.filter($scope.objeto.gruposSemaforicos, {tipo: 'VEICULAR'}).length > 0;

            $scope.inicializaConfirmacaoNadaHaPreencher();
            $scope.atualizaTotalDetectoresPorAnel();
            return _.isArray($scope.currentDetectores) && $scope.selecionaDetector($scope.currentDetectores[0], 0);
          }
        });
      };

      $scope.limpaTempoDeteccao = function(detector) {
        detector.tempoAusenciaDeteccao = null;
        detector.tempoDeteccaoPermanente = null;
      };

      $scope.atribuiTempoDeteccao = function(detector) {
        detector.tempoAusenciaDeteccao = $scope.objeto.ausenciaDeteccaoMin;
        detector.tempoDeteccaoPermanente = $scope.objeto.deteccaoPermanenteMin;
      };

      /**
       * Associa/Desassocia um estagio a um detector.
       *
       * @param      {<type>}  estagio   The estagio
       * @param      {<type>}  detector  The detector
       */
      $scope.toggleAssociacaoDetector = function(estagio, detector) {
        // Não deverá permitir a associação de dois detectores para um mesmo estágio.
        if (estagio.temDetector && (!detector.estagio || detector.estagio.idJson !== estagio.idJson)) {
          return false;
        }

        if (detector.estagio && detector.estagio.idJson === estagio.idJson) {
          detector.estagio = {};
          estagio.detector = {};
        } else {
          detector.estagio = { idJson: estagio.idJson };
          estagio.detector = { idJson: detector.idJson };
        }

        atualizaEstagiosComDetector();
        abreModalConfiguracaoDetector(detector);
      };

      /**
       * Adiciona um novo detector à lista de detectores do controlador.
       */
      $scope.adicionaDetector = function(tipo) {
        var posicao = _.filter($scope.objeto.detectores, {tipo: tipo}).length + 1;
        var detector = {
          idJson: UUID.generate(),
          anel: { idJson: $scope.currentAnel.idJson },
          controlador: { idJson: $scope.objeto.idJson },
          monitorado: true,
          posicao: posicao,
          tipo: tipo,
          tempoAusenciaDeteccao: 0,
          tempoDeteccaoPermanente: 0
        };

        $scope.objeto.detectores = $scope.objeto.detectores || [];
        $scope.currentAnel.detectores = $scope.currentAnel.detectores || [];
        $scope.currentAnel.detectores.push({idJson: detector.idJson});
        $scope.objeto.detectores.push(detector);

        $scope.atualizaDetectores();
        atualizaPosicoesDetectores();
        $scope.verificaConfirmacaoNadaHaPreencher();
        $scope.atualizaTotalDetectoresPorAnel();
      };

      $scope.atualizaTotalDetectoresPorAnel = function(){
        $scope.maxDetectoresPorAnel = $scope.currentEstagios.length - $scope.currentDetectores.length;
      };

      excluirDetectorNoCliente = function(detector) {
        var idJson = detector.idJson;
        var detectorIndex = _.findIndex($scope.objeto.detectores, {idJson: idJson});
        $scope.objeto.detectores.splice(detectorIndex, 1);

        detectorIndex = _.findIndex($scope.currentAnel.detectores, {idJson: idJson});
        $scope.currentAnel.detectores.splice(detectorIndex, 1);

        $scope.atualizaDetectores();
        atualizaPosicoesDetectores();
        atualizaEstagiosComDetector();
        $scope.verificaConfirmacaoNadaHaPreencher();
        $scope.atualizaTotalDetectoresPorAnel();
      };

      $scope.excluirDetector = function(detector) {
        return influuntAlert
          .delete()
          .then(function(confirmado) {
            if (confirmado) {
              if (angular.isUndefined(detector.id)) {
                excluirDetectorNoCliente(detector);
              } else {
                Restangular.one('detectores', detector.id).remove()
                  .then(function() {
                    excluirDetectorNoCliente(detector);
                  })
                  .catch(function() {
                    toast.error($filter('translate')('controladores.detectores.msg_erro_apagar_detector'));
                  })
                  .finally(influuntBlockui.unblock);
              }
            }
          });
      };

      $scope.selecionaDetector = function(detector, index) {
        $scope.currentDetectorIndex = index;
        $scope.currentDetector = detector;
      };

      $scope.selecionaAnelAssociacaoDetectores = function(index) {
        $scope.selecionaAnel(index);
        $scope.atualizaEstagios();
        $scope.atualizaDetectores();
        atualizaPosicoesDetectores();
        $scope.atualizaTotalDetectoresPorAnel();
      };

      $scope.atualizaDetectores = function() {
        var ids = _.map($scope.currentAnel.detectores, 'idJson');
        $scope.currentDetectores = _
          .chain($scope.objeto.detectores)
          .filter(function(d) {
            return ids.indexOf(d.idJson) >= 0;
          })
          .orderBy(['tipo', 'posicao'])
          .value();

        $scope.currentAnel.detectores = _.map($scope.currentDetectores, function(d) {
          return {idJson: d.idJson};
        });

        return $scope.currentDetectores;
      };

      /**
       * Abre o modal de configurações de determinado controlador na primeira vez
       * que o usuário o relaciona a algum estágio.
       *
       * @param      {<type>}  detector  The detector
       */
      abreModalConfiguracaoDetector = function(detector) {
        if (!detector.configurado && _.has(detector.estagio, 'idJson')) {
          var $modal = $('#Config');
          if ($modal.length > 0) {
            $modal.modal();
          }
        }
      };

      $scope.configurarDetector = function(detector) {
        detector.configurado = true;
      };

      /**
       * Atualiza a posicao de toda a cadeia de detectores. Util quando um novo detector é
       * adicionado ou um é removido.
       */
      atualizaPosicoesDetectores = function() {
        return ['VEICULAR', 'PEDESTRE'].forEach(function(tipo) {
          return _.chain($scope.objeto.detectores)
            .filter({tipo: tipo})
            .orderBy(['posicao'])
            .each(function(d, i) {
              d.posicao = i + 1;
            })
            .value();
        });
      };

      /**
       * Anota todos os estagios que possuem detector associado.
       */
      atualizaEstagiosComDetector = function() {
        _.each($scope.currentEstagios, function(e) {e.temDetector = false;});
        var estagios = _.chain($scope.currentDetectores)
          .map('estagio').flatten()
          .filter(function(e) {
            return !!(e && e.idJson);
          }).value();

        _.each(estagios, function(e) {
          var estagio = _.find($scope.currentEstagios, {idJson: e.idJson});
          estagio.temDetector = true;
        });
      };

      $scope.possuiInformacoesPreenchidas = function(anel) {
        if(anel){
          return _.values(anel.detectores).length > 0;
        }else{
          return _.values($scope.currentDetectores).length > 0;
        }
      };
    }]);
