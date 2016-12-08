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
    STATUS_CONTROLADORES: 'app/mudanca_status_controlador',
    ALARMES_FALHAS: 'app/alarmes_falhas',
    TROCA_PLANO: 'app/troca_plano',
    CONTROLADOR_ONLINE: 'app/conn/online',
    CONTROLADOR_OFFLINE: 'app/conn/offline',
    STATUS_TRANSACAO: 'app/transacoes/+/status',
    DADOS_CONTROLADOR: 'app/controlador/:envelopeId/dados'
  });
