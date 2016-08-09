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
                             'influuntAlert', 'influuntBlockui',
    function ($scope, $state, $timeout, Restangular, $filter,
              validaTransicao, utilEstagios, toast, modoOperacaoService,
              influuntAlert, influuntBlockui) {

      var adicionaPlano, selecionaAnel, atualizaTabelaEntreVerdes, atualizaEstagios, atualizaPlanos, atualizaEstagiosPlanos, adicionaEstagioASequencia, atualizaPosicaoPlanos, atualizaPosicaoEstagiosPlanos, getOpcoesEstagiosDisponiveis, montaTabelaValoresMinimos, parseAllToInt;
      var diagramaDebouncer = null;

      /**
       * Inicializa a tela de planos. Carrega os dados básicos da tela.
       */
      $scope.init = function() {
        var id = $state.params.id;
        Restangular.one('controladores', id).get().then(function(res) {
          $scope.objeto = res;
          parseAllToInt();
          montaTabelaValoresMinimos();

          $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
          $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});
          $scope.aneis.forEach(function(anel) {
            if(!(_.isArray(anel.planos) && anel.planos.length > 0)) {
              adicionaPlano(anel);
            }
          });

          return $scope.selecionaAnelPlanos(0);
        });
      };

      // /**
      //  * Adiciona um novo plano à lista de planos dos aneis. Após a criação do novo
      //  * plano, este deverá ser colocado em edição imediatamente.
      //  */
      // $scope.adicionarPlano = function() {
      //   adicionaPlano($scope.currentAnel);
      //
      //   var plano = $scope.objeto.planos[$scope.objeto.planos.length - 1];
      //   $scope.selecionaPlano(plano, $scope.currentAnel.planos.length - 1);
      // };
      //
      //
      
      $scope.selecionaEstagioPlano = function(estagioPlano, index) {
        $scope.currentEstagioPlanoIndex = index;
        $scope.currentEstagioPlano = estagioPlano;
        getOpcoesEstagiosDisponiveis();
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

      $scope.adicionarNovoPlano = function(){
        adicionaPlano($scope.currentAnel);
        atualizaPlanos();
      }

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
          estagiosPlanos: []
        };
        
        $scope.objeto.gruposSemaforicosPlanos = $scope.objeto.gruposSemaforicosPlanos || [];
        anel.gruposSemaforicos.forEach(function (g){
          var grupo =  _.find($scope.objeto.gruposSemaforicos, {idJson: g.idJson});
          var grupoPlano = {
            idJson: UUID.generate(),
            ativo: true,
            grupoSemaforico: {
              idJson: grupo.idJson
            },
            plano: {
              idJson: plano.idJson
            }
          }
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
          }
          $scope.objeto.estagiosPlanos.push(estagioPlano);
          plano.estagiosPlanos.push({idJson: estagioPlano.idJson});
        });


        $scope.objeto.planos = $scope.objeto.planos || [];
        $scope.objeto.planos.push(plano);
        anel.planos.push({idJson: plano.idJson});
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
      //
      //
      //
      //
      // $scope.getTabelasEntreVerdes = function() {
      //   var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: $scope.currentAnel.gruposSemaforicos[0].idJson});
      //   $scope.tabelasEntreVerdes = atualizaTabelaEntreVerdes(grupoSemaforico);
      //   $scope.currentTabelaEntreVerdes = $scope.tabelasEntreVerdes[0];
      // };
      //
      // criaSequenciaEstagios = function() {
      //   $scope.objeto.estagiosPlanos = $scope.objeto.estagiosPlanos || [];
      //   $scope.currentPlano.sequenciaEstagios = $scope.currentPlano.sequenciaEstagios || [];
      //   $scope.currentEstagios.forEach(function(estagio, index) {
      //     adicionaEstagioASequencia(estagio, index + 1);
      //   });
      //
      //   return $scope.currentPlano.sequenciaEstagios;
      // };
      //
      //
      // $scope.onChangeCheckboxGrupo = function(grupo, isAtivo) {
      //   if (!isAtivo) {
      //     grupo.intervalos.unshift({
      //       status: modoOperacaoService.getModoIdByName('APAGADO'),
      //       duracao: 255
      //     });
      //   } else {
      //     grupo.intervalos.shift();
      //   }
      // };
      //
      // /**
      //  * Atualiza o diagrama de intervalos para os casos de modo de operação intermitente e desligado, onde
      //  * todos os grupos deverão assumir o mesmo estágio (entre amarelo-intermitente e desligado). Se não assumir
      //  * nenhum destes, deverá utilizar o diagrama produzido a partir do plugin de diagrama.
      //  */
      // setDiagramaEstatico = function() {
      //   var modo = modoOperacaoService.getModoIdByName($scope.currentPlano.modoOperacao);
      //   var modoApagado = modoOperacaoService.getModoIdByName('APAGADO');
      //   var grupos = _.map($scope.currentAnel.gruposSemaforicos, function(g, i) {
      //     var grupo = _.find($scope.objeto.gruposSemaforicos, {idJson: g.idJson});
      //     return {
      //       ativo: true,
      //       posicao: (i + 1),
      //       intervalos: [{
      //         status: grupo.tipo === 'VEICULAR' ? modo : modoApagado,
      //         duracao: 255
      //       }]
      //     };
      //   });
      //
      //   $scope.dadosDiagrama = {
      //     estagios: [{posicao: 1, duracao: 255}],
      //     gruposSemaforicos: grupos,
      //     erros: [],
      //     tempoCiclo: 255
      //   };
      // };
      //
      // /**
      //  * Caso o modo de operação seja intermitente ou apagado, ele deverá renderizar um diagrama estágio, contendo
      //  * somente estes modos. Caso contrário, deverá executar o metodo de geração do diagrama a partir do plugin.
      //  */
      // atualizaDiagramaIntervalos = function() {
      //   if (['INTERMITENTE', 'APAGADO'].indexOf($scope.currentPlano.modoOperacao) < 0) {
      //     getPlanoParaDiagrama();
      //     var diagramaBuilder = new influunt.components.DiagramaIntervalos($scope.plano, $scope.valoresMinimos);
      //     var result = diagramaBuilder.calcula();
      //
      //     result.gruposSemaforicos.forEach(function(g) {
      //       g.ativo = true;
      //     });
      //
      //     $scope.dadosDiagrama = result;
      //   } else {
      //     setDiagramaEstatico();
      //   }
      // };
      //
      // /**
      //  * Evita que dados informados para um plano em determinado modo de operação vaze
      //  * para o diagrama criado.
      //  *
      //  * @param      {<type>}  plano   The plano
      //  */
      // limpaDadosPlano = function(plano) {
      //   if (plano.modoOperacao === 'ATUADO') {
      //     plano.tempoCiclo = null;
      //     plano.sequenciaEstagios.forEach(function(estagio) {
      //       estagio.tempoVerde = null;
      //     });
      //   } else {
      //     plano.tempoVerdeMinimo = null;
      //     plano.tempoVerdeMaximo = null;
      //     plano.tempoVerdeIntermediario = null;
      //     plano.tempoExtensaoVerde = null;
      //   }
      //
      //   if (plano.modoOperacao !== 'COORDENADO') {
      //     plano.defasagem = null;
      //   }
      // };
      //
      // /**
      //  * Retorna uma copia do currentPlano com algumas modificações.
      //  * Deverá ser utilizado para produzir o diagrama de intervalos.
      //  *
      //  * @return     {<type>}  The plano para diagrama.
      //  */
      // getPlanoParaDiagrama = function() {
      //   $scope.plano = _.cloneDeep($scope.currentPlano);
      //   $scope.plano.quantidadeGruposSemaforicos = $scope.currentAnel.gruposSemaforicos.length;
      //   $scope.plano.posicaoTabelaEntreVerdes = $scope.currentTabelaEntreVerdes.posicao;
      //
      //   for(var i = 0; i < $scope.plano.sequenciaEstagios.length; i++) {
      //     var ep = _.find($scope.objeto.estagiosPlanos, {idJson: $scope.plano.sequenciaEstagios[i].idJson});
      //     var estagio = _.find($scope.objeto.estagios, {idJson: ep.estagio.idJson});
      //
      //     var campos = [
      //       'tempoVerdeMinimo', 'tempoVerdeMaximo', 'tempoVerdeIntermediario',
      //       'tempoExtensaoVerde', 'dispensavel', 'tempoVerde', 'tipoEstagioDispensavel'
      //     ];
      //     var dados = _.pick($scope.plano.sequenciaEstagios[i], campos);
      //     $scope.plano.sequenciaEstagios[i] = _.merge(dados, estagio);
      //
      //     $scope.plano.sequenciaEstagios[i].estagiosGruposSemaforicos.forEach(function(egs) {
      //       var estagioGrupoSemaforico = _.find($scope.objeto.estagiosGruposSemaforicos, {idJson: egs.idJson});
      //       var id = estagioGrupoSemaforico.grupoSemaforico && estagioGrupoSemaforico.grupoSemaforico.idJson;
      //       if (id) {
      //         estagio.gruposSemaforicos = estagio.gruposSemaforicos || [];
      //         var grupoSemaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: id});
      //
      //         var tevs = [];
      //         _.each(grupoSemaforico.tabelasEntreVerdes, function(t) {
      //           var tev = _.find($scope.objeto.tabelasEntreVerdes, {idJson: t.idJson});
      //           tevs.push(tev);
      //         });
      //         grupoSemaforico.tabelasEntreVerdes = tevs;
      //
      //
      //         var transicoes = [];
      //         _.each(grupoSemaforico.transicoes, function(t) {
      //           var transicao = _.find($scope.objeto.transicoes, {idJson: t.idJson});
      //
      //           var tevts = [];
      //           _.each(transicao.tabelaEntreVerdesTransicoes, function(tevt) {
      //             var x = _.find($scope.objeto.tabelasEntreVerdesTransicoes, {idJson: tevt.idJson});
      //             tevts.push(x);
      //           });
      //           transicao.tabelaEntreVerdesTransicoes = tevts;
      //
      //           transicoes.push(transicao);
      //         });
      //
      //         grupoSemaforico.transicoes = transicoes;
      //
      //         estagio.gruposSemaforicos.push(grupoSemaforico);
      //       }
      //     });
      //   }
      //
      //   limpaDadosPlano($scope.plano);
      //   return $scope.plano;
      // };
      //
      // // variavel de controle, utilizada para verificar se o currentAnelId deve ou não ser atualizado.
      // // Isto deve ser feito somente se a transição existir (ex.: E1, na posicao 1, caso seja arrastado para a
      // // posicao 2, o currentEstagioId deve ser 2; caso contrário, permanece em 1).
      // var transicaoAprovada = false;
      // $scope.sortableOptions = {
      //   handle: '> .sortable',
      //   update: function(event, ui) {
      //     var msg = validaTransicao.valida(ui, $scope.currentSequenciaEstagios, $scope.objeto);
      //     if (msg) {
      //       toast.warn(msg);
      //       ui.item.sortable.cancel();
      //       return false;
      //     }
      //
      //     transicaoAprovada = true;
      //   },
      //   stop: function(event, ui) {
      //     if (transicaoAprovada) {
      //       transicaoAprovada = false;
      //       $scope.currentPlanoEstagioIndex = ui.item.sortable.dropindex;
      //       return $scope.getOpcoesEstagiosDisponiveis();
      //     }
      //   }
      // };
      //
      // /**
      //  * Reenderiza novamente o diagrama de intervalos quando qualquer aspecto do plano for alterado.
      //  * Faz um debounce de 500ms, para evitar chamadas excessivas à "calculadora" do diagrama.
      //  *
      //  * Caso o modo de operação do plano for "amarelo intermitente" ou "desligado", o diagrama deverá ser gerado
      //  * de forma estática (todo o diagrama deve assumir um dos modos acima).
      //  */
      // $scope.$watch('currentPlano', function(value) {
      //   if (value) {
      //     $timeout.cancel(diagramaDebouncer);
      //     diagramaDebouncer = $timeout(atualizaDiagramaIntervalos, 500);
      //   }
      // }, true);
      //
      $scope.selecionaAnelPlanos = function(index) {
        selecionaAnel(index);
        $scope.selecionaPlano($scope.currentPlanos[0], 0);
      };
      
      selecionaAnel = function(index) {
        $scope.currentAnelIndex = index;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelIndex];
        atualizaEstagios($scope.currentAnel);
        atualizaTabelaEntreVerdes($scope.currentAnel);
        atualizaPlanos();
        

        // var plano = _.find($scope.objeto.planos, {idJson: $scope.currentAnel.planos[0].idJson});
        // $scope.selecionaPlano(plano, 0);
        // if (angular.isDefined($scope.currentEstagioId)) {
        //   $scope.selecionaPlanoEstagio($scope.currentEstagioId);
        // }
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
      
      $scope.selecionaPlano = function(plano, index) {
        $scope.currentPlanoIndex = index;
        $scope.currentPlano = plano;
        atualizaEstagiosPlanos();
        // getPlanoParaDiagrama();
        return atualizaPosicaoPlanos();
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
      
      $scope.getImagemDeEstagio = function(estagioPlano) {
        var ep = _.find($scope.objeto.estagiosPlanos, {idJson: estagioPlano.idJson});
        var estagio = _.find($scope.objeto.estagios, {idJson: ep.estagio.idJson});
        var imagem = _.find($scope.objeto.imagens, {idJson: estagio.imagem.idJson});
        return imagem && $filter('imageSource')(imagem.id);
      };
      
      
      
      //
      // /**
      //  * Seleciona um grupo semafórico do anel atual atraves do índice.
      //  *
      //  * @param      {int}  index   The index
      //  */
      // $scope.selecionaGrupoSemaforico = function(gs, index) {
      //   $scope.currentGrupoSemaforicoIndex = index;
      //   $scope.currentGrupoSemaforico = gs;
      //   $scope.currentGrupoSemaforicoIdentifier = $scope.currentAnelIndex.toString() + index.toString();
      // };
      //
      //
      $scope.getEstagio = function(estagioPlano) {
        var ep = _.find($scope.objeto.estagiosPlanos, {idJson: estagioPlano.idJson});
        var estagio = _.find($scope.objeto.estagios, {idJson: ep.estagio.idJson});
        return estagio;
      };

      $scope.adicionarEstagioPlano = function(estagioPlano) {
        // posicao = posicao || $scope.currentPlano.estagiosPlanos.length + 1;
        var posicao = $scope.currentPlano.estagiosPlanos.length + 1;
        adicionaEstagioASequencia(estagioPlano.estagio.idJson, estagioPlano.plano.idJson, posicao);
        atualizaEstagiosPlanos();
        // atualizaDiagramaIntervalos();
      };
      
      $scope.adicionarEstagio = function(estagio) {
        var posicao = $scope.currentPlano.estagiosPlanos.length + 1;
        adicionaEstagioASequencia(estagio.idJson, $scope.currentPlano.idJson, posicao);
        atualizaEstagiosPlanos();
        // atualizaDiagramaIntervalos();
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
          posicao: posicao
        }
        
        $scope.objeto.estagiosPlanos.push(novoEstagioPlano);
        $scope.currentPlano.estagiosPlanos.push({idJson: novoEstagioPlano.idJson});
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
      //
      // $scope.duplicarSequencia = function(planoEstagio, index) {
      //   var estagio = _.find($scope.objeto.estagios, {idJson: planoEstagio.estagio.idJson});
      //   $scope.adicionarSequencia(estagio, index + 1);
      // };
      //
      //
      //
      // atualizaPlanos = function() {
      //   var ids = _.map($scope.currentAnel.planos, 'idJson');
      //   $scope.currentPlanos = _
      //     .chain($scope.objeto.planos)
      //     .filter(function(p) { return ids.indexOf(p.idJson) >= 0; })
      //     .orderBy(['posicao'])
      //     .each(function(p, index) { p.posicao = index + 1; })
      //     .value();
      //
      //   return $scope.currentPlanos;
      // };
      //
      //
      //
      
      $scope.submitForm = function() {
        Restangular
        .all('planos')
        .post($scope.objeto)
        .then(function(res) {
          $scope.objeto = res;

          $scope.errors = {};
          $state.go(nextStep, {id: $scope.objeto.id});
          influuntBlockui.unblock();
        })
        .catch(function(res) {
          influuntBlockui.unblock();
          if (res.status === 422) {
            // $scope.buildValidationMessages(res.data);
          } else {
            console.error(res);
          }
        });
      }
      
      atualizaPosicaoEstagiosPlanos = function(){
        $scope.currentEstagiosPlanos.forEach(function (estagioPlano, index){
          estagioPlano.posicao = index + 1;
        });
      }
      
      atualizaPosicaoPlanos = function(){
        $scope.currentPlanos.forEach(function (plano, index){
          plano.posicao = index + 1;
        });
      }
      
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

    }]);
