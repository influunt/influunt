'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:customBackground
 * @description
 * # customBackground
 */
angular.module('influuntApp')
  .directive('customBackground', [function () {
    return {
      restrict: 'A',
      scope: {
        customBackground: '='
      },
      link: function (scope, element) {
        scope.$watch('customBackground', function(value) {
          if (value) {
            $(element[0]).css('background', 'url(' + value + ')');
            $(element[0]).css('background-size', 'cover');
            $(element[0]).css('background-repeat', 'no-repeat');
            $(element[0]).css('background-position', 'center center');
          }
        });
      }
    };
  }]);
