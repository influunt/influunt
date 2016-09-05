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
      template: '<input type="text" autocomplete="off" placeholder="{{\'controladores.endereco\' | translate }}" class="form-control" data-ng-model="result" g-places-autocomplete>',
      restrict: 'E',
      scope: {
        latitude: '=',
        longitude: '=',
        localizacao: '=',
        ngModel: '='
      },
      link: function postLink(scope) {
        var updateGeometryData = function(value) {
          if (value.geometry && value.geometry.location) {
            scope.latitude = value.geometry.location.lat();
            scope.longitude = value.geometry.location.lng();
          }
        };

        scope.$watch('result', function(value) {
          if (angular.isObject(value)) {
            updateGeometryData(value);

            if (value.address_components && _.isArray(value.address_components)) {
              var foundName = false;
              for (var i = 0; i < value.address_components.length && !foundName; i++) {
                if (value.address_components[i].types) {
                  for (var j = 0; j < value.address_components[i].types.length && !foundName; j++) {
                    if (value.address_components[i].types[j] === 'route') {
                      scope.localizacao = value.address_components[i].short_name;
                      foundName = true;
                    }
                  }
                }
              }
            }
          }else if(!value){
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
