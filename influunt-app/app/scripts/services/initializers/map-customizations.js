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

    Number.prototype.toRad = function() { return this * Math.PI / 180; };
    Number.prototype.toDeg = function() { return this * 180 / Math.PI; };

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
      var lng2 = latLng.lng.toRad();

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
