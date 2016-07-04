'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntDropzone
 * @description
 * # influuntDropzone
 *
 * CORRIGIR: As traduções das labels ainda estão estáticas, porque o filter é
 * carregado antes mesmo do angular-translate estar pronto (ele carrega o arquivo
 * i18n de forma assincrona).
 */
angular.module('influuntApp')
  .directive('influuntDropzone', ['APP_ROOT', '$timeout', function (APP_ROOT, $timeout) {
    return {
      restrict: 'A',
      scope: {
        url: '@',
        anel: '=',
        imagensUrl: '=',
        onSuccess: '&'
      },
      link: function postLink(scope, element) {
        /**
         * A cada troca de anel, este watcher deverá esconder os itens que
         * não são deste anel e exibir aqueles que são deles.
         */
        var filterVisiblePreviews = function() {
          $timeout(function() {
            $('.dz-preview[data-anel-id="' + scope.anel.idAnel + '"]').show();
            $('.dz-preview:not([data-anel-id="' + scope.anel.idAnel + '"])').hide();
          }, 0);
        };

        scope.$watch('anel.idAnel', function(value) {
          return value && filterVisiblePreviews();
        });

        scope.$watch(function() {
          return $(element).children('.dz-preview').length;
        }, function(val) {
          return val > 0 && filterVisiblePreviews();
        });

        $(element).dropzone({
          url: APP_ROOT + '/imagens',
          dictDefaultMessage: 'Arraste imagens para este local',
          dictFallbackMessage: 'Seu navegador não suporta arrastar e soltar upload de arquivos.',
          autoProcessQueue: true,
          uploadMultiple: false,
          parallelUploads: 100,
          thumbnailHeight: 100,
          thumbnailWidth: 100,
          clickable: true,
          paramName: 'imagem',
          maxFiles: 100,
          headers: {
            authToken: localStorage.token
          },
          success: function(upload, imagem) {
            var anel = scope.anel;

            // Adiciona o anel id ao elemento do preview. Este id será utilizado
            // para filtrar as imagens de estagios para os diferentes aneis.
            $('.dz-preview').filter(function() {
              return !$(this).attr('data-anel-id');
            }).attr('data-anel-id', anel.idAnel);

            return scope.onSuccess({upload: upload, imagem: imagem});
          }
        });
      }
    };
  }]);
