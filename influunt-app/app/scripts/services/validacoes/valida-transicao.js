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

      var getTransicaoProibida = function(objeto, origem, destino) {
        var query = { origem: { idJson: origem.idJson }, destino: { idJson: destino.idJson } };
        return _.find(objeto.transicoesProibidas, query);
      };

      /**
       * Verifica se após a movimentação dos estágios uma transição inválida é criada.
       * Se for o caso, a movimentação deverá ser anulada e a mensagem de validação apresentada.
       *
       * @param      {<type>}  ui        The user interface
       * @param      {<type>}  estagiosPlanos  The estagiosPlanos
       */
      var valida = function(estagiosPlanos, objeto) {
        var errors = [];

        // Deve iterar por todos os estagios coletando as transicoes proibidas.
        if (estagiosPlanos) {
          estagiosPlanos.forEach(function(estagioPlano, index) {
            var proximoEstagio = utilEstagios.getProximoEstagio(estagiosPlanos, index);
            var estagioAnterior = utilEstagios.getEstagioAnterior(estagiosPlanos, index);

            var estagio = _.find(objeto.estagios, estagioPlano.estagio);
            proximoEstagio = _.find(objeto.estagios, proximoEstagio.estagio);
            estagioAnterior = _.find(objeto.estagios, estagioAnterior.estagio);

            var transicaoAnterior = getTransicaoProibida(objeto, estagioAnterior, estagio);
            var transicaoPosterior = getTransicaoProibida(objeto, estagio, proximoEstagio);

            if (transicaoAnterior) {
              errors.push({
                mensagem: 'transição proibida de E' + estagioAnterior.posicao + ' para E' + estagio.posicao,
                origem: ((index -1) + estagiosPlanos.length) % estagiosPlanos.length,
                destino: index
              });
            }

            if (transicaoPosterior) {
              errors.push({
                mensagem: 'transição proibida de E' + estagio.posicao + ' para E' + proximoEstagio.posicao,
                origem: index,
                destino: (index + 1) % estagiosPlanos.length
              });
            }
          });
        }

        return errors;
      };

      return {
        valida: valida
      };
    }]);
