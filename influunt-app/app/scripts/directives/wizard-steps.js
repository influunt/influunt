'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:wizardSteps
 * @description
 * # wizardSteps
 */
angular.module('influuntApp')
  .directive('wizardSteps', function () {
    return {
      restrict: 'A',
      scope: {
        enabled: '='
      },
      link: function postLink(scope, element) {
        var $element = $(element);


        scope.$watch('enabled', function(enabled) {
          if (angular.isUndefined(enabled)) {
            return false;
          }

          $element.removeClass('disabled');
          $element.siblings().removeClass('disabled');
          if (enabled) {
            // $element.prevAll().removeClass('disabled');
            $element.nextAll().addClass('disabled');
          }


          if (enabled) {
            $element.removeClass('disabled');
          } else {
            $element.addClass('disabled');
          }
        });
      }
    };
  });
