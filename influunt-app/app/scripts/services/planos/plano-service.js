'use strict';

/**
* @ngdoc service
* @name influuntApp.planos/planoService
* @description
* # planos/planoService
* Factory in the influuntApp.
*/
angular.module('influuntApp')
  .factory('planoService', ['validaTransicao', 'modoOperacaoService', 'geraDadosDiagramaIntervalo', 'PermissionsService',
    function planoService(validaTransicao, modoOperacaoService, geraDadosDiagramaIntervalo, PermissionsService) {

      var criarPlano, associarEstagios, associarGruposSemaforicos, criarPlanoManualExclusivo, adicionar,
          verdeMinimoDoEstagio, setDiagramaEstatico, podeEditarControlador, getGruposNaoAssociados,
          isGrupoDemandaPrioritaria, isGrupoNemAssociadoNemDemandaPrioritaria;

      verdeMinimoDoEstagio = function(controlador, verdeMin, estagio) {
        var tempoMax = verdeMin;
        var veicular = false;
        _.each(estagio.estagiosGruposSemaforicos, function(gs){
          var egs = _.find(controlador.estagiosGruposSemaforicos, {idJson: gs.idJson});
          var grupo = _.find(controlador.gruposSemaforicos, {idJson: egs.grupoSemaforico.idJson});
          tempoMax = _.max([grupo.tempoVerdeSeguranca, tempoMax]);
          veicular = grupo.tipo === 'VEICULAR' ? true : veicular;
        });
        estagio.verdeMinimoEstagio = tempoMax;
        estagio.isVeicular = veicular;
        return tempoMax;
      };

      adicionar = function(controlador, anel, posicao, plano) {
        plano = plano || _.find(controlador.planos, {posicao: posicao, anel: {idJson: anel.idJson}});
        if (plano) {
          plano.configurado = true;
        } else {
          plano = criarPlano(controlador, anel, posicao);
        }
        return controlador;
      };

      criarPlanoManualExclusivo = function(controlador, anel) {
        var plano = _.find(controlador.planos, {modoOperacao: 'MANUAL', anel: {idJson: anel.idJson}});
        if (plano) {
          plano.configurado = true;
        } else {
          plano = criarPlano(controlador, anel, 0, 'Exclusivo', 'MANUAL');
        }
        plano.manualExclusivo = true;
        delete plano.cicloMin;
      };

      criarPlano = function(controlador, anel, posicao, descricao, modoOperacao) {
        descricao = descricao || 'PLANO ' + posicao ;
        modoOperacao = modoOperacao || 'TEMPO_FIXO_COORDENADO';
        var plano = {
          idJson: UUID.generate(),
          anel: { idJson: anel.idJson },
          descricao: descricao,
          posicao: posicao,
          modoOperacao: modoOperacao,
          posicaoTabelaEntreVerde: 1,
          gruposSemaforicosPlanos: [],
          estagiosPlanos: [],
          tempoCiclo: controlador.cicloMin,
          configurado: posicao === 1,
          versaoPlano: {idJson: anel.versaoPlano.idJson}
        };

        var versaoPlano = _.find(controlador.versoesPlanos, {idJson: anel.versaoPlano.idJson});
        versaoPlano.planos = versaoPlano.planos || [];
        versaoPlano.planos.push({idJson: plano.idJson});

        controlador = associarGruposSemaforicos(controlador, anel, plano);
        controlador = associarEstagios(controlador, anel, plano);

        controlador.planos = controlador.planos || [];
        controlador.planos.push(plano);

        anel.planos = anel.planos || [];
        anel.planos.push({idJson: plano.idJson});

        return plano;
      };

      associarGruposSemaforicos = function(controlador, anel, plano) {
        controlador.gruposSemaforicosPlanos = controlador.gruposSemaforicosPlanos || [];
        anel.gruposSemaforicos.forEach(function (g) {
          var grupo =  _.find(controlador.gruposSemaforicos, {idJson: g.idJson});
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

          controlador.gruposSemaforicosPlanos.push(grupoPlano);
          plano.gruposSemaforicosPlanos.push({idJson: grupoPlano.idJson});
        });

        return controlador;
      };

      associarEstagios = function(controlador, anel, plano) {
        controlador.estagiosPlanos = controlador.estagiosPlanos || [];
        anel.estagios.forEach(function (e){
          var estagio =  _.find(controlador.estagios, {idJson: e.idJson});

          if(!estagio.demandaPrioritaria){
            var estagioPlano = {
              idJson: UUID.generate(),
              estagio: {
                idJson: estagio.idJson
              },
              plano: {
                idJson: plano.idJson
              },
              posicao: estagio.posicao,
              tempoVerde: verdeMinimoDoEstagio(controlador, controlador.verdeMin, estagio),
              dispensavel: false
            };

            controlador.estagiosPlanos = controlador.estagiosPlanos || [];
            plano.estagiosPlanos = plano.estagiosPlanos || [];
            estagio.estagiosPlanos = estagio.estagiosPlanos || [];

            controlador.estagiosPlanos.push(estagioPlano);
            plano.estagiosPlanos.push({idJson: estagioPlano.idJson});
            estagio.estagiosPlanos.push({idJson: estagioPlano.idJson});
          }
        });

        return controlador;
      };


      // @todo: verificar se é o melhor local para estes métodos.
      var atualizaPlanos, atualizaGruposSemaforicos, atualizaEstagios, atualizaTabelaEntreVerdes,
      atualizaEstagiosPlanos, atualizaPosicaoEstagiosPlanos, atualizaTransicoesProibidas, atualizaDiagramaIntervalos,
      getPlanoParaDiagrama, montaTabelaValoresMinimos;

      atualizaPlanos = function(controlador, anel) {
        var ids = _.map(anel.planos, 'idJson');
        var currentPlanos = _
          .chain(controlador.planos)
          .filter(function(e) { return ids.indexOf(e.idJson) >= 0; })
          .orderBy('posicao')
          .value();

        anel.planos = _.map(currentPlanos, function(p) {
          return {
            idJson: p.idJson
          };
        });

        return currentPlanos;
      };

      atualizaGruposSemaforicos = function(controlador, anel) {
        var ids = _.map(anel.gruposSemaforicos, 'idJson');
        return _
          .chain(controlador.gruposSemaforicos)
          .filter(function(ep) {
            return ids.indexOf(ep.idJson) >= 0;
          })
          .orderBy('posicao')
          .value();
      };

      atualizaEstagios = function(controlador, anel) {
        var ids = _.map(anel.estagios, 'idJson');
        return _
          .chain(controlador.estagios)
          .filter(function(e) {
            return ids.indexOf(e.idJson) >= 0 && !e.demandaPrioritaria;
          })
          .orderBy('posicao')
          .value();
      };

      atualizaTabelaEntreVerdes = function(controlador, gruposSemaforicos) {
        var grupoSemaforico = _.find(controlador.gruposSemaforicos, {idJson: gruposSemaforicos[0].idJson});
        var ids = _.map(grupoSemaforico.tabelasEntreVerdes, 'idJson');

        return _
          .chain(controlador.tabelasEntreVerdes)
          .filter(function(tev) { return ids.indexOf(tev.idJson) >= 0; })
          .orderBy('posicao')
          .value();
      };

      atualizaEstagiosPlanos = function(controlador, plano) {
        var ids = _.map(plano.estagiosPlanos, 'idJson');
        var currentEstagiosPlanos = _
          .chain(controlador.estagiosPlanos)
          .filter(function(ep) {
            return !ep.destroy && ids.indexOf(ep.idJson) >= 0;
          })
          .orderBy('posicao')
          .value();

          return atualizaPosicaoEstagiosPlanos(currentEstagiosPlanos);
      };

      atualizaPosicaoEstagiosPlanos = function(currentEstagiosPlanos) {
        currentEstagiosPlanos.forEach(function (estagioPlano, index){
          estagioPlano.posicao = index + 1;
        });

        return currentEstagiosPlanos;
      };

      atualizaTransicoesProibidas = function(controlador, estagiosPlanos) {
        var transicoesProibidas = validaTransicao.valida(estagiosPlanos, controlador);

        // limpa as transicoes proibidas dos objetos.
        _.each(estagiosPlanos, function(ep) {
          ep.origemTransicaoProibida = false;
          ep.destinoTransicaoProibida = false;
        });

        // marca as transicoes proibidas nos objetos.
        _.each(transicoesProibidas, function(t) {
          estagiosPlanos[t.origem].origemTransicaoProibida = true;
          estagiosPlanos[t.destino].destinoTransicaoProibida = true;
        });

        return transicoesProibidas;
      };

      /**
       * Caso o modo de operação seja intermitente ou apagado, ele deverá renderizar um diagrama estágio, contendo
       * somente estes modos. Caso contrário, deverá executar o metodo de geração do diagrama a partir do plugin.
       */
      atualizaDiagramaIntervalos = function(controlador, anel, currentGruposSemaforicos, currentEstagiosPlanos, plano, valoresMinimos) {
        var transicoesProibidas = atualizaTransicoesProibidas(controlador, currentEstagiosPlanos);
        var dadosDiagrama;

        // Não deverá fazer o diagrama de intervalos enquanto houver transicoes proibidas
        if (transicoesProibidas.length > 0) {
          dadosDiagrama = {
            erros: _.chain(transicoesProibidas).map('mensagem').uniq().value()
          };
        } else if (['INTERMITENTE', 'APAGADO', 'ATUADO', 'MANUAL'].indexOf(plano.modoOperacao) < 0) {
          var fakenPlano = getPlanoParaDiagrama(plano, anel, currentGruposSemaforicos, controlador);
          var diagramaBuilder = new influunt.components.DiagramaIntervalos(fakenPlano, valoresMinimos);
          var result = diagramaBuilder.calcula();

          var estagiosPlanos = _.chain(controlador.estagiosPlanos)
            .filter(function(ep) {
              return !ep.destroy && ep.plano.idJson === plano.idJson;
            })
            .orderBy('posicao')
            .value();

          _.each(result.estagios, function(e, i) {
            var estagioPlano = estagiosPlanos[i];
            estagioPlano.tempoEstagio = e.duracao;
          });

          var gruposSemaforicos = _.chain(controlador.gruposSemaforicos)
            .filter({ anel: { idJson: anel.idJson } })
            .orderBy('posicao')
            .value();

          _.each(result.gruposSemaforicos, function(g) {
            var grupo = gruposSemaforicos[g.posicao-1];
            var grupoPlano = _.find(fakenPlano.gruposSemaforicosPlanos, {grupoSemaforico: {idJson: grupo.idJson}, plano: {idJson: fakenPlano.idJson}});
            var nemAssociadoNemDemandaPrioritaria = isGrupoNemAssociadoNemDemandaPrioritaria(controlador, anel, fakenPlano, grupo);

            if (nemAssociadoNemDemandaPrioritaria) {
              g.ativado = false;
            } else {
              g.ativado = grupoPlano.ativado;
            }
            if(!g.ativado){
              g.intervalos.unshift({
                status: modoOperacaoService.getModoIdByName('APAGADO'),
                duracao: fakenPlano.tempoCiclo || controlador.cicloMax
              });
            }
          });

          dadosDiagrama = result;
        } else {
          dadosDiagrama = setDiagramaEstatico(controlador, plano, anel);
        }

        return dadosDiagrama;
      };

      getPlanoParaDiagrama = function(plano, anel, gruposSemaforicos, controlador) {
        return geraDadosDiagramaIntervalo.gerar(plano, anel, gruposSemaforicos, controlador);
      };

      /**
       * Atualiza o diagrama de intervalos para os casos de modo de operação intermitente e desligado, onde
       * todos os grupos deverão assumir o mesmo estágio (entre amarelo-intermitente e desligado). Se não assumir
       * nenhum destes, deverá utilizar o diagrama produzido a partir do plugin de diagrama.
       */
      setDiagramaEstatico = function(controlador, plano, anel) {
        var modo = modoOperacaoService.getModoIdByName(plano.modoOperacao);
        var modoApagado = modoOperacaoService.getModoIdByName('APAGADO');
        var grupos = _.map(anel.gruposSemaforicos, function(g) {
          var grupo = _.find(controlador.gruposSemaforicos, {idJson: g.idJson});
          return {
            ativado: grupo.tipo === 'VEICULAR',
            posicao: grupo.posicao,
            labelPosicao: 'G' + grupo.posicao,
            intervalos: [{
              status: grupo.tipo === 'VEICULAR' ? modo : modoApagado,
              duracao: plano.tempoCiclo || controlador.cicloMax
            }]
          };
        });
        grupos = _.orderBy(grupos, ['posicao']);
        return {
          estagios: [{posicao: 1, duracao: plano.tempoCiclo || controlador.cicloMax}],
          gruposSemaforicos: grupos,
          erros: [],
          tempoCiclo: plano.tempoCiclo || controlador.cicloMax
        };
      };

      montaTabelaValoresMinimos = function(controlador) {
        return {
          verdeMin: parseInt(controlador.verdeMin),
          verdeMinimoMin: parseInt(controlador.verdeMinimoMin)
        };
      };

      podeEditarControlador = function(controlador) {
        if (controlador) {
          var controladorConfigurado = controlador.statusControladorReal !== 'EM_CONFIGURACAO' && controlador.statusControladorReal !== 'EDITANDO';
          var usuario = PermissionsService.getUsuario();
          var editor = _.get(controlador, 'versaoControlador.usuario');
          return controladorConfigurado || !editor || usuario.id === editor.id;
        }
        return false;
      };


      getGruposNaoAssociados = function(controlador, anel, plano) {
        var estagiosIdJson = _.chain(controlador.estagios)
                              .filter({ anel: { idJson: anel.idJson } })
                              .map('idJson')
                              .value();
        var estagiosAssociadosIdJson = _.chain(controlador.estagiosPlanos)
                                        .filter(function(ep) { return !ep.destroy && estagiosIdJson.indexOf(ep.estagio.idJson) > -1 && ep.plano.idJson === plano.idJson; })
                                        .map('estagio.idJson')
                                        .value();

        var gruposSemaforicos = _.filter(controlador.gruposSemaforicos, { anel: { idJson: anel.idJson } });
        var gruposNaoAssociados = [];
        _.each(gruposSemaforicos, function(gs) {
          var isGrupoNaoAssociado = _
            .chain(controlador.estagiosGruposSemaforicos)
            .filter(function(egs) { return egs.grupoSemaforico.idJson === gs.idJson && estagiosAssociadosIdJson.indexOf(egs.estagio.idJson) > -1; })
            .value().length === 0;

          if (isGrupoNaoAssociado) {
            gruposNaoAssociados.push(gs.idJson);
          }
        });

        return gruposNaoAssociados;
      };

      isGrupoDemandaPrioritaria = function(controlador, grupoSemaforico) {
        var estagios = _.chain(controlador.estagiosGruposSemaforicos)
                        .filter({ grupoSemaforico: { idJson: grupoSemaforico.idJson } })
                        .map('estagio.idJson')
                        .value();
        return _.find(controlador.estagios, function(e) { return e.demandaPrioritaria && estagios.indexOf(e.idJson) > -1; });
      };

      /*
       * Retorna true se:
       *   - o grupo semafórico não estiver associado a um estágio
       *      de demanda prioritária
       *   - nenhum estágio associado ao grupo semafórico estiver associado à
       *     sequência de estágios do plano.
       */
      isGrupoNemAssociadoNemDemandaPrioritaria = function(controlador, anel, plano, grupoSemaforico) {
        var isDemandaPrioritaria = isGrupoDemandaPrioritaria(controlador, grupoSemaforico);
        if (!isDemandaPrioritaria) {
          var gruposNaoAssociados = getGruposNaoAssociados(controlador, anel, plano);
          var isGrupoNaoAssociado = gruposNaoAssociados.indexOf(grupoSemaforico.idJson) > -1;
          return isGrupoNaoAssociado;
        }
        return false;
      };

      return {
        adicionar: adicionar,
        verdeMinimoDoEstagio: verdeMinimoDoEstagio,
        criarPlanoManualExclusivo: criarPlanoManualExclusivo,
        atualizaPlanos: atualizaPlanos,
        atualizaGruposSemaforicos: atualizaGruposSemaforicos,
        atualizaEstagios: atualizaEstagios,
        atualizaTabelaEntreVerdes: atualizaTabelaEntreVerdes,
        atualizaEstagiosPlanos: atualizaEstagiosPlanos,
        atualizaPosicaoEstagiosPlanos: atualizaPosicaoEstagiosPlanos,
        atualizaTransicoesProibidas: atualizaTransicoesProibidas,
        atualizaDiagramaIntervalos: atualizaDiagramaIntervalos,
        montaTabelaValoresMinimos: montaTabelaValoresMinimos,
        podeEditarControlador: podeEditarControlador,
        isGrupoNemAssociadoNemDemandaPrioritaria: isGrupoNemAssociadoNemDemandaPrioritaria
      };
    }]);
