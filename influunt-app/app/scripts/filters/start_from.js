'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:startFrom
 * @function
 * @description
 * # startFrom
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('startFrom', function () {
  return function (input, start) {
    start = +start;
    if (input) {
      return input.slice(start);
    } else {
      return null;
    }
  };
});
