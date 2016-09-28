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
  .filter('influuntTime', function () {
    return function influuntTime(input, format) {
      format = format || 'HH:mm:ss';
      if(_.isString(input)) {
        return moment(input, 'HH:mm:ss.000').format(format);
      }
      return '-----';
    };
  });
