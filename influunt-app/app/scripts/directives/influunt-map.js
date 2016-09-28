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
        agrupamentos: '=?',
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
        var HULL_CONCAVITY = 0.0013;

        // private methods.
        var addAreas, addMarkers, addAgrupamentos, createArea, createMarker,
            createAgrupamento, getConcaveHullPoints, getBoundingBox, initializeMap, agrupaAneis;
        var map, markersLayer, areasLayer, agrupamentosLayer, polylineLayer;

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

          var points = obj.points.map(function(p) {
            return new L.LatLng(p.latitude, p.longitude);
          });

          var area = L.polygon(points, options);
          return area;
        };

        var getAreaTitle = function(area, title) {
          var labelLocation = area.getCentroid();
          return new L.LabelOverlay(
            labelLocation, '<h1><strong>' + title + '</strong></h1>'
          );
        };

        createAgrupamento = function(obj) {
          var colors = {
            ROTA: '#555',
            CORREDOR: '#2196f3',
            SUBAREA: '#4caf50'
          };

          var options = {
            color: colors[obj.type],
            fill: false,
            opacity: 1,
            weight: 4,
            dashArray: [20, 10]
          };

          options = _.merge(options, obj.options);
          var points = getBoundingBox(obj.points);
          points = getConcaveHullPoints(points);
          var agrupamento = L.polygon(points, options);

          return agrupamento;
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
            areasLayer.addLayer(getAreaTitle(a, area.label));
          });

          // Forces zoom when double clicking in an area.
          areasLayer.on('dblclick', function() {
            var zoom = map.getZoom();
            map.setZoom(zoom + 1);
          });

          map.addLayer(areasLayer);
        };

        addAgrupamentos = function(agrupamentos) {
          if (_.isObject(agrupamentosLayer)) {
            map.removeLayer(agrupamentosLayer);
          }

          agrupamentosLayer = new L.FeatureGroup();
          agrupamentos.forEach(function(agrupamento, index) {
            var a = createAgrupamento(agrupamento, index);
            agrupamentosLayer.addLayer(a);
          });

          map.addLayer(agrupamentosLayer);
        };

        getConcaveHullPoints = function(points) {
          return hull(points, HULL_CONCAVITY, ['.lat', '.lng']);
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

        agrupaAneis = function(markers) {
          if (_.isObject(polylineLayer)) {
            map.removeLayer(polylineLayer);
          }

          polylineLayer = new L.FeatureGroup();
          var controladores = _
            .chain(markers)
            .filter('options.controladorId')
            .groupBy('options.controladorId')
            .value();

          _.each(controladores, function(aneis) {
            aneis = _.map(aneis, function(anel) { return new L.LatLng(anel.latitude, anel.longitude); });
            var polyline = L.polyline(aneis, {color: 'red', weight: 2, dashArray: [20, 10]});
            polylineLayer.addLayer(polyline);
          });

          map.addLayer(polylineLayer);
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
            agrupaAneis(markers);
          }
        }, true);

        scope.$watch('areas', function(areas) {
          initializeMap();

          if (_.isArray(areas) && angular.isDefined(map)) {
            addAreas(areas);
          }
        }, true);

        scope.$watch('agrupamentos', function(agrupamentos) {
          initializeMap();

          if (_.isArray(agrupamentos) && angular.isDefined(map)) {
            addAgrupamentos(agrupamentos);
          }
        }, true);
      }
    };
  }]);
