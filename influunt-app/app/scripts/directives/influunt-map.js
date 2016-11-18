'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntMap
 * @description
 * # influuntMap
 */
angular.module('influuntApp')
  .directive('influuntMap', ['mapaProvider', function(mapaProvider) {
    return {
      restrict: 'A',
      scope: {
        markers: '=?',
        areas: '=?',
        agrupamentos: '=?',
        options: '=?',
        onClickMarker: '&?'
      },
      link: function(scope, element) {
        mapaProvider.initializeMap(element[0], scope.options);

        var markersTimeout, areasTimeout, agrupamentosTimeout;

        scope.$watch('areas', function(areas, oldAreas) {
          clearTimeout(areasTimeout);
          areasTimeout = setTimeout(function() {
            mapaProvider.renderAreas(areas);
            return _.size(areas) !== _.size(oldAreas) && mapaProvider.setViewForMarkers();
          }, 200);
        });

        scope.$watch('agrupamentos', function(agrupamentos, oldAgrupamentos) {
          clearTimeout(agrupamentosTimeout);
          agrupamentosTimeout = setTimeout(function() {
            mapaProvider.renderAgrupamentos(agrupamentos);
            return _.size(agrupamentos) !== _.size(oldAgrupamentos) && mapaProvider.setViewForMarkers();
          }, 200);
        });

        scope.$watch('markers', function(markers, oldMarkers) {
          clearTimeout(markersTimeout);
          markersTimeout = setTimeout(function() {
            mapaProvider.renderMarkers(markers);
            return _.size(markers) !== _.size(oldMarkers) && mapaProvider.setViewForMarkers();
          }, 200);
        });

        mapaProvider.setOnMarkerClick(function(ev) {
          var markerData = ev.target.options;
          return angular.isFunction(scope.onClickMarker) &&
            scope.onClickMarker({$markerData: markerData});
        });
      }
    };
  }]);

