'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntFaixaValores
 * @description
 * # influuntFaixaValores
 */
angular.module('influuntApp')
  .directive('influuntFaixaValores', ['PermissionStrategies',
    function (PermissionStrategies) {
      return {
        templateUrl: 'views/directives/influunt-faixa-valores.html',
        restrict: 'E',
        scope: {
          name: '=',
          min: '=',
          max: '=',
          unidade: '='
        },
        link: function influuntFaixaValores(scope) {
          var nome = scope.name.split('.')[1];
          scope.PermissionStrategies = PermissionStrategies;
          scope.nameMin = nome + 'Min';
          scope.nameMax = nome + 'Max';
          return true;
        }
      };
    }]);
