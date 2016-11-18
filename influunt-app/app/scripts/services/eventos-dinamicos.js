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

    STATUS_CONTROLADORES: 'central/mudanca_status_controlador',
    ALARMES_FALHAS: 'central/alarmes_falhas',
    TROCA_PLANO: 'central/troca_plano',
    CONTROLADOR_ONLINE: 'controladores/conn/online',
    CONTROLADOR_OFFLINE: 'controladores/conn/offline',
  });
