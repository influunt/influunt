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
  .directive('influuntDropzone', ['APP_ROOT', function (APP_ROOT) {
    return {
      restrict: 'A',
      scope: {
        url: '@',
        anel: '=',
        imagensUrl: '=',
        onSuccess: '&'
      },
      link: function postLink(scope, element) {
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
          success: function(upload, imagem) {
            var anel = scope.anel;

            // Adiciona o anel id ao elemento do preview. Este id será utilizado
            // para filtrar as imagens de movimentos para os diferentes aneis.
            $('.dz-preview').filter(function() {
              return !$(this).attr('data-anel-id');
            }).attr('data-anel-id', anel.id_anel);

            // A cada troca de anel, este watcher deverá esconder os itens que
            // não são deste anel e exibir aqueles que são deles.
            scope.$watch('anel.id_anel', function(value) {
              $('.dz-preview[data-anel-id="' + value + '"]').show();
              $('.dz-preview:not([data-anel-id="' + value + '"])').hide();
            });

            return scope.onSuccess({upload: upload, imagem: imagem});
          }
        });
      }
    };
  }]);
