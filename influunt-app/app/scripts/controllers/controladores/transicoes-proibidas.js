'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresTransicoesProibidasCtrl
 * @description
 * # ControladoresTransicoesProibidasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresTransicoesProibidasCtrl', ['$scope', '$state', '$controller',
    function ($scope, $state, $controller) {

      // $controller('ControladoresCtrl', {$scope: $scope});
      // @todo       Esta linha deverá ser substituida pela linha acima assim que o retorno dos verdes conflitantes
      //             em grupos semaforicos for corrigido.
      $controller('ControladoresVerdesConflitantesCtrl', {$scope: $scope});

      // funcoes privadas.
      var desativarTransicaoProibida, ativarTransicaoProibida;

      /**
       * Garante que o controlador tem as condições mínimas para acessar a tela de transicoes proibidas.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertTransicoesProibidas = function() {
        var condition = $scope.objeto.aneis && $scope.objeto.aneis.length;
        condition = condition && _.chain($scope.objeto.aneis).map('estagios').flatten().compact().value().length > 0;
        if (!condition) {
          $state.go('app.wizard_controladores.verdes_conflitantes', {id: $scope.objeto.id});
          return false;
        }

        return true;
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
                anel.estagios.forEach(function(estagio, index) {
                  estagio.posicao = index + 1;
                });

                anel.estagios.forEach(function(estagio) {
                  return estagio.origemDeTransicoesProibidas &&
                    estagio.origemDeTransicoesProibidas.forEach(function(transicao) {
                      var origem = _.find(anel.estagios, transicao.origem);
                      var destino = _.find(anel.estagios, transicao.destino);
                      var t = 'E' + origem.posicao + '-E' + destino.posicao;

                      // Campo de posicao utilizado na apresentação das validações da interface.
                      transicao.origem.posicao = origem.posicao;
                      transicao.destino.posicao = destino.posicao;
                      anel.transicoesProibidas[t] = _.cloneDeep(transicao);
                    });
                });
              });

              $scope.selecionaAnel(0);
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
        if ($scope.currentAnel.transicoesProibidas.hasOwnProperty(transicao)) {
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
        var alternativoAnterior = null;
        var estagioOrigem = _.find($scope.currentAnel.estagios, {id: transicao.origem.id});
        estagioOrigem.origemDeTransicoesProibidas = estagioOrigem.origemDeTransicoesProibidas || [];
        var transicaoOrigem = _.findIndex(estagioOrigem.origemDeTransicoesProibidas, {destino: {id: transicao.destino.id}});
        if (transicaoOrigem >= 0) {
          alternativoAnterior = estagioOrigem.origemDeTransicoesProibidas[transicaoOrigem].alternativo &&
                                    estagioOrigem.origemDeTransicoesProibidas[transicaoOrigem].alternativo.id;
          estagioOrigem.origemDeTransicoesProibidas.splice(transicaoOrigem, 1);
        }
        estagioOrigem.origemDeTransicoesProibidas.push(transicao);


        var estagioDestino = _.find($scope.currentAnel.estagios, {id: transicao.destino.id});
        estagioDestino.destinoDeTransicoesProibidas = estagioDestino.destinoDeTransicoesProibidas || [];
        var transicaoDestino = _.findIndex(estagioDestino.destinoDeTransicoesProibidas, {origem: {id: transicao.origem.id}});
        if (transicaoDestino >= 0) {
          estagioDestino.destinoDeTransicoesProibidas.splice(transicaoDestino, 1);
        }
        estagioDestino.destinoDeTransicoesProibidas.push(transicao);


        if (transicao.alternativo && transicao.alternativo.id) {
          var estagioAlternativo = _.find($scope.currentAnel.estagios, {id: transicao.alternativo.id});
          estagioAlternativo.alternativaDeTransicoesProibidas = estagioAlternativo.alternativaDeTransicoesProibidas || [];
          estagioAlternativo.alternativaDeTransicoesProibidas.push(transicao);
        }

        var alternativoId = transicao.alternativo && transicao.alternativo.id;
        if (alternativoAnterior && alternativoAnterior !== alternativoId) {
          alternativoAnterior = _.find($scope.currentAnel.estagios, {id: alternativoAnterior});
          var transicaoAnterior = _.findIndex(estagioDestino.destinoDeTransicoesProibidas, {origem: {id: transicao.origem.id}});
          if (transicaoAnterior >= 0) {
            alternativoAnterior.alternativaDeTransicoesProibidas.splice(transicaoAnterior, 1);
          }
        }
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
          return item.id !== origem.id && item.id !== destino.id;
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
        if (!($scope.errors && $scope.errors.aneis)) {
          return false;
        }

        var indexOrigem = _.findIndex($scope.currentAnel.estagios, {id: origem.id});
        var estagioOrigem = $scope.currentAnel.estagios[indexOrigem];
        var indexDestino = _.findIndex(estagioOrigem.origemDeTransicoesProibidas, {destino: {id: destino.id}});

        return $scope.errors.aneis[$scope.currentAnel.posicao - 1].estagios[indexOrigem] &&
          $scope.errors.aneis[$scope.currentAnel.posicao - 1]
          .estagios[indexOrigem]
          .origemDeTransicoesProibidas[indexDestino];
      };

      /**
       * Ativa as transições proibidas
       *
       * @param      {<type>}  estagio1  The estagio 1
       * @param      {<type>}  estagio2  The estagio 2
       */
      ativarTransicaoProibida = function(estagio1, estagio2) {
        var transicaoProibida = {
          origem: {id: estagio1.id},
          destino: {id: estagio2.id}
        };

        estagio1.origemDeTransicoesProibidas = estagio1.origemDeTransicoesProibidas || [];
        estagio1.origemDeTransicoesProibidas.push(transicaoProibida);

        estagio2.destinoDeTransicoesProibidas = estagio2.destinoDeTransicoesProibidas || [];
        estagio2.destinoDeTransicoesProibidas.push(transicaoProibida);

        var transicao = 'E' + estagio1.posicao + '-E' + estagio2.posicao;
        $scope.currentAnel.transicoesProibidas[transicao] = {
          origem: {
            id: estagio1.id,
            posicao: estagio1.posicao
          },
          destino: {
            id: estagio2.id,
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
      desativarTransicaoProibida = function(estagio1, estagio2) {
        var transicao = 'E' + estagio1.posicao + '-E' + estagio2.posicao;
        delete $scope.currentAnel.transicoesProibidas[transicao];
        var idx1 = _.findIndex(estagio1.origemDeTransicoesProibidas, {destino: {id: estagio2.id}});
        var idx2 = _.findIndex(estagio2.destinoDeTransicoesProibidas, {origem: {id: estagio1.id}});
        estagio1.origemDeTransicoesProibidas.splice(idx1, 1);
        estagio2.destinoDeTransicoesProibidas.splice(idx2, 1);
      };

    }]);
