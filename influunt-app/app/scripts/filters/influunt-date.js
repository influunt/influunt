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
    return function (input, format) {
      format = format || 'dd/mm/yyyy';
      if(input) {
        if(_.isString(input)) {
          input = moment(input, "dd/MM/yyyy HH:mm:ss");
        }
        if(format === 'fromNow') {
          return moment(input).fromNow();
        }

        return moment(input).format(format);
      }
    };
  });
