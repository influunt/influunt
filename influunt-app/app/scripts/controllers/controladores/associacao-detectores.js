'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresAssociacaoDetectoresCtrl
 * @description
 * # ControladoresAssociacaoDetectoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresAssociacaoDetectoresCtrl', ['$scope', '$state', '$controller', 'assertControlador', 'influuntAlert',
    function ($scope, $state, $controller, assertControlador, influuntAlert) {
      $controller('ControladoresCtrl', {$scope: $scope});

      var atualizaPosicoesDetectores, atualizaEstagiosComDetector;

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
            return _.isArray($scope.currentDetectores) && $scope.selecionaDetector($scope.currentDetectores[0], 0);
          }
        });
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
        } else {
          detector.estagio = { idJson: estagio.idJson };
        }

        atualizaEstagiosComDetector();
      };

      /**
       * Adiciona um novo detector à lista de detectores do controlador.
       */
      $scope.adicionaDetector = function(tipo) {
        var posicao = _.filter($scope.currentDetectores, {tipo: tipo}).length + 1;
        var detector = {
          idJson: UUID.generate(),
          anel: { idJson: $scope.currentAnel.idJson },
          controlador: { idJson: $scope.objeto.idJson },
          posicao: posicao,
          tipo: tipo
        };

        $scope.objeto.detectores = $scope.objeto.detectores || [];
        $scope.currentAnel.detectores = $scope.currentAnel.detectores || [];
        $scope.currentAnel.detectores.push({idJson: detector.idJson});
        $scope.objeto.detectores.push(detector);

        $scope.atualizaDetectores();
        atualizaPosicoesDetectores();
      };

      $scope.excluirDetector = function(detector) {
        return influuntAlert
          .delete()
          .then(function(confirmado) {
            if (confirmado) {
              var idJson = detector.idJson;
              var detectorIndex = _.findIndex($scope.objeto.detectores, {idJson: idJson});
              $scope.objeto.detectores.splice(detectorIndex, 1);

              detectorIndex = _.findIndex($scope.currentAnel.detectores, {idJson: idJson});
              $scope.currentAnel.detectores.splice(detectorIndex, 1);

              $scope.atualizaDetectores();
              atualizaPosicoesDetectores();
              atualizaEstagiosComDetector();
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

          return $scope.currentDetectores;
      };

      /**
       * Atualiza a posicao de toda a cadeia de detectores. Util quando um novo detector é
       * adicionado ou um é removido.
       */
      atualizaPosicoesDetectores = function() {
        return ['VEICULAR', 'PEDESTRE'].forEach(function(tipo) {
          return _.chain($scope.currentDetectores)
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
    }]);
