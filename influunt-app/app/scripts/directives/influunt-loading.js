'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntLoading
 * @description
 * # influuntLoading
 */
angular.module('influuntApp')
  .directive('influuntLoading', [
    function () {
      return {
        template: '<img class="loading" src="images/loading.gif" width="{{ width }}">',
        restrict: 'E',
        scope: {
          width: '@'
        },
        link: function(scope) {
          scope.width = parseInt(scope.width) || 20;
        }
      };
    }]);
