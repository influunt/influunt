'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntMetis
 * @description
 * # influuntMetis
 */
angular.module('influuntApp')
  .directive('influuntRevisao', function () {
    return {
      restrict: 'E',
      templateUrl: 'views/directives/influunt-revisao/main.html',
      objeto: '='
    };
  })

  .directive('editInRevisao', function () {
    return {
      restrict: 'E',
      templateUrl: 'views/directives/influunt-revisao/edit-in-revisao.html',
      objeto: '=',
      scope: {
        condition: '=',
        onClick: '&',
        tooltip: '@'
      }
    };
  });
