'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:influuntMap
 * @description
 * # influuntMap
 */
angular.module('influuntApp')
  .directive('influuntMap', ['$timeout', function ($timeout) {
    return {
      restrict: 'A',
      scope: {
        latitude: '=',
        longitude: '='
      },
      link: function (scope, element) {
        var map = null;
        var marker = null;

        /**
         * Defaults:
         *  posicao: marco zero de São Paulo;
         *  zoom: 15
         *
         * @type       {<type>}
         */
        var DEFAULTS = {
          LATITUDE: -23.550382,
          LONGITUDE: -46.663956,
          ZOOM: 15
        };

        /**
         * Cria um novo marker em determinada posicao.
         *
         * @param      {<type>}    latitude   The latitude
         * @param      {<type>}    longitude  The longitude
         * @return     {Function}  { description_of_the_return_value }
         */
        var createMarker = function(latitude, longitude) {
          var options = {
            draggable: true
          };

          marker = L.marker([latitude, longitude], options)
            .addTo(map)
            .on('dragend', function(ev) {
              $timeout(function() {
                var coordinates = ev.target._latlng;
                scope.latitude = coordinates.lat;
                scope.longitude = coordinates.lng;
              });
            });

          return marker;
        };

        /**
         * Inicializa o componente de mapa. Este deverá ser criado com a primeira view apontando para o
         * ponto default declarado nas constantes acima.
         */
        var initializeMap = function() {
          map = L.map(element[0]).setView([DEFAULTS.LATITUDE, DEFAULTS.LONGITUDE], DEFAULTS.ZOOM);
          createMarker(DEFAULTS.LATITUDE, DEFAULTS.LONGITUDE);

          L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
            maxZoom: 20,
            attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
              '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
              'Imagery © <a href="http://mapbox.com">Mapbox</a>',
            id: 'mapbox.streets'
          }).addTo(map);
        };

        /**
         * Atualiza as coordenadas do mapa.
         */
        var updateCoordinates = function() {
          if (marker) {
            map.removeLayer(marker);
          }

          var latitude = scope.latitude || DEFAULTS.LATITUDE;
          var longitude = scope.longitude || DEFAULTS.LONGITUDE;

          map.setView([latitude, longitude]);
          createMarker(latitude, longitude);
        };

        scope.$watchGroup(['latitude', 'longitude'], function(value) {
          return value && map && updateCoordinates();
        });

        $(document).ready(initializeMap);
      }
    };
  }]);
