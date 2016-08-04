'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresTransicoesProibidasCtrl
 * @description
 * # ControladoresTransicoesProibidasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresTransicoesProibidasCtrl', ['$scope', '$state', '$controller', 'assertControlador',
    function ($scope, $state, $controller, assertControlador) {

      // $controller('ControladoresCtrl', {$scope: $scope});
      // @todo       Esta linha deverá ser substituida pela linha acima assim que o retorno dos verdes conflitantes
      //             em grupos semaforicos for corrigido.
      $controller('ControladoresVerdesConflitantesCtrl', {$scope: $scope});

      // funcoes privadas.
      var desativarTransicaoProibida, ativarTransicaoProibida, removeAlternativoAnterior, setEstagioAlternativoEmAlternativo,
          setEstagioAlternativoEmDestino, setEstagioAlternativoEmOrigem, getEstagioAnterior;

      /**
       * Garante que o controlador tem as condições mínimas para acessar a tela de transicoes proibidas.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertTransicoesProibidas = function() {
        var valid = assertControlador.hasAneis($scope.objeto) && assertControlador.hasEstagios($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.verdes_conflitantes', {id: $scope.objeto.id});
        }

        return valid;
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
      $scope.inicializaTransicoesProibidas = function() {
        return $scope.inicializaWizard().then(function() {
          $scope.inicializaVerdesConflitantes().then(function() {

            if ($scope.assertTransicoesProibidas()) {
              $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);
              $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});

              $scope.aneis.forEach(function(anel) {
                anel.transicoesProibidas =  {};

                anel.estagios.forEach(function(e) {
                  var estagio = _.find($scope.objeto.estagios, {idJson: e.idJson});
                  return estagio.origemDeTransicoesProibidas &&
                    estagio.origemDeTransicoesProibidas.forEach(function(res) {
                      var transicao = _.find($scope.objeto.transicoesProibidas, {idJson: res.idJson});
                      var origem = _.find($scope.objeto.estagios, transicao.origem);
                      var destino = _.find($scope.objeto.estagios, transicao.destino);
                      var t = 'E' + origem.posicao + '-E' + destino.posicao;
                      anel.transicoesProibidas[t] = _.cloneDeep(transicao);
                    });
                });
              });

              $scope.selecionaAnelTransicoesProibidas(0);
              $scope.selecionaEstagio(0);
            }

          });
        });
      };

      /**
       * Ativa/desativa uma transição proibida da tabela de transições proibidas.
       *
       * @param      {<type>}   estagio1  The estagio 1
       * @param      {<type>}   estagio2  The estagio 2
       * @param      {<type>}   disabled  The disabled
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.toggleTransicaoProibida = function(estagio1, estagio2, disabled) {
        if (disabled) {
          return false;
        }

        var transicao = 'E' + estagio1.posicao + '-E' + estagio2.posicao;
        if ($scope.currentTransicoesProibidas.hasOwnProperty(transicao)) {
          desativarTransicaoProibida(estagio1, estagio2);
        } else {
          ativarTransicaoProibida(estagio1, estagio2);
        }
      };

      /**
       * Executado na seleção de um estágio alternativo para determinada transição.
       *
       * Este método deve atualizar as listas de alternativaDeTransicoesProibidas, adicionando a transição ao estágio
       * alternativo settado e removendo do anterior.
       *
       * @param      {<type>}  transicao  The transicao
       */
      $scope.marcarTransicaoAlternativa = function(transicao) {
        var estagioOrigem       = _.find($scope.objeto.estagios, {idJson: transicao.origem.idJson});
        var estagioAlternativo  = _.find($scope.objeto.estagios, {idJson: transicao.alternativo.idJson});
        var alternativoAnterior = getEstagioAnterior(estagioOrigem, transicao);

        var query = {
          origem: { idJson: transicao.origem.idJson },
          destino: { idJson: transicao.destino.idJson }
        };
        var transicaoProibida   = _.find($scope.objeto.transicoesProibidas, query);

        if (angular.isDefined(alternativoAnterior)) {
          var index = _.findIndex(alternativoAnterior.alternativaDeTransicoesProibidas, {idJson: transicaoProibida.idJson});
          if (index >= 0) {
            alternativoAnterior.alternativaDeTransicoesProibidas.splice(index, 1);
          }
        }

        transicaoProibida.alternativo = {idJson: transicao.alternativo.idJson};
        estagioAlternativo.alternativaDeTransicoesProibidas = estagioAlternativo.alternativaDeTransicoesProibidas || [];
        estagioAlternativo.alternativaDeTransicoesProibidas.push({idJson: transicaoProibida.idJson});
      };

      /**
       * Filtra os estágios que podem ser apresentados como alternativos para determinada transicao.
       *
       * Dados os estágios E1, E2 e E3; A transição E1-E2 deve somente ter o estágio E3 como opção
       * para estágio alternativo.
       *
       * @param      {<type>}  origem   The origem
       * @param      {<type>}  destino  The destino
       * @return     {<type>}  { description_of_the_return_value }
       */
      $scope.filterEstagiosAlternativos = function(origem, destino) {
        return function(item) {
          return item.idJson !== destino.idJson;
          // return item.id !== origem.id && item.id !== destino.id;
        };
      };

      /**
       * Retorna a lista de erros de cada um dos campos de alternativas de estagios proibidos.
       *
       * @param      {<type>}   origem   The origem
       * @param      {<type>}   destino  The destino
       * @return     {boolean}  The erros estagios alternativos.
       */
      $scope.getErrosEstagiosAlternativos = function(origem, destino) {
        var indexOrigem = _.findIndex($scope.objeto.estagios, {idJson: origem.idJson});
        var estagioOrigem = $scope.objeto.estagios[indexOrigem];
        var indexAnel = _.findIndex($scope.objeto.aneis, {idJson: estagioOrigem.anel.idJson});
        var anel = $scope.objeto.aneis[indexAnel];

        if (!($scope.errors && $scope.errors.aneis[indexAnel])) {
          return false;
        }

        var query = {
          origem: { idJson: origem.idJson },
          destino: { idJson: destino.idJson }
        };

        var transicao = _.find($scope.objeto.transicoesProibidas, query);
        var indexDestino = _.findIndex(estagioOrigem.origemDeTransicoesProibidas, {idJson: transicao.idJson});

        var indexEstagioAnel = _.findIndex(anel.estagios, {idJson: origem.idJson});

        return $scope.errors.aneis[indexAnel].estagios[indexEstagioAnel] &&
          $scope.errors.aneis[indexAnel]
          .estagios[indexEstagioAnel]
          .origemDeTransicoesProibidas[indexDestino];
      };

      $scope.selecionaAnelTransicoesProibidas = function(index) {
        $scope.selecionaAnel(index);
        $scope.atualizaEstagios();
        $scope.atualizaTransicoesProibidas();
      };

      $scope.atualizaTransicoesProibidas = function() {
        $scope.currentAnel.transicoesProibidas = $scope.currentAnel.transicoesProibidas || {};
        $scope.currentTransicoesProibidas = $scope.currentAnel.transicoesProibidas;
        return $scope.currentTransicoesProibidas;
      };

      /**
       * Ativa as transições proibidas
       *
       * @param      {<type>}  estagio1  The estagio 1
       * @param      {<type>}  estagio2  The estagio 2
       */
      ativarTransicaoProibida = function(estagio1, estagio2) {
        var transicaoProibida = {
          idJson: UUID.generate(),
          origem: {idJson: estagio1.idJson},
          destino: {idJson: estagio2.idJson}
        };

        $scope.objeto.transicoesProibidas = $scope.objeto.transicoesProibidas || [];
        estagio1.origemDeTransicoesProibidas = estagio1.origemDeTransicoesProibidas || [];
        estagio2.destinoDeTransicoesProibidas = estagio2.destinoDeTransicoesProibidas || [];

        $scope.objeto.transicoesProibidas.push(transicaoProibida);
        estagio1.origemDeTransicoesProibidas.push({idJson: transicaoProibida.idJson});
        estagio2.destinoDeTransicoesProibidas.push({idJson: transicaoProibida.idJson});

        var transicao = 'E' + estagio1.posicao + '-E' + estagio2.posicao;
        $scope.currentTransicoesProibidas[transicao] = {
          origem: {
            idJson: estagio1.idJson,
            posicao: estagio1.posicao
          },
          destino: {
            idJson: estagio2.idJson,
            posicao: estagio1.posicao
          }
        };
      };

      /**
       * desativa as transições proibidas
       *
       * @param      {<type>}  estagio1  The estagio 1
       * @param      {<type>}  estagio2  The estagio 2
       */
      desativarTransicaoProibida = function(estagioOrigem, estagioDestino) {
        var t = 'E' + estagioOrigem.posicao + '-E' + estagioDestino.posicao;
        delete $scope.currentTransicoesProibidas[t];

        var query = {
          origem: { idJson: estagioOrigem.idJson },
          destino: { idJson: estagioDestino.idJson }
        };

        var transicao = _.find($scope.objeto.transicoesProibidas, query);

        var idxTransicao = _.findIndex($scope.objeto.transicoesProibidas, query);
        var idxOrigem = _.findIndex(estagioOrigem.origemDeTransicoesProibidas, {idJson: transicao.idJson});
        var idxDestino = _.findIndex(estagioDestino.destinoDeTransicoesProibidas, {idJson: transicao.idJson});


        $scope.objeto.transicoesProibidas.splice(idxTransicao, 1);
        estagioOrigem.origemDeTransicoesProibidas.splice(idxOrigem, 1);
        estagioDestino.destinoDeTransicoesProibidas.splice(idxDestino, 1);

        var estagioAlternativo = null;
        if (transicao.alternativo) {
          estagioAlternativo = _.find($scope.objeto.estagios, {idJson: transicao.alternativo.idJson});
          var idxAlternativo = _.findIndex(estagioAlternativo.alternativaDeTransicoesProibidas, {idJson: transicao.idJson});
          if (estagioAlternativo && estagioAlternativo.alternativaDeTransicoesProibidas) {
            estagioAlternativo.alternativaDeTransicoesProibidas.splice(idxAlternativo, 1);
          }
        }
      };

      /**
       * Retorna o estagio anterior à alteração de transição alternativa.
       *
       * @param      {<type>}   estagioOrigem  The estagio origem
       * @param      {<type>}   transicao      The transicao
       * @return     {boolean}  The estagio anterior.
       */
      getEstagioAnterior = function(estagioOrigem, transicao) {
        var query = {
          origem: { idJson: transicao.origem.idJson },
          destino: { idJson: transicao.destino.idJson }
        };

        var t = _.find($scope.objeto.transicoesProibidas, query);
        return t && t.alternativo && _.find($scope.objeto.estagios, {idJson: t.alternativo.idJson});
      };

      /**
       * Atualiza o objeto de transição em origemDeTransicoesProibidas quando o estágio alternativo é atualizado.
       *
       * @param      {<type>}  transicao      The transicao
       * @param      {<type>}  estagioOrigem  The estagio origem
       */
      setEstagioAlternativoEmOrigem = function(transicao, estagioOrigem) {
        var query = {
          origem: { idJson: transicao.origem.idJson },
          destino: { idJson: transicao.destino.idJson }
        };

        var t = _.find($scope.objeto.transicoesProibidas, query);
        var index = _.findIndex(estagioOrigem.origemDeTransicoesProibidas, {idJson: t.idJson});
        if (index >= 0) {
          estagioOrigem.origemDeTransicoesProibidas.splice(index, 1);
        } else {
          estagioOrigem.origemDeTransicoesProibidas.push({idJson: t.idJson});
        }

      };

      /**
       * Atualiza o objeto de transição em destinoDeTransicoesProibidas quando o estágio alternativo é atualizado.
       *
       * @param      {<type>}  transicao       The transicao
       * @param      {<type>}  estagioDestino  The estagio destino
       */
      setEstagioAlternativoEmDestino = function(transicao, estagioDestino) {
        var query = {
          origem: { idJson: transicao.origem.idJson },
          destino: { idJson: transicao.destino.idJson }
        };

        var t = _.find($scope.objeto.transicoesProibidas, query);
        var index = _.findIndex(estagioDestino.destinoDeTransicoesProibidas, {idJson: t.idJson});
        if (index >= 0) {
          estagioDestino.destinoDeTransicoesProibidas.splice(index, 1);
        } else {
          estagioDestino.destinoDeTransicoesProibidas.push({idJson: t.idJson});
        }

      };

      /**
       * Adiciona o objeto de transição à lista de alternativaDeTransicoesProibidas do estágio alternativo.
       *
       * @param      {<type>}  transicao  The transicao
       */
      setEstagioAlternativoEmAlternativo = function(transicao, estagioAlternativo) {
        var query = {
          origem: { idJson: transicao.origem.idJson },
          destino: { idJson: transicao.destino.idJson }
        };

        var t = _.find($scope.objeto.transicoesProibidas, query);
        var index = _.findIndex(estagioAlternativo.alternativaDeTransicoesProibidas, {idJson: t.idJson});
        if (index >= 0) {
          estagioAlternativo.alternativaDeTransicoesProibidas.splice(index, 1);
        } else {
          estagioAlternativo.alternativaDeTransicoesProibidas.push({idJson: t.idJson});
        }

      };

      /**
       * Este método remove o objeto de alternativaDeTransicoesProibidas do estágio
       * alternativo, quando este é substituido por outro em alguma transição.
       *
       * @param      {<type>}   transicao            The transicao
       * @param      {<type>}   alternativoAnterior  The alternativo anterior
       * @param      {<type>}   estagioDestino       The estagio destino
       * @return     {boolean}  { description_of_the_return_value }
       */
      removeAlternativoAnterior = function(transicao, alternativoAnterior, estagioDestino) {
        var alternativoId = transicao.alternativo && transicao.alternativo.idJson;
        if (alternativoAnterior && alternativoAnterior !== alternativoId) {
          alternativoAnterior = _.find($scope.currentAnel.estagios, {id: alternativoAnterior});
          var transicaoAnterior = _.findIndex(
            estagioDestino.destinoDeTransicoesProibidas, {origem: {id: transicao.origem.idJson}}
          );

          return transicaoAnterior >= 0 &&
            alternativoAnterior.alternativaDeTransicoesProibidas.splice(transicaoAnterior, 1);
        }
      };
    }]);
