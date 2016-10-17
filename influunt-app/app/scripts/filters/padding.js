'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:padding
 * @function
 * @description
 * # influuntTime
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('padding', function () {
    return function padding(input, length, char) {
      length = length ? parseInt(length) : 2;
      char = char || '0';
      return _.padStart(input, length, char);
    };
  });
