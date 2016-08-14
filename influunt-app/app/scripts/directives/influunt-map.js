'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntMap
 * @description
 * # influuntMap
 */
angular.module('influuntApp')
  .directive('influuntMap', [function() {
    return {
      restrict: 'A',
      scope: {
        markers: '=',
        areas: '='
      },
      link: function(scope, element) {
        var map, markersLayer, areasLayer;
        var TILE_LAYER = 'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw';
        var DEFAULTS = {LATITUDE: -23.550382, LONGITUDE: -46.663956, ZOOM: 15};
        var DEFAULT_MARKER_OPTINS = {draggable: true};

        // private methods.
        var initializeMap, createMarker, addMarkers, addAreas, createArea;

        initializeMap = function() {
          var options = {scrollWheelZoom: false};
          map = L.map(element[0], options).setView([DEFAULTS.LATITUDE, DEFAULTS.LONGITUDE], DEFAULTS.ZOOM);
          L.tileLayer(TILE_LAYER, {maxZoom: 20, id: 'mapbox.streets'}).addTo(map);
        };

        createMarker = function(obj) {
          if (obj.options && _.isString(obj.options.icon)) {
            obj.options.icon = L.icon({
              iconUrl: obj.options.icon
            });
          }

          var options = _.merge(DEFAULT_MARKER_OPTINS, obj.options);
          obj.latitude = obj.latitude || DEFAULTS.LATITUDE;
          obj.longitude = obj.longitude || DEFAULTS.LONGITUDE;
          var marker = L
            .marker([obj.latitude, obj.longitude], options)
            .on('dragend', function(ev) {
              scope.$apply(function() {
                var coordinates = ev.target._latlng;
                obj.latitude = coordinates.lat;
                obj.longitude = coordinates.lng;
              });
            });

          if (obj.popupText) {
            marker.bindPopup(obj.popupText);
          }

          map.setView([obj.latitude, obj.longitude]);
          return marker;
        };

        createArea = function(obj) {
          var points = obj.points.map(function(p) { return [p.latitude, p.longitude];});
          var area = L.polygon(points, obj.options);
          return area;
        };

        addAreas = function(areas) {
          if (_.isObject(areasLayer)) {
            map.removeLayer(areasLayer);
          }

          areasLayer = new L.FeatureGroup();
          areas.forEach(function(area) {
            var a = createArea(area);
            areasLayer.addLayer(a);
          });

          map.addLayer(areasLayer);
        };

        addMarkers = function(markers) {
          if (_.isObject(markersLayer)) {
            map.removeLayer(markersLayer);
          }

          markersLayer = new L.FeatureGroup();
          markers.forEach(function(marker) {
            var m = createMarker(marker);
            markersLayer.addLayer(m);
          });

          map.addLayer(markersLayer);
        };

        scope.$watch('markers', function(markers) {
          if (_.isObject(markers) && angular.isDefined(map)) {
            addMarkers([markers]);
          }

          if (_.isArray(markers) && angular.isDefined(map)) {
            addMarkers(markers);
          }
        });

        scope.$watch('areas', function(areas) {
          if (_.isArray(areas) && angular.isDefined(map)) {
            addAreas(areas);
          }
        });

        $(document).ready(initializeMap);
      }
    };
  }]);
