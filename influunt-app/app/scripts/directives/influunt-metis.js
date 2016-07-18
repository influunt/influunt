'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntMetis
 * @description
 * # influuntMetis
 */
angular.module('influuntApp')
  .directive('influuntMetis', ['$timeout', function ($timeout) {
    return {
      restrict: 'A',
      link: function postLink(scope, element) {
        $timeout(function() {
          angular.element(element).metisMenu();
        });
      }
    };
  }]);
