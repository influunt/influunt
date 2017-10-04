'use strict';

/**
 * @ngdoc service
 * @name influuntApp.util/estagios
 * @description
 * # util/estagios
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('utilEstagios', function () {
    /**
     * Retorna o proximo estágio do array, considerando um array circular (o ultimo
     * elemento tem como próximo elemento o primeiro item).
     *
     * @param      {<type>}  lista   The lista
     * @param      {number}  index   The index
     * @return     {<type>}  The proximo estagio.
     */
    var getProximoEstagio = function(lista, index, isArrayCiclico) {
      isArrayCiclico = _.isUndefined(isArrayCiclico) ? true : isArrayCiclico;
      if (index >= lista.length || index < 0) {return null;}

      var posicao = isArrayCiclico ? ((index + 1) % lista.length) : (index + 1);
      return lista[posicao];
    };

    /**
     * Retorna o estágio anterior do array, considerando um array circular (o ultimo
     * elemento tem como próximo elemento o primeiro item).
     *
     * @param      {<type>}  lista   The lista
     * @param      {number}  index   The index
     * @return     {<type>}  The estagio anterior.
     */
    var getEstagioAnterior = function(lista, index, isArrayCiclico) {
      isArrayCiclico = _.isUndefined(isArrayCiclico) ? true : isArrayCiclico;
      if (index >= lista.length || index < 0) {return null;}

      var posicao = isArrayCiclico ? ((index -1) + lista.length) % lista.length : (index -1);
      return lista[posicao];
    };

    return {
      getEstagioAnterior: getEstagioAnterior,
      getProximoEstagio: getProximoEstagio
    };
  });
