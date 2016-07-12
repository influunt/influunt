'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:select2
 * @description
 */
angular.module('influuntApp')
  .directive('select2', [function () {
    return {
      restrict: 'A',
      link: function postLink(scope, element) {
        $(element).select2();
      }
    };
  }]);
