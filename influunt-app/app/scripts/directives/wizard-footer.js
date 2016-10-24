'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:wizardFooter
 * @description
 * # wizardFooter
 */
angular.module('influuntApp')
  .directive('wizardFooter', ['$filter', '$state', function ($filter, $state) {
    return {
      templateUrl: 'views/directives/wizard-footer.html',
      restrict: 'E',
      link: function(scope, el, attrs) {
        scope.url = attrs.url;
        scope.currentStep = attrs.currentStep;
        scope.nextStep = attrs.nextStep;
        scope.previousStep = attrs.previousStep;
        scope.requireAssertion = attrs.requireAssertion;

        scope.getStepTitle = function(step) {
          return $filter('translate')($state.get(step).data.title);
        };
      }
    };
  }]);
