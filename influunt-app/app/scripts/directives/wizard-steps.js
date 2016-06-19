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
      link: function postLink(scope, el) {

        // scope.$watch('enabled', function(enabled) {
        //   console.log($(el).children('li.current'));
        //   if (enabled) {
        //     _.each(enabled, function(isEnabled, key) {
        //       if (isEnabled) {
        //         var a = $('a[step-id="' + key + '"]');
        //         var prevAll = a.parents('li').prevAll();
        //         return prevAll && _.each(prevAll, function(prev) {
        //           $(prev).find('a').addClass('disabled');
        //         });
        //       }
        //     });
        //   }
        // });

        scope.$watch(function() {
          return $(el).html();
        }, function(val) {
          if (val && val.match(/current/)) {
            var current = $(el).find('li.current');
            if (current.length > 0) {
              current.addClass('visited');
              current.nextAll(':not(.visited)').addClass('disabled');
              current.prevAll().removeClass('disabled');
              // _.each(nextAll, function(next) {
              //   $(next).addClass('disabled');
              //   console.log($(next).find('a'));
              // });
            }
          }
        });
      }
    };
  });
