'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:ichecks
 * @description
 * # ichecks
 */
angular.module('influuntApp')
  .directive('ichecks', ['$timeout', function ($timeout) {
    return {
      restrict: 'A',
      scope: {
        isDisabled: '=',
        ngModel: '=',
        iChanged: '&'
      },
      link: function postLink(scope, element) {
        $(document).ready(function() {
          $timeout(function() {
            $(element[0]).iCheck({
              checkboxClass: 'icheckbox_square-green',
              radioClass: 'iradio_square-green'
            });

            $(element[0]).on('ifChanged', function(ev) {
              $timeout(function() {
                scope.ngModel = ev.target.checked;
                return scope.iChanged && scope.iChanged();
              });
            });

            scope.$watch('isDisabled', function(value) {
              return value && $(element[0]).iCheck('disable');
            });
          });
        });
      }
    };
  }]);
