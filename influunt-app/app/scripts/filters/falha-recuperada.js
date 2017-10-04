'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:falhaRecuperada
 * @function
 * @description
 * # falhaRecuperada
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('falhaRecuperada', function () {
    return function (lista) {
      return _.isArray(lista) ? _.filter(lista, 'recuperado') : [];
    };
  })
  .filter('falhaNaoRecuperada', function () {
    return function (lista) {
      return _.isArray(lista) ? _.reject(lista, 'recuperado') : [];
    };
  });
