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
  .directive('influuntDropzone', ['APP_ROOT', '$timeout', '$filter', '$q', 'Restangular', 'toast', 'influuntBlockui', 'dropzoneUtils',
    function (APP_ROOT, $timeout, $filter, $q, Restangular, toast, influuntBlockui, dropzoneUtils) {
      return {
        restrict: 'A',
        scope: {
          anel: '=',
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
                $(element).find('.dz-preview[data-anel-id="' + scope.anel.idJson + '"]').removeClass('hide');
                $(element).find('.dz-preview:not([data-anel-id="' + scope.anel.idJson + '"])').addClass('hide');
                dropzoneUtils.countFiles(element[0], scope.options.maxFiles);
              }
            });
          };

          var deleteImage = function(imagemId, dropzoneFile, dropzone, e) {
            var deferred = $q.defer();
            if (imagemId) {
              Restangular.one('imagens', imagemId).remove().then(deferred.resolve).catch(deferred.reject);
            } else {
              deferred.resolve(true);
            }

            return deferred.promise
              .then(function() {
                dropzoneFile.previewElement = null;
                dropzone.removeFile(dropzoneFile);
                $(e.target).parent().remove();
                dropzoneUtils.countFiles(element[0], scope.options.maxFiles);
                scope.onDelete({ imagem: { id: imagemId } });
              })
              .catch(function() {
                toast.error($filter('translate')('geral.dropzone.erro_apagar_imagem'));
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
            success: function(upload, response) {
              var anel = scope.anel;
              if (angular.isDefined(anel)) {
                // Adiciona o anel id ao elemento do preview. Este id será utilizado
                // para filtrar as imagens de estagios para os diferentes aneis.
                $(upload.previewElement).attr('data-anel-id', anel.idJson);
              }
              $(upload.previewElement).attr('data-imagem-id', response.imagem.id);

              return scope.onSuccess({ upload: upload, imagem: response.imagem, anelIdJson: response.anelIdJson });
            },
            init: function() {
              this.on('addedfile', function(file) {
                var removeButton = Dropzone.createElement('<a class="dz-remove" title="'+scope.removeButtonText+'">'+scope.removeButtonText+'</a>');
                var _this = this;
                removeButton.addEventListener('click', function(e) {
                  e.preventDefault();
                  e.stopPropagation();
                  var imagemId = $(this).parent('.dz-image-preview').attr('data-imagem-id');
                  deleteImage(imagemId, file, _this, e);
                });
                file.dropzoneId = this.element.id;
                file.previewElement.appendChild(removeButton);

                dropzoneUtils.countFiles(element[0], scope.options.maxFiles);
              })
              .on('error', function(file, errorMessage) {
                console.error(errorMessage);
                var text = $filter('translate')('directives.influuntDropzone.errorMessage');
                $(file.previewElement).find('.dz-error-message').text(text);
              });

              // chamado logo antes de o arquivo ser enviado p/ o servidor
              this.on('sending', function(file, xhr, formData) {
                var anelIdJson = scope.anel ? scope.anel.idJson : UUID.generate();
                formData.append('anelIdJson', anelIdJson);
              });
            }
          };

          var options = _.merge(defaults, scope.options);
          $(element).dropzone(options);
        }
      };
    }]);
