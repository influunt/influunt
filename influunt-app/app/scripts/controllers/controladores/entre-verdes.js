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
        $scope.objeto.gruposSemaforicos = _.orderBy($scope.objeto.gruposSemaforicos, ['posicao'], ['asc']);

        $scope.objeto.tabelasEntreVerdesTransicoes.forEach(function(tevTransicao) {
          var tabelaEntreVerde = _.find($scope.objeto.tabelasEntreVerdes, {idJson: tevTransicao.tabelaEntreVerdes.idJson});
          var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: tabelaEntreVerde.grupoSemaforico.idJson});
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

        $scope.selecionaAnelEntreVerdes(0);
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
            $scope.limiteTabelasEntreVerdes = $scope.objeto.limiteTabelasEntreVerdes;
            $scope.sortByPosicao();
          }
        });
      };

      $scope.adicionarTabelaEntreVerdes = function() {
        $scope.currentAnel.gruposSemaforicos.forEach(function(gs) {
          var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: gs.idJson});
          var totalTabelasEntreVerdes = grupoSemaforico.tabelasEntreVerdes.length;
          if (totalTabelasEntreVerdes < $scope.limiteTabelasEntreVerdes) {

            var tabelaEntreVerde =  {
              idJson: UUID.generate(),
              descricao: 'Nova',
              posicao: totalTabelasEntreVerdes + 1,
              grupoSemaforico: {
                idJson: grupoSemaforico.idJson
              },
              tabelaEntreVerdesTransicoes: []
            };
            $scope.objeto.tabelasEntreVerdes = $scope.objeto.tabelasEntreVerdes || [];
            $scope.objeto.tabelasEntreVerdes.push(tabelaEntreVerde);

            grupoSemaforico.tabelasEntreVerdes.push({ idJson: tabelaEntreVerde.idJson });
            grupoSemaforico.transicoes.forEach(function(t) {
              var transicao = _.find($scope.objeto.transicoes, {idJson: t.idJson});

              var fieldAmareloOuVermelho = $scope.amareloOuVermelho(grupoSemaforico, true);
              var tevTransicao = {
                idJson: UUID.generate(),
                transicao: { idJson: t.idJson},
                tabelaEntreVerdes: { idJson: tabelaEntreVerde.idJson },
                tempoVermelhoLimpeza: $scope.limites(grupoSemaforico).tempoVermelhoLimpeza.min
              };

              tevTransicao[fieldAmareloOuVermelho] = $scope.limites(grupoSemaforico)[fieldAmareloOuVermelho].min;

              $scope.objeto.tabelasEntreVerdesTransicoes = $scope.objeto.tabelasEntreVerdesTransicoes || [];
              $scope.objeto.tabelasEntreVerdesTransicoes.push(tevTransicao);
              transicao.tabelaEntreVerdesTransicoes.push({ idJson: tevTransicao.idJson });
              tabelaEntreVerde.tabelaEntreVerdesTransicoes.push({ idJson: tevTransicao.idJson });
            });

            $scope.atualizaTabelaEntreVerdes();
            $scope.selecionaTabelaEntreVerdes($scope.currentTabelasEntreVerdes[totalTabelasEntreVerdes], totalTabelasEntreVerdes);
          }
        });
      };

      $scope.removerTabelaEntreVerdes = function(index) {
        $scope.currentAnel.gruposSemaforicos.forEach(function(gs) {
          var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: gs.idJson});
          var totalTabelasEntreVerdes = grupoSemaforico.tabelasEntreVerdes.length;
          var tabelaEntreVerde = grupoSemaforico.tabelasEntreVerdes[index];
          var tevIndex = _.findIndex($scope.objeto.tabelasEntreVerdes, {idJson: tabelaEntreVerde.idJson});
          $scope.objeto.tabelasEntreVerdes.splice(tevIndex, 1);
          grupoSemaforico.tabelasEntreVerdes.splice(index, 1);

          // Remove as associacoes de tabelasEntreVerdesTransicoes para a tabelaEntreVerde
          // a ser removida.
          $scope.objeto.tabelasEntreVerdesTransicoes = _
            .chain($scope.objeto.tabelasEntreVerdesTransicoes)
            .map(function(tevt) {
              if (tevt.tabelaEntreVerdes.idJson === tabelaEntreVerde.idJson) {
                var transicao = _.find($scope.objeto.transicoes, {idJson: tevt.transicao.idJson});
                var index = _.findIndex(transicao.tabelasEntreVerdesTransicoes, {idJson: tevt.idJson});
                transicao.tabelaEntreVerdesTransicoes.splice(index, 1);
                return null;
              } else {
                return tevt;
              }
              // return (tevt.tabelaEntreVerdes.idJson === tabelaEntreVerde.idJson);
            })
            .compact()
            .value();

          $scope.atualizaTabelaEntreVerdes();

          if ($scope.currentTabelaEntreVerdesIndex >= index) {
            if ($scope.currentTabelaEntreVerdesIndex === totalTabelasEntreVerdes - 1) {
              $scope.selecionaTabelaEntreVerdes($scope.currentTabelasEntreVerdes[$scope.currentTabelaEntreVerdesIndex - 1], $scope.currentTabelaEntreVerdesIndex - 1);
            } else {
              $scope.selecionaTabelaEntreVerdes($scope.currentTabelasEntreVerdes[$scope.currentTabelaEntreVerdesIndex], $scope.currentTabelaEntreVerdesIndex);
            }
          }
        });
      };

      $scope.amareloOuVermelho = function(grupoSemaforico, field) {
        field = field || false;
        if (grupoSemaforico.tipo === 'PEDESTRE') {
          return field ? 'tempoVermelhoIntermitente' : 'Vermelho Intermitente';
        }
        return field ? 'tempoAmarelo' : 'Amarelo';
      };

      $scope.amareloOuVermelhoColor = function(grupoSemaforico) {
        if (grupoSemaforico.tipo === 'PEDESTRE') {
          return 'vermelho-intermitente';
        }
        return 'amarelo';
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

      $scope.selecionaAnelEntreVerdes = function(index) {
        $scope.selecionaAnel(index);
        $scope.atualizaGruposSemaforicos();
        $scope.selecionaGrupoSemaforico($scope.currentGruposSemaforicos[0], 0);
        $scope.atualizaTabelaEntreVerdes();
      };

    }]);
