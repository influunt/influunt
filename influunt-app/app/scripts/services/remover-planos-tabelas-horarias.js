'use strict';

/**
 * @ngdoc service
 * @name influuntApp.removerPlanosTabelasHorarias
 * @description
 * # removerPlanosTabelasHorarias
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .service('removerPlanosTabelasHorarias',['Restangular', 'influuntBlockui',
   function (Restangular, influuntBlockui) {

    var deletarPlanosTabelasHorariosNoServidor = function(controladorId) {
      return Restangular.one('controladores', controladorId).customDELETE('remover_planos_tabelas_horarios').finally(influuntBlockui.unblock);
    };

    return {
      deletarPlanosTabelasHorariosNoServidor: deletarPlanosTabelasHorariosNoServidor
    };

  }]);
