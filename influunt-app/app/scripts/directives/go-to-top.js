'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:goToTop
 * @description
 * # goToTop
 */
angular.module('influuntApp')
  .directive('goToTop', function () {
    return {
      restrict: 'AC',
      link: function postLink(scope, element) {
        $(element).click(function () {
          $('html, body').animate({ scrollTop: 0 }, 500);
          return false;
        });
      }
    };
  });
