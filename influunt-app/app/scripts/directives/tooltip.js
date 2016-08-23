'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:tooltip
 * @description
 * # tooltip
 */
angular.module('influuntApp')
  .directive('infTooltip', function () {
    return {
      restrict: 'A',
      scope: {
        messages: '='
      },
      link: function postLink(scope, element) {
        $(document).ready(function() {
          $(element[0]).tooltipster({
            contentAsHTML: true,
            trigger: 'hover',
            side: 'right',
            theme: 'tooltipster-error-tag'
          });
        });

        scope.$watch('messages', function(value) {
          if (value) {
            var messages = [];

            _.each(value, function(group) {
              messages.push(
                _.map(group, function(message) { return '<li>' + message + '</li>'; })
              );
            });

            messages = _.chain(messages).flatten().uniq().value().join('');
            messages = '<ul style="margin: 0px;">' + messages + '</ul>';
            $(element[0]).tooltipster('content', messages);
          }
        });
      }
    };
  });
