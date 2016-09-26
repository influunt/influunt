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
        options: '=',
        onClose: '&'
      },
      link: function(scope, el) {
        var limpaFiltro;

        var toggleOpen = function() {
          $(el).find('.theme-config-box').toggleClass('open');
        };

        scope.pesquisar = function() {
          toggleOpen();
          return angular.isDefined(scope.onClose) && scope.onClose();
        };

        scope.limparPesquisa = function() {
          toggleOpen();
          limpaFiltro();

          return angular.isDefined(scope.onClose) && scope.onClose();
        };

        limpaFiltro = function() {
          _.each(scope.ngModel, function(item) {
            if (item.tipoCampo === 'data') {
              item.start = null;
              item.end = null;
            } else {
              item.valor = null;
            }
          });
        };

        scope.ngModel = {};
        $('.spin-icon').on('click', function() {
          toggleOpen();
        });
      }
    };
  });
