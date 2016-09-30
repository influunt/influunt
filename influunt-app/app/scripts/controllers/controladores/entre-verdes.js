'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresEntreVerdesCtrl
 * @description
 * # ControladoresEntreVerdesCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresEntreVerdesCtrl', ['$scope', '$state', '$controller', '$filter', 'assertControlador', 'influuntAlert',
    function ($scope, $state, $controller, $filter, assertControlador, influuntAlert) {
      $controller('ControladoresCtrl', {$scope: $scope});

      $scope.isTabelaEntreVerdes = true;
      var aneisBkp, removeTabelaEntreVerdeLocal;

      /**
       * Garante que o controlador tem as condições mínimas para acessar a tela de entre verdes.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertEntreVerdes = function() {
        var valid = assertControlador.hasTransicoes($scope.objeto) && assertControlador.hasTabelasEntreVerdes($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.atraso_de_grupo', {id: $scope.objeto.id});
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

        _.forEach($scope.objeto.tabelasEntreVerdesTransicoes, function(tevTransicao) {
          var tabelaEntreVerde = _.find($scope.objeto.tabelasEntreVerdes, {idJson: tevTransicao.tabelaEntreVerdes.idJson});
          var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: tabelaEntreVerde.grupoSemaforico.idJson});
          var amareloOuVermelhoIntermitente = $scope.amareloOuVermelho(grupoSemaforico, true);
          if (!_.has(tevTransicao, amareloOuVermelhoIntermitente)) {
            tevTransicao[amareloOuVermelhoIntermitente] = $scope.limites(grupoSemaforico)[amareloOuVermelhoIntermitente].min;
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
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.inicializaEntreVerdes = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertEntreVerdes()) {
            $scope.limiteTabelasEntreVerdes = $scope.objeto.limiteTabelasEntreVerdes;
            $scope.sortByPosicao();
            $scope.inicializaConfirmacaoNadaHaPreencher();
          }
        });
      };

      $scope.adicionarTabelaEntreVerdes = function() {
        var totalTabelasEntreVerdes = $scope.currentTabelasEntreVerdes.length;
        $scope.currentAnel.gruposSemaforicos.forEach(function(gs) {
          var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: gs.idJson});

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
          }
        });

        $scope.atualizaTabelaEntreVerdes();
        $scope.selecionaTabelaEntreVerdes(
          _.last($scope.currentTabelasEntreVerdes),
          $scope.currentTabelasEntreVerdes.length - 1
        );
      };


      $scope.removerTabelaEntreVerdes = function(tev) {
        var posicaoTev = tev.posicao;
        influuntAlert.delete().then(function(confirmacao) {
          if (confirmacao) {
            $scope.currentAnel.gruposSemaforicos.forEach(function(gs) {
              var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: gs.idJson});

              var tabelaEntreVerde = _
                .chain($scope.objeto.tabelasEntreVerdes)
                .reject('_destroy')
                .find({
                  posicao: posicaoTev,
                  grupoSemaforico: { idJson: gs.idJson }
                })
                .value();

              if (tabelaEntreVerde && angular.isDefined(tabelaEntreVerde.id)) {
                tabelaEntreVerde._destroy = true;
              } else {
                var tevIndex = _.findIndex($scope.objeto.tabelasEntreVerdes, {idJson: tabelaEntreVerde.idJson});
                removeTabelaEntreVerdeLocal(tevIndex, grupoSemaforico, tabelaEntreVerde);
              }
            });

            $scope.atualizaTabelaEntreVerdes();
            $scope.selecionaTabelaEntreVerdes(_.last($scope.currentTabelasEntreVerdes), $scope.currentTabelasEntreVerdes.length - 1);
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
        _.forEach($scope.aneis, function(anel) {

          var gsIdJson = _.map(anel.gruposSemaforicos, 'idJson');
          anel.gruposSemaforicos = _
            .chain($scope.objeto.gruposSemaforicos)
            .filter(function(gs) { return gsIdJson.indexOf(gs.idJson) > -1; })
            .map(function(gs) { return { idJson: gs.idJson }; })
            .value();
        });

      };

      $scope.afterSubmitFormOnValidationError = function() {
        $scope.objeto.aneis = aneisBkp;
        this.sortByPosicao();
      };

      $scope.errosAmareloOuVermelho = function(grupoSemaforico, transicaoIndex) {
        var path = 'aneis['+$scope.currentAnelIndex+'].gruposSemaforicos['+$scope.currentGrupoSemaforicoIndex+'].transicoes['+transicaoIndex+'].tabelaEntreVerdesTransicoes['+$scope.currentTabelaEntreVerdesIndex+']';
        return _.get($scope.errors, path);
      };

      $scope.possuiErroAmareloOuVermelho = function(grupoSemaforico, transicaoIndex) {
        var path = 'aneis['+$scope.currentAnelIndex+'].gruposSemaforicos['+$scope.currentGrupoSemaforicoIndex+'].transicoes['+transicaoIndex+'].tabelaEntreVerdesTransicoes['+$scope.currentTabelaEntreVerdesIndex+'].'+$scope.amareloOuVermelho(grupoSemaforico, true)+'Ok';
        var errors = _.get($scope.errors, path);
        return _.isObject(errors) && Object.keys(errors).length > 0;
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
        $scope.selecionaTabelaEntreVerdes($scope.currentTabelasEntreVerdes[0], 0);
      };

      removeTabelaEntreVerdeLocal = function(tevIndex, grupoSemaforico, tabelaEntreVerde) {
        $scope.objeto.tabelasEntreVerdes.splice(tevIndex, 1);

        var index = _.findIndex(grupoSemaforico.tabelasEntreVerdes, {idJson: tabelaEntreVerde.idJson});
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
          })
          .compact()
          .value();
      };

      $scope.$watch('currentTabelasEntreVerdesTransicoes', function() {
        if($scope.currentGrupoSemaforico && $scope.confirmacao){
          $scope.verificaConfirmacaoNadaHaPreencher();
        }
      }, true);

      //Confirmar não há nada a preencher por grupo Semaforico
      var confirmacaoNadaHaPreencher = {};

      $scope.inicializaConfirmacaoNadaHaPreencher = function(){
        $scope.confirmacao = {};
        $scope.objeto.gruposSemaforicos.forEach(function(grupo) {
          confirmacaoNadaHaPreencher[grupo.posicao] = confirmacaoNadaHaPreencher[grupo.posicao] || $scope.possuiInformacoesPreenchidas(grupo);
        });
      };

      $scope.confirmacaoNadaHaPreencher = function(){
        confirmacaoNadaHaPreencher[$scope.currentGrupoSemaforico.posicao] = !confirmacaoNadaHaPreencher[$scope.currentGrupoSemaforico.posicao];
      };

      $scope.podeSalvar = function() {
        return _.values(confirmacaoNadaHaPreencher).every(function(e) {return e;});
      };

      $scope.verificaConfirmacaoNadaHaPreencher = function(){
        if (!$scope.confirmacao[$scope.currentGrupoSemaforico.posicao]) {
          if ($scope.possuiInformacoesPreenchidas()) {
            confirmacaoNadaHaPreencher[$scope.currentGrupoSemaforico.posicao] = true;
            $scope.confirmacao[$scope.currentGrupoSemaforico.posicao] = false;
          } else {
            confirmacaoNadaHaPreencher[$scope.currentGrupoSemaforico.posicao] = false;
            $scope.confirmacao[$scope.currentGrupoSemaforico.posicao] = false;
          }
        }
      };

      $scope.possuiInformacoesPreenchidas = function(grupo) {
        grupo = grupo || $scope.currentGrupoSemaforico;
        if(grupo){
          var resultados = _.chain(grupo.tabelasEntreVerdes).map(function(tev){
            var tabelaEntreVerde = _.find($scope.objeto.tabelasEntreVerdes, {idJson: tev.idJson});
            var ids = _.map(tabelaEntreVerde.tabelaEntreVerdesTransicoes, 'idJson');
            var resultados = _.chain($scope.objeto.tabelasEntreVerdesTransicoes).filter(function(tevt) {
              return ids.indexOf(tevt.idJson) >= 0;
            }).map(function(tevt) {
              var tempoAmarelo = tevt.tempoVermelhoIntermitente ? tevt.tempoVermelhoIntermitente : tevt.tempoAmarelo;
              return parseInt(tempoAmarelo) !== 3 || parseInt(tevt.tempoVermelhoLimpeza) !== 0;
            }).value();
            return resultados.some(function(e) { return e; });
          }).value();
          return resultados.some(function(e) { return e; });
        }
        return false;
      };

      $scope.mensagemConfirmacao = function() {
        var msg = $filter('translate')('geral.tooltip.naoPodeSalvarSemConfirmacaoEntreVerdes');
        var posicoesGrupos = [];
        _.each(confirmacaoNadaHaPreencher, function(confirmado, posicaoGrupo) {
          if (!confirmado) {
            posicoesGrupos.push(posicaoGrupo);
          }
        });

        return msg + _.map(posicoesGrupos, function(posicao) { return "G"+posicao; }).join(", ");
      };

    }]);
