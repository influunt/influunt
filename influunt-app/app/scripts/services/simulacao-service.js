'use strict';

/**
 * @ngdoc service
 * @name influuntApp.SimulacaoService
 * @description
 * # SimulacaoService
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .service('SimulacaoService', function () {

    var podeSimular = function(controlador) {
      return _.includes(['ATIVO', 'CONFIGURADO'], _.get(controlador, 'statusControlador'));
    };

    return {
      podeSimular: podeSimular
    };

  });
