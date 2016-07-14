'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntKnob
 * @description
 * # influuntKnob
 */
angular.module('influuntApp')
  .directive('influuntKnob', ['$timeout',
    function ($timeout) {
      var changeTimeout = null;
      /**
       * On change event for knob jquery plugin.
       * It will debounce the ng-model value update.
       *
       * @param      {<type>}  value   The value
       * @param      {<type>}  scope   The scope
       */
      var onChange = function(dial, value, scope) {
        $timeout.cancel(changeTimeout);
        changeTimeout = $timeout(function() {
          value = (value >= scope.min) ? value : scope.min;
          value = (value <= scope.max) ? value : scope.max;
          dial.val(value).trigger('change');
          scope.ngModel = value;
        }, 200);
      };

      return {
        templateUrl: 'views/directives/influunt-knob.html',
        restrict: 'E',
        scope: {
          title: '@',
          label: '@',
          min: '=',
          max: '=',
          ngModel: '='
        },
        link: function postLink(scope, element) {
          // scope.min = 0;

          var dial = $(element).find('.dial');
          dial.knob({
            max: scope.max,
            change: function(v) {
              onChange(dial, v, scope);
            }
          });

          dial.val(scope.ngModel || scope.min).trigger('change');
        }
      };
    }]);
