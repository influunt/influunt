'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:helperEndereco
 * @description
 * # helperEndereco
 */
angular.module('influuntApp')
  .directive('helperEndereco', function () {
    return {
      templateUrl: 'views/directives/helper-endereco.html',
      restrict: 'E',
      scope: {
        latitude: '=',
        longitude: '=',
        localizacao: '=',
        ngModel: '='
      },
      link: function postLink(scope) {

        scope.mapOptions = {
          language: 'pt',
          location: new google.maps.LatLng(-23.6659, -46.6973),
          radius: 39239,
          types: ['address'],
          componentRestrictions: {
            country: ['br']
          }
        };

        var updateGeometryData = function(value) {
          if (value.geometry && value.geometry.location) {
            scope.latitude = value.geometry.location.lat();
            scope.longitude = value.geometry.location.lng();
          }
        };

        scope.$watch('result', function(value) {
          if (angular.isObject(value)) {
            updateGeometryData(value);
            scope.localizacao = _.isArray(value.address_components) && _.chain(value.address_components)
              .filter(function(component) {
                return component.types.indexOf('route') >= 0 ||
                       component.types.indexOf('sublocality_level_1') >= 0;
              })
              .first()
              .get('short_name')
              .value();
          } else if(!value) {
            scope.localizacao = '';
          }
        });

        scope.filterCityPoints = function(predictions) {
          var requiredTerms = ['São Paulo', 'SP'];
          return predictions.filter(function(prediction) {
            var predictionTerms = _.map(prediction.terms, 'value');
            return requiredTerms.every(function(term) {
              return predictionTerms.indexOf(term) >= 0;
            });
          });
        };

        /**
         * Testa se houve alguma variavel de retorno da API. Caso positivo, deve atualizar o
         * campo com este valor.
         */
        scope.$watch('ngModel', function() {
          scope.result = scope.ngModel;
        });
      }
    };
  });
