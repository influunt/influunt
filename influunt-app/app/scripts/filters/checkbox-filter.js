'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:checkboxFilter
 * @function
 * @description
 * # checkboxFilter
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('checkboxFilter', function () {
    return function (input, checkboxes) {
      var property = 'id';

      if (input) {
        var hasFilters = _.reduce(checkboxes, function(a, b) {return a || b;}, false);

        if (!hasFilters) {
          return input;
        }


        return _.filter(input, function(obj) {
          console.log('var checkboxes = ', checkboxes);
          console.log('var obj = ', obj);
          console.log('var property = ', property);
          return checkboxes[obj[property]];
        });
      }
    };
  });
