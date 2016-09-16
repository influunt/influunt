'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntMatch
 * @description
 * # influuntMatch
 */
angular.module('influuntApp')
  .directive('influuntMatch', function () {
    return {
      require: 'ngModel',
        scope: {
            otherModelValue: '=influuntMatch'
        },
        link: function(scope, element, attributes, ngModel) {

            ngModel.$validators.influuntMatch = function(modelValue) {
                return modelValue == scope.otherModelValue;
            };

            scope.$watch('otherModelValue', function() {
                ngModel.$validate();
            });
        }
    };
  });
