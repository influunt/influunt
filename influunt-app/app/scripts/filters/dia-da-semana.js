'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:diaDaSemana
 * @function
 * @description
 * # diaDaSemana
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('diaDaSemana', function () {
    return function (input) {
      if (input) {
        switch(input) {
          case 'SEGUNDA_A_SEXTA':
            return 'Segunda à Sexta';
          case 'SEGUNDA_A_SABADO':
            return 'Segunda à Sábado';
          case 'SABADO_A_DOMINGO':
            return 'Sábado e Domingo';
          case 'TODOS_OS_DIAS':
            return 'Todos os dias da semana';
          case 'SEGUNDA':
            return 'Segunda-feira';
          case 'TERCA':
            return 'Terça-feira';
          case 'QUARTA':
            return 'Quarta-feira';
          case 'QUINTA':
            return 'Quinta-feira';
          case 'SEXTA':
            return 'Sexta-feira';
          case 'SABADO':
            return 'Sábado';
          case 'DOMINGO':
            return 'Domingo';
        }
      }
    };
  });
