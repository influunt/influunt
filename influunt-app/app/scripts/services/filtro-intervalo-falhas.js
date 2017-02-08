'use strict';

/**
 * @ngdoc service
 * @name influuntApp.filtroIntervaloFalhas
 * @description
 * # filtroIntervaloFalhas
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('filtroIntervaloFalhas', ['$filter', function filtroIntervaloFalhas($filter) {

      var HOJE = 'HOJE';
      var ESTA_SEMANA = 'ESTA_SEMANA';
      var ESTE_MES = 'ESTE_MES';
      var ESTE_ANO = 'ESTE_ANO';
      var ONTEM = 'ONTEM';
      var SEMANA_PASSADA = 'SEMANA_PASSADA';
      var MES_PASSADO = 'MES_PASSADO';
      var ANO_PASSADO = 'ANO_PASSADO';

      var today = moment();
      var obj = {
        opcoes: [
          {
            chave: HOJE,
            label: $filter('translate')('main.intervaloDeFalhas.HOJE'),
            intervalo: {
              inicio: today.clone().startOf('day').toDate().getTime(),
              fim: today.clone().endOf('day').toDate().getTime()
            }
          },
          {
            chave: ESTA_SEMANA,
            label: $filter('translate')('main.intervaloDeFalhas.ESTA_SEMANA'),
            intervalo: {
              inicio: today.clone().startOf('week').toDate().getTime(),
              fim: today.clone().endOf('week').toDate().getTime()
            }
          },
          {
            chave: ESTE_MES,
            label: $filter('translate')('main.intervaloDeFalhas.ESTE_MES'),
            intervalo: {
              inicio: today.clone().startOf('month').toDate().getTime(),
              fim: today.clone().endOf('month').toDate().getTime()
            }
          },
          {
            chave: ESTE_ANO,
            label: $filter('translate')('main.intervaloDeFalhas.ESTE_ANO'),
            intervalo: {
              inicio: today.clone().startOf('year').toDate().getTime(),
              fim: today.clone().endOf('year').toDate().getTime()
            }
          },
          {
            chave: ONTEM,
            label: $filter('translate')('main.intervaloDeFalhas.ONTEM'),
            intervalo: {
              inicio: today.clone().subtract(1, 'days').startOf('day').toDate().getTime(),
              fim: today.clone().subtract(1, 'days').endOf('day').toDate().getTime()
            }
          },
          {
            chave: SEMANA_PASSADA,
            label: $filter('translate')('main.intervaloDeFalhas.SEMANA_PASSADA'),
            intervalo: {
              inicio: today.clone().subtract(1, 'weeks').startOf('week').toDate().getTime(),
              fim: today.clone().subtract(1, 'weeks').endOf('week').toDate().getTime()
            }
          },
          {
            chave: MES_PASSADO,
            label: $filter('translate')('main.intervaloDeFalhas.MES_PASSADO'),
            intervalo: {
              inicio: today.clone().subtract(1, 'months').startOf('month').toDate().getTime(),
              fim: today.clone().subtract(1, 'months').endOf('month').toDate().getTime()
            }
          },
          {
            chave: ANO_PASSADO,
            label: $filter('translate')('main.intervaloDeFalhas.ANO_PASSADO'),
            intervalo: {
              inicio: today.clone().subtract(1, 'years').startOf('year').toDate().getTime(),
              fim: today.clone().subtract(1, 'years').endOf('year').toDate().getTime()
            }
          }
        ],
        selecionado: 'ESTE_MES',
      };

      var get = function() {
        return obj;
      };

      var getIntervalo = function(chave) {
        return _.find(obj.opcoes, {chave: chave}).intervalo;
      };

      return {
        get: get,
        getIntervalo: getIntervalo
      };
  }]);
