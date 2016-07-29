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

      var setPosicaoDetectores, atualizaPosicoesDetectores, atualizaEstagiosComDetector;

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
            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
            $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
            $scope.aneis.forEach(function(anel) {
              setPosicaoDetectores(anel.detectores);
              anel.detectores = _.orderBy(anel.detectores, ['tipo', 'posicao']);
              anel.estagios.forEach(function(estagio, index) {
                estagio.posicao = index + 1;
              });

              anel.estagios = _.orderBy(anel.estagios, ['posicao'], ['asc']);
            });

            $scope.selecionaAnel(0);
            atualizaEstagiosComDetector();
            return _.isArray($scope.currentAnel.detectores) && $scope.selecionaDetector(0);
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
        // Não deverá alterar a associação se já existir um detector para o estágio.
        if (estagio.temDetector && detector.estagio.id !== estagio.id) {
          return false;
        }

        if (detector.estagio && detector.estagio.id === estagio.id) {
          detector.estagio = {};
        } else {
          detector.estagio = { id: estagio.id };
        }

        atualizaEstagiosComDetector();
      };

      /**
       * Adiciona um novo detector à lista de detectores do controlador.
       */
      $scope.adicionaDetector = function(tipo) {
        $scope.currentAnel.detectores = $scope.currentAnel.detectores || [];
        $scope.currentAnel.detectores.push({
          anel: { id: $scope.currentAnel.id },
          controlador: { id: $scope.objeto.id },
          posicao: $scope.currentAnel.detectores.length + 1,
          tipo: tipo
        });

        atualizaPosicoesDetectores();
      };

      $scope.excluirDetector = function(detector) {
        return influuntAlert
          .delete()
          .then(function(confirmado) {
            if (confirmado) {
              $scope.currentAnel.detectores.splice(detector, 1);
              atualizaPosicoesDetectores();
              atualizaEstagiosComDetector();
            }
          });
      };

      $scope.selecionaDetector = function(index) {
        $scope.currentDetectorIndex = index;
        $scope.currentDetector = $scope.currentAnel.detectores[index];
      };

      /**
       * Cria o valor de posicao para os diversos detectores. Importante notar que as
       * posições serão criadas por tipo de detector, ou seja, dados que haja o tipo A
       * e B, haverão os detectores A1, A2, A3..., B1, B2, B3...
       *
       * @param      {<type>}  detectores  The detectores
       * @return     {<type>}  { description_of_the_return_value }
       */
      setPosicaoDetectores = function(detectores) {
        var posicoes = {};
        return detectores && detectores.forEach(function(detector) {
          posicoes[detector.tipo] = ++posicoes[detector.tipo] || 1;
          detector.posicao = posicoes[detector.tipo];
        });
      };

      /**
       * Atualiza a posicao de toda a cadeia de detectores. Util quando um novo detector é
       * adicionado ou um é removido.
       */
      atualizaPosicoesDetectores = function() {
        return ['VEICULAR', 'PEDESTRE'].forEach(function(tipo) {
          return _.chain($scope.currentAnel.detectores)
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
        _.each($scope.currentAnel.estagios, function(e) {e.temDetector = false;});
        var estagios = _.chain($scope.currentAnel.detectores)
          .map('estagio').flatten()
          .filter(function(e) {
            return !!(e && e.id);
          }).value();

        _.each(estagios, function(e) {
          var estagio = _.find($scope.currentAnel.estagios, {id: e.id});
          estagio.temDetector = true;
        });
      };
    }]);
