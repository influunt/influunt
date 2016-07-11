'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:select2
 * @description
 */
angular.module('influuntApp')
  .directive('select2', [function () {
    console.log('registrando diretiva');
    return {
      restrict: 'A',
      link: function postLink(scope, element) {
        console.log('linking diretiva')
        $(element).select2();
      }
    };
  }]);
