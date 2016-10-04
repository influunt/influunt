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

    var deletarPlanosTabelasHorariosNoServidor = function(controlador) {
      controlador.versoesPlanos = controlador.versoesPlanos || [];
      controlador.versoesTabelasHorarias = controlador.versoesTabelasHorarias || [];
      if(!controlador.enviadoRemoverPlanosETabelaHoraria && (controlador.versoesPlanos.length > 0 || controlador.versoesTabelasHorarias.length > 0)){
        controlador.enviadoRemoverPlanosETabelaHoraria = true;
        return Restangular.one('controladores', controlador.id).customDELETE('remover_planos_tabelas_horarios').finally(influuntBlockui.unblock);
      }
      return false;
    };

    return {
      deletarPlanosTabelasHorariosNoServidor: deletarPlanosTabelasHorariosNoServidor
    };

  }]);
