'use strict';

/**
 * @ngdoc service
 * @name influuntApp.eventosDinamicos
 * @description
 * # eventosDinamicos
 * Constant in the influuntApp.
 */
angular.module('influuntApp')
  .constant('eventosDinamicos', {
    STATUS_CONEXAO_CONTROLADORES: 'status_conexao_controladores',
    IMPOSICAO_PLANO_CONTROLADOR: 'imposicao_plano_controlador',
    MODOS_CONTROLADORES: 'modos_controladores',
    STATUS_CONTROLADORES: 'status_controladores',

    ALARMES_FALHAS: 'central/alarmes_falhas',
    TROCA_PLANO: 'central/troca_plano'
  });
