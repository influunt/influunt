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
      template: '<input type="text" autocomplete="off" placeholder="{{\'controladores.endereco\' | translate }}" class="form-control" data-ng-model="result" options="mapOptions" g-places-autocomplete>',
      restrict: 'E',
      scope: {
        latitude: '=',
        longitude: '=',
        localizacao: '=',
        ngModel: '='
      },
      link: function postLink(scope) {

        scope.mapOptions = {
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
                return component.types.indexOf('route') >= 0;
              })
              .first()
              .get('short_name')
              .value();
          } else if(!value) {
            scope.localizacao = '';
          }
        });

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
