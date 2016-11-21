'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:statusControlador
 * @function
 * @description
 * # statusControlador
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('statusControlador', ['$filter', function ($filter) {
    return function (input) {
      var text = $filter('translate')(input);
      var tags = {
        EM_CONFIGURACAO: '',
        EM_EDICAO: 'badge-warning',
        EDITANDO: 'badge-warning',
        CONFIGURADO: 'badge-primary',
        ATIVO: 'badge-success'
      };

      return '<span class="badge ' + tags[input] + ' empty-badge">' + text + ' </span>';
    };
  }]);
