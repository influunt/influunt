'use strict';

/**
 * @ngdoc service
 * @name influuntApp.mapCustomizations
 * @description
 * # mapCustomizations
 * Inicializa customizações nas bibliotecas de mapas.
 */
angular.module('influuntApp')
  .run(function () {
    var getArea, getXCentroid, getYCentroid;

    var BOUNDING_BOX_SIZE = 10.0 / 1000; // unidade: km's.
    var BOUNDING_BOX_VARIATION = 90;    // intervalo (em graus) da variacao do bounding box.
    var DISTANCE_BETWEEN_POINTS = 100;
    var HULL_CONCAVITY = 0.0013;

    Number.prototype.toRad = function() { return this * Math.PI / 180; };
    Number.prototype.toDeg = function() { return this * 180 / Math.PI; };

    L.Icon.Default.imagePath = 'images/leaflet';
    L.LatLng.prototype.destinationPoint = function(brng, dist) {
      dist = dist / 6371;
      brng = brng.toRad();

      var lat1 = this.lat.toRad(), lng1 = this.lng.toRad();

      var lat2 = Math.asin(Math.sin(lat1) * Math.cos(dist) +
                 Math.cos(lat1) * Math.sin(dist) * Math.cos(brng));

      var lon2 = lng1 + Math.atan2(Math.sin(brng) * Math.sin(dist) *
                        Math.cos(lat1),
                        Math.cos(dist) - Math.sin(lat1) *
                        Math.sin(lat2));

      if (isNaN(lat2) || isNaN(lon2)) {
        return null;
      }

      return new L.LatLng(lat2.toDeg(), lon2.toDeg());
    };

    /**
     * http://stackoverflow.com/questions/4656802/midpoint-between-two-latitude-and-longitude
     *
     * @param      {<type>}  latLng  The lat lng
     */
    L.LatLng.prototype.getMidPointTo = function(latLng) {
      var lng = (latLng.lng - this.lng).toRad();

      var lat1 = this.lat.toRad();
      var lng1 = this.lng.toRad();
      var lat2 = latLng.lat.toRad();

      var bx = Math.cos(lat2) * Math.cos(lng);
      var by = Math.cos(lat2) * Math.sin(lng);

      var lat3 = Math.atan2(
        Math.sin(lat1) + Math.sin(lat2),
        Math.sqrt((Math.cos(lat1) + bx) * (Math.cos(lat1) + bx) + by * by)
      );
      var lng3 = lng1 + Math.atan2(by, Math.cos(lat1) + bx);

      return new L.LatLng(lat3.toDeg(), lng3.toDeg());
    };


    /**
     * retorna a centroide do poligono. Formula utilizada disponivel em:
     * http://dan-scientia.blogspot.com.br/2009/10/centroide-de-um-poligono.html
     */
    L.Polygon.prototype.getCentroid = function () {
      var latLngList = this.getLatLngs();
      latLngList.push(latLngList[0]);
      var area = getArea(latLngList);
      return new L.LatLng(getXCentroid(latLngList, area), getYCentroid(latLngList, area));
    };

    /**
     * Retorna a lista contedo os pontos entre dois pontos a cada intervalo de distancia (em metros);
     *
     * @param      {<type>}  lat1         The lat 1
     * @param      {<type>}  lat2         The lat 2
     * @param      {number}  maxDistance  The maximum distance
     * @return     {<type>}  The points between.
     */
    L.LatLng.getPointsBetween = function(lat1, lat2, maxDistance) {
      var points = [];

      var midPoint = lat1.getMidPointTo(lat2);
      if (lat1.distanceTo(lat2) > maxDistance) {
        points.push(L.LatLng.getPointsBetween(lat1, midPoint, maxDistance));
        points.push(L.LatLng.getPointsBetween(midPoint, lat2, maxDistance));
      } else {
        points.push(midPoint);
      }

      return _.flatten(points);
    };

    /**
     * Retorna a area ao redor de determinados pontos do mapa.
     *
     * @param      {<type>}  points  The points
     * @return     {Array}   The bounding box.
     */
    L.LatLng.getBoundingBox = function(points) {
      var boxedPoints = [];
      _.each(points, function(point) {
        boxedPoints.push(point);
        for (var i = 0; i < 360; i += BOUNDING_BOX_VARIATION) {
          boxedPoints.push(point.destinationPoint(i, BOUNDING_BOX_SIZE));
        }
      });

      return boxedPoints;
    };

    /**
     * Retorna os pontos necessários para obter a area ao redor de poontos.
     *
     * @param      {<type>}  points         The points
     * @param      {<type>}  hullConcavity  The hull concavity
     * @return     {<type>}  The hull points.
     */
    L.LatLng.getHullPoints = function(points, hullConcavity) {
      hullConcavity = hullConcavity || HULL_CONCAVITY;
      return hull(points, hullConcavity, ['.lat', '.lng']);
    };

    /**
     * Converte um array de hashes que contenha lat lng em um array de L.LatLng.
     *
     * @param      {<type>}  points  The points
     * @return     {<type>}  The lat lng.
     */
    L.LatLng.getLatLng = function(points) {
      return points.map(function(point) { return new L.LatLng(point.latitude, point.longitude); });
    };

    /**
     * Cria pontos entre os pontos originais do agrupamento. O objetivo deste método é garantir que o contorno dos
     * agrupamentos fique sempre na mesma espessura, inclusive em espaços onde não há pontos desenhados.
     *
     * @param      {<type>}  points  The points
     */
    L.LatLng.getMiddlePoints = function(points) {
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

    /**
     * Ordena os pontos por proximidade.
     *
     * @param      {<type>}  tail    The tail
     * @param      {<type>}  head    The head
     * @return     {Array}   { description_of_the_return_value }
     */
    L.LatLng.orderPoints = function(tail, head) {
      tail = _.cloneDeep(tail);
      head = _.cloneDeep(head);
      if (!head) {
        tail = _.orderBy(tail, ['lat', 'lng']);
        head = tail.shift();
      }

      tail = tail.sort(function(a, b) { return head.distanceTo(a) - head.distanceTo(b); });
      var nearest = tail.shift();

      if (tail.length > 0) {
        return _.concat([head], L.LatLng.orderPoints(tail, nearest));
      } else {
        return [head, nearest];
      }
    };

    L.LabelOverlay = L.Class.extend({
      initialize: function(latLng, label, options) {
        this._latlng = latLng;
        this._label = label;
        L.Util.setOptions(this, options);
      },
      options: {
        offset: new L.Point(0, 2)
      },
      onAdd: function(map) {
        this._map = map;
        if (!this._container) {
          this._initLayout();
        }
        map.getPanes().overlayPane.appendChild(this._container);
        this._container.innerHTML = this._label;
        map.on('viewreset', this._reset, this);
        this._reset();
      },
      onRemove: function(map) {
        map.getPanes().overlayPane.removeChild(this._container);
        map.off('viewreset', this._reset, this);
      },
      _reset: function() {
        var pos = this._map.latLngToLayerPoint(this._latlng);
        var op = new L.Point(pos.x + this.options.offset.x, pos.y - this.options.offset.y);
        L.DomUtil.setPosition(this._container, op);
      },
      _initLayout: function() {
        this._container = L.DomUtil.create('div', 'leaflet-label-overlay');
      }
  });

  getArea = function(pts) {
    var area = 0;
    for (var i = 0; i < pts.length - 1; i++) {
      area += (pts[i].lat * pts[i+1].lng) - (pts[i+1].lat * pts[i].lng);
    }

    area = area / 2;
    return area;
  };
  getXCentroid = function(pts, area) {
    var x = 0;
    for (var i = 0; i < pts.length - 1; i++) {
      x += (pts[i].lat + pts[i + 1].lat) * ((pts[i].lat*pts[i+1].lng) - (pts[i+1].lat*pts[i].lng));
    }

    x = x / (6*area);
    return x;
  };
  getYCentroid = function(pts, area) {
    var y = 0;
    for (var i = 0; i < pts.length - 1; i++) {
      y += (pts[i].lng + pts[i + 1].lng) * ((pts[i].lat*pts[i+1].lng) - (pts[i+1].lat*pts[i].lng));
    }

    y = y / (6*area);
    return y;
  };
});
