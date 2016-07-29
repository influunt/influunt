'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:wizardSteps
 * @description
 * # wizardSteps
 */
angular.module('influuntApp')
  .directive('wizardSteps', function () {
    return {
      restrict: 'A',
      link: function postLink(scope, el) {
        scope.$watch(function() {
          return $(el).html();
        }, function(val) {
          if (val && val.match(/active/)) {
            var current = $(el).find('li.active');
            current.nextAll().attr('class','');
            current.prevAll().addClass('completed');
          } else {
            $(el).children('li').addClass('disabled');
          }
        });
      }
    };
  });
