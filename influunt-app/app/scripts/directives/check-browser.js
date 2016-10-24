'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:checkBrowser
 * @description
 * # checkBrowser
 */
angular.module('influuntApp')
  .directive('checkBrowser', function () {
    return {
      templateUrl: 'views/directives/check-browser.html',
      restrict: 'E',
      scope: {
        required: '=',
        rejected: '='
      },
      link: function postLink(scope) {
        var checkBrowser = function() {
          scope.supported = true;

          if (_.isArray(scope.required)) {
            // Verifica o browser corresponde com os requisitos
            scope.supported = scope.supported && scope.required.every(function(i) {
              return bowser.check(i, window.navigator.userAgent);
            });
          }

          if (_.isArray(scope.rejected)) {
            scope.supported = scope.supported && scope.rejected.every(function(i) {
              return !bowser[i];
            });
          }

          return scope.supported;
        };

        scope.$watch('required', function() {
          return checkBrowser();
        }, true);

        scope.$watch('required', function() {
          return checkBrowser();
        }, true);
      }
    };
  });
