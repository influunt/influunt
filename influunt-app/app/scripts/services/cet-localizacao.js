'use strict';

/**
 * @ngdoc service
 * @name influuntApp.CETLocalizacao
 * @description
 * # CETLocalizacao
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('CETLocalizacaoService', ['$http', '$filter', 'PLACES_API', function CETLocalizacaoService($http, $filter, PLACES_API) {
    var URL = PLACES_API.baseUrl + '/latloninexato';

    var getLatLng = function(endereco) {
      return $http
        .get(URL + '/' + endereco)
        .then(function(response) {
          return {
            lat: response.data.Ponto.y,
            lng: response.data.Ponto.x
          };
        });
    };

    var atualizaLatLngPorEndereco = function(currentEndereco, prevVal) {
      var endereco = $filter('nomeEndereco')(currentEndereco, 'plain');
      var prevEndereco = $filter('nomeEndereco')(prevVal, 'plain');

      if ((!!prevEndereco || !_.get(currentEndereco, 'id')) && endereco !== prevEndereco) {
        return getLatLng(endereco)
          .then(function(response) {
            if (response.lat && response.lng) {
              currentEndereco.latitude = response.lat;
              currentEndereco.longitude = response.lng;
            }
          });
      }
    };

    return {
      getLatLng: getLatLng,
      atualizaLatLngPorEndereco: atualizaLatLngPorEndereco
    };
  }]);
