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
      templateUrl: 'views/directives/influunt-revisao.html',
      objeto: '=',
      showFooter: '=',
      link: function postLink() {

      }
    };
  });
