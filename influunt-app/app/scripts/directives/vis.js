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
          tempoCiclo: '='
        },
        link: function postLink(scope) {
          var container = document.getElementById('visualization');
          var timeline = new window.vis.Timeline(container);
          var bindCheckboxEvents = function() {
            $('.group-checkbox').on('change', function() {
              var checkbox = $(this);
              var grupo = _.find(scope.grupos, {posicao: checkbox.data('posicao')});
              grupo.ativo = checkbox.is(':checked');
            });
          };

          var setData = function(grupos, estagios) {
            var groups = [];
            var items = [];

            groups.push({id: 'title', content: '&nbsp;'});
            grupos.forEach(function(grupo) {
              var groupId = 'G' + grupo.posicao;
              groups.push({
                content: '<input type="checkbox" class="group-checkbox" data-posicao="' + grupo.posicao + '" icheks name="' + groupId + '"><strong>' + groupId + '</strong>',
                id: groupId,
                value: groupId
              });

              var initialState = 0;
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
            });

            var initialState = 0;
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
            timeline.redraw();
            bindCheckboxEvents();
          };

          scope.$watch('grupos', function(value) {
            return value && setData(scope.grupos, scope.estagios);
          }, true);

          timeline.on('click', function(props) {
            if ('group' in props && 'item' in props) {
              var g = parseInt(props.group.replace('G', ''));
              var i = parseInt(props.item.split('i')[1]);
              $rootScope.$broadcast('item-double-clicked', {
                group: g,
                item: i
              });
            }

            props.event.preventDefault();
          });
        }
      };
    }]);
