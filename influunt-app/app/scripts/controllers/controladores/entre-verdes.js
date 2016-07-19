'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresEntreVerdesCtrl
 * @description
 * # ControladoresEntreVerdesCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresEntreVerdesCtrl', ['$scope', '$state', '$controller', 'assertControlador',
    function ($scope, $state, $controller, assertControlador) {

      // $controller('ControladoresCtrl', {$scope: $scope});
      // @todo       Esta linha deverá ser substituida pela linha acima assim que o retorno dos verdes conflitantes
      //             em grupos semaforicos for corrigido.
      $controller('ControladoresTransicoesProibidasCtrl', {$scope: $scope});

      $scope.isTabelaEntreVerdes = true;

      var aneisBkp;

      /**
       * Garante que o controlador tem as condições mínimas para acessar a tela de entre verdes.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertEntreVerdes = function() {
        var valid = assertControlador.hasTransicoes($scope.objeto) && assertControlador.hasTabelasEntreVerdes($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.transicoes_proibidas', {id: $scope.objeto.id});
        }

        return valid;
      };

      $scope.limites = function(grupoSemaforico) {
        // 'Pedestre' ou 'Veicular'
        var tipoGS = grupoSemaforico.tipo.charAt(0).toUpperCase() + grupoSemaforico.tipo.substr(1).toLowerCase();
        return  {
          tempoVermelhoIntermitente: { min: $scope.objeto.vermelhoIntermitenteMin, max: $scope.objeto.vermelhoIntermitenteMax },
          tempoVermelhoLimpeza: { min: $scope.objeto['vermelhoLimpeza'+tipoGS+'Min'], max: $scope.objeto['vermelhoLimpeza'+tipoGS+'Max'] },
          tempoAtrasoGrupo: { min: $scope.objeto.atrasoGrupoMin, max: $scope.objeto.atrasoGrupoMax },
          tempoAmarelo: { min: $scope.objeto.amareloMin, max: $scope.objeto.amareloMax }
        };
      };

      $scope.sortByPosicao = function() {
        $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
        $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
        $scope.aneis.forEach(function(anel) {
          anel.gruposSemaforicos = _.orderBy(anel.gruposSemaforicos, ['posicao'], ['asc']);
          anel.gruposSemaforicos.forEach(function(grupoSemaforico) {
            grupoSemaforico.tabelasEntreVerdes = _.orderBy(grupoSemaforico.tabelasEntreVerdes, ['posicao'], ['asc']);

            grupoSemaforico.transicoes.forEach(function(transicao) {
              transicao.tabelaEntreVerdesTransicoes.forEach(function(tevTransicao) {
                if (!_.has(tevTransicao, $scope.amareloOuVermelho(grupoSemaforico, true))) {
                  tevTransicao[$scope.amareloOuVermelho(grupoSemaforico, true)] = $scope.limites(grupoSemaforico)[$scope.amareloOuVermelho(grupoSemaforico, true)].min;
                }
                if (!_.has(tevTransicao, 'tempoVermelhoLimpeza')) {
                  tevTransicao.tempoVermelhoLimpeza = $scope.limites(grupoSemaforico).tempoVermelhoLimpeza.min;
                }
                if (!_.has(tevTransicao, 'tempoAtrasoGrupo') && $scope.currentTabelaEntreVerdesIndex === 1) {
                  tevTransicao.tempoAtrasoGrupo = $scope.limites(grupoSemaforico).tempoAtrasoGrupo.min;
                }
              });
            });
          });
        });
        $scope.selecionaAnel(0);
        $scope.selecionaGrupoSemaforico(0);
      };

      /**
       * Inicializa a tela de estagios proibidos: Carrega os dados necessários, ordena os aneis e estágios a partir
       * das posições.
       *
       * @todo Este bloco faz chamado ao inicializaVerdesConflitantes para corrigir os verdes conflitantes que a API
       *       não está devolvendo.
       *
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.inicializaEntreVerdes = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertEntreVerdes()) {
            $scope.limiteTabelasEntreVerdes = $scope.objeto.modelo.configuracao.limiteTabelasEntreVerdes;
            $scope.sortByPosicao();
          }
        });
      };

      $scope.adicionarTabelaEntreVerdes = function() {
        var totalTabelasEntreVerdes = $scope.currentGrupoSemaforico.tabelasEntreVerdes.length;
        if (totalTabelasEntreVerdes < $scope.limiteTabelasEntreVerdes) {
          $scope.currentGrupoSemaforico.tabelasEntreVerdes.push({ descricao: "Nova", posicao: totalTabelasEntreVerdes + 1, grupoSemaforico: { id: $scope.currentGrupoSemaforico.id } });
          $scope.currentGrupoSemaforico.transicoes.forEach(function(transicao) {
            var transicaoCopy = angular.copy(transicao);
            transicaoCopy.tabelaEntreVerdesTransicoes = null;
            var fieldAmareloOuVermelho = $scope.amareloOuVermelho($scope.currentGrupoSemaforico, true);
            var tevTransicao = {
              transicao: angular.merge(transicaoCopy, { grupoSemaforico: { tipo: $scope.currentGrupoSemaforico.tipo } }),
              tabelaEntreVerdes: $scope.currentGrupoSemaforico.tabelasEntreVerdes[totalTabelasEntreVerdes],
              tempoVermelhoLimpeza: $scope.limites($scope.currentGrupoSemaforico).tempoVermelhoLimpeza.min
            }

            tevTransicao[fieldAmareloOuVermelho] = $scope.limites($scope.currentGrupoSemaforico)[fieldAmareloOuVermelho].min;
            transicao.tabelaEntreVerdesTransicoes.push(tevTransicao);
          });
          $scope.selecionaTabelaEntreVerdes(totalTabelasEntreVerdes);
        }
      };

      $scope.removerTabelaEntreVerdes = function(index) {
        var totalTabelasEntreVerdes = $scope.currentGrupoSemaforico.tabelasEntreVerdes.length;
        $scope.currentGrupoSemaforico.tabelasEntreVerdes.splice(index, 1);

        if ($scope.currentTabelaEntreVerdesIndex >= index) {
          if ($scope.currentTabelaEntreVerdesIndex === totalTabelasEntreVerdes - 1) {
            $scope.selecionaTabelaEntreVerdes($scope.currentTabelaEntreVerdesIndex - 1);
          } else {
            $scope.selecionaTabelaEntreVerdes($scope.currentTabelaEntreVerdesIndex);
          }
        }

        for (var i = index; i < totalTabelasEntreVerdes - 1; i++) {
          var currentPosicao = $scope.currentGrupoSemaforico.tabelasEntreVerdes[i].posicao;
          $scope.currentGrupoSemaforico.tabelasEntreVerdes[i].posicao = currentPosicao - 1;
        }
      };

      $scope.amareloOuVermelho = function(grupoSemaforico, field) {
        field = field || false;
        if (grupoSemaforico.tipo === 'PEDESTRE') {
          return field ? 'tempoVermelhoIntermitente' : 'Vermelho Intermitente';
        }
        return field ? 'tempoAmarelo' : 'Amarelo';
      };

      $scope.beforeSubmitForm = function() {
        aneisBkp = angular.copy($scope.objeto.aneis);
        $scope.aneis.forEach(function(anel) {
          anel.gruposSemaforicos.forEach(function(grupoSemaforico) {
            delete grupoSemaforico.tabelasEntreVerdes;
          });
        });
      };

      $scope.afterSubmitFormOnValidationError = function() {
        $scope.objeto.aneis = aneisBkp;
        this.sortByPosicao();
      };

      $scope.errosAmareloOuVermelho = function(grupoSemaforico, transicaoIndex) {
        var path = 'aneis['+$scope.currentAnelIndex+'].gruposSemaforicos['+$scope.currentGrupoSemaforicoIndex+'].transicoes['+transicaoIndex+'].tabelaEntreVerdesTransicoes['+$scope.currentTabelaEntreVerdesIndex+'].'+this.amareloOuVermelho(grupoSemaforico, true);
        return _.get($scope.errors, path);
      };

      $scope.possuiErroAmareloOuVermelho = function(grupoSemaforico, transicaoIndex) {
        var errors = this.errosAmareloOuVermelho(grupoSemaforico, transicaoIndex);
        return _.isArray(errors) && errors.length > 0;
      };

      $scope.errosVermelhoLimpeza = function(grupoSemaforico, transicaoIndex) {
        var path = 'aneis['+$scope.currentAnelIndex+'].gruposSemaforicos['+$scope.currentGrupoSemaforicoIndex+'].transicoes['+transicaoIndex+'].tabelaEntreVerdesTransicoes['+$scope.currentTabelaEntreVerdesIndex+'].';
        if (grupoSemaforico.tipo === 'PEDESTRE') {
          path += 'tempoVermelhoLimpezaFieldPedestre';
        } else {
          path += 'tempoVermelhoLimpezaFieldVeicular';
        }
        return _.get($scope.errors, path);
      };

      $scope.possuiErroVermelhoLimpeza = function(grupoSemaforico, transicaoIndex) {
        var errors = this.errosVermelhoLimpeza(grupoSemaforico, transicaoIndex);
        return _.isArray(errors) && errors.length > 0;
      };

      $scope.errosAtrasoGrupo = function(grupoSemaforico, transicaoIndex) {
        var path = 'aneis['+$scope.currentAnelIndex+'].gruposSemaforicos['+$scope.currentGrupoSemaforicoIndex+'].transicoes['+transicaoIndex+'].tabelaEntreVerdesTransicoes['+$scope.currentTabelaEntreVerdesIndex+'].tempoAtrasoGrupo';
        return _.get($scope.errors, path);
      };

      $scope.possuiErroAtrasoGrupo = function(grupoSemaforico, transicaoIndex) {
        var errors = this.errosAtrasoGrupo(grupoSemaforico, transicaoIndex);
        return _.isArray(errors) && errors.length > 0;
      };

      $scope.errosNumeroTabelasEntreVerdes = function() {
        var path = 'aneis['+$scope.currentAnelIndex+'].gruposSemaforicos['+$scope.currentGrupoSemaforicoIndex+'].numeroCorretoTabelasEntreVerdes';
        return _.get($scope.errors, path);
      };

      $scope.possuiErrosNumeroTabelasEntreVerdes = function() {
        var errors = this.errosNumeroTabelasEntreVerdes();
        return _.isArray(errors) && errors.length > 0;
      };

    }]);
