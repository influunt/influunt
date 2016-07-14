'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:vis
 * @description
 * # vis
 */
angular.module('influuntApp')
  .directive('vis', ['$rootScope', function vis($rootScope) {
    return {
      restrict: 'A',
      scope: {
        grupos: '=',
        estagios: '='
      },
      link: function postLink(scope) {
        var container = document.getElementById('visualization');

        var setData = function(timeline, grupos, estagios) {
          var groupsData = [];
          var itemsData = [];
          var max = 0;

          grupos.forEach(function(g) {
            var groupId = 'G' + g.posicao;
            groupsData.push({
              content: groupId,
              id: groupId,
              value: groupId
            });

            var initialState = {};
            estagios.forEach(function(e, i) {
              initialState[groupId] = initialState[groupId] || 0;
              var step = 10 * (1 + Math.ceil(Math.random() * 10) % 4);

              itemsData.push({
                start: initialState[groupId],
                end: initialState[groupId] + step,
                group: groupId,
                className: 'E' + e.posicao,
                content: step + 's',
                id: g.posicao + 'i' + (i + 1)
              });

              initialState[groupId] += step;
              max = Math.max(max, initialState[groupId]);
            });
          });

          var options = {
            margin: {
              item: {
                horizontal: 0
              },
              axis: 5
            },
            autoResize: true,
            maxMinorChars: 1,
            showCurrentTime: false,
            showMajorLabels: false,
            showMinorLabels: false,
            moveable: false,
            zoomable: false,
            type: 'range',
            start: 0,
            end: max
          };

          timeline.setOptions(options);
          timeline.setGroups(groupsData);
          timeline.setItems(itemsData);
        };

        var timeline = new window.vis.Timeline(container);

        scope.$watch('grupos', function(value) {
          if (value) {
            setData(timeline, scope.grupos, scope.estagios);
          }
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
