'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntModalTimeline
 * @description
 * # influuntModalTimeline
 */
angular.module('influuntApp')
  .directive('influuntModalInstalacao', ['Restangular', 'toast', '$filter', 'influuntBlockui',
    function (Restangular, toast, $filter, influuntBlockui) {
      return {
        templateUrl: 'views/directives/influunt-modal-instalacao.html',
        restrict: 'E',
        scope: {
          resource: '=',
          resourceId: '=',
          title: '@'
        },
        link: function(scope) {
          scope.instalacao = function(resource, id) {
            return Restangular.one(resource, id).all('instalacao').customGET()
              .then(function(res) {
                scope.instalacaoControlador = {
                  id: res.idControlador,
                  publicKey: res.publicKey,
                  privateKey: res.privateKey
                };
              })
              .catch(function(err) {
                toast.error($filter('translate')('geral.mensagens.default_erro'));
                console.error(err);
              })
              .finally(influuntBlockui.unblock);
          };

          scope.closeInstalacaoModal = function() {
            $('#modal-instalacao').modal('hide');
          };

          scope.$watch('resourceId', function(resourceId) {
            return resourceId && scope.instalacao(scope.resource, resourceId);
          });
        }
      };
    }]);
