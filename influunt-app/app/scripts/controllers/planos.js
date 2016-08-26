'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:PlanosCtrl
 * @description
 * # PlanosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('PlanosCtrl', ['$scope', '$state', '$timeout', 'Restangular', '$filter',
                             'validaTransicao', 'utilEstagios', 'toast', 'modoOperacaoService',
                             'influuntAlert', 'influuntBlockui', 'geraDadosDiagramaIntervalo',
                             'handleValidations',
    function ($scope, $state, $timeout, Restangular, $filter,
              validaTransicao, utilEstagios, toast, modoOperacaoService,
              influuntAlert, influuntBlockui, geraDadosDiagramaIntervalo,
              handleValidations) {

      var adicionaPlano, selecionaAnel, atualizaTabelaEntreVerdes, atualizaEstagios, atualizaGruposSemaforicos, atualizaPlanos,
          atualizaEstagiosPlanos, adicionaEstagioASequencia, atualizaPosicaoPlanos, atualizaPosicaoEstagiosPlanos,
          carregaDadosPlano, getOpcoesEstagiosDisponiveis, montaTabelaValoresMinimos, parseAllToInt, setDiagramaEstatico,
          atualizaDiagramaIntervalos, getPlanoParaDiagrama, atualizaTransicoesProibidas, getErrosGruposSemaforicosPlanos,
          duplicarPlano;
      var diagramaDebouncer = null;

      /**
       * Inicializa a tela de planos. Carrega os dados básicos da tela.
       */
      $scope.init = function() {
        var id = $state.params.id;
        Restangular.one('controladores', id).get().then(function(res) {
          $scope.objeto = res;
          $scope.comCheckBoxGrupo = true;
          parseAllToInt();
          montaTabelaValoresMinimos();

          $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
          $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
          $scope.aneis.forEach(function(anel) {
            anel.planos = anel.planos || [];
            for (var i = 0; i < $scope.objeto.limitePlanos; i++) {
              adicionaPlano(anel, i + 1);
            }
          });

          $scope.selecionaAnelPlanos(0);
          return atualizaDiagramaIntervalos();
        });
      };

      $scope.selecionaEstagioPlano = function(estagioPlano, index) {
        $scope.currentEstagioPlanoIndex = index;
        $scope.currentEstagioPlano = estagioPlano;
        getOpcoesEstagiosDisponiveis();
      };

      $scope.copiarPlano = function(plano) {
        $scope.planoCopiado = plano;
        $scope.planosDestino = [];
      };

      $scope.confirmacaoCopiarPlano = function() {
        _.each($scope.planosDestino, function(p) {
          if (p.idJson === $scope.planoCopiado.idJson) {
            return false;
          }

          var plano = _.find($scope.objeto.planos, {idJson: $scope.planoCopiado.idJson});
          var novoPlano = duplicarPlano(plano);
          novoPlano.id = p.id;
          
          var index = _.findIndex($scope.objeto.planos, {idJson: p.idJson});
          $scope.objeto.planos.splice(index, 1);

          index = _.findIndex($scope.currentAnel.planos, {idJson: p.idJson});

          novoPlano.descricao = 'PLANO ' + (index + 1);
          novoPlano.posicao = index + 1;

          $scope.objeto.planos.push(novoPlano);
          $scope.currentAnel.planos.splice(index, 1, {idJson: novoPlano.idJson});
        });
        atualizaPlanos();
      };

      duplicarPlano = function(plano) {
        var novoPlano = _.cloneDeep(plano);

        novoPlano.idJson = UUID.generate();
        novoPlano.gruposSemaforicosPlanos.forEach(function (gp) {
          var grupoSemaforicoPlano = _.find($scope.objeto.gruposSemaforicosPlanos, {idJson: gp.idJson});
          var novoGrupoSemaforicoPlano = _.cloneDeep(grupoSemaforicoPlano);
          gp.idJson = UUID.generate();
          novoGrupoSemaforicoPlano.idJson = gp.idJson;
          novoGrupoSemaforicoPlano.plano.idJson = novoPlano.idJson;
          delete novoGrupoSemaforicoPlano.id;
          $scope.objeto.gruposSemaforicosPlanos.push(novoGrupoSemaforicoPlano);
        });

        novoPlano.estagiosPlanos.forEach(function (ep){
          var estagioPlano = _.find($scope.objeto.estagiosPlanos, {idJson: ep.idJson});
          var novoEstagioPlano = _.cloneDeep(estagioPlano);
          ep.idJson = UUID.generate();
          novoEstagioPlano.idJson = ep.idJson;
          novoEstagioPlano.plano.idJson = novoPlano.idJson;
          delete novoEstagioPlano.id;
          $scope.objeto.estagiosPlanos.push(novoEstagioPlano);
        });

        return novoPlano;
      };

      $scope.resetarPlano = function(plano, index) {
        influuntAlert.confirm(
            $filter('translate')('planos.resetarPlano.tituloAlert'),
            $filter('translate')('planos.resetarPlano.mensagemAlert')
          )
          .then(function(res) {
            if (res) {
              var idPlano = plano.id;
              var indexPlano = _.findIndex($scope.objeto.planos, {idJson: plano.idJson});
              $scope.objeto.planos.splice(indexPlano, 1);

              indexPlano = _.findIndex($scope.currentAnel.planos, {idJson: plano.idJson});
              $scope.currentAnel.planos.splice(indexPlano, 1);
              adicionaPlano($scope.currentAnel, index + 1);
              atualizaPlanos();

              plano = _.find($scope.objeto.planos, {idJson: $scope.currentPlanos[index].idJson});
              plano.id = idPlano;
              $scope.selecionaPlano(plano, index);
            }else{
              plano.configurado = true;
            }
          });
      };

      $scope.renomearPlano = function(plano) {
        influuntAlert
          .prompt(
            $filter('translate')('planos.renomearPlano.tituloAlert'),
            $filter('translate')('planos.renomearPlano.mensagemAlert')
          )
          .then(function(res) {
            if (!res) {
              return false;
            }

            plano.descricao = res;
          });
      };

      /**
       * Remove index.
       *
       * @param      {<type>}  plano   The plano
       * @param      {number}  index   The index
       */
      $scope.removerPlano = function(plano, index) {
        influuntAlert.delete().then(function(confirmado) {
          if (confirmado) {
            var planoIndex = _.findIndex($scope.objeto.planos, {idJson: plano.idJson});
            $scope.objeto.planos.splice(planoIndex, 1);

            planoIndex = _.findIndex($scope.currentAnel.planos, {idJson: plano.idJson});
            $scope.currentAnel.planos.splice(planoIndex, 1);

            plano = _.find($scope.objeto.planos, {idJson: $scope.currentAnel.planos[index - 1].idJson});
            $scope.selecionaPlano(plano, index - 1);
            atualizaPlanos();
          }
        });
      };

      $scope.onChangeCheckboxGrupo = function(grupo, isAtivo) {
        var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {posicao: grupo.posicao});
        var grupoSemaforioPlano = _.find($scope.objeto.gruposSemaforicosPlanos, {plano: {idJson: $scope.currentPlano.idJson}, grupoSemaforico: {idJson: grupoSemaforico.idJson}});
        grupoSemaforioPlano.ativado = isAtivo;
        if (!isAtivo) {
          grupo.intervalos.unshift({
            status: modoOperacaoService.getModoIdByName('APAGADO'),
            duracao: 255
          });
        } else {
          grupo.intervalos.shift();
        }
      };

      $scope.leftEstagio = function(posicaoAtual) {
        var estagioAtual = $scope.currentEstagiosPlanos[posicaoAtual];
        var estagioAnterior = utilEstagios.getEstagioAnterior($scope.currentEstagiosPlanos, posicaoAtual);
        var indexAnterior = _.findIndex($scope.currentEstagiosPlanos, estagioAnterior);

        $scope.currentEstagiosPlanos.splice(indexAnterior, 1);
        var indexAtual = _.findIndex($scope.currentEstagiosPlanos, estagioAtual);
        $scope.currentEstagiosPlanos.splice(indexAtual+1, 0, estagioAnterior);

        atualizaPosicaoEstagiosPlanos();
      };

      $scope.rightEstagio = function(posicaoAtual) {
        var estagioAtual = $scope.currentEstagiosPlanos[posicaoAtual];
        var proximoEstagio = utilEstagios.getProximoEstagio($scope.currentEstagiosPlanos, posicaoAtual);

        $scope.currentEstagiosPlanos.splice(posicaoAtual, 1);
        var posicaoProximoEstagio = _.findIndex($scope.currentEstagiosPlanos, proximoEstagio);
        $scope.currentEstagiosPlanos.splice(posicaoProximoEstagio + 1, 0, estagioAtual);

        atualizaPosicaoEstagiosPlanos();
      };

      $scope.sortableOptions = {
        handle: '> .sortable',
        stop: function() {
          atualizaPosicaoEstagiosPlanos();
        }
      };

      /**
       * Reenderiza novamente o diagrama de intervalos quando qualquer aspecto do plano for alterado.
       * Faz um debounce de 500ms, para evitar chamadas excessivas à "calculadora" do diagrama.
       *
       * Caso o modo de operação do plano for "amarelo intermitente" ou "desligado", o diagrama deverá ser gerado
       * de forma estática (todo o diagrama deve assumir um dos modos acima).
       */
      $scope.$watch('currentPlano', function() {
        $timeout.cancel(diagramaDebouncer);
        diagramaDebouncer = $timeout(atualizaDiagramaIntervalos, 500);
      }, true);

      $scope.$watch('currentEstagioPlano', function() {
        $timeout.cancel(diagramaDebouncer);
        diagramaDebouncer = $timeout(atualizaDiagramaIntervalos, 500);
      }, true);

      $scope.$watch('currentEstagiosPlanos', function() {
        $timeout.cancel(diagramaDebouncer);
        diagramaDebouncer = $timeout(function() {
          atualizaDiagramaIntervalos();
        }, 500);
      }, true);

      $scope.selecionaAnelPlanos = function(index) {
        selecionaAnel(index);
        $scope.selecionaPlano($scope.currentPlanos[0], 0);
      };

      $scope.selecionaPlano = function(plano, index) {
        $scope.currentPlanoIndex = index;
        $scope.currentPlano = plano;
        atualizaEstagiosPlanos();
        return atualizaPosicaoPlanos();
      };

      $scope.getImagemDeEstagio = function(estagioPlano) {
        var ep = _.find($scope.objeto.estagiosPlanos, {idJson: estagioPlano.idJson});
        var estagio = _.find($scope.objeto.estagios, {idJson: ep.estagio.idJson});
        var imagem = _.find($scope.objeto.imagens, {idJson: estagio.imagem.idJson});
        return imagem && $filter('imageSource')(imagem.id);
      };

      $scope.getEstagio = function(estagioPlano) {
        var ep = _.find($scope.objeto.estagiosPlanos, {idJson: estagioPlano.idJson});
        var estagio = _.find($scope.objeto.estagios, {idJson: ep.estagio.idJson});
        return estagio;
      };

      $scope.adicionarEstagioPlano = function(estagioPlano) {
        var posicao = $scope.currentPlano.estagiosPlanos.length + 1;
        adicionaEstagioASequencia(estagioPlano.estagio.idJson, estagioPlano.plano.idJson, posicao);
        atualizaEstagiosPlanos();
      };

      $scope.adicionarEstagio = function(estagio) {
        var posicao = $scope.currentPlano.estagiosPlanos.length + 1;
        adicionaEstagioASequencia(estagio.idJson, $scope.currentPlano.idJson, posicao);
        atualizaEstagiosPlanos();
      };

      $scope.removerEstagioPlano = function(estagioPlano, index) {
        influuntAlert.delete().then(function(confirmado) {
          if (confirmado) {
            //Remover do plano
            var estagioPlanoIndex = _.findIndex($scope.currentPlano.estagiosPlanos, {idJson: estagioPlano.idJson});
            $scope.currentPlano.estagiosPlanos.splice(estagioPlanoIndex, 1);
            //Remover do objeto
            index = _.findIndex($scope.objeto.estagiosPlanos, {idJson: estagioPlano.idJson});
            $scope.objeto.estagiosPlanos.splice(index, 1);
            atualizaEstagiosPlanos();
          }
        });
      };

      /**
       * Adiciona um novo plano ao controlador.
       *
       * @param      {<type>}  anel    The anel
       */
      adicionaPlano = function(anel, posicao) {
        var plano = _.find($scope.objeto.planos, {posicao: posicao, anel: {idJson: anel.idJson}});
        if (plano) {
          plano.configurado = true;
        }else {
          plano = {
            idJson: UUID.generate(),
            anel: { idJson: anel.idJson },
            descricao: 'PLANO ' + posicao,
            posicao: posicao,
            modoOperacao: 'TEMPO_FIXO_ISOLADO',
            posicaoTabelaEntreVerde: 1,
            gruposSemaforicosPlanos: [],
            estagiosPlanos: [],
            tempoCiclo: $scope.objeto.cicloMin,
            configurado: posicao === 1 ? true : false
          };
          
          $scope.objeto.gruposSemaforicosPlanos = $scope.objeto.gruposSemaforicosPlanos || [];
          anel.gruposSemaforicos.forEach(function (g){
            var grupo =  _.find($scope.objeto.gruposSemaforicos, {idJson: g.idJson});
            var grupoPlano = {
              idJson: UUID.generate(),
              ativado: true,
              grupoSemaforico: {
                idJson: grupo.idJson
              },
              plano: {
                idJson: plano.idJson
              }
            };

            $scope.objeto.gruposSemaforicosPlanos.push(grupoPlano);
            plano.gruposSemaforicosPlanos.push({idJson: grupoPlano.idJson});
          });

          anel.estagios.forEach(function (e){
            var estagio =  _.find($scope.objeto.estagios, {idJson: e.idJson});
            var estagioPlano = {
              idJson: UUID.generate(),
              estagio: {
                idJson: estagio.idJson
              },
              plano: {
                idJson: plano.idJson
              },
              posicao: estagio.posicao,
              tempoVerde: $scope.objeto.verdeMin,
              dispensavel: false
            };
            $scope.objeto.estagiosPlanos.push(estagioPlano);
            plano.estagiosPlanos.push({idJson: estagioPlano.idJson});
          });

          $scope.objeto.planos = $scope.objeto.planos || [];
          $scope.objeto.planos.push(plano);
          anel.planos.push({idJson: plano.idJson});
        }
      };

      getOpcoesEstagiosDisponiveis = function() {
        var estagiosPlanos = _.map($scope.currentEstagiosPlanos, function(ep) {
          return _.find($scope.objeto.estagios, { idJson: ep.estagio.idJson });
        });

        $scope.opcoesEstagiosDisponiveis = [
          utilEstagios.getEstagioAnterior(estagiosPlanos, $scope.currentEstagioPlanoIndex),
          utilEstagios.getProximoEstagio(estagiosPlanos, $scope.currentEstagioPlanoIndex)
        ];

        return $scope.opcoesEstagiosDisponiveis;
      };

      adicionaEstagioASequencia = function(estagioIdJson, planoIdJson, posicao) {
        var novoEstagioPlano = {
          idJson: UUID.generate(),
          estagio: {
            idJson: estagioIdJson
          },
          plano: {
            idJson: planoIdJson
          },
          posicao: posicao,
          tempoVerde: $scope.objeto.verdeMin,
          dispensavel: false
        };

        $scope.objeto.estagiosPlanos.push(novoEstagioPlano);
        $scope.currentPlano.estagiosPlanos.push({idJson: novoEstagioPlano.idJson});
      };

      selecionaAnel = function(index) {
        $scope.currentAnelIndex = index;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelIndex];
        atualizaEstagios($scope.currentAnel);
        atualizaGruposSemaforicos();
        atualizaTabelaEntreVerdes($scope.currentAnel);
        atualizaPlanos();
      };

      atualizaEstagios = function(anel) {
        var ids = _.map(anel.estagios, 'idJson');
        $scope.currentEstagios = _
          .chain($scope.objeto.estagios)
          .filter(function(e) {
            return ids.indexOf(e.idJson) >= 0;
          })
          .orderBy(['posicao'])
          .value();

          return $scope.currentEstagios;
      };

      atualizaGruposSemaforicos = function() {
        var ids = _.map($scope.currentAnel.gruposSemaforicos, 'idJson');
        $scope.currentGruposSemaforicos = _
          .chain($scope.objeto.gruposSemaforicos)
          .filter(function(ep) {
            return ids.indexOf(ep.idJson) >= 0;
          })
          .orderBy(['posicao'])
          .value();

          return $scope.currentGruposSemaforicos;
      };

      atualizaTabelaEntreVerdes = function(anel) {
        var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: anel.gruposSemaforicos[0].idJson});
        var ids = _.map(grupoSemaforico.tabelasEntreVerdes, 'idJson');

        $scope.currentTabelasEntreVerdes = _
          .chain($scope.objeto.tabelasEntreVerdes)
          .filter(function(tev) {
            return ids.indexOf(tev.idJson) >= 0;
          })
          .orderBy(['posicao'])
          .value();

        return $scope.currentTabelasEntreVerdes;
      };

      atualizaPlanos = function() {
        var ids = _.map($scope.currentAnel.planos, 'idJson');
        $scope.currentPlanos = _
          .chain($scope.objeto.planos)
          .filter(function(e) {
            return ids.indexOf(e.idJson) >= 0;
          })
          .orderBy(['posicao'])
          .value();

        // reordena a lista de associacoes de planos conforme a posicao deles.
        $scope.currentAnel.planos = _.map($scope.currentPlanos, function(p) {
          return {
            idJson: p.idJson
          };
        });

          return $scope.currentPlanos;
      };

      atualizaEstagiosPlanos = function() {
        var ids = _.map($scope.currentPlano.estagiosPlanos, 'idJson');
        $scope.currentEstagiosPlanos = _
          .chain($scope.objeto.estagiosPlanos)
          .filter(function(ep) {
            return ids.indexOf(ep.idJson) >= 0;
          })
          .orderBy(['posicao'])
          .value();

          return atualizaPosicaoEstagiosPlanos();
      };

      atualizaPosicaoEstagiosPlanos = function(){
        $scope.currentEstagiosPlanos.forEach(function (estagioPlano, index){
          estagioPlano.posicao = index + 1;
        });
      };

      atualizaPosicaoPlanos = function(){
        $scope.currentPlanos.forEach(function (plano, index){
          plano.posicao = index + 1;
        });
      };

      /**
       * Evita que dados informados para um plano em determinado modo de operação vaze
       * para o diagrama criado.
       *
       * @param      {<type>}  plano   The plano
       */
      $scope.limpaDadosPlano = function() {
        var plano = $scope.currentPlano;
        if (plano.modoOperacao === 'ATUADO') {
          plano.tempoCiclo = null;
          plano.estagiosPlanos.forEach(function(e) {
            var estagio = _.find($scope.objeto.estagiosPlanos, {idJson: e.idJson});
            estagio.tempoVerde = null;
          });
        } else {
          plano.estagiosPlanos.forEach(function(e) {
            var estagio = _.find($scope.objeto.estagiosPlanos, {idJson: e.idJson});
            estagio.tempoVerdeMinimo = null;
            estagio.tempoVerdeMaximo = null;
            estagio.tempoVerdeIntermediario = null;
            estagio.tempoExtensaoVerde = null;
          });
        }

        if (plano.modoOperacao !== 'TEMPO_FIXO_COORDENADO') {
          plano.defasagem = null;
        }
        carregaDadosPlano(plano);
      };

      carregaDadosPlano = function(plano){
        if (plano.modoOperacao === 'ATUADO') {
          plano.estagiosPlanos.forEach(function(e) {
            var estagio = _.find($scope.objeto.estagiosPlanos, {idJson: e.idJson});
            estagio.tempoVerdeMinimo = $scope.objeto.verdeMinimoMin;
            estagio.tempoVerdeMaximo = $scope.objeto.verdeMaximoMin;
            estagio.tempoVerdeIntermediario = $scope.objeto.verdeIntermediarioMin;
            estagio.tempoExtensaoVerde = $scope.objeto.extensaVerdeMin;
          });
        } else {
          plano.tempoCiclo = $scope.objeto.cicloMin;
          plano.estagiosPlanos.forEach(function(e) {
            var estagio = _.find($scope.objeto.estagiosPlanos, {idJson: e.idJson});
            estagio.tempoVerde = $scope.objeto.verdeMin;
          });
        }

        if (plano.modoOperacao === 'TEMPO_FIXO_COORDENADO') {
          plano.defasagem = $scope.objeto.defasagemMin;
        }
      };

      montaTabelaValoresMinimos = function() {
        $scope.valoresMinimos = {
          verdeMin: $scope.objeto.verdeMin,
          verdeMinimoMin: $scope.objeto.verdeMinimoMin
        };
        return $scope.valoresMinimos;
      };

      parseAllToInt = function() {
        $scope.objeto.amareloMax = parseInt($scope.objeto.amareloMax);
        $scope.objeto.amareloMin = parseInt($scope.objeto.amareloMin);
        $scope.objeto.atrasoGrupoMin = parseInt($scope.objeto.atrasoGrupoMin);
        $scope.objeto.cicloMax = parseInt($scope.objeto.cicloMax);
        $scope.objeto.cicloMin = parseInt($scope.objeto.cicloMin);
        $scope.objeto.defasagemMin = parseInt($scope.objeto.defasagemMin);
        $scope.objeto.extensaVerdeMax = parseInt($scope.objeto.extensaVerdeMax);
        $scope.objeto.extensaVerdeMin = parseInt($scope.objeto.extensaVerdeMin);
        $scope.objeto.maximoPermanenciaEstagioMax = parseInt($scope.objeto.maximoPermanenciaEstagioMax);
        $scope.objeto.maximoPermanenciaEstagioMin = parseInt($scope.objeto.maximoPermanenciaEstagioMin);
        $scope.objeto.verdeIntermediarioMax = parseInt($scope.objeto.verdeIntermediarioMax);
        $scope.objeto.verdeIntermediarioMin = parseInt($scope.objeto.verdeIntermediarioMin);
        $scope.objeto.verdeMax = parseInt($scope.objeto.verdeMax);
        $scope.objeto.verdeMaximoMax = parseInt($scope.objeto.verdeMaximoMax);
        $scope.objeto.verdeMaximoMin = parseInt($scope.objeto.verdeMaximoMin);
        $scope.objeto.verdeMin = parseInt($scope.objeto.verdeMin);
        $scope.objeto.verdeMinimoMax = parseInt($scope.objeto.verdeMinimoMax);
        $scope.objeto.verdeMinimoMin = parseInt($scope.objeto.verdeMinimoMin);
        $scope.objeto.verdeSegurancaPedestreMax = parseInt($scope.objeto.verdeSegurancaPedestreMax);
        $scope.objeto.verdeSegurancaPedestreMin = parseInt($scope.objeto.verdeSegurancaPedestreMin);
        $scope.objeto.verdeSegurancaVeicularMax = parseInt($scope.objeto.verdeSegurancaVeicularMax);
        $scope.objeto.verdeSegurancaVeicularMin = parseInt($scope.objeto.verdeSegurancaVeicularMin);
        $scope.objeto.vermelhoIntermitenteMax = parseInt($scope.objeto.vermelhoIntermitenteMax);
        $scope.objeto.vermelhoIntermitenteMin = parseInt($scope.objeto.vermelhoIntermitenteMin);
        $scope.objeto.vermelhoLimpezaPedestreMax = parseInt($scope.objeto.vermelhoLimpezaPedestreMax);
        $scope.objeto.vermelhoLimpezaPedestreMin = parseInt($scope.objeto.vermelhoLimpezaPedestreMin);
        $scope.objeto.vermelhoLimpezaVeicularMax = parseInt($scope.objeto.vermelhoLimpezaVeicularMax);
        $scope.objeto.vermelhoLimpezaVeicularMin = parseInt($scope.objeto.vermelhoLimpezaVeicularMin);
      };

      $scope.submitForm = function() {
        Restangular
        .all('planos')
        .post($scope.objeto)
        .then(function(res) {
          $scope.objeto = res;

          $scope.errors = {};
          influuntBlockui.unblock();
          $state.go('app.controladores');
        })
        .catch(function(res) {
          influuntBlockui.unblock();
          if (res.status === 422) {
            $scope.errors = handleValidations.buildValidationMessages(res.data);
          } else {
            console.error(res);
          }
        });
      };

      /**
       * Deve informar que determinado anel possui erros caso haja uma lista de
       * erros para determinado anel.
       *
       * @param      {<type>}  indice  The indice
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.anelTemErro = function(indice) {
        return handleValidations.anelTemErro($scope.errors, indice);
      };

      $scope.getErrosPlanos = function(listaErros) {
        var erros = _
        .chain(listaErros)
        .filter(function(e) {
          return _.isString(e[0]);
        })
        .map()
        .flatten()
        .value();
        erros.push(getErrosGruposSemaforicosPlanos(listaErros));
        return _.chain(erros).flatten().value();
      };

      $scope.getErrosEstagiosPlanos = function(index) {
        var erros = _.get($scope.errors, 'aneis[' + $scope.currentAnelIndex + '].planos[' + $scope.currentPlanoIndex + '].estagiosPlanos[' + index + ']');
        return erros;
      };

      getErrosGruposSemaforicosPlanos = function(listaErros){
        var erros = [];
        if(listaErros){
          _.each(listaErros.gruposSemaforicosPlanos, function (erro, index){
            if(erro) {
              var grupoSemaforicoPlanoIdJson = $scope.currentPlano.gruposSemaforicosPlanos[index].idJson;
              var grupoSemaforicoPlano = _.find($scope.objeto.gruposSemaforicosPlanos, {idJson: grupoSemaforicoPlanoIdJson});
              var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: grupoSemaforicoPlano.grupoSemaforico.idJson});
              var texto = 'G' + grupoSemaforico.posicao + ' - ' + erro.respeitaVerdesDeSeguranca[0];
              erros.push(texto);
            }
          });
        }
        return erros;
      };

      //Funções para Diagrama de Planos
      /**
       * Atualiza o diagrama de intervalos para os casos de modo de operação intermitente e desligado, onde
       * todos os grupos deverão assumir o mesmo estágio (entre amarelo-intermitente e desligado). Se não assumir
       * nenhum destes, deverá utilizar o diagrama produzido a partir do plugin de diagrama.
       */
      setDiagramaEstatico = function() {
        var modo = modoOperacaoService.getModoIdByName($scope.currentPlano.modoOperacao);
        var modoApagado = modoOperacaoService.getModoIdByName('APAGADO');
        var grupos = _.map($scope.currentAnel.gruposSemaforicos, function(g) {
          var grupo = _.find($scope.objeto.gruposSemaforicos, {idJson: g.idJson});
          return {
            ativado: grupo.tipo === 'VEICULAR',
            posicao: grupo.posicao,
            intervalos: [{
              status: grupo.tipo === 'VEICULAR' ? modo : modoApagado,
              duracao: 255
            }]
          };
        });
        grupos = _.orderBy(grupos, ['posicao']);
        $scope.dadosDiagrama = {
          estagios: [{posicao: 1, duracao: 255}],
          gruposSemaforicos: grupos,
          erros: [],
          tempoCiclo: 255
        };
      };

      /**
       * Caso o modo de operação seja intermitente ou apagado, ele deverá renderizar um diagrama estágio, contendo
       * somente estes modos. Caso contrário, deverá executar o metodo de geração do diagrama a partir do plugin.
       */
      atualizaDiagramaIntervalos = function() {
        var transicoesProibidas = atualizaTransicoesProibidas();

        // Não deverá fazer o diagrama de intervalos enquanto houver transicoes proibidas
        if (transicoesProibidas.length > 0) {
          $scope.dadosDiagrama = {
            erros: _.chain(transicoesProibidas).map('mensagem').uniq().value()
          };

          return false;
        }

        if (['INTERMITENTE', 'APAGADO', 'ATUADO'].indexOf($scope.currentPlano.modoOperacao) < 0) {
          getPlanoParaDiagrama();
          var diagramaBuilder = new influunt.components.DiagramaIntervalos($scope.plano, $scope.valoresMinimos);
          var result = diagramaBuilder.calcula();

          _.each(result.gruposSemaforicos, function(g) {
            var grupo = _.find($scope.objeto.gruposSemaforicos, {posicao: g.posicao, anel: {idJson: $scope.plano.anel.idJson}});
            var grupoPlano = _.find($scope.plano.gruposSemaforicosPlanos, {grupoSemaforico: {idJson: grupo.idJson}, plano: {idJson: $scope.plano.idJson}});
            g.ativado = grupoPlano.ativado;
            if(!g.ativado){
              g.intervalos.unshift({
                status: modoOperacaoService.getModoIdByName('APAGADO'),
                duracao: 255
              });
            }
          });

          $scope.dadosDiagrama = result;
        } else {
          setDiagramaEstatico();
        }
      };

      getPlanoParaDiagrama = function() {
        $scope.plano = geraDadosDiagramaIntervalo.gerar($scope.currentPlano, $scope.currentAnel, $scope.currentGruposSemaforicos, $scope.objeto);
      };

      atualizaTransicoesProibidas = function() {
        var transicoesProibidas = validaTransicao.valida($scope.currentEstagiosPlanos, $scope.objeto);

        // limpa as transicoes proibidas dos objetos.
        _.each($scope.currentEstagiosPlanos, function(ep) {
          ep.origemTransicaoProibida = false;
          ep.destinoTransicaoProibida = false;
        });

        // marca as transicoes proibidas nos objetos.
        _.each(transicoesProibidas, function(t) {
          $scope.currentEstagiosPlanos[t.origem].origemTransicaoProibida = true;
          $scope.currentEstagiosPlanos[t.destino].destinoTransicaoProibida = true;
        });

        return transicoesProibidas;
      };

    }]);
