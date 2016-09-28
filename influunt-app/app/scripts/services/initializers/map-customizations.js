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

      var lat1 = this.lat.toRad(), lon1 = this.lng.toRad();

      var lat2 = Math.asin(Math.sin(lat1) * Math.cos(dist) +
                 Math.cos(lat1) * Math.sin(dist) * Math.cos(brng));

      var lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(dist) *
                        Math.cos(lat1),
                        Math.cos(dist) - Math.sin(lat1) *
                        Math.sin(lat2));

      if (isNaN(lat2) || isNaN(lon2)) {
        return null;
      }

      return new L.LatLng(lat2.toDeg(), lon2.toDeg());
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
