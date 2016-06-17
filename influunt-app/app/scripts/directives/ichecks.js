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
        ngModel: '='
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
