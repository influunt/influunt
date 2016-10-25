'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:PlanosCtrl
 * @description
 * # PlanosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('PlanosCtrl', ['$controller', '$scope', '$state', '$timeout', 'Restangular', '$filter',
                             'validaTransicao', 'utilEstagios', 'toast', 'modoOperacaoService',
                             'influuntAlert', 'influuntBlockui', 'geraDadosDiagramaIntervalo',
                             'handleValidations', 'utilControladores', 'planoService', 'breadcrumbs',
    function ($controller, $scope, $state, $timeout, Restangular, $filter,
              validaTransicao, utilEstagios, toast, modoOperacaoService,
              influuntAlert, influuntBlockui, geraDadosDiagramaIntervalo,
              handleValidations, utilControladores, planoService, breadcrumbs) {

      $controller('HistoricoCtrl', {$scope: $scope});
      $scope.inicializaResourceHistorico('planos');

      var selecionaAnel, adicionaEstagioASequencia, carregaDadosPlano, getOpcoesEstagiosDisponiveis,
          getErrosGruposSemaforicosPlanos, getErrosPlanoAtuadoSemDetector, duplicarPlano, removerPlanoLocal,
          getErrosUltrapassaTempoCiclo, getErrosSequenciaInvalida, getIndexPlano, handleErroEditarPlano,
          setLocalizacaoNoCurrentAnel, limpaDadosPlano, atualizaDiagramaIntervalos;

      var diagramaDebouncer = null;

      $scope.somenteVisualizacao = $state.current.data.somenteVisualizacao;

      /**
       * Inicializa a tela de planos. Carrega os dados básicos da tela.
       */
      $scope.init = function() {
        var id = $state.params.id;
        Restangular.one('controladores', id).get()
          .then(function(res) {
            $scope.objeto = res;
            $scope.comCheckBoxGrupo = !$scope.somenteVisualizacao;
            $scope.objeto = utilControladores.parseLimitsToInt($scope.objeto);
            $scope.valoresMinimos = planoService.montaTabelaValoresMinimos($scope.objeto);

            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
            $scope.aneis = _.filter($scope.objeto.aneis, 'ativo');

            $scope.aneis.forEach(function(anel) {
              if (!(_.isArray(anel.planos) && anel.planos.length > 0)) {
                anel.planos = [];
                var versaoPlano = {idJson: UUID.generate(), anel:{idJson: anel.idJson}};

                $scope.objeto.versoesPlanos = $scope.objeto.versoesPlanos || [];
                $scope.objeto.versoesPlanos.push(versaoPlano);
                anel.versaoPlano = {idJson: versaoPlano.idJson};
              }

              if(anel.aceitaModoManual) {
                planoService.criarPlanoManualExclusivo($scope.objeto, anel);
              }

              for (var i = 0; i < $scope.objeto.limitePlanos; i++) {
                planoService.adicionar($scope.objeto, anel, i + 1);
              }
            });

            $scope.selecionaAnelPlanos(0);
            return atualizaDiagramaIntervalos();
          })
          .finally(influuntBlockui.unblock);
      };

      $scope.clonarPlanos = function(controladorId) {
        return $scope.clonar(controladorId).finally(influuntBlockui.unblock);
      };

      $scope.editarPlano = function(controladorId) {
        return $scope.editar(controladorId).finally(influuntBlockui.unblock);
      };

      $scope.cancelarEdicao = function() {
        var plano = _.chain($scope.objeto.planos).filter(function(p) { return !!p.id; }).last().value();
        return $scope.cancelar(plano);
      };

      $scope.copiarPlano = function(plano) {
        $scope.planoCopiado = plano;
        $scope.planosDestino = [];
      };

      $scope.confirmacaoCopiarPlano = function() {
        _.each($scope.planosDestino, function(p) {
          if (p.idJson !== $scope.planoCopiado.idJson) {
            var plano = _.find($scope.objeto.planos, {idJson: $scope.planoCopiado.idJson});
            var novoPlano = duplicarPlano(plano);
            novoPlano.id = p.id;

            var index = _.findIndex($scope.objeto.planos, {idJson: p.idJson});
            $scope.objeto.planos.splice(index, 1);

            index = _.findIndex($scope.currentAnel.planos, {idJson: p.idJson});

            novoPlano.descricao = 'PLANO ' + (p.posicao);
            novoPlano.posicao = p.posicao;

            $scope.objeto.planos.push(novoPlano);
            $scope.currentAnel.planos.splice(index, 1, {idJson: novoPlano.idJson});

            var versaoPlano = _.find($scope.objeto.versoesPlanos, { idJson: $scope.currentAnel.versaoPlano.idJson });
            index = _.findIndex(versaoPlano.planos, { idJson: p.idJson });
            versaoPlano.planos.splice(index, 1, {idJson: novoPlano.idJson});
          }
        });

        $scope.currentPlanos = planoService.atualizaPlanos($scope.objeto, $scope.currentAnel);
      };

      $scope.resetarPlano = function(plano, index) {
        var title = $filter('translate')('planos.resetarPlano.tituloAlert'),
            text = $filter('translate')('planos.resetarPlano.mensagemAlert');
        influuntAlert.confirm(title, text)
          .then(function(res) {
            if (res) {
              if (angular.isUndefined(plano.id)) {
                removerPlanoLocal(plano, index);
              } else {
                Restangular.one('planos', plano.id).remove()
                  .then(function() {
                    removerPlanoLocal(plano, index);
                  }).catch(function() {
                    toast.error($filter('translate')('planos.resetarPlano.erroAoDeletar'));
                  })
                  .finally(influuntBlockui.unblock);
              }
            } else {
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
       * Evita que dados informados para um plano em determinado modo de operação vaze
       * para o diagrama criado.
       *
       * @param      {<type>}  plano   The plano
       */
      $scope.onChangeModoOperacao = function() {
        limpaDadosPlano($scope.currentPlano);
        carregaDadosPlano($scope.currentPlano);

        $timeout(function() {
          $scope.currentEstagiosPlanos = planoService.atualizaEstagiosPlanos($scope.objeto, $scope.currentPlano);
        });
      };

      $scope.onChangeCheckboxGrupo = function(grupo, isAtivo) {
        var gruposSemaforicos = _.chain($scope.objeto.gruposSemaforicos)
          .filter(function(gs) { return gs.anel.idJson === $scope.currentAnel.idJson; })
          .orderBy(['posicao'])
          .value();

        var grupoSemaforico = gruposSemaforicos[grupo.posicao-1];
        var grupoSemaforioPlano = _.find($scope.objeto.gruposSemaforicosPlanos, {plano: {idJson: $scope.currentPlano.idJson}, grupoSemaforico: {idJson: grupoSemaforico.idJson}});
        grupoSemaforioPlano.ativado = isAtivo;
        if (!isAtivo) {
          grupo.intervalos.unshift({
            status: modoOperacaoService.getModoIdByName('APAGADO'),
            duracao: $scope.currentPlano.tempoCiclo || $scope.objeto.cicloMax
          });
        } else {
          grupo.intervalos.shift();
        }
      };

      $scope.adicionarEstagio = function(estagio) {
        var posicao = $scope.currentPlano.estagiosPlanos.length + 1;
        adicionaEstagioASequencia(estagio.idJson, $scope.currentPlano.idJson, posicao);
        $scope.currentEstagiosPlanos = planoService.atualizaEstagiosPlanos($scope.objeto, $scope.currentPlano);
      };

      $scope.removerEstagioPlano = function(estagioPlano) {
        influuntAlert.delete().then(function(confirmado) {
          if (confirmado) {
            //Remover do plano
            var estagioPlanoIndex = _.findIndex($scope.currentPlano.estagiosPlanos, {idJson: estagioPlano.idJson});
            $scope.currentPlano.estagiosPlanos.splice(estagioPlanoIndex, 1);
            //Remover do objeto
            var index = _.findIndex($scope.objeto.estagiosPlanos, {idJson: estagioPlano.idJson});
            $scope.objeto.estagiosPlanos.splice(index, 1);
            $scope.currentEstagiosPlanos = planoService.atualizaEstagiosPlanos($scope.objeto, $scope.currentPlano);
          }
        });
      };

      $scope.selecionaEstagioPlano = function(estagioPlano, index) {
        $scope.currentEstagioPlanoIndex = index;
        $scope.currentEstagioPlano = estagioPlano;
        getOpcoesEstagiosDisponiveis();
      };

      $scope.leftEstagio = function(posicaoAtual) {
        if (posicaoAtual === 0) {
          return false;
        }

        var estagioAtual = $scope.currentEstagiosPlanos[posicaoAtual];
        var estagioAnterior = utilEstagios.getEstagioAnterior($scope.currentEstagiosPlanos, posicaoAtual);
        var indexAnterior = _.findIndex($scope.currentEstagiosPlanos, estagioAnterior);

        $scope.currentEstagiosPlanos.splice(indexAnterior, 1);
        var indexAtual = _.findIndex($scope.currentEstagiosPlanos, estagioAtual);
        $scope.currentEstagiosPlanos.splice(indexAtual+1, 0, estagioAnterior);

        planoService.atualizaPosicaoEstagiosPlanos($scope.currentEstagiosPlanos);
      };

      $scope.rightEstagio = function(posicaoAtual) {
        if (posicaoAtual === $scope.currentEstagiosPlanos.length - 1) {
          return false;
        }

        var estagioAtual = $scope.currentEstagiosPlanos[posicaoAtual];
        var proximoEstagio = utilEstagios.getProximoEstagio($scope.currentEstagiosPlanos, posicaoAtual);

        $scope.currentEstagiosPlanos.splice(posicaoAtual, 1);
        var posicaoProximoEstagio = _.findIndex($scope.currentEstagiosPlanos, proximoEstagio);
        $scope.currentEstagiosPlanos.splice(posicaoProximoEstagio + 1, 0, estagioAtual);

        planoService.atualizaPosicaoEstagiosPlanos($scope.currentEstagiosPlanos);
      };

      $scope.getEstagio = function(estagioPlano) {
        if (estagioPlano) {
          var ep = _.find($scope.objeto.estagiosPlanos, {idJson: estagioPlano.idJson});
          if (ep) {
            var estagio = _.find($scope.objeto.estagios, {idJson: ep.estagio.idJson});
            estagio.imagem.id = _.find($scope.objeto.imagens, {idJson: estagio.imagem.idJson}).id;
            return estagio;
          }
        }
      };

      $scope.getErrosEstagiosPlanos = function(index) {
        var erros = _.get($scope.errors, 'aneis[' + $scope.currentAnelIndex + '].versoesPlanos[' + $scope.currentVersaoPlanoIndex + '].planos[' + getIndexPlano($scope.currentAnel, $scope.currentPlano) + '].estagiosPlanos[' + index + ']');
        return erros;
      };

      $scope.sortableOptions = {
        disabled: $scope.somenteVisualizacao,
        handle: '> .sortable',
        stop: function() {
          planoService.atualizaPosicaoEstagiosPlanos($scope.currentEstagiosPlanos);
        }
      };

      $scope.selecionaAnelPlanos = function(index) {
        selecionaAnel(index);
        if ($scope.currentAnel.aceitaModoManual) {
          $scope.selecionaPlano($scope.currentPlanos[1], 1);
        } else {
          $scope.selecionaPlano($scope.currentPlanos[0], 0);
        }
      };

      $scope.selecionaPlano = function(plano, index) {
        $scope.currentPlanoIndex = index;
        $scope.currentPlano = plano;
        var versoes = _
          .chain($scope.objeto.versoesPlanos)
          .filter(function (versao) { return versao.anel.idJson === $scope.currentAnel.idJson;})
          .value();

        $scope.currentVersaoPlanoIndex = _.findIndex(versoes, {anel: {idJson: $scope.currentAnel.idJson}});
        $scope.currentVersaoPlano = versoes[$scope.currentVersaoPlanoIndex];
        $scope.currentEstagiosPlanos = planoService.atualizaEstagiosPlanos($scope.objeto, $scope.currentPlano);
        return $scope.currentEstagiosPlanos;
      };

      $scope.submitForm = function() {
        return $scope
          .submit($scope.objeto)
          .then(function(res) { $scope.objeto = res; })
          .catch(function(err) { $scope.errors = err; })
          .finally(influuntBlockui.unblock);
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

      $scope.planoTemErro = function(index) {
        var temErro = !!_.get($scope.errors, 'aneis[' + $scope.currentAnelIndex + '].versoesPlanos['+ $scope.currentVersaoPlanoIndex +'].planos['+ index +']');
        return temErro;
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

        var currentPlanoIndex = getIndexPlano($scope.currentAnel, $scope.currentPlano);
        erros.push(getErrosGruposSemaforicosPlanos(listaErros, currentPlanoIndex));
        erros.push(getErrosUltrapassaTempoCiclo(listaErros, currentPlanoIndex));
        erros.push(getErrosPlanoAtuadoSemDetector(listaErros, currentPlanoIndex));
        erros.push(getErrosSequenciaInvalida(listaErros, currentPlanoIndex));

        return _.flatten(erros);
      };

      $scope.erroTempoCiclo = function() {
        var errors = _.get($scope.errors, 'aneis['+ $scope.currentAnelIndex +'].versoesPlanos['+ $scope.currentVersaoPlanoIndex +'].planos['+ $scope.currentPlanoIndex +'].tempoCiclo');
        return errors;
      };

      $scope.erroDefasagem = function() {
        var errors = _.get($scope.errors, 'aneis['+ $scope.currentAnelIndex +'].versoesPlanos['+ $scope.currentVersaoPlanoIndex +'].planos['+ $scope.currentPlanoIndex +'].defasagem');
        return errors;
      };

      $scope.getErrosTempo = function(tempo) {
        var errors = _.get($scope.errors, 'aneis['+ $scope.currentAnelIndex +'].versoesPlanos['+ $scope.currentVersaoPlanoIndex +'].planos['+ $scope.currentPlanoIndex +'].estagiosPlanos['+ $scope.currentEstagioPlanoIndex +'].tempo'+tempo);
        return errors;
      };

      $scope.verificaVerdeMinimoDoEstagio = function(oldValue, value){
        var estagio = _.find($scope.objeto.estagios, {idJson: $scope.currentEstagiosPlanos[$scope.currentEstagioPlanoIndex].estagio.idJson});
        var tempoVerde = value;
        var verdeMinimo = estagio.verdeMinimoEstagio || planoService.verdeMinimoDoEstagio($scope.objeto, $scope.objeto.verdeMin, estagio);
        if (tempoVerde < verdeMinimo) {
          if (estagio.isVeicular) {
            influuntAlert
              .confirm(
                $filter('translate')('planos.verdeMinimoVeicular.tituloAlert'),
                $filter('translate')('planos.verdeMinimoVeicular.mensagemAlert')
              ).then(function(confirmado) {
                if (!confirmado) {
                  $scope.currentEstagiosPlanos[$scope.currentEstagioPlanoIndex].tempoVerde = oldValue;
                }
              });
          } else {
            influuntAlert.alert(
              $filter('translate')('planos.verdeMinimoPedestre.tituloAlert'),
              $filter('translate')('planos.verdeMinimoPedestre.mensagemAlert')
            );
            $scope.currentEstagiosPlanos[$scope.currentEstagioPlanoIndex].tempoVerde = oldValue;
          }
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
        diagramaDebouncer = $timeout(atualizaDiagramaIntervalos, 500);
      }, true);

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

      getErrosGruposSemaforicosPlanos = function(listaErros, currentPlanoIndex){
        var erros = [];

        if (listaErros) {
          var errosGruposSemaforicosPlanos = _.get(listaErros, 'planos['+ currentPlanoIndex +'].gruposSemaforicosPlanos');
          if (errosGruposSemaforicosPlanos) {
            _.each(errosGruposSemaforicosPlanos, function(erro, index) {
              if(erro) {
                var grupoSemaforicoPlanoIdJson = $scope.currentPlano.gruposSemaforicosPlanos[index].idJson;
                var grupoSemaforicoPlano = _.find($scope.objeto.gruposSemaforicosPlanos, {idJson: grupoSemaforicoPlanoIdJson});
                var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: grupoSemaforicoPlano.grupoSemaforico.idJson});
                var texto = 'G' + grupoSemaforico.posicao + ' - ' + erro.respeitaVerdesDeSeguranca[0];
                erros.push(texto);
              }
            });
          }
        }
        return erros;
      };

      getErrosUltrapassaTempoCiclo = function(listaErros, currentPlanoIndex){
        var erros = [];

        if(listaErros){
          var errosultrapassaTempoCiclo = _.get(listaErros, 'planos['+ currentPlanoIndex +'].ultrapassaTempoCiclo');
          if (errosultrapassaTempoCiclo) {
            _.each(errosultrapassaTempoCiclo, function (errosNoPlano){
              if(errosNoPlano) {
                var texto = errosNoPlano.replace("{temposEstagios}", _.sumBy($scope.currentEstagiosPlanos, function(o) {
                  return o.tempoEstagio || 0;
                }))
                .replace("{tempoCiclo}", $scope.currentPlano.tempoCiclo);
                erros.push(texto);
              }
            });
          }
        }
        return erros;
      };

      getErrosPlanoAtuadoSemDetector = function(listaErros, currentPlanoIndex) {
        var erros = _.get(listaErros, 'planos['+ currentPlanoIndex +'].modoOperacaoValido');
        if (erros) {
          return erros;
        }
        return [];
      };

      getErrosSequenciaInvalida = function(listaErros, currentPlanoIndex) {
        var erros = [];
        var errosSequencia;
        errosSequencia = _.get(listaErros, 'planos['+ currentPlanoIndex +'].sequenciaInvalida');
        if (errosSequencia) {
          erros.push(errosSequencia[0]);
        }
        errosSequencia = _.get(listaErros, 'planos['+ currentPlanoIndex +'].sequenciaInvalidaSeExisteEstagioDispensavel');
        if (errosSequencia) {
          erros.push(errosSequencia[0]);
        }
        return _.flatten(erros);
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
        var estagio = _.find($scope.objeto.estagios, {idJson: estagioIdJson});
        var novoEstagioPlano = {
          idJson: UUID.generate(),
          estagio: {
            idJson: estagioIdJson
          },
          plano: {
            idJson: planoIdJson
          },
          posicao: posicao,
          tempoVerde: planoService.verdeMinimoDoEstagio($scope.objeto, $scope.objeto.verdeMin, estagio),
          dispensavel: false
        };

        $scope.objeto.estagiosPlanos.push(novoEstagioPlano);
        $scope.currentPlano.estagiosPlanos.push({idJson: novoEstagioPlano.idJson});
      };

      selecionaAnel = function(index) {
        $scope.currentAnelIndex = index;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelIndex];
        setLocalizacaoNoCurrentAnel($scope.currentAnel);
        breadcrumbs.setNomeEndereco($scope.currentAnel.localizacao);
        $scope.currentEstagios = planoService.atualizaEstagios($scope.objeto, $scope.currentAnel);
        $scope.currentGruposSemaforicos = planoService.atualizaGruposSemaforicos($scope.objeto, $scope.currentAnel);
        $scope.currentTabelasEntreVerdes = planoService.atualizaTabelaEntreVerdes($scope.objeto, $scope.currentGruposSemaforicos);
        $scope.currentPlanos = planoService.atualizaPlanos($scope.objeto, $scope.currentAnel);
      };

      setLocalizacaoNoCurrentAnel = function(currentAnel){
        var idJsonEndereco = _.get(currentAnel.endereco, 'idJson');
        var currentEndereco = _.find($scope.objeto.todosEnderecos, {idJson: idJsonEndereco });
        $scope.currentAnel.localizacao = $filter('nomeEndereco')(currentEndereco);
      };

      limpaDadosPlano = function(plano) {
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
      };

      carregaDadosPlano = function(plano){
        if (plano.modoOperacao === 'ATUADO') {
          plano.estagiosPlanos.forEach(function(e) {
            var estagioPlano = _.find($scope.objeto.estagiosPlanos, {idJson: e.idJson});
            var estagio = _.find($scope.objeto.estagios, {idJson: estagioPlano.estagio.idJson});
            estagioPlano.tempoVerdeMinimo = planoService.verdeMinimoDoEstagio($scope.objeto, $scope.objeto.verdeMinimoMin, estagio);
            estagioPlano.tempoVerdeMaximo = $scope.objeto.verdeMaximoMin;
            estagioPlano.tempoVerdeIntermediario = $scope.objeto.verdeIntermediarioMin;
            estagioPlano.tempoExtensaoVerde = $scope.objeto.extensaoVerdeMin;
          });
        } else {
          plano.tempoCiclo = $scope.objeto.cicloMin;
          plano.estagiosPlanos.forEach(function(e) {
            var estagioPlano = _.find($scope.objeto.estagiosPlanos, {idJson: e.idJson});
            var estagio = _.find($scope.objeto.estagios, {idJson: estagioPlano.estagio.idJson});
            estagioPlano.tempoVerde = planoService.verdeMinimoDoEstagio($scope.objeto, $scope.objeto.verdeMin, estagio);
          });
        }

        if (plano.modoOperacao === 'TEMPO_FIXO_COORDENADO') {
          plano.defasagem = $scope.objeto.defasagemMin;
        }
      };

      removerPlanoLocal = function(plano, index) {
        var idPlano = plano.id;
        var indexPlano = _.findIndex($scope.objeto.planos, {idJson: plano.idJson});
        $scope.objeto.planos.splice(indexPlano, 1);

        indexPlano = _.findIndex($scope.currentAnel.planos, {idJson: plano.idJson});
        $scope.currentAnel.planos.splice(indexPlano, 1);

        if(plano.manualExclusivo) {
          planoService.criarPlanoManualExclusivo($scope.objeto, $scope.currentAnel);
        } else {
          planoService.adicionar($scope.objeto, $scope.currentAnel, plano.posicao);
        }

        $scope.currentPlanos = planoService.atualizaPlanos($scope.objeto, $scope.currentAnel);
        plano = _.find($scope.objeto.planos, {idJson: $scope.currentPlanos[index].idJson});
        plano.id = idPlano;
      };

      handleErroEditarPlano = function(err) {
        if (err.status === 403 && _.get(err, 'data.[0].message')) {
          toast.clear();
          influuntAlert.alert('Controlador', err.data[0].message);
        } else {
          toast.error($filter('translate')('geral.mensagens.default_erro'));
        }
      };

      /**
       * Caso o modo de operação seja intermitente ou apagado, ele deverá renderizar um diagrama estágio, contendo
       * somente estes modos. Caso contrário, deverá executar o metodo de geração do diagrama a partir do plugin.
       */
      atualizaDiagramaIntervalos = function() {
        $scope.dadosDiagrama = planoService.atualizaDiagramaIntervalos(
          $scope.objeto, $scope.currentAnel, $scope.currentGruposSemaforicos,
          $scope.currentEstagiosPlanos, $scope.currentPlano, $scope.valoresMinimos
        );
      };

      getIndexPlano = function(anel, plano){
        var planos = _.find($scope.objeto.versoesPlanos, {idJson: anel.versaoPlano.idJson}).planos;
        return _.findIndex(planos, {idJson: plano.idJson});
      };
    }]);
