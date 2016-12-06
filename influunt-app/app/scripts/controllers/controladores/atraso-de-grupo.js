'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresEntreVerdesCtrl
 * @description
 * # ControladoresEntreVerdesCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresAtrasoDeGrupoCtrl', ['$scope', '$state', '$controller', 'assertControlador',
    function ($scope, $state, $controller, assertControlador) {

      $scope.isAtrasoDeGrupo = true;
      var inicializaTransicoes, getTransicoesDoAnel, getTransicoesComGanhoDePassagemDoAnel;

      $controller('ControladoresCtrl', {$scope: $scope});
      $controller('ConfirmacaoNadaHaPreencherCtrl', {$scope: $scope});

      /**
       * Garante que o controlador tem as condições mínimas para acessar a tela de atraso de grupo.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertAtrasoDeGrupo = function() {
        var valid = assertControlador.assertStepAtrasoDeGrupo($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.entre_verdes', {id: $scope.objeto.id});
        }

        return valid;
      };

      /**
       * Inicializa a tela de atraso de grupo: Carrega os dados necessários, ordena os aneis e estágios a partir
       * das posições.
       *
       * @return     {<type>}  { description_of_the_return_value }
       */

      $scope.setAtributos = function() {
        $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
        $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
        $scope.objeto.gruposSemaforicos = _.orderBy($scope.objeto.gruposSemaforicos, ['posicao']);
        $scope.atrasoGrupoMin = $scope.objeto.atrasoGrupoMin;
        $scope.atrasoGrupoMax = $scope.objeto.atrasoGrupoMax;
        inicializaTransicoes();
      };

      $scope.inicializaAtrasoDeGrupo = function(index) {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertAtrasoDeGrupo()) {
            $scope.selecionaAnel(index);
            $scope.atualizaGruposSemaforicos();
            $scope.selecionaGrupoSemaforico($scope.currentGruposSemaforicos[0], 0);
            $scope.setAtributos();
            $scope.inicializaConfirmacaoNadaHaPreencher();
          }
        });
      };

      inicializaTransicoes = function() {
        $scope.objeto.atrasosDeGrupo = $scope.objeto.atrasosDeGrupo || [];
        var allTransicoes = _.union($scope.objeto.transicoes, $scope.objeto.transicoesComGanhoDePassagem);
        _.forEach(allTransicoes, function(transicao) {
          if (typeof transicao.atrasoDeGrupo === 'undefined') {
            transicao.atrasoDeGrupo = { idJson: UUID.generate(), atrasoDeGrupo: $scope.atrasoGrupoMin };
            $scope.objeto.atrasosDeGrupo.push(transicao.atrasoDeGrupo);
          } else {
            var atrasoDeGrupo = _.find($scope.objeto.atrasosDeGrupo, { idJson: transicao.atrasoDeGrupo.idJson });
            transicao.atrasoDeGrupo = atrasoDeGrupo;
          }
        });
      };

      $scope.selecionaAnelAtrasoDeGrupo = function(index) {
        $scope.selecionaAnel(index);
        $scope.atualizaGruposSemaforicos();
        $scope.selecionaGrupoSemaforico($scope.currentGruposSemaforicos[0], 0);
        $scope.setAtributos();
      };

      $scope.$watch('currentTransicoesComGanhoDePassagem', function() {
        if($scope.currentAnel && $scope.confirmacao){
          $scope.verificaConfirmacaoNadaHaPreencher();
        }
      }, true);

      $scope.$watch('currentTransicoes', function() {
        if($scope.currentAnel && $scope.confirmacao){
          $scope.verificaConfirmacaoNadaHaPreencher();
        }
      }, true);

      $scope.possuiInformacoesPreenchidas = function() {
        var totalNaoPreenchido, total, totalNaoPreenchidoComGanhoDePassagem, totalComGanhoDePassagem;
        if($scope.currentTransicoes && $scope.currentTransicoesComGanhoDePassagem){
          var transicoesDoAnel = getTransicoesDoAnel();
          var transicoesComGanhoDePassagemDoAnel = getTransicoesComGanhoDePassagemDoAnel();

          totalNaoPreenchido = _.filter(transicoesDoAnel, function(i) {
            return parseInt(i.atrasoDeGrupo.atrasoDeGrupo) === 0;
          }).length;;
          total = _.values(transicoesDoAnel).length;

          totalNaoPreenchidoComGanhoDePassagem = _.filter(transicoesComGanhoDePassagemDoAnel, function(i) {
            return parseInt(i.atrasoDeGrupo.atrasoDeGrupo) === 0;
          }).length;;
          totalComGanhoDePassagem = _.values(transicoesComGanhoDePassagemDoAnel).length;

          return totalNaoPreenchido < total || totalNaoPreenchidoComGanhoDePassagem < totalComGanhoDePassagem;
        }

        return false;
      };

      $scope.possuiErroAtrasoDeGrupo = function(anelIndex, grupoSemaforicoIndex, transicaoIndex) {
        var errors = _.get($scope.errors, 'aneis[' + anelIndex + '].gruposSemaforicos[' + grupoSemaforicoIndex + '].transicoes[' + transicaoIndex + ']');
        return _.isObject(errors) && Object.keys(errors).length > 0;
      };

      $scope.beforeSubmitForm = function() {
        _.forEach($scope.objeto.aneis, function(anel) {
          var gsIdJson = _.map(anel.gruposSemaforicos, 'idJson');
          anel.gruposSemaforicos = _
            .chain($scope.objeto.gruposSemaforicos)
            .filter(function(gs) { return gsIdJson.indexOf(gs.idJson) > -1; })
            .map(function(gs) { return { idJson: gs.idJson }; })
            .value();
        });
      };

      getTransicoesDoAnel = function() {
        var gruposIds = _.map($scope.currentAnel.gruposSemaforicos, 'idJson');
        return _
          .chain($scope.objeto.gruposSemaforicos)
          .filter(function(g) {
            return gruposIds.indexOf(g.idJson) >= 0;
          })
          .map('transicoes')
          .flatten()
          .map(function(i){
            return _.find($scope.objeto.transicoes, {idJson: i.idJson});
          })
          .value();

      };

      getTransicoesComGanhoDePassagemDoAnel = function() {
        var gruposIds = _.map($scope.currentAnel.gruposSemaforicos, 'idJson');
        return _
          .chain($scope.objeto.gruposSemaforicos)
          .filter(function(g) {
            return gruposIds.indexOf(g.idJson) >= 0;
          })
          .map('transicoesComGanhoDePassagem')
          .flatten()
          .map(function(i){
            return _.find($scope.objeto.transicoesComGanhoDePassagem, {idJson: i.idJson});
          })
          .value();
      };

    }]);
