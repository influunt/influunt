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
  .directive('influuntDropzone', function () {
    return {
      restrict: 'A',
      scope: {
        url: '@'
      },
      link: function postLink(scope, element) {
        $(element).dropzone({
          url: scope.url,
          dictDefaultMessage: 'Arraste imagens para este local',
          dictFallbackMessage: 'Seu navegador não suporta arrastar e soltar upload de arquivos.',
          autoProcessQueue: false,
          uploadMultiple: true,
          parallelUploads: 100,
          thumbnailHeight: 100,
          thumbnailWidth: 100,
          clickable: true,
          paramName: true,
          maxFiles: 100
        });
      }
    };
  });
