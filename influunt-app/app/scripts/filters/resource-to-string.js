'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:resourceToString
 * @function
 * @description
 * # resourceToString
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('resourceToString', function () {
    return function (input, nomeCampo) {
      nomeCampo = nomeCampo || 'nome';
      if (input) {
        return input[nomeCampo];
      }
    };
  });
