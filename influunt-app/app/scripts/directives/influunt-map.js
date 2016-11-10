'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntMap
 * @description
 * # influuntMap
 */
angular.module('influuntApp')
  .directive('influuntMap', ['MAP', function(MAP) {
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

        var DEFAULTS = {LATITUDE: -23.550382, LONGITUDE: -46.663956, ZOOM: 15};
        var DEFAULT_MARKER_OPTINS = {draggable: true};
        var DEFAULT_MAP_OPTIONS = {scrollWheelZoom: false};
        var DEFAULT_BG_COLORS = ['#FFC107', '#FF5722', '#009688', '#4CAF50', '#3F51B5', '#D32F2F'];
        var BOUNDING_BOX_SIZE = 10.0 / 1000; // unidade: km's.
        var BOUNDING_BOX_VARIATION = 90;    // intervalo (em graus) da variacao do bounding box.
        var HULL_CONCAVITY = 0.0013;
        var DISTANCE_BETWEEN_POINTS = 100;

        // private methods.
        var addAgrupamentos, addAreas, addMarkers, agrupaAneis, createAgrupamento, createArea, createMarker,
            getAreaTitle, getBoundingBox, getMiddlePoints, getHullPoints, initializeMap, renderAgrupamentos, renderAreas,
            renderMarkers, setView, setViewForArea, orderPoints, getLatLng;
        var map, markersLayer, areasLayer, agrupamentosLayer, polylineLayer;

        initializeMap = function() {
          if (_.isObject(map)) {
            return true;
          }

          var options = _.merge(_.clone(DEFAULT_MAP_OPTIONS), scope.options);
          map = L.map(element[0], options);
          map.setView([DEFAULTS.LATITUDE, DEFAULTS.LONGITUDE], DEFAULTS.ZOOM);

          var tileLayer = new L.tileLayer.wms(MAP.url, MAP.options);
          tileLayer.addTo(map);
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

          setView([obj.latitude, obj.longitude]);
          return marker;
        };

        createArea = function(obj, index) {
          var options = {color: DEFAULT_BG_COLORS[index]};
          options = _.merge(options, obj.options);
          options.className = 'influunt-area';

          var points = obj.points.map(function(p) {
            return new L.LatLng(p.latitude, p.longitude);
          });

          points = getHullPoints(points, Infinity);
          var area = L.polygon(points, options);
          return area;
        };

        getAreaTitle = function(area, title) {
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
            dashArray: [20, 10],
            className: 'influunt-agrupamento'
          };

          options = _.merge(options, obj.options);

          var points = getLatLng(obj.points);
          points = orderPoints(points);
          points = getMiddlePoints(points);
          points = getBoundingBox(points);
          points = getHullPoints(points);

          return L.polygon(points, options);
        };

        addMarkers = function(markers) {
          if (_.isObject(markersLayer)) {
            map.removeLayer(markersLayer);
          }

          // Remove o primeiro anel do mapa, caso o controlador de mesmo endereço seja exibido.
          markers = _
            .chain(markers)
            .orderBy('options.tipo', ['desc'])
            .uniqBy(function(m) { return m.latitude + '' + m.longitude; })
            .value();

          markersLayer = new L.markerClusterGroup();
          // markersLayer = new L.FeatureGroup();
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
            if (a.getLatLngs().length > 0) {
              areasLayer.addLayer(a);
              areasLayer.addLayer(getAreaTitle(a, area.label));
            }
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

        getHullPoints = function(points, hullConcavity) {
          hullConcavity = hullConcavity || HULL_CONCAVITY;
          return hull(points, hullConcavity, ['.lat', '.lng']);
        };

        orderPoints = function(tail, head) {
          tail = _.cloneDeep(tail);
          head = _.cloneDeep(head);
          if (!head) {
            tail = _.orderBy(tail, ['lat', 'lng']);
            head = tail.shift();
          }

          tail = tail.sort(function(a, b) { return head.distanceTo(a) - head.distanceTo(b); });
          var nearest = tail.shift();

          if (tail.length > 0) {
            return _.concat([head], orderPoints(tail, nearest));
          } else {
            return [head, nearest];
          }
        };

        getLatLng = function(points) {
          return points.map(function(point) { return new L.LatLng(point.latitude, point.longitude); });
        };

        /**
         * Cria pontos entre os pontos originais do agrupamento. O objetivo deste método é garantir que o contorno dos
         * agrupamentos fique sempre na mesma espessura, inclusive em espaços onde não há pontos desenhados.
         *
         * @param      {<type>}  points  The points
         */
        getMiddlePoints = function(points) {
          var middlePoints = [];
          for (var i = 0; i < points.length - 1; i++) {
            var lat1 = points[i];
            var lat2 = points[i + 1];
            middlePoints = _.concat(middlePoints, L.LatLng.getPointsBetween(lat1, lat2, DISTANCE_BETWEEN_POINTS));
            middlePoints.push(lat1);
            middlePoints.push(lat2);
          }

          return middlePoints;
        };

        getBoundingBox = function(points) {
          var boxedPoints = [];
          _.each(points, function(point) {
            // var point = new L.LatLng(point.latitude, point.longitude);
            boxedPoints.push(point);
            for (var i = 0; i < 360; i += BOUNDING_BOX_VARIATION) {
              boxedPoints.push(point.destinationPoint(i, BOUNDING_BOX_SIZE));
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

        var markersTimeout, areasTimeout, agrupamentosTimeout;
        renderMarkers = function(markers) {
          if (_.isObject(markersLayer)) { map.removeLayer(markersLayer); }

          if (_.isPlainObject(markers) && angular.isDefined(map)) { addMarkers([markers]); }

          if (_.isArray(markers) && markers.length > 0 && angular.isDefined(map)) {
            addMarkers(markers);
            // agrupaAneis(markers);
          }
        };

        renderAreas = function(areas) {
          if (_.isArray(areas) && angular.isDefined(map)) {
            addAreas(areas);
            setViewForArea();
          }
        };

        renderAgrupamentos = function(agrupamentos) {
          if (_.isArray(agrupamentos) && angular.isDefined(map)) { addAgrupamentos(agrupamentos); }
        };

        setViewForArea = function() {
          if (angular.isDefined(areasLayer)) {
            var layers = _.filter(areasLayer.getLayers(), 'getBounds');
            var group = new L.featureGroup(layers);
            var bounds = group.getBounds();
            return bounds.isValid() && map.fitBounds(bounds);
          }
        };

        var setViewTimeout;
        setView = function(latlng, zoom) {
          clearTimeout(setViewTimeout);
          setViewTimeout = setTimeout(function() {
            return angular.isDefined(map) && map.setView(latlng, zoom);
          }, 500);
        };

        scope.$watch('markers', function(markers) {
          clearTimeout(markersTimeout);
          markersTimeout = setTimeout(function() {renderMarkers(markers);}, 200);
        }, true);

        scope.$watch('areas', function(areas) {
          clearTimeout(areasTimeout);
          areasTimeout = setTimeout(function() {renderAreas(areas);}, 200);
        }, true);

        scope.$watch('agrupamentos', function(agrupamentos) {
          clearTimeout(agrupamentosTimeout);
          agrupamentosTimeout = setTimeout(function() {renderAgrupamentos(agrupamentos);}, 200);
        }, true);

        initializeMap();
      }
    };
  }]);
