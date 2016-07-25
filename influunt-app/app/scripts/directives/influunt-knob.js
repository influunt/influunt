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

      var knob;


      return {
        templateUrl: 'views/directives/influunt-knob.html',
        restrict: 'E',
        scope: {
          title: '@',
          label: '@',
          class: '@',
          min: '=',
          max: '=',
          ngModel: '='
        },
        link: function influuntKnob(scope, element) {
          var showLabel = function(args) {
            return '<p class="knob-value">' + args.value + '</p><p class="knob-label">' + scope.label + '</p>';
          };

          scope.ngModel = scope.ngModel || scope.min || 0;
          knob = $(element).find('.knob-shape').roundSlider({
            radius: 90,
            min: 0,
            max: 120,
            sliderType: 'min-range',
            value: scope.ngModel,
            handleShape: 'dot',
            tooltipFormat: showLabel
          });

          knob.on('change', function(ev) {
            if (ev.value && ev.value !== ev.preValue) {
              scope.ngModel = ev.value;
              scope.$apply();
            }
          });
        }
      };
    }]);
