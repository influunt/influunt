'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntModalTimeline
 * @description
 * # influuntModalTimeline
 */
angular.module('influuntApp')
  .directive('influuntModalInstalacao', ['Restangular', 'toast', '$filter', 'influuntBlockui', '$http',
    function (Restangular, toast, $filter, influuntBlockui, $http) {
      return {
        templateUrl: 'views/directives/influunt-modal-instalacao.html',
        restrict: 'E',
        scope: {
          resource: '=',
          resourceId: '=',
          title: '@'
        },
        link: function(scope) {
          var prepareDownloadData;

          scope.instalacao = function(resource, id) {
            return Restangular.one(resource, id).all('instalacao').customGET()
              .then(function(res) {
                scope.instalacaoControlador = {
                  id: res.idControlador,
                  publicKey: res.publicKey,
                  privateKey: res.privateKey,
                  senha: res.senha
                };
                prepareDownloadData();
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

          prepareDownloadData = function() {
            $http.get('/resources/controlador.conf').then(function(response) {
              var config = response.data
                  .replace('|CONTROLADOR_ID|', scope.instalacaoControlador.id)
                  .replace('|CHAVE_PUBLICA|', scope.instalacaoControlador.publicKey)
                  .replace('|CHAVE_PRIVADA|', scope.instalacaoControlador.privateKey);

              var blob = new Blob([config], { type: 'text/plain' });
              scope.downloadUrl = window.URL.createObjectURL(blob);
            });
          };
        }
      };
    }]);
