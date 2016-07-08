'use strict';

/**
 * @ngdoc service
 * @name influuntApp.assertControlador
 * @description
 * # assertControlador
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('assertControlador', function () {

    /**
     * Verifica se há aneis no controlador
     *
     * @param      {<type>}   controlador  The controlador
     * @return     {boolean}  True if has aneis, False otherwise.
     */
    var hasAneis = function(controlador) {
      return controlador.aneis && controlador.aneis.length;
    };

    /**
     * Verifica se o controlador possui ao menos um estágio.
     *
     * @param      {<type>}   controlador  The controlador
     * @return     {boolean}  True if has estagios, False otherwise.
     */
    var hasEstagios = function(controlador) {
      return hasAneis(controlador) && _.chain(controlador.aneis)
        .map('estagios')
        .flatten()
        .compact()
        .value()
        .length > 0;
    };

    return {
      hasAneis: hasAneis,
      hasEstagios: hasEstagios,
    };
  });
