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

// console.log('lendo arquivo directive dropzone');

angular.module('influuntApp')
  .directive('influuntDropzone', function () {
    console.log('registrando directive dropzone');
    return {
      restrict: 'A',
      scope: {
        url: '@',
        anel: "=",
        imagensUrl: "="
      },
      link: function postLink(scope, element) {
        $(element).dropzone({
          url: "http://localhost/api/v1/imagens",
          dictDefaultMessage: 'Arraste imagens para este local',
          dictFallbackMessage: 'Seu navegador não suporta arrastar e soltar upload de arquivos.',
          autoProcessQueue: true,
          uploadMultiple: false,
          parallelUploads: 100,
          thumbnailHeight: 100,
          thumbnailWidth: 100,
          clickable: true,
          paramName: "imagem",
          maxFiles: 100,
          success: function(upload, imagem) {
            var anel = scope.anel;
            var url = scope.imagensUrl;
            if (!('movimentos' in anel)) {
              anel.movimentos = []
            }
            anel.movimentos.push({ imagem: { id: imagem.id } });

            console.log("anel: ", anel)
          }
        });
      }
    };
  });
