'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntDateTimePicker
 * @description
 * # influuntDateTimePicker
 */
angular.module('influuntApp')
  .directive('influuntDateTimePicker', ['HorariosService', function (HorariosService) {
    return {
      templateUrl: 'views/directives/influunt-date-time-picker.html',
      restrict: 'E',
      scope: {
        label: '@',
        ngModel: '=',
        isRequired: '=?'
      },
      link: function postLink(scope) {
        scope.elementController = {};
        scope.dateOptions = {
          showWeeks: false
        };

        scope.horas = HorariosService.getHoras();
        scope.minutos = HorariosService.getMinutos();
        scope.segundos = HorariosService.getSegundos();
      }
    };
  }]);
