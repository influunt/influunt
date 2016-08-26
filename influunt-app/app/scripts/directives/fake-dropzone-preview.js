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
        link: function postLink(scope, element) {
          var $form = $(element[0]).parent();
          var template = '<div class="dz-preview dz-processing dz-image-preview" data-anel-id="{{ data.idJsonAnel }}" data-imagem-id="{{ data.source }}" data-estagio-id="{{ data.idJsonEstagio }}"><div class="dz-details"><div class="dz-size" data-dz-size=""><strong>9.4</strong> KiB</div> <img data-dz-thumbnail="" alt="{{ data.nome }}" src="{{ data.source | imageSource }}"> </div><div class="dz-filename"><span data-dz-name="">{{ data.nome }}</span></div><div class="dz-success-mark"><span>✔</span></div><div class="dz-error-mark"><span>✘</span></div><div class="dz-error-message"><span data-dz-error-message=""></span></div><a class="dz-remove" title="Remover estágio" ng-click="removerImagem(\'{{ data.idJsonEstagio }}\')">Remover estágio</a></div>';

          scope.removerImagem = function(estagioIdJson) {
            console.log('removendo estagio '+estagioIdJson);
          };

          var criarImagensFake = function(aneis, objeto) {
            _.forEach(aneis, function(anel) {
              var ids = _.map(anel.estagios, 'idJson');
              return ids && _
                .chain(objeto.estagios)
                .filter(function(estagio) {
                  return ids.indexOf(estagio.idJson) >= 0;
                })
                .each(function(estagio) {
                  if (estagio.id) {
                    var imagem = _.find(objeto.imagens, { idJson: estagio.imagem.idJson });
                    scope.data = {
                      idJsonAnel: anel.idJson,
                      idJsonEstagio: estagio.idJson,
                      nome: imagem.filename,
                      source: imagem.id
                    };

                    var preview = $interpolate(template)(scope);
                    $form.append(preview);
                  }
                })
                .value();
            });
          };

          scope.$on('influuntWizard.dropzoneOk', function(ev) {
            if (ev.targetScope.aneis) {
              criarImagensFake(ev.targetScope.aneis, ev.targetScope.objeto);
              $compile($form.contents())(scope);
            }
          });
        }
      };
    }]);
