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
  });
