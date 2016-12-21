'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:vis
 * @description
 * # vis
 */
angular.module('influuntApp')
  .directive('vis', ['$rootScope', 'modoOperacaoService',
    function vis($rootScope, modoOperacaoService) {
      return {
        restrict: 'A',
        scope: {
          grupos: '=',
          estagios: '=',
          onChangeCheckbox: '&',
          beforeChangeCheckbox: '&?', // deve retornar uma promise
          tempoCiclo: '=',
          comCheckBoxGrupo: '='
        },
        link: function postLink(scope) {
          var container = document.getElementById('visualization');
          var timeline = new window.vis.Timeline(container);
          scope.comCheckBoxGrupo = angular.isDefined(scope.comCheckBoxGrupo) ? scope.comCheckBoxGrupo : true;

          var initCheckboxValues = function() {
            scope.grupos.forEach(function(grupo) {
              var selector = '.group-checkbox[data-posicao=' + grupo.posicao + ']';
              $(selector).prop('checked', grupo.ativado);
            });
          };

          var handleCheckboxChange = function() {
            var checkbox = $(this);
            var grupo = _.find(scope.grupos, {posicao: checkbox.data('posicao')});
            var grupoAtivado = checkbox.is(':checked');

            // se beforeChangeCheckbox for definido, o checkbox só troca de estado
            // se a promise retornada por scope.beforeChangeCheckbox for resolvida.
            if (scope.beforeChangeCheckbox) {
              checkbox.prop('checked', !grupoAtivado);
              scope.beforeChangeCheckbox({grupo: grupo, isAtivo: grupo.ativado})
                .then(function() {
                  grupo.ativado = grupoAtivado;
                  scope.onChangeCheckbox({grupo: grupo, isAtivo: grupo.ativado});
                });
            } else {
              grupo.ativado = grupoAtivado;
              scope.onChangeCheckbox({grupo: grupo, isAtivo: grupo.ativado});
              scope.$apply();
            }
          };

          var bindCheckboxEvents = function() {
            $('.group-checkbox').on('change', handleCheckboxChange);
          };

          var setData = function(grupos, estagios) {
            var groups = [];
            var items = [];

            groups.push({id: 'title', content: '&nbsp;'});
            grupos.forEach(function(grupo, index) {
              var groupId = grupo.labelPosicao;
              if(scope.comCheckBoxGrupo){
                groups.push({
                  content: '<input type="checkbox" class="group-checkbox" data-posicao="' + grupo.posicao + '" name="' + groupId + '"><strong class="m-l-sm">' + groupId + '</strong>',
                  id: groupId,
                  value: groupId
                });
              }else{
                groups.push({
                  id: groupId,
                  value: groupId
                });
              }

              var initialState = 1;
              if (grupo.intervalos[0].status === 0) {
                items.push({
                  start: 0,
                  end: grupo.intervalos[0].duracao,
                  group: groupId,
                  content: grupo.intervalos[0].duracao + 's',
                  id: groupId + 'i' + index,
                  className: 'indicacao-' + modoOperacaoService.getCssClass(0),
                  type: 'range'
                });
              } else {
                grupo.intervalos.forEach(function(intervalo, index) {
                  items.push({
                    start: initialState,
                    end: initialState + intervalo.duracao,
                    group: groupId,
                    content: intervalo.duracao + 's',
                    id: groupId + 'i' + index,
                    className: 'indicacao-' + modoOperacaoService.getCssClass(intervalo.status),
                    type: 'range'
                  });

                  initialState += intervalo.duracao;
                });
              }
            });

            var initialState = 1;
            estagios.forEach(function(estagio) {
              // Add o objeto da barra de titulos.
              items.push({
                id: 'title-id-' + estagio.id,
                content: 'E' + estagio.posicao,
                start: initialState,
                end: initialState + estagio.duracao,
                group: 'title',
                className: 'vis-title'
              });

              // Add o objeto de background.
              items.push({
                id: 'vis-background-id-' + estagio.id,
                content: '<strong>E' + estagio.posicao + '</strong>',
                start: initialState,
                end: initialState + estagio.duracao,
                type: 'background',
                className: 'vis-background-E' + estagio.posicao
              });

              initialState += estagio.duracao;
            });

            var options = {
              margin: {
                item: {
                  horizontal: 0
                },
                axis: 5
              },
              autoResize: false,
              maxMinorChars: 1,
              showCurrentTime: false,
              showMajorLabels: false,
              showMinorLabels: false,
              moveable: false,
              zoomable: false,
              type: 'range',
              start: 1,
              end: parseInt(scope.tempoCiclo)
            };
            timeline.setOptions(options);
            timeline.setGroups(groups);
            timeline.setItems(items);

            if(scope.comCheckBoxGrupo){
              initCheckboxValues();
              bindCheckboxEvents();
            }
            timeline.redraw();
          };

          scope.$watch('grupos', function(value) {
            return value && setData(scope.grupos, scope.estagios);
          }, true);
        }
      };
    }]);
