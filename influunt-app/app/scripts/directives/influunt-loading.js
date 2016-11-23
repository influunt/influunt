'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntLoading
 * @description
 * # influuntLoading
 */
angular.module('influuntApp')
  .directive('influuntLoading', ['Restangular', 'toast', '$filter', 'influuntBlockui', '$state',
    function (Restangular, toast, $filter, influuntBlockui, $state) {
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
