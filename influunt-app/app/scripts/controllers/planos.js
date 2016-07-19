'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:PlanosCtrl
 * @description
 * # PlanosCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('PlanosCtrl', ['$scope', '$state', '$timeout', 'Restangular',
                             'validaTransicao', 'utilEstagios', 'toast', 'modoOperacaoService',
    function ($scope, $state, $timeout, Restangular,
              validaTransicao, utilEstagios, toast, modoOperacaoService) {

      var getPlanoParaDiagrama;

      var diagramaDebouncer = null;
      $scope.min = 0;
      $scope.max = 100;

      /**
       * Inicializa a tela de planos. Carrega os dados básicos da tela.
       */
      $scope.init = function() {
        var id = $state.params.id;
        Restangular.one('controladores', id).get().then(function(res) {
          $scope.objeto = res;
          $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
          $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});

          $scope.aneis.forEach(function(anel) {
            anel.estagios = _.orderBy(anel.estagios, ['descricao']);

            anel.planos = _.orderBy(anel.planos, ['posicao']);
            if (!(_.isArray(anel.planos) && anel.planos.length > 0)) {
              anel.planos =  [{ posicao: 1, modoOperacao: 'TEMPO_FIXO_ISOLADO' }];
            }

            // @todo temporario. A posicao do estagio deverá vir da API.
            anel.estagios.forEach(function(estagio, index) {
              estagio.posicao = index + 1;
            });
          });

          $scope.selecionaAnel(0);
          $scope.getTabelasEntreVerdes();
          return getPlanoParaDiagrama();
        });
      };

      /**
       * Adiciona um novo plano à lista de planos dos aneis. Após a criação do novo
       * plano, este deverá ser colocado em edição imediatamente.
       */
      $scope.adicionaPlano = function() {
        $scope.objeto.aneis.forEach(function(anel) {
          var posicao = anel.planos.length + 1;
          var plano = {
            posicao: posicao,
            modoOperacao: 'ISOLADO'
          };
          anel.planos.push(plano);
        });

        // Depois de criar um novo conjunto de planos, deve coloca-lo em edição
        // imediatamente.
        $scope.selecionaPlano($scope.currentAnel.planos.length - 1);
      };

      $scope.selecionaAnel = function(index) {
        $scope.currentAnelIndex = index;
        $scope.currentAnel = $scope.objeto.aneis[index];
        $scope.selecionaPlano(0);
        $scope.selecionaEstagio(0);
      };

      $scope.selecionaPlano = function(index) {
        $scope.currentPlanoIndex = index;
        $scope.currentPlano = $scope.currentAnel.planos[index];
        $scope.currentPlano.sequenciaEstagios = $scope.currentPlano.sequenciaEstagios || $scope.currentAnel.estagios;
      };

      $scope.selecionaEstagio = function(index) {
        $scope.currentEstagioIndex = index;
        $scope.currentEstagio = $scope.currentAnel.estagios[index];
        $scope.getOpcoesEstagiosDisponiveis();
      };

      $scope.getOpcoesEstagiosDisponiveis = function() {
        $scope.opcoesEstagiosDisponiveis = [
          utilEstagios.getEstagioAnterior($scope.currentPlano.sequenciaEstagios, $scope.currentEstagioIndex),
          utilEstagios.getProximoEstagio($scope.currentPlano.sequenciaEstagios, $scope.currentEstagioIndex)
        ];

        return $scope.opcoesEstagiosDisponiveis;
      };

      $scope.getTabelasEntreVerdes = function() {
        $scope.tabelasEntreVerdes = $scope.currentAnel.gruposSemaforicos[0].tabelasEntreVerdes;
        $scope.currentTabelaEntreVerdes = $scope.tabelasEntreVerdes[0];
      };

      /**
       * Atualiza o diagrama de intervalos para os casos de modo de operação intermitente e desligado, onde
       * todos os grupos deverão assumir o mesmo estágio (entre amarelo-intermitente e desligado). Se não assumir
       * nenhum destes, deverá utilizar o diagrama produzido a partir do plugin de diagrama.
       */
      var setDiagramaEstatico = function() {
        var modo = modoOperacaoService.getModoIdByName($scope.currentPlano.modoOperacao);
        var grupos = _.times($scope.currentAnel.gruposSemaforicos.length)
          .map(function(i) {
            return {
              posicao: (i + 1),
              intervalos: [{
                status: modo,
                duracao: 255
              }]
            };
          });

        $scope.dadosDiagrama = {
          estagios: [{posicao: 1, duracao: 255}],
          gruposSemaforicos: grupos
        };
      };

      /**
       * Caso o modo de operação seja intermitente ou apagado, ele deverá renderizar um diagrama estágio, contendo
       * somente estes modos. Caso contrário, deverá executar o metodo de geração do diagrama a partir do plugin.
       */
      var atualizaDiagramaIntervalos = function() {
        if (['INTERMITENTE', 'APAGADO'].indexOf($scope.currentPlano.modoOperacao) < 0) {
          getPlanoParaDiagrama();
          var diagramaBuilder = new influunt.components.DiagramaIntervalos($scope.plano);
          var result = diagramaBuilder.calcula();
          $scope.dadosDiagrama = result;
        } else {
          setDiagramaEstatico();
        }

        $scope.currentPlano.tempoCiclo = $scope.dadosDiagrama.tempoCiclo;
      };

      /**
       * Evita que dados informados para um plano em determinado modo de operação vaze
       * para o diagrama criado.
       *
       * @param      {<type>}  plano   The plano
       */
      var limpaDadosPlano = function(plano) {
        if (plano.modoOperacao === 'ATUADO') {
          plano.tempoCiclo = null;
          plano.sequenciaEstagios.forEach(function(estagio) {
            estagio.tempoVerde = null;
          });
        } else {
          plano.tempoVerdeMinimo = null;
          plano.tempoVerdeMaximo = null;
          plano.tempoVerdeIntermediario = null;
          plano.tempoExtensaoVerde = null;
        }

        if (plano.modoOperacao !== 'COORDENADO') {
          plano.defasagem = null;
        }
      };

      /**
       * Retorna uma copia do currentPlano com algumas modificações.
       * Deverá ser utilizado para produzir o diagrama de intervalos.
       *
       * @return     {<type>}  The plano para diagrama.
       */
      getPlanoParaDiagrama = function() {
        $scope.plano = _.cloneDeep($scope.currentPlano);
        $scope.plano.quantidadeGruposSemaforicos = $scope.currentAnel.gruposSemaforicos.length;
        $scope.plano.posicaoTabelaEntreVerdes = $scope.currentTabelaEntreVerdes.posicao;

        $scope.plano.sequenciaEstagios.forEach(function(estagio) {
          estagio.estagiosGruposSemaforicos.forEach(function(egs) {
            var id = egs.grupoSemaforico && egs.grupoSemaforico.id;
            if (id) {
              estagio.gruposSemaforicos = estagio.gruposSemaforicos || [];
              estagio.gruposSemaforicos.push(_.find($scope.currentAnel.gruposSemaforicos, {id: id}));
            }
          });
        });


        limpaDadosPlano($scope.plano);
        return $scope.plano;
      };

      // variavel de controle, utilizada para verificar se o currentAnelId deve ou não ser atualizado.
      // Isto deve ser feito somente se a transição existir (ex.: E1, na posicao 1, caso seja arrastado para a
      // posicao 2, o currentEstagioId deve ser 2; caso contrário, permanece em 1).
      var transicaoAprovada = false;
      $scope.sortableOptions = {
        update: function(event, ui) {
          var msg = validaTransicao.valida(ui, $scope.currentPlano.sequenciaEstagios);
          if (msg) {
            toast.warn(msg);
            ui.item.sortable.cancel();
            return false;
          }

          transicaoAprovada = true;
        },
        stop: function(event, ui) {
          if (transicaoAprovada) {
            transicaoAprovada = false;
            $scope.currentEstagioIndex = ui.item.sortable.dropindex;
            return $scope.getOpcoesEstagiosDisponiveis();
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
      $scope.$watch('currentPlano', function(value) {
        if (value) {
          $timeout.cancel(diagramaDebouncer);
          diagramaDebouncer = $timeout(atualizaDiagramaIntervalos, 500);
        }
      }, true);

    }]);
