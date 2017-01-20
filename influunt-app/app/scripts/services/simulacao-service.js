'use strict';

/**
* @ngdoc service
* @name influuntApp.SimulacaoService
* @description
* # SimulacaoService
* Service in the influuntApp.
*/
angular.module('influuntApp')
.service('SimulacaoService', function() {

  var podeSimular = function(controlador) {
    return controlador && controlador.controladorConfigurado && controlador.planoConfigurado && controlador.tabelaHorariaConfigurado;
  };

  return {
    podeSimular: podeSimular
  };

});
