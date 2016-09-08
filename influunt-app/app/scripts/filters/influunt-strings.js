'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:influuntStrings
 * @function
 * @description
 * # influuntStrings
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('humanize', function () {
    return function(text) {
      if(text) {
        text = S(text).humanize().s;

        return text.split(/\s+/g).map(function(i) {
          return S(i).capitalize().s;
        }).join(' ');
      }
    };
  })
  .filter('camelize', function () {
    return function (input) {
      return S(input).camelize().s;
    };
  })
  .filter('capitalize', function () {
    return function (input) {
      return S(input).capitalize().s;
    };
  });
