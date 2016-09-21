'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:wizardButtons
 * @description
 * # wizardButtons
 */
angular.module('influuntApp')
  .directive('wizardButtons', ['$state',
    function ($state) {
      return {
        templateUrl: 'views/directives/wizard-buttons.html',
        restrict: 'E',
        scope: {
          anterior: '@',
          proximo: '@',
          atual: '@'
        },
        link: function postLink(scope) {
          scope.getStepTitle = function(stepName) {
            return $state.get(stepName).data.title || 'undefined';
          };
        }
      };
    }]);
