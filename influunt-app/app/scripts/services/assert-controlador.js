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
     * @param      {Object}   controlador  The controlador
     * @return     {boolean}  True if has aneis, False otherwise.
     */
    var hasAneis = function(controlador) {
      return controlador.aneis && controlador.aneis.length > 0;
    };

    /**
     * Verifica se o controlador possui ao menos um estágio.
     *
     * @param      {Object}   controlador  The controlador
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

    /**
     * Verifica se o controlador possui ao menos uma transição por grupo semafórico
     *
     * @param      {Object}   controlador  The controlador
     * @return     {boolean}  True if has transições, False otherwise.
     */
    var hasTransicoes = function(controlador) {
      if (!hasAneis(controlador)) {
        return false;
      }

      var gruposSemaforicos = _.chain(controlador.aneis).map('gruposSemaforicos').value();
      var transicoes = _.chain(gruposSemaforicos).map('transicoes').value();
      return transicoes.length >= gruposSemaforicos.length;
    };

    /**
     * Verifica se o controlador possui ao menos uma tabela entre-verdes por transição
     *
     * @param      {Object}   controlador  The controlador
     * @return     {boolean}  True if has tabela entre-verdes, False otherwise.
     */
    var hasTabelasEntreVerdes = function(controlador) {
      if (hasAneis(controlador) && hasTransicoes(controlador)) {
        var transicoes = _.chain(controlador.aneis)
                                 .map('gruposSemaforicos')
                                 .flatten()
                                 .map('transicoes')
                                 .flatten()
                                 .value();
        var tabelasEV = _.chain(transicoes)
                          .map('tabelaEntreVerdesTransicoes')
                          .flatten()
                          .value();
        return tabelasEV.length >= transicoes.length;
      } else {
        return false;
      }
    };



    return {
      hasAneis: hasAneis,
      hasEstagios: hasEstagios,
      hasTransicoes: hasTransicoes,
      hasTabelasEntreVerdes: hasTabelasEntreVerdes
    };
  });
