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
          removeButtonText: '@'
        },
        link: function postLink(scope, element) {
          var $form = $(element[0]).parent();
          var template = '<div class="dz-preview dz-processing dz-image-preview" data-anel-id="{{ anel.idJson }}" data-imagem-id="{{ data.source }}"><div class="dz-details"><img data-dz-thumbnail="" alt="{{ data.nome }}" src="{{ data.source }}"></div><div class="dz-filename"><span data-dz-name="">{{ data.nome }}</span></div><a class="dz-remove" title="{{ removeButtonText }}" ng-click="removerImagem(\'{{ data.source }}\')">{{ removeButtonText }}</a></div>';

          scope.removerImagem = function(objIdjson) {
            console.log('removendo estagio ' + objIdjson);
          };

          var criarImagensFake = function(imagens) {
            _.each(imagens, function(imagem) {
              scope.data = {
                nome: imagem.nomeImagem,
                source: imagem.url,
                url: imagem.url
              };

              var preview = $interpolate(template)(scope);
              $form.append(preview);
            });
          };

          var destroiFakePreviews = function() {
            $form.find('.dz-preview').detach();
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
