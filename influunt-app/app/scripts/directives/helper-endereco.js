'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:helperEndereco
 * @description
 * # helperEndereco
 */
angular.module('influuntApp')
  .directive('helperEndereco', ['PLACES_API', '$timeout',
    function (PLACES_API, $timeout) {
      return {
        restrict: 'A',
        scope: {
          localizacao: '=',
          anelId: '=?'
        },
        link: function (scope, element) {
          var URL = PLACES_API.baseUrl + '/endereco';
          var getSelect2Object = function(obj) {
            var endereco = obj.logradouro1;
            var texto = [endereco.tipo, endereco.titulo, endereco.nome, ',', endereco.altNum, ',', endereco.distrito]
                .join(' ')
                .replace(/\,\s+\,/, ',')
                .replace(/\s\,/, ',')
                .replace(/\s+/, ' ');
            var id = [endereco.tipo, endereco.titulo, endereco.nome, endereco.altNum]
                .join(' ')
                .replace(/\s+/, ' ');
            return { id: id, text: texto };
          };

          $(element).select2(
            {
              ajax: {
                url: function(params) { return [URL, '/', params.term].join(''); },
                dataType: 'json',
                delay: 250,
                data: {},
                processResults: function (data) {
                  var result = _.get(data, 'ArrayOfEndereco.Endereco');
                  if (_.isArray(result)) {
                    return { results: _.chain(result).map(getSelect2Object).uniqBy('text').take(5).value() };
                  } else if (_.isObject(result)) {
                    return { results: [getSelect2Object(result)] };
                  } else {
                    return [];
                  }
                },
                cache: true
              },
              minimumInputLength: 3
            }
          )
          .on('change', function(ev) {
            $timeout(function() {
              scope.localizacao = ev.target.value;
            });
          })
          ;

          var cacheAnel = null;
          scope.$watch('localizacao', function(val, prevVal) {
            $timeout(function() {
              if (!!scope.anelId && cacheAnel !== scope.anelId || !!val && typeof prevVal === 'undefined') {
                cacheAnel = scope.anelId;
                var $option = $('<option></option>').val(val).text(val);
                $(element).append($option).val(val).trigger('change');
              }
            }, 1000);
          });
        }
      };
    }]);
