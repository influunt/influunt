'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:nomeAreas
 * @function
 * @description
 * # nomeAreas
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('nomeAreas', function () {
    return function (input) {
      if (input) {
        return input.map(function(area) {
          return '<li>' + area.descricao + '</li>';
        }).join('');
      }
    };
  });
