'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:resourceListToString
 * @function
 * @description
 * # resourceListToString
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('resourceListToString', function () {
    return function (input, nomeCampo) {
      nomeCampo = nomeCampo || 'descricao';
      if (input) {
        return input.map(function(resource) {
          return '<li>' + resource[nomeCampo] + '</li>';
        }).join('');
      }
    };
  });
