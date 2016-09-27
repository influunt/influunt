'use strict';

/**
 * @ngdoc service
 * @name influuntApp.horarioService
 * @description
 * # horarioService
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('HorariosService', ['$filter', function ($filter) {

    var getTimes = function(quantidade){
      return new Array(quantidade);
    };

    var getDias = function() {
      return [
        {
          label: $filter('diaDaSemana')('TODOS_OS_DIAS'),
          value: 'TODOS_OS_DIAS',
          dias: ['dom','seg','ter','qua','qui','sex','sab'],
          prioridade: 11
        },
        {
          label: $filter('diaDaSemana')('DOMINGO'),
          value: 'DOMINGO',
          dias: ['dom'],
          prioridade: 7
        },
        {
          label: $filter('diaDaSemana')('SEGUNDA'),
          value: 'SEGUNDA',
          dias: ['seg'] ,
          prioridade: 6,
        },
        {
          label: $filter('diaDaSemana')('TERCA'),
          value: 'TERCA',
          dias: ['ter'],
          prioridade: 5
        },
        {
          label: $filter('diaDaSemana')('QUARTA'),
          value: 'QUARTA',
          dias: ['qua'],
          prioridade: 4,
        },
        {
          label: $filter('diaDaSemana')('QUINTA'),
          value: 'QUINTA',
          dias: ['qui'],
          prioridade: 3
        },
        {
          label: $filter('diaDaSemana')('SEXTA'),
          value: 'SEXTA',
          dias: ['sex'],
          prioridade: 2
        },
        {
          label: $filter('diaDaSemana')('SABADO'),
          value: 'SABADO',
          dias: ['sab'],
          prioridade: 1
        },
        {
          label: $filter('diaDaSemana')('SABADO_A_DOMINGO'),
          value: 'SABADO_A_DOMINGO',
          dias: ['dom','sab'],
          prioridade: 8
        },

        {
          label: $filter('diaDaSemana')('SEGUNDA_A_SEXTA'),
          value: 'SEGUNDA_A_SEXTA',
          dias: ['seg','ter','qua','qui','sex'],
          prioridade: 9
        },
        {
          label: $filter('diaDaSemana')('SEGUNDA_A_SABADO'),
          value: 'SEGUNDA_A_SABADO',
          dias: ['seg','ter','qua','qui','sex','sab'],
          prioridade: 10
        }
      ];
    };

    var getHoras = function() {
      return getTimes(24);
    };

    var getMinutos = function() {
      return getTimes(60);
    };

    var getSegundos = function() {
      return getTimes(60);
    };

    var getPlanos = function(limitePlanos) {
      if (typeof limitePlanos !== 'number') {
        limitePlanos = 16;
      }
      return getTimes(parseInt(limitePlanos));
    };


    return {
      getDias: getDias,
      getHoras: getHoras,
      getMinutos: getMinutos,
      getSegundos: getSegundos,
      getPlanos: getPlanos
    };
  }]);
