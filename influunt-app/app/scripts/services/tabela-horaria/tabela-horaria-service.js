'use strict';

/**
 * @ngdoc service
 * @name influuntApp.tabelaHoraria/tabelaHorariaService
 * @description
 * # tabelaHoraria/tabelaHorariaService
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('TabelaHorariaService', function () {

    var tipoEvento = '';

    var initialize = function(tipo) {
      tipoEvento = tipo;
    };

    var isCurrentTipoEventoNaoRecorrente = function() {
      return tipoEvento === 'ESPECIAL_NAO_RECORRENTE';
    };

    var isCurrentTipoEventoEspecial = function() {
      return tipoEvento === 'ESPECIAL_RECORRENTE';
    };

    var isCurrentTipoEventoNormal = function() {
      return tipoEvento === 'NORMAL';
    };

    return {
      initialize: initialize,
      isCurrentTipoEventoNaoRecorrente: isCurrentTipoEventoNaoRecorrente,
      isCurrentTipoEventoEspecial: isCurrentTipoEventoEspecial,
      isCurrentTipoEventoNormal: isCurrentTipoEventoNormal
    };

  });
