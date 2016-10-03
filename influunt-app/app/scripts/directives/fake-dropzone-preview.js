'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:fakeDropzonePreview
 * @description
 * # fakeDropzonePreview
 */
angular.module('influuntApp')
  .directive('fakeDropzonePreview', ['$compile', '$interpolate',
    function ($compile, $interpolate) {
      return {
        restrict: 'E',
        scope: {
          imagens: '=',
          controlador: '=',
          anel: '=',
          removeButtonText: '@',
          onDelete: '&'
        },
        link: function postLink(scope, element) {
          var $form = $(element[0]).parent();
          var template = '<div class="dz-preview dz-processing dz-image-preview" data-anel-id="{{ anel.idJson }}" data-imagem-id="{{ data.objIdJson }}"><div class="dz-details"><img data-dz-thumbnail="" alt="{{ data.nome }}" ng-src="{{ data.source }}"></div><div class="dz-filename"><span data-dz-name="">{{ data.nome }}</span></div><a class="dz-remove" title="{{ removeButtonText }}" ng-click="removerImagem(\'{{ data.objIdJson }}\')">{{ removeButtonText }}</a></div>';
          var destroiFakePreviews, destroySingleFakePreview, criarImagensFake;

          scope.removerImagem = function(objIdjson) {
            if (angular.isFunction(scope.onDelete())) {
              scope.onDelete()(objIdjson).then(function(deveRemover) {
                if (deveRemover) {
                  var dropzoneId = 'croqui';
                  if(scope.anel){
                    dropzoneId = dropzoneId + '_' + (scope.anel.posicao - 1);
                  }
                  var dropzone = Dropzone.forElement('#' + dropzoneId);
                  _.filter(dropzone.files, {dropzoneId: dropzoneId}).map(function (e) {
                    e.accepted = false;
                  });
                  destroySingleFakePreview(objIdjson);
                }
              });
            }
          };

          criarImagensFake = function(imagens) {
            _.each(imagens, function(imagem) {
              scope.data = {
                nome: imagem.nomeImagem,
                source: imagem.url,
                objIdJson: imagem.idJson
              };

              var preview = $interpolate(template)(scope);
              $form.append(preview);
            });
          };

          destroiFakePreviews = function() {
            $form.find('.dz-preview').detach();
          };

          destroySingleFakePreview = function(imagemIdJson) {
            $form.find('.dz-preview[data-imagem-id="'+imagemIdJson+'"]').detach();
          };

          scope.$watch('imagens', function(imagens) {
            if (!_.isArray(imagens) && _.isObject(imagens)) {
              imagens = [imagens];
            }

            if (_.isArray(imagens)) {
              destroiFakePreviews();
              criarImagensFake(imagens);
              $compile($form.contents())(scope);
            }
          });
        }
      };
    }]);
