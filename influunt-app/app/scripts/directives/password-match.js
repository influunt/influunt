'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:passwordMatch
 * @description
 * # passwordMatch
 */
angular.module('influuntApp')
  .directive('passwordMatch', [function () {
    return {
      require: 'ngModel',
      scope: {
        passwordMatch: '=',
        ngModel: '='
      },
      link: function (scope, elem, attrs, ctrl) {
        scope.$watchGroup(['ngModel', 'passwordMatch'], function() {
          ctrl.$setValidity('passwordMatch', scope.ngModel === scope.passwordMatch);
        });
      }
    };
  }]);
