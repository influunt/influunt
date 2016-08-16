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
        areas: '=',
        options: '='
      },
      link: function(scope, element) {
        var map, markersLayer, areasLayer;
        var TILE_LAYER = 'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw';
        var DEFAULTS = {LATITUDE: -23.550382, LONGITUDE: -46.663956, ZOOM: 15};
        var DEFAULT_MARKER_OPTINS = {draggable: true};
        var DEFAULT_MAP_OPTIONS = {scrollWheelZoom: false};
        var DEFAULT_BG_COLORS = ['#FFC107', '#FF5722', '#009688', '#4CAF50', '#3F51B5', '#D32F2F'];

        // private methods.
        var initializeMap, createMarker, addMarkers, addAreas, createArea;

        initializeMap = function() {
          if (_.isObject(map)) {
            return true;
          }

          var options = _.merge(_.clone(DEFAULT_MAP_OPTIONS), scope.options);
          map = L.map(element[0], options).setView([DEFAULTS.LATITUDE, DEFAULTS.LONGITUDE], DEFAULTS.ZOOM);
          L.tileLayer(TILE_LAYER, {maxZoom: 20, id: 'mapbox.streets'}).addTo(map);
        };

        createMarker = function(obj) {
          if (obj.options && _.isString(obj.options.icon)) {
            obj.options.icon = L.icon({
              iconUrl: obj.options.icon,
              iconSize: obj.options.iconSize,
              iconAnchor: obj.options.iconAnchor,
              popupAnchor: obj.options.popupAnchor,
              className: obj.options.className
            });
          }

          var options = _.merge(_.clone(DEFAULT_MARKER_OPTINS), obj.options);
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

        createArea = function(obj, index) {
          var options = {color: DEFAULT_BG_COLORS[index]};
          options = _.merge(options, obj.options);
          var points = obj.points.map(function(p) { return [p.latitude, p.longitude];});
          var area = L.polygon(points, options);

          if (obj.popupText) {
            area.bindPopup(obj.popupText);
          }
          return area;
        };

        addAreas = function(areas) {
          if (_.isObject(areasLayer)) {
            map.removeLayer(areasLayer);
          }

          areasLayer = new L.FeatureGroup();
          areas.forEach(function(area, index) {
            var a = createArea(area, index);
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
          initializeMap();
          if (_.isObject(markersLayer)) {
            map.removeLayer(markersLayer);
          }

          if (_.isPlainObject(markers) && angular.isDefined(map)) {
            addMarkers([markers]);
          }

          if (_.isArray(markers) && markers.length > 0 && angular.isDefined(map)) {
            addMarkers(markers);
          }
        }, true);

        scope.$watch('areas', function(areas) {
          initializeMap();

          if (_.isArray(areas) && angular.isDefined(map)) {
            addAreas(areas);
          }
        }, true);
      }
    };
  }]);
