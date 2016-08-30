'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntModalTimeline
 * @description
 * # influuntModalTimeline
 */
angular.module('influuntApp')
  .directive('influuntModalTimeline', function () {
    return {
      templateUrl: 'views/directives/influunt-modal-timeline.html',
      restrict: 'E'
    };
  });
