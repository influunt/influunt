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
        ngModel: '=?',
        ifChanged: '&',
        ifChecked: '&',
        ifUnchecked: '&'
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
                return scope.ifChanged && scope.ifChanged();
              });
            });

            $(element[0]).on('ifUnchecked', function() {
              $timeout(function() {
                return scope.ifUnchecked && scope.ifUnchecked();
              });
            });

            $(element[0]).on('ifChecked', function() {
              $timeout(function() {
                return scope.ifChecked && scope.ifChecked();
              });
            });

            /**
             * Atualiza a view do componente sempre que o ng-model é alterado.
             */
            scope.$watch('ngModel', function() {
              $(element[0]).iCheck('update');
            });

            scope.$watch('isDisabled', function(value) {
              if (angular.isUndefined(value)) {
                return false;
              }

              if (value) {
                $(element[0]).iCheck('disable');
              } else {
                $(element[0]).iCheck('enable');
              }
            });
          });
        });
      }
    };
  }]);
