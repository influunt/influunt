'use strict';

/**
 * @ngdoc service
 * @name influuntApp.mapaProvider
 * @description
 * # mapaProvider
 * Factory in the influuntApp.
 */

angular.module('influuntApp')
  .factory('mapaProvider', ['MAP', '$timeout', function mapaProvider(MAP, $timeout) {
    var map, markersLayer, areasLayer, agrupamentosLayer;

    // constantes
    var DEFAULT_BG_COLORS = ['#FFC107', '#FF5722', '#009688', '#4CAF50', '#3F51B5', '#D32F2F'];
    var DEFAULT_MAP_OPTIONS = {scrollWheelZoom: false};
    var DEFAULT_MARKER_OPTINS = {draggable: true};
    var DEFAULTS = {LATITUDE: -23.550382, LONGITUDE: -46.663956, ZOOM: 15};

    // funcoes de mapa.
    var initializeMap, setView, getMap;

    // funcoes de markers.
    var onMarkerClick, setOnMarkerClick;
    var addMarkers, createMarker, renderMarkers, setViewForMarkers;
    var selectMarkerById;

    // funcoes de areas
    var addAreas, createArea, getAreaTitle, renderAreas;

    // funcoes de agrupamentos
    var addAgrupamentos, createAgrupamento, renderAgrupamentos;

    // Funções gerais de mapa.
    initializeMap = function(element, options) {
      options = _.merge(_.clone(DEFAULT_MAP_OPTIONS), options);
      map = L.map(element, options);

      var tileLayer = new L.tileLayer.wms(MAP.url, MAP.options);
      tileLayer.addTo(map);

      setView([DEFAULTS.LATITUDE, DEFAULTS.LONGITUDE], DEFAULTS.ZOOM);
      return map;
    };

    getMap = function() {
      return map;
    };

    var setViewTimeout;
    setView = function(obj, zoom) {
      clearTimeout(setViewTimeout);
      setViewTimeout = setTimeout(function() {
        if (obj instanceof L.LatLngBounds) {
          map.fitBounds(obj);
        } else {
          map.setView(obj, zoom);
        }
      }, 500);
    };

    setOnMarkerClick = function(fn) {
      onMarkerClick = fn;
    };

    // Funções de markers.
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
      markers.forEach(function(marker) {
        var m = createMarker(marker);
        markersLayer.addLayer(m);
      });

      map.addLayer(markersLayer);
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
          $timeout(function() {
            var coordinates = ev.target._latlng;
            obj.latitude = coordinates.lat;
            obj.longitude = coordinates.lng;
          });
        })
        .on('click', function(ev) {
          return _.isFunction(onMarkerClick) && onMarkerClick(ev);
        });

      if (obj.options && obj.options.popupText) {
        marker.bindPopup(obj.options.popupText);
      }

      return marker;
    };

    renderMarkers = function(markers) {
      if (_.isObject(markersLayer)) { map.removeLayer(markersLayer); }

      if (_.isPlainObject(markers) && angular.isDefined(map)) { addMarkers([markers]); }

      if (_.isArray(markers) && markers.length > 0 && angular.isDefined(map)) {
        addMarkers(markers);
      }
    };

    setViewForMarkers = function() {
      if (angular.isDefined(markersLayer)) {
        var group = new L.featureGroup(markersLayer.getLayers());
        var bounds = group.getBounds();
        return bounds.isValid() && setView(bounds);
      }
    };

    // Funções de áreas.
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

    createArea = function(obj, index) {
      var options = {color: DEFAULT_BG_COLORS[index]};
      options = _.merge(options, obj.options);
      options.className = 'influunt-area';

      var points = obj.points.map(function(p) {
        return new L.LatLng(p.latitude, p.longitude);
      });

      points = L.LatLng.getHullPoints(points, Infinity);
      var area = L.polygon(points, options);
      return area;
    };

    getAreaTitle = function(area, title) {
      var labelLocation = area.getCentroid();
      return new L.LabelOverlay(
        labelLocation, '<h1><strong>' + title + '</strong></h1>'
      );
    };

    renderAreas = function(areas) {
      if (_.isArray(areas) && angular.isDefined(map)) {
        addAreas(areas);
      }
    };

    // Funções de agrupamentos.
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

      var points = L.LatLng.getLatLng(obj.points);
      points = L.LatLng.orderPoints(points);
      points = L.LatLng.getMiddlePoints(points);
      points = L.LatLng.getBoundingBox(points);
      points = L.LatLng.getHullPoints(points);

      return L.polygon(points, options);
    };

    renderAgrupamentos = function(agrupamentos) {
      if (_.isArray(agrupamentos) && angular.isDefined(map)) { addAgrupamentos(agrupamentos); }
    };

    selectMarkerById = function(id) {
      var marker = _.find(markersLayer.getLayers(), function(marker) { return marker.options.id === id; });
      if (marker) {
        setView(marker.getLatLng(), getMap().getMaxZoom());
        marker.fireEvent('click');
      }
    };

    return {
      initializeMap: initializeMap,
      getMap: getMap,
      setViewForMarkers: setViewForMarkers,

      renderMarkers: renderMarkers,
      renderAreas: renderAreas,
      renderAgrupamentos: renderAgrupamentos,

      setOnMarkerClick: setOnMarkerClick,
      selectMarkerById: selectMarkerById
     };
  }]);
