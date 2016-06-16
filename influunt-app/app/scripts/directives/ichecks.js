'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:ichecks
 * @description
 * # ichecks
 */
angular.module('influuntApp')
  .directive('ichecks', function () {
    return {
      restrict: 'A',
      link: function postLink(scope, element) {
        $(element[0]).iCheck({
          checkboxClass: 'icheckbox_square-green',
          radioClass: 'iradio_square-green',
        });
      }
    };
  });
