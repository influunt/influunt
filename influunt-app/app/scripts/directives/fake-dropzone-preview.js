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
        link: function postLink(scope, el) {
          var $form = $(el[0]).parent();
          var template = '<div class="dz-preview dz-processing dz-image-preview" data-anel-id="{{ data.idAnel }}" data-imagem-id="{{ data.source }}"><div class="dz-details"><div class="dz-filename"><span data-dz-name="">{{ data.nome }}</span></div><div class="dz-size" data-dz-size=""><strong>9.4</strong> KiB</div> <img data-dz-thumbnail="" alt="{{ data.nome }}" src="{{ data.source | imageSource }}"> </div><div class="dz-filename"><span data-dz-name="">{{ data.nome }}</span></div><div class="dz-progress"><span class="dz-upload" data-dz-uploadprogress="" style="width: 100%;"></span></div><div class="dz-success-mark"><span>✔</span></div><div class="dz-error-mark"><span>✘</span></div><div class="dz-error-message"><span data-dz-error-message=""></span></div></div>';

          scope.$on('influuntWizard.dropzoneOk', function(ev) {
            if (ev.targetScope.aneis) {
              ev.targetScope.aneis.forEach(function(anel) {
                return anel.estagios && anel.estagios.forEach(function(estagio) {
                  if (estagio.id) {
                    scope.data = {
                      idAnel: anel.idAnel,
                      nome: estagio.imagem.filename,
                      source: estagio.imagem.id
                    };

                    var preview = $interpolate(template)(scope);
                    $form.append(preview);
                  }
                });
              });
            }

            var hasItems = $('.dz-preview').length > 0;
            if (hasItems) {
              $('.dz-default.dz-message').hide();
            }
          });
        }
      };
    }]);
