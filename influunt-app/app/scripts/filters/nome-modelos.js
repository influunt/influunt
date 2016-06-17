'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:nomeModelos
 * @function
 * @description
 * # nomeModelos
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('nomeModelos', function () {
    return function (input) {
      if (input) {
        return input.map(function(modelo) {
          return '<li>' + modelo.descricao + '</li>';
        }).join('');
      }
    };
  });
