'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:helperEndereco
 * @description
 * # helperEndereco
 */
angular.module('influuntApp')
  .directive('helperEndereco', function () {
    return {
      template: '<input type="text" class="form-control" data-ng-model="ngModel" g-places-autocomplete>',
      restrict: 'E',
      scope: {
        ngModel: '=',
        latitude: '=',
        longitude: '='
      },
      link: function postLink(scope) {
        scope.$watch('ngModel', function(value) {
          if (value && value.geometry && location) {
            scope.latitude = value.geometry.location.lat();
            scope.longitude = value.geometry.location.lng();
          }
        }, true);
      }
    };
  });
