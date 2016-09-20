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
  .directive('influuntDropzone', ['APP_ROOT', '$timeout', '$filter', 'Restangular', 'toast', 'influuntBlockui',
    function (APP_ROOT, $timeout, $filter, Restangular, toast, influuntBlockui) {
      return {
        restrict: 'A',
        scope: {
          url: '@',
          anel: '=',
          imagensUrl: '=',
          options: '=',
          onSuccess: '&',
          onDelete: '&',
          removeButtonText: '@'
        },
        link: function postLink(scope, element) {
          /**
           * A cada troca de anel, este watcher deverá esconder os itens que
           * não são deste anel e exibir aqueles que são deles.
           */
          var filterVisiblePreviews = function() {
            $timeout(function() {
              if (scope.anel) {
                $(element).find('.dz-preview[data-anel-id="' + scope.anel.idJson + '"]').show();
                $(element).find('.dz-preview:not([data-anel-id="' + scope.anel.idJson + '"])').hide();
              }
            });
          };

          var deleteImage = function(imagemId, dropzoneFile, dropzone) {
            return Restangular
              .one('imagens', imagemId).remove().then(function() {
                dropzoneFile.previewElement = null;
                dropzone.removeFile(dropzoneFile);
                scope.onDelete({ imagem: { id: imagemId } });
              }).catch(function() {
                toast.error($filter('translate')('controladores.geral.dropzone.erro_apagar_imagem'));
              })
              .finally(influuntBlockui.unblock);
          };

          scope.$watch('anel.idJson', function(value) {
            return value && filterVisiblePreviews();
          });

          scope.$watch(function() {
            return $(element).children('.dz-preview').length;
          }, function(val) {
            return val > 0 && filterVisiblePreviews();
          });

          var defaults = {
            url: APP_ROOT + '/imagens',
            dictDefaultMessage: 'Arraste imagens para este local',
            dictFallbackMessage: 'Seu navegador não suporta arrastar e soltar upload de arquivos.',
            autoProcessQueue: true,
            uploadMultiple: false,
            parallelUploads: 100,
            thumbnailHeight: 150,
            thumbnailWidth: 150,
            clickable: true,
            paramName: 'imagem',
            maxFiles: 100,
            headers: {
              authToken: localStorage.token
            },
            success: function(upload, imagem) {
              var anel = scope.anel;
              if (angular.isDefined(anel)) {
                // Adiciona o anel id ao elemento do preview. Este id será utilizado
                // para filtrar as imagens de estagios para os diferentes aneis.
                $(upload.previewElement).attr('data-anel-id', anel.idJson);
                $(upload.previewElement).attr('data-imagem-id', imagem.id);
              }

              return scope.onSuccess({ upload: upload, imagem: imagem });
            },
            init: function() {
              this.on('addedfile', function(file) {
                var removeButton = Dropzone.createElement('<a class="dz-remove" title="'+scope.removeButtonText+'">'+scope.removeButtonText+'</a>');
                var _this = this;
                removeButton.addEventListener('click', function(e) {
                  e.preventDefault();
                  e.stopPropagation();
                  var imagemId = $(this).parent('.dz-image-preview').attr('data-imagem-id');
                  deleteImage(imagemId, file, _this);
                });
                file.dropzoneId = this.element.id;
                file.previewElement.appendChild(removeButton);
              });
            }
          };

          var options = _.merge(defaults, scope.options);
          $(element).dropzone(options);
        }
      };
    }]);
