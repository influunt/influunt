'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:nomeDetector
 * @function
 * @description
 * # nomeDetector
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('nomeDetector', function nomeDetector() {
    return function (input) {
      return input && (input.tipo === 'PEDESTRE' ? 'DP' : 'DV') + input.posicao;
    };
  });
