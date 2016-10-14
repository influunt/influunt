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
                             'handleValidations', 'utilControladores', 'planoService', 'breadcrumbs', 'SimulacaoService',
    function ($scope, $state, $timeout, Restangular, $filter,
              validaTransicao, utilEstagios, toast, modoOperacaoService,
              influuntAlert, influuntBlockui, geraDadosDiagramaIntervalo,
              handleValidations, utilControladores, planoService, breadcrumbs, SimulacaoService) {

      var selecionaAnel, atualizaTabelaEntreVerdes, atualizaEstagios, atualizaGruposSemaforicos, atualizaPlanos,
          atualizaEstagiosPlanos, adicionaEstagioASequencia, atualizaPosicaoEstagiosPlanos,
          carregaDadosPlano, getOpcoesEstagiosDisponiveis, montaTabelaValoresMinimos, setDiagramaEstatico,
          atualizaDiagramaIntervalos, getPlanoParaDiagrama, atualizaTransicoesProibidas, getErrosGruposSemaforicosPlanos,
          getErrosPlanoAtuadoSemDetector, duplicarPlano, removerPlanoLocal, getErrosUltrapassaTempoCiclo, getErrosSequenciaInvalida,
          getIndexPlano, handleErroEditarPlano, setLocalizacaoNoCurrentAnel, sortPlanos;

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
            montaTabelaValoresMinimos();

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
        return Restangular
          .one('controladores', controladorId).all('pode_editar').customGET()
          .then(function() {
            return Restangular.one('controladores', controladorId).all('editar_planos').customGET();
          })
          .then(function() {
            $state.go('app.planos_edit', { id: controladorId });
          })
          .catch(handleErroEditarPlano)
          .finally(influuntBlockui.unblock);
      };

      $scope.editarPlano = function(controladorId) {
        return Restangular.one('controladores', controladorId).all("pode_editar").customGET()
          .then(function() {
            $state.go('app.planos_edit', { id: controladorId });
          })
          .catch(handleErroEditarPlano)
          .finally(influuntBlockui.unblock);
      };

      $scope.cancelarEdicao = function() {
        var plano = _.chain($scope.objeto.planos).filter(function(p) { return !!p.id; }).last().value();
        influuntAlert.delete().then(function(confirmado) {
          if (plano && plano.id) {
            return confirmado && Restangular.one('planos', plano.id).all('cancelar_edicao').customDELETE()
              .then(function() {
                toast.success($filter('translate')('geral.mensagens.removido_com_sucesso'));
                $state.go('app.controladores');
              })
              .catch(function(err) {
                toast.error($filter('translate')('geral.mensagens.default_erro'));
                throw new Error(JSON.stringify(err));
              })
              .finally(influuntBlockui.unblock);
          } else {
            $state.go('app.controladores');
          }
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

        atualizaPlanos();

        plano = _.find($scope.objeto.planos, {idJson: $scope.currentPlanos[index].idJson});
        plano.id = idPlano;
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
        disabled: $scope.somenteVisualizacao,
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
        return atualizaEstagiosPlanos();
      };

      $scope.getImagemDeEstagio = function(estagioPlano) {
        if(estagioPlano.idJson){
          var ep = _.find($scope.objeto.estagiosPlanos, {idJson: estagioPlano.idJson});
          var estagio = _.find($scope.objeto.estagios, {idJson: ep.estagio.idJson});
          var imagem = _.find($scope.objeto.imagens, {idJson: estagio.imagem.idJson});
          return imagem && $filter('imageSource')(imagem.id);
        }
      };

      $scope.getEstagio = function(estagioPlano) {
        if (estagioPlano) {
          var ep = _.find($scope.objeto.estagiosPlanos, {idJson: estagioPlano.idJson});
          if (ep) {
            var estagio = _.find($scope.objeto.estagios, {idJson: ep.estagio.idJson});
            return estagio;
          }
        }
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

      $scope.timeline = function() {
        if($scope.currentAnel) {
          return Restangular.one('planos', $scope.currentAnel.id).all('timeline').customGET()
            .then(function(res) {
              $scope.versoes = res;
            })
            .catch(function(err) {
              toast.error($filter('translate')('geral.mensagens.default_erro'));
              throw new Error(JSON.stringify(err));
            })
            .finally(influuntBlockui.unblock);
        }
      };

      /**
       * Evita que dados informados para um plano em determinado modo de operação vaze
       * para o diagrama criado.
       *
       * @param      {<type>}  plano   The plano
       */
      $scope.limpaDadosPlano = function() {
        $timeout(function(){
          atualizaEstagiosPlanos();
        });
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

      sortPlanos = function() {
        _.forEach($scope.objeto.versoesPlanos, function(versaoPlano) {
          versaoPlano.planos = _
            .chain($scope.objeto.planos)
            .filter(function(plano) { return plano.anel.idJson === versaoPlano.anel.idJson; })
            .orderBy('posicao')
            .map(function(plano) { return { idJson: plano.idJson } })
            .value();
        })
      };

      $scope.submitForm = function() {
        // planos são ordenados antes de submeter o form
        // para que os erros voltem ordenados da API.
        sortPlanos();

        Restangular.all('planos').post($scope.objeto)
          .then(function(res) {
            $scope.objeto = res;

            $scope.errors = {};
            influuntBlockui.unblock();
            $state.go('app.controladores');
          })
          .catch(function(res) {
            influuntBlockui.unblock();
            if (res.status === 422) {
              $scope.errors = handleValidations.buildValidationMessages(res.data, $scope.objeto);
            } else {
              console.error(res);
            }
          })
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




      // getKeysErros = function(errors) {
      //   var keysErrors = [];
      //   _.forEach(errors, function(v, key){
      //     if (typeof v !== 'undefined' && v !== null) {
      //       keysErrors.push(key);
      //     }
      //   });
      //   return keysErrors;
      // };

      // getIdJsonDePlanosQuePossuemErros = function (keysErrors) {
      //   var errorsPlanoIdJson = [];
      //   _.map(keysErrors, function(KeyError) {
      //     var versaoPlanosByCurrentAnel = _.find($scope.objeto.versoesPlanos, {anel: {idJson: $scope.currentAnel.idJson}});
      //     versaoPlanosByCurrentAnel.planos = _
      //       .chain($scope.objeto.planos)
      //       .orderBy('posicao')
      //       .map(function(plano) { return { idJson: plano.idJson }; })
      //       .value();
      //     errorsPlanoIdJson.push(versaoPlanosByCurrentAnel.planos[KeyError].idJson);
      //   });
      //   return errorsPlanoIdJson;
      // };

      // getPlanoComErro = function (planos, errorsPlanoIdJson) {
      //   var errorsPlanos = [];

      //   errorsPlanos = _.chain(planos)
      //     .filter(function(e) {
      //       return errorsPlanoIdJson.indexOf(e.idJson) >= 0;
      //    }).value();
      //   return errorsPlanos;
      // };

      // $scope.getErroPorPlano = function(index) {
      //   $scope.objeto.planos = _.orderBy($scope.objeto.planos, 'posicao');
      //   var errors              = _.get($scope.errors, 'aneis[' + $scope.currentAnelIndex + '].versoesPlanos['+ $scope.currentVersaoPlanoIndex +'].planos');
      //   var keysErrors          = getKeysErros(errors);
      //   var errorsPlanoIdJson   = getIdJsonDePlanosQuePossuemErros(keysErrors);
      //   var errorsInPlanos      = getPlanoComErro($scope.objeto.planos, errorsPlanoIdJson);
      //   var errorsPosicao       = [];

      //   _.map(errorsInPlanos, function(errorInPlano) {
      //     errorsPosicao.push(errorInPlano.posicao);
      //   });

      //   var assertError = _.some(errorsPosicao, function(errorPosicao) {
      //     // console.log($scope)
      //     // debugger
      //     if (!$scope.currentAnel.aceitaModoManual) {
      //       index = index + 1;
      //     }
      //     return index === errorPosicao;
      //   });

      //   return assertError;
      // };






      $scope.getErrosEstagiosPlanos = function(index) {
        var erros = _.get($scope.errors, 'aneis[' + $scope.currentAnelIndex + '].versoesPlanos[' + $scope.currentVersaoPlanoIndex + '].planos[' + getIndexPlano($scope.currentAnel, $scope.currentPlano) + '].estagiosPlanos[' + index + ']');
        return erros;
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
          tempoVerde: planoService.verdeMinimoDoEstagio($scope.objeto, estagio),
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
        atualizaEstagios($scope.currentAnel);
        atualizaGruposSemaforicos();
        atualizaTabelaEntreVerdes($scope.currentAnel);
        atualizaPlanos();
        $scope.timeline();
      };

      setLocalizacaoNoCurrentAnel = function(currentAnel){
        var idJsonEndereco = _.get(currentAnel.endereco, 'idJson');
        var currentEndereco = _.find($scope.objeto.todosEnderecos, {idJson: idJsonEndereco });
        $scope.currentAnel.localizacao = $filter('nomeEndereco')(currentEndereco);
      };

      atualizaEstagios = function(anel) {
        var ids = _.map(anel.estagios, 'idJson');
        $scope.currentEstagios = _
          .chain($scope.objeto.estagios)
          .filter(function(e) {
            return ids.indexOf(e.idJson) >= 0 && !e.demandaPrioritaria;
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

      atualizaTabelaEntreVerdes = function() {
        var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: $scope.currentGruposSemaforicos[0].idJson});
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

      carregaDadosPlano = function(plano){
        if (plano.modoOperacao === 'ATUADO') {
          plano.estagiosPlanos.forEach(function(e) {
            var estagio = _.find($scope.objeto.estagiosPlanos, {idJson: e.idJson});
            estagio.tempoVerdeMinimo = $scope.objeto.verdeMinimoMin;
            estagio.tempoVerdeMaximo = $scope.objeto.verdeMaximoMin;
            estagio.tempoVerdeIntermediario = $scope.objeto.verdeIntermediarioMin;
            estagio.tempoExtensaoVerde = $scope.objeto.extensaoVerdeMin;
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

      handleErroEditarPlano = function(err) {
        if (err.status === 403 && _.get(err, 'data.[0].message')) {
          toast.clear();
          influuntAlert.alert('Controlador', err.data[0].message);
        } else {
          toast.error($filter('translate')('geral.mensagens.default_erro'));
        }
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
            labelPosicao: 'G' + grupo.posicao,
            intervalos: [{
              status: grupo.tipo === 'VEICULAR' ? modo : modoApagado,
              duracao: $scope.currentPlano.tempoCiclo || $scope.objeto.cicloMax
            }]
          };
        });
        grupos = _.orderBy(grupos, ['posicao']);
        $scope.dadosDiagrama = {
          estagios: [{posicao: 1, duracao: $scope.currentPlano.tempoCiclo || $scope.objeto.cicloMax}],
          gruposSemaforicos: grupos,
          erros: [],
          tempoCiclo: $scope.currentPlano.tempoCiclo || $scope.objeto.cicloMax
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

        if (['INTERMITENTE', 'APAGADO', 'ATUADO', 'MANUAL'].indexOf($scope.currentPlano.modoOperacao) < 0) {
          getPlanoParaDiagrama();
          var diagramaBuilder = new influunt.components.DiagramaIntervalos($scope.plano, $scope.valoresMinimos);
          var result = diagramaBuilder.calcula();

          var estagiosPlanos = _.chain($scope.objeto.estagiosPlanos)
            .filter(function(ep) { return ep.plano.idJson === $scope.currentPlano.idJson; })
            .orderBy(['posicao'])
            .value();

          _.each(result.estagios, function(e, i) {
            var estagioPlano = estagiosPlanos[i];
            estagioPlano.tempoEstagio = e.duracao;
          });

          var gruposSemaforicos = _.chain($scope.objeto.gruposSemaforicos)
            .filter(function(gs) { return gs.anel.idJson === $scope.currentAnel.idJson; })
            .orderBy(['posicao'])
            .value();

          _.each(result.gruposSemaforicos, function(g) {
            var grupo = gruposSemaforicos[g.posicao-1];
            var grupoPlano = _.find($scope.plano.gruposSemaforicosPlanos, {grupoSemaforico: {idJson: grupo.idJson}, plano: {idJson: $scope.plano.idJson}});
            g.ativado = grupoPlano.ativado;
            if(!g.ativado){
              g.intervalos.unshift({
                status: modoOperacaoService.getModoIdByName('APAGADO'),
                duracao: $scope.plano.tempoCiclo || $scope.objeto.cicloMax
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

      getIndexPlano = function(anel, plano){
        var planos = _.find($scope.objeto.versoesPlanos, {idJson: anel.versaoPlano.idJson}).planos;
        return _.findIndex(planos, {idJson: plano.idJson});
      };

      $scope.verificaVerdeMinimoDoEstagio = function(oldValue, value){
        var estagio = _.find($scope.objeto.estagios, {idJson: $scope.currentEstagiosPlanos[$scope.currentEstagioPlanoIndex].estagio.idJson});
        var tempoVerde = value;
        var verdeMinimo = estagio.verdeMinimoEstagio || planoService.verdeMinimoDoEstagio($scope.objeto, estagio);
        if(tempoVerde < verdeMinimo){
          if(estagio.isVeicular){
            influuntAlert.confirm($filter('translate')('planos.verdeMinimoVeicular.tituloAlert'),
                $filter('translate')('planos.verdeMinimoVeicular.mensagemAlert')).then(function(confirmado) {
              if (!confirmado) {
                $scope.currentEstagiosPlanos[$scope.currentEstagioPlanoIndex].tempoVerde = oldValue;
              }
            });
          }else{
            influuntAlert.alert($filter('translate')('planos.verdeMinimoPedestre.tituloAlert'),
                $filter('translate')('planos.verdeMinimoPedestre.mensagemAlert'));
            $scope.currentEstagiosPlanos[$scope.currentEstagioPlanoIndex].tempoVerde = oldValue;
          }
        }
      };

      $scope.podeSimular = function(controlador) {
        return SimulacaoService.podeSimular(controlador);
      };

    }]);
