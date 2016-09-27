'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:influuntTime
 * @function
 * @description
 * # influuntTime
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('influuntDate', function () {
    return function influuntDate(input, format) {
      format = format || 'HH:mm:ss';
      if(input) {
        if(_.isString(input)) {
          return moment(input, 'HH:mm:ss.000').format(format);
        }
      }
      return '-----';
    };
  });
