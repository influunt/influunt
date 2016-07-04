'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:limitesListToString
 * @function
 * @description
 * # limitesListToString
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('limitesListToString', function () {
    return function (input) {
      if (input) {
        return input.map(function(resource) {
          return '<li>(' + resource.latitude + '; ' + resource.longitude + ')</li>';
        }).join('');
      }
    };
  });
