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
          atualizaDiagramaIntervalos, getPlanoParaDiagrama, atualizaTransicoesProibidas;
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
            if(!(_.isArray(anel.planos) && anel.planos.length > 0)) {
              adicionaPlano(anel);
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

      $scope.adicionarNovoPlano = function(){
        adicionaPlano($scope.currentAnel);
        atualizaPlanos();
      };

      $scope.duplicarPlano = function(plano) {
        var posicao = $scope.currentAnel.planos.length + 1;

        var novoPlano = _.cloneDeep(plano);
        novoPlano.gruposSemaforicosPlanos.forEach(function (gp){
          var grupoSemaforicoPlano = _.find($scope.objeto.gruposSemaforicosPlanos, {idJson: gp.idJson});
          var novoGrupoSemaforicoPlano = _.cloneDeep(grupoSemaforicoPlano);
          gp.idJson = UUID.generate();
          novoGrupoSemaforicoPlano.idJson = gp.idJson;
          $scope.objeto.gruposSemaforicosPlanos.push(novoGrupoSemaforicoPlano);
        });
        novoPlano.estagiosPlanos.forEach(function (ep){
          var estagioPlano = _.find($scope.objeto.estagiosPlanos, {idJson: ep.idJson});
          var novoEstagioPlano = _.cloneDeep(estagioPlano);
          ep.idJson = UUID.generate();
          novoEstagioPlano.idJson = ep.idJson;
          $scope.objeto.estagiosPlanos.push(novoEstagioPlano);
        });
        novoPlano.idJson = UUID.generate();
        novoPlano.posicao = posicao;

        $scope.objeto.planos.push(novoPlano);
        $scope.currentAnel.planos.push({idJson: novoPlano.idJson});
        atualizaPlanos();
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

      $scope.getErrosPlanos = function(listaErros) {
        return _.chain(listaErros).map().flatten().map().flatten().value();
      };

      /**
       * Adiciona um novo plano ao controlador.
       *
       * @param      {<type>}  anel    The anel
       */
      adicionaPlano = function(anel) {
        // anel = anel;
        var posicao = anel.planos.length + 1;
        var plano = {
          idJson: UUID.generate(),
          anel: { idJson: anel.idJson },
          posicao: posicao,
          modoOperacao: 'TEMPO_FIXO_ISOLADO',
          posicaoTabelaEntreVerde: 1,
          gruposSemaforicosPlanos: [],
          estagiosPlanos: [],
          tempoCiclo: $scope.objeto.cicloMin
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
            ativo: true,
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

        if (['INTERMITENTE', 'APAGADO'].indexOf($scope.currentPlano.modoOperacao) < 0) {
          getPlanoParaDiagrama();
          var diagramaBuilder = new influunt.components.DiagramaIntervalos($scope.plano, $scope.valoresMinimos);
          var result = diagramaBuilder.calcula();

          _.each(result.gruposSemaforicos, function(g) {
            g.ativo = true;
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
