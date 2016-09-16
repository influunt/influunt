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
        markers: '=?',
        areas: '=?',
        subareas: '=?',
        options: '=?',
        onClickMarker: '&?'
      },
      link: function(scope, element) {
        L.Icon.Default.imagePath = 'images/leaflet';

        var TILE_LAYER = 'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw';
        var DEFAULTS = {LATITUDE: -23.550382, LONGITUDE: -46.663956, ZOOM: 15};
        var DEFAULT_MARKER_OPTINS = {draggable: true};
        var DEFAULT_MAP_OPTIONS = {scrollWheelZoom: false};
        var DEFAULT_BG_COLORS = ['#FFC107', '#FF5722', '#009688', '#4CAF50', '#3F51B5', '#D32F2F'];
        var BOUNDING_BOX_SIZE = 10.0 / 1000; // unidade: km's.
        var BOUNDING_BOX_VARIATION = 15;    // intervalo (em graus) da variacao do bounding box.
        var HULL_CONCAVITY = 29;

        // private methods.
        var addAreas, addMarkers, addSubareas, createArea, createMarker,
            createSubarea, getConcaveHullPoints, getBoundingBox, initializeMap;
        var map, markersLayer, areasLayer, subareasLayer;

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
            })
            .on('click', function(ev) {
              var markerData = ev.target.options;
              return angular.isFunction(scope.onClickMarker) &&
                scope.onClickMarker({$markerData: markerData});
            });

          map.setView([obj.latitude, obj.longitude]);
          return marker;
        };

        createArea = function(obj, index) {
          var options = {color: DEFAULT_BG_COLORS[index]};
          options = _.merge(options, obj.options);
          var points = obj.points.map(function(p) { return [p.latitude, p.longitude];});
          var area = L
            .polygon(points, options);

          if (obj.popupText) {
            area.bindPopup(obj.popupText);
          }
          return area;
        };

        createSubarea = function(obj) {
          var options = {
            color: '#000',
            fill: false,
            dashArray: [20, 10]
          };

          options = _.merge(options, obj.options);

          var points = getBoundingBox(obj.points);
          points = getConcaveHullPoints(points);
          var subarea = L.polygon(points, options);

          if (obj.popupText) {
            subarea.bindPopup(obj.popupText);
          }
          return subarea;
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

        addSubareas = function(subareas) {
          if (_.isObject(subareasLayer)) {
            map.removeLayer(subareasLayer);
          }

          subareasLayer = new L.FeatureGroup();
          subareas.forEach(function(subarea, index) {
            var a = createSubarea(subarea, index);
            subareasLayer.addLayer(a);
          });

          map.addLayer(subareasLayer);
        };

        getConcaveHullPoints = function(points) {
          points = _.map(points, function(i) { return {lat: i.lat, lng: i.lng}; });
          var pts = points.map(map.latLngToContainerPoint.bind(map));

          pts = hull(pts, HULL_CONCAVITY, ['.x', '.y']);
          pts = pts.map(function(pt) {
            return map.containerPointToLatLng(L.point(pt.x, pt.y));
          });

          return pts;
        };

        getBoundingBox = function(points) {
          var boxedPoints = [];
          _.each(points, function(point) {
            var thisPoint = new L.LatLng(point.latitude, point.longitude);
            boxedPoints.push(thisPoint);
            for (var i = 0; i < 360; i += BOUNDING_BOX_VARIATION) {
              boxedPoints.push(thisPoint.destinationPoint(i, BOUNDING_BOX_SIZE));
            }
          });

          return boxedPoints;
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

        scope.$watch('subareas', function(subareas) {
          initializeMap();

          if (_.isArray(subareas) && angular.isDefined(map)) {
            addSubareas(subareas);
          }
        }, true);
      }
    };
  }]);
