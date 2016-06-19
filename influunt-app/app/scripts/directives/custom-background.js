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
          }
        });
      }
    };
  }]);
