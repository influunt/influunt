'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntKnob
 * @description
 * # influuntKnob
 */
angular.module('influuntApp')
  .directive('influuntKnob', [
    function () {

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
            scope.ngModel = ev.value;
            scope.$apply();
          });

          scope.$watch('ngModel', function(value) {
            $(element).find('.knob-shape').roundSlider('setValue', value);
          });

          return knob;
        }
      };
    }]);
