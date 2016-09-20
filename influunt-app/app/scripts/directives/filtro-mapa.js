'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:filtroMapa
 * @description
 * # filtroMapa
 */
angular.module('influuntApp')
  .directive('filtroMapa', function () {
    return {
      templateUrl: 'views/directives/filtro-mapa.html',
      restrict: 'E',
      scope: {
        ngModel: '='
      },
      link: function postLink(scope, el) {
        var toggleOpen = function() {
          $(el).find('.theme-config-box, .leaflet-left').toggleClass('open');
          $(el).find('.fa-angle-left').toggleClass('fa-map');
        };

        $('.spin-icon').on('click', function(){
          toggleOpen();
        });
      }
    };
  });
