'use strict';

/**
 * @ngdoc service
 * @name influuntApp.validacoes/validaTransicao
 * @description
 * # validacoes/validaTransicao
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('validaTransicao', ['utilEstagios',
    function validaTransicao(utilEstagios) {

      var getEstagios;

      /**
       * Cria um objeto com os dados ordenados de acordo com o formato esperado caso
       * as transições criadas sejam válidas.
       *
       * @param      {<type>}  ui        The user interface
       * @param      {<type>}  estagios  The estagios
       * @return     {<type>}  { description_of_the_return_value }
       */
      var createMockObject = function(ui, estagios, objeto) {
        estagios = getEstagios(objeto, estagios);
        var mock = _.clone(estagios);
        var indexOrigem = ui.item.sortable.index;
        var indexDestino = ui.item.sortable.dropindex;

        var estagio = mock.splice(indexOrigem, 1)[0];
        mock.splice(indexDestino, 0, estagio);

        return mock;
      };

      getEstagios = function(objeto, estagios) {
        var ids = _.map(estagios, 'estagio.idJson');
        return _.map(ids, function(id) {
          return _.find(objeto.estagios, {idJson: id});
        });
      };

      var getTransicaoProibida = function(objeto, origem, destino) {
        var query = { origem: { idJson: origem.idJson }, destino: { idJson: destino.idJson } };
        return _.find(objeto.transicoesProibidas, query);
      };

      /**
       * Verifica se após a movimentação dos estágios uma transição inválida é criada.
       * Se for o caso, a movimentação deverá ser anulada e a mensagem de validação apresentada.
       *
       * @param      {<type>}  ui        The user interface
       * @param      {<type>}  estagios  The estagios
       */
      var valida = function(ui, estagios, objeto) {
        var mock = createMockObject(ui, estagios, objeto);
        var msg = null;

        // Deve iterar atraves dos estágios até encontrar uma transicao inválida. Caso encontre, deverá cancelar
        // a ordenação.
        mock.some(function(estagio, index) {
          var proximoEstagio = utilEstagios.getProximoEstagio(mock, index);
          var estagioAnterior = utilEstagios.getEstagioAnterior(mock, index);

          // Verifica se o item corrente é destino de uma transicao proibida anterior.
          var transicaoAnterior = getTransicaoProibida(objeto, estagioAnterior, estagio);
          var transicaoPosterior = getTransicaoProibida(objeto, estagio, proximoEstagio);
          var transicaoInvalida = transicaoAnterior || transicaoPosterior;


          if (!!transicaoInvalida) {
            var origem = _.find(objeto.estagios, transicaoInvalida.origem);
            var destino = _.find(objeto.estagios, transicaoInvalida.destino);

            msg = 'transição proibida de E' + origem.posicao + ' para E' + destino.posicao;
            return true;
          }
        });

        return msg;
      };

      return {
        valida: valida
      };
    }]);
