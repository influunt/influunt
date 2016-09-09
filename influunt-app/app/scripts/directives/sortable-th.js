'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:sortableTh
 * @description
 * # sortableTh
 */
angular.module('influuntApp')
  .directive('sortableTh', function () {
    return {
      templateUrl: 'views/directives/sortable-th.html',
      restrict: 'A',
      scope: {
        label: '@',
        name: '@',
        ngModel: '='
      }
    };
  });
