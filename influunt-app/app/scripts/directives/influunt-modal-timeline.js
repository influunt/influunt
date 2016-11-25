'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntModalTimeline
 * @description
 * # influuntModalTimeline
 */
angular.module('influuntApp')
  .directive('influuntModalTimeline', ['Restangular', 'toast', '$filter', 'influuntBlockui', '$state',
    function (Restangular, toast, $filter, influuntBlockui, $state) {
      return {
        templateUrl: 'views/directives/influunt-modal-timeline.html',
        restrict: 'E',
        scope: {
          resource: '=',
          resourceId: '=',
          title: '@',
          mostrarLinkAlteracoes: '=',
          linkAlteracoes: '@'
        },
        link: function(scope) {
          scope.timeline = function(resource, id) {
            return Restangular.one(resource, id).all('timeline').customGET()
              .then(function(res) {
                scope.versoes = _
                  .chain(res)
                  .map(function(v) {
                    v.dataCriacaoStr = moment(v.dataCriacao, 'DD/MM/YYYY HH:mm:ss').format('YYYYMMDDHHmmSS');
                    v.href = $state.href(scope.linkAlteracoes, { id: scope.resourceId, versaoIdJson: v.idJson });
                    return v;
                  })
                  .orderBy('dataCriacaoStr', 'desc')
                  .value();
              })
              .catch(function(err) {
                toast.error($filter('translate')('geral.mensagens.default_erro'));
                console.error(err);
              })
              .finally(influuntBlockui.unblock);
          };

          scope.closeTimelineModal = function() {
            $('#modal-timeline').modal('hide');
          };

          scope.$watch('resourceId', function(resourceId) {
            return resourceId && scope.timeline(scope.resource, resourceId);
          });
        }
      };
    }]);
