'use strict';

/**
 * @ngdoc service
 * @name influuntApp.mapaProvider
 * @description
 * # mapaProvider
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('mapaProvider', ['MAP', function mapaProvider(MAP) {
    var map;

    var DEFAULT_MAP_OPTIONS = {scrollWheelZoom: false};

    var getMap = function(element, options) {
      if (_.isObject(map)) {
        return map;
      }

      options = _.merge(_.clone(DEFAULT_MAP_OPTIONS), options);
      map = L.map(element, options);

      var tileLayer = new L.tileLayer.wms(MAP.url, MAP.options);
      tileLayer.addTo(map);

      return map;
    };

    var setView = function(latlng, zoom) {
      map.setView(latlng, zoom);
    };

   return {
    getMap: getMap,
    setView: setView
   };

  }]);
