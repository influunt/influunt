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
        onClickMarker: '&?',
        mapId: '=?'
      },
      link: function(scope, element, attrs) {
        scope.mapId = attrs.mapaId || UUID.generate();
        var _mapaProvider = mapaProvider.getMap(scope.mapId);

        _mapaProvider.initializeMap(element[0], scope.options);

        var markersTimeout, areasTimeout, agrupamentosTimeout;

        scope.$watch('areas', function(areas, oldAreas) {
          clearTimeout(areasTimeout);
          areasTimeout = setTimeout(function() {
            _mapaProvider.renderAreas(areas);
            return _.size(areas) !== _.size(oldAreas) && _mapaProvider.setViewForMarkers();
          }, 200);
        });

        scope.$watch('agrupamentos', function(agrupamentos, oldAgrupamentos) {
          clearTimeout(agrupamentosTimeout);
          agrupamentosTimeout = setTimeout(function() {
            _mapaProvider.renderAgrupamentos(agrupamentos);
            return _.size(agrupamentos) !== _.size(oldAgrupamentos) && _mapaProvider.setViewForMarkers();
          }, 200);
        });

        var handleManyMarkers = function(markers, oldMarkers) {
          _mapaProvider.renderMarkers(markers);
          return _.size(markers) !== _.size(oldMarkers) && _mapaProvider.setViewForMarkers();
        };

        var handleSingleMarker = function(marker, oldMarker) {
          _mapaProvider.renderMarkers(marker);

          var shouldSetView = _.get(marker, 'id') !== _.get(oldMarker, 'id') ||
                              _.get(marker, 'latitude') !== _.get(oldMarker, 'latitude') ||
                              _.get(marker, 'longitude') !== _.get(oldMarker, 'longitude');

          return shouldSetView && _mapaProvider.setViewForMarkers();
        };

        scope.$watch('markers', function(markers, oldMarkers) {
          clearTimeout(markersTimeout);
          markersTimeout = setTimeout(function() {
            if (_.isPlainObject(markers)) {
              handleSingleMarker(markers, oldMarkers);
            }

            if (_.isArray(markers)) {
              handleManyMarkers(markers, oldMarkers);
            }
          }, 500);
        }, true);

        // scope.$watch('markers', function(marker, oldMarker) {
        //   if (_.isPlainObject(marker)) {
        //     clearTimeout(markersTimeout);
        //     markersTimeout = setTimeout(function() {
        //       _mapaProvider.renderMarkers(marker);

        //       var shouldSetView = _.get(marker, 'id') !== _.get(oldMarker, 'id') ||
        //                           _.get(marker, 'latitude') !== _.get(oldMarker, 'latitude') ||
        //                           _.get(marker, 'longitude') !== _.get(oldMarker, 'longitude');

        //       return shouldSetView && _mapaProvider.setViewForMarkers();
        //     }, 200);
        //   }
        // }, true);

        // scope.$watch('markers', function(markers, oldMarkers) {
        //   if (_.isArray(markers)) {
        //     clearTimeout(markersTimeout);
        //     markersTimeout = setTimeout(function() {
        //       _mapaProvider.renderMarkers(markers);
        //       return _.size(markers) !== _.size(oldMarkers) && _mapaProvider.setViewForMarkers();
        //     }, 200);
        //   }
        // });

        scope.$on('$destroy', function() {
          mapaProvider.destroyMap(scope.mapId);
        });

        _mapaProvider.setOnMarkerClick(function(ev) {
          var markerData = ev.target.options;
          return angular.isFunction(scope.onClickMarker) &&
            scope.onClickMarker({$markerData: markerData});
        });
      }
    };
  }]);

