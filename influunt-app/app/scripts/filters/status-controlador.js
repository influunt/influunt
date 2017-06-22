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
        COM_FALHAS: 'badge-warning',
        EM_EDICAO: 'badge-warning',
        EDITANDO: 'badge-warning',
        CONFIGURADO: 'badge-primary',
        SINCRONIZADO: 'badge-success',
        ONLINE: 'badge-primary',
      };

      return '<span class="badge ' + tags[input] + '">' + text + ' </span>';
    };
  }]);
