'use strict';

/**
 * @ngdoc service
 * @name influuntApp.imposicoesService
 * @description
 * # imposicoesService
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('imposicoesService', ['Restangular', 'influuntBlockui', '$filter', 'toast',
    function (Restangular, influuntBlockui, $filter, toast) {
      var LIMITE_MINIMO_DURACAO = 15;
      var LIMITE_MAXIMO_DURACAO = 600;

      var imposicao = function(tipo, configuracao) {
        var horarioEntrada = moment(configuracao.horarioEntradaObj.data)
          .startOf('day')
          .add(parseInt(configuracao.horarioEntradaObj.hora), 'hours')
          .add(parseInt(configuracao.horarioEntradaObj.minuto), 'minutes')
          .add(parseInt(configuracao.horarioEntradaObj.segundo), 'seconds');


        configuracao.horarioEntrada = horarioEntrada.toDate().getTime();
        // horario de entrada
        return Restangular
          .one('imposicoes', tipo)
          .post(null, configuracao)
          .catch(console.error)
          .finally(influuntBlockui.unblock);
      };

      var alertStatusTransacao = function(transacoes, aneis, tipo) {
        var tiposTransacao;
        if (tipo === 'modo_operacao') {
          tiposTransacao = ['IMPOSICAO_MODO_OPERACAO'];
        } else {
          tiposTransacao = ['IMPOSICAO_PLANO', 'IMPOSICAO_PLANO_TEMPORARIO'];
        }

        if (_.isObject(transacoes) && _.isArray(aneis)) {
          _.each(aneis, function(anel) {
            if (anel) {
              var transacao = transacoes[anel.controladorFisicoId];
              var isPacoteTabelaHoraria = transacao && tiposTransacao.indexOf(transacao.tipoTransacao) >= 0;

              if (isPacoteTabelaHoraria && transacao.status === 'DONE') {
                toast.success($filter('translate')('imporConfig.' + tipo + '.sucesso'));
              } else if (isPacoteTabelaHoraria && transacao.status === 'ABORTED') {
                // @todo: Adicionar mensagem de erro do mqtt?
                toast.error($filter('translate')('imporConfig.' + tipo + '.erro'));
              }
            }
          });
        }
      };

      return {
        LIMITE_MINIMO_DURACAO: LIMITE_MINIMO_DURACAO,
        LIMITE_MAXIMO_DURACAO: LIMITE_MAXIMO_DURACAO,
        imposicao: imposicao,
        alertStatusTransacao: alertStatusTransacao
      };
    }]);
