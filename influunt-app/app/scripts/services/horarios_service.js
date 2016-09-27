'use strict';

/**
 * @ngdoc service
 * @name influuntApp.horarioService
 * @description
 * # horarioService
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('HorariosService', function () {

    var getTimes = function(quantidade){
      return new Array(quantidade);
    };

    var getDias = function() {
      return [
        {
          label: 'Todos os dias da semana',
          value: 'TODOS_OS_DIAS',
          dias: ['dom','seg','ter','qua','qui','sex','sab'],
          prioridade: 11
        },
        {
          label: 'Domingo',
          value: 'DOMINGO',
          dias: ['dom'],
          prioridade: 7
        },
        {
          label: 'Segunda-feira',
          value: 'SEGUNDA',
          dias: ['seg'] ,
          prioridade: 6,
        },
        {
          label: 'Terça-feira',
          value: 'TERCA',
          dias: ['ter'],
          prioridade: 5
        },
        {
          label: 'Quarta-feira',
          value: 'QUARTA',
          dias: ['qua'],
          prioridade: 4,
        },
        {
          label: 'Quinta-feira',
          value: 'QUINTA',
          dias: ['qui'],
          prioridade: 3
        },
        {
          label: 'Sexta-feira',
          value: 'SEXTA',
          dias: ['sex'],
          prioridade: 2
        },
        {
          label: 'Sábado',
          value: 'SABADO',
          dias: ['sab'],
          prioridade: 1
        },
        {
          label: 'Sábado e Domingo',
          value: 'SABADO_A_DOMINGO',
          dias: ['dom','sab'],
          prioridade: 8
        },

        {
          label: 'Segunda à Sexta',
          value: 'SEGUNDA_A_SEXTA',
          dias: ['seg','ter','qua','qui','sex'],
          prioridade: 9
        },
        {
          label: 'Segunda à Sábado',
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
  });
