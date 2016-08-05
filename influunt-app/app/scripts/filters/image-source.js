'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:imageSource
 * @function
 * @description
 * # imageSource
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('imageSource', ['APP_ROOT',
    function (APP_ROOT) {
      return function (input, version) {
        if (input) {
          var parts = [APP_ROOT, 'imagens', input, version];
          return _.compact(parts).join('/');
        }
      };
    }]);
