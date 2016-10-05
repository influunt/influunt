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
      var labels = {
        SEGUNDA_A_SEXTA: 'Segunda à Sexta',
        SEGUNDA_A_SABADO: 'Segunda à Sábado',
        SABADO_A_DOMINGO: 'Sábado e Domingo',
        TODOS_OS_DIAS: 'Todos os dias da semana',
        SEGUNDA: 'Segunda-feira',
        TERCA: 'Terça-feira',
        QUARTA: 'Quarta-feira',
        QUINTA: 'Quinta-feira',
        SEXTA: 'Sexta-feira',
        SABADO: 'Sábado',
        DOMINGO: 'Domingo'
      };

      return labels[input];
    };
  });
