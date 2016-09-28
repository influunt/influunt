'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:influuntDate
 * @function
 * @description
 * # influuntDate
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('influuntDate', function () {
    return function influuntDate(input, format) {
      format = format || 'DD/MM/YYYY HH:mm:ss';
      if(input) {
        if(_.isString(input)) {
          input = moment(input, 'DD/MM/YYYY HH:mm:ss');
        }
        if(format === 'fromNow') {
          return moment(input).fromNow();
        }
        if(format === 'fromHours') {
          return humanizeDuration(moment.duration(input, 'hours').asMilliseconds(),{ language: 'pt' });
        }
        return moment(input).format(format);
      } else {
        return '-----';
      }
    };
  });
