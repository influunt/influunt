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
      link: function postLink(scope, element, attrs) {
        $(window).scroll(function () {
          if ($(this).scrollTop() > 100) {
            $(element).fadeIn();
          } else {
            $(element).fadeOut();
          }
        });

        $(element).click(function () {
          console.log('gototop!!!!!!!!');
          $("html, body").animate({ scrollTop: 0 }, 500);
          return false;
        });
      }
    };
  });
