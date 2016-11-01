'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntKnob
 * @description
 * # influuntKnob
 */
angular.module('influuntApp')
  .directive('influuntKnob', ['influuntAlert', '$filter',
    function (influuntAlert, $filter) {

      var knob;

      return {
        templateUrl: 'views/directives/influunt-knob.html',
        restrict: 'E',
        scope: {
          title: '@',
          label: '@',
          class: '@',
          mostrarPercentual: '@',
          maxPercentual: '=',
          min: '=',
          max: '=',
          step: '=?',
          readOnly: '=',
          ngModel: '=',
          onChange: '&'
        },
        link: function influuntKnob(scope, element) {
          var showLabel = function(args) {
            return '<p class="knob-value">' + args.value + '</p><p class="knob-label">' + scope.label + '</p>';
          };

          scope.mostrarPercentual = scope.mostrarPercentual || false;
          scope.ngModel = scope.ngModel || scope.min || 0;
          knob = $(element).find('.knob-shape').roundSlider({
            radius: 90,
            min: scope.min,
            max: scope.max,
            sliderType: 'min-range',
            value: scope.ngModel,
            handleShape: 'dot',
            tooltipFormat: showLabel,
            step: scope.step || 1
          });

          scope.percentual = function() {
            if(scope.ngModel && scope.maxPercentual > 0){
              return Math.round((scope.ngModel*100)/scope.maxPercentual) || 0;
            }
            return 0;
          };

          knob.on('change', function(ev) {
            var value = ev.value;
            var oldValue = scope.ngModel;

            if (value) {
              scope.ngModel = ev.value;
              scope.$apply();
              return angular.isFunction(scope.onChange()) && scope.onChange()(oldValue, value);
            }
          });

          knob.on('outOfBounds', _.debounce(function() {
            var texto;

            if (parseFloat(scope.ngModel) === parseFloat(scope.max)) {
              texto = $filter('translate')('directives.influuntKnob.alertaLimiteMaximo');
            } else {
              texto = $filter('translate')('directives.influuntKnob.alertaLimiteMinimo');
            }

            influuntAlert.alert($filter('translate')('geral.atencao'), texto);
          }, 300));

          scope.$watch('ngModel', function(value) {
            value = value || scope.min;
            $(element).find('.knob-shape').roundSlider('setValue', value);
          });

          scope.$watch('readOnly', function(value) {
            if (value) {
              $(element).find('.knob-shape').roundSlider('disable');
            } else {
              $(element).find('.knob-shape').roundSlider('enable');
            }
          });

          return knob;
        }
      };
    }]);
