'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:nomeCidade
 * @function
 * @description
 * # nomeCidade
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('nomeCidade', function () {
    return function (input) {
      if (input) {
        return input.nome;
      }
    };
  });
