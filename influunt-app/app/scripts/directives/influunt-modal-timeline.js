'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntModalTimeline
 * @description
 * # influuntModalTimeline
 */
angular.module('influuntApp')
  .directive('influuntModalTimeline', ['Restangular', 'toast', '$filter', 'influuntBlockui',
    function (Restangular, toast, $filter, influuntBlockui) {
      return {
        templateUrl: 'views/directives/influunt-modal-timeline.html',
        restrict: 'E',
        scope: {
          resource: '=',
          resourceId: '='
        },
        link: function(scope) {
          scope.timeline = function(resource, id) {
            return Restangular.one(resource, id).all('timeline').customGET()
              .then(function(res) { scope.versoes = res; })
              .catch(function(err) {
                toast.error($filter('translate')('geral.mensagens.default_erro'));
                console.error(err);
              })
              .finally(influuntBlockui.unblock);
          };

          scope.$watch('resourceId', function(resourceId) {
            return resourceId && scope.timeline(scope.resource, resourceId);
          });
        }
      };
    }]);
