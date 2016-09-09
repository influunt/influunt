'use strict';

/**
 * @ngdoc directive
 * @name influuntApp.directive:pesquisaLateral
 * @description
 * # pesquisaLateral
 */
angular.module('influuntApp')
  .directive('pesquisaLateral', function () {
    return {
      templateUrl: 'views/directives/pesquisa-lateral/pesquisa-lateral.html',
      restrict: 'E',
      scope: {
        ngModel: '=',
        campos: '=',
        onClose: '&'
      },
      link: function(scope, el) {

        var toggleOpen = function() {
          $(el).find('.theme-config-box').toggleClass('open');
        };

        scope.pesquisar = function() {
          toggleOpen();
          return angular.isDefined(scope.onClose) && scope.onClose();
        };

        scope.ngModel = {};
        $('.spin-icon').on('click', function() {
          toggleOpen();
        });
      }
    };
  });
