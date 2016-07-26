'use strict';

/**
 * @ngdoc service
 * @name influuntApp.modoOperacao
 * @description
 * # modoOperacao
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('modoOperacaoService', function () {

    var MODOS = [
      'APAGADO',
      'VERDE',
      'AMARELO',
      'VERMELHO',
      'VERMELHO_INTERMITENTE',
      'INTERMITENTE',
      'VERMELHO_LIMPEZA'
    ];

    /**
     * Retorna o modo operacao snake-case'd. Utilizado principalmente para a geração de
     * classes css.
     *
     * @param      {<type>}  status  The status
     * @return     {Array}   The status class.
     */
    var getCssClass = function(status) {
      var modo = MODOS[status];
      return modo && modo.replace(/\_/g, '-').toLowerCase();
    };

    /**
     * Retorna o modo de operação a partir do id.
     *
     * @param      {<type>}  id      The identifier
     * @return     {<type>}  The modo by identifier.
     */
    var getModoById = function(id) {
      return MODOS[id];
    };

    /**
     * Retorna o id do modo de operação a partir do nome (uppercase'd). Deve retornar
     * -1 para os valores inexistentes.
     *
     * @param      {<type>}   name    The name
     * @return     {boolean}  The modo by name.
     */
    var getModoIdByName = function(name) {
      var id = MODOS.indexOf(name);
      return id >= 0 ? id : -1;
    };

    return {
      getCssClass: getCssClass,
      getModoById: getModoById,
      getModoIdByName: getModoIdByName
    };
  });
