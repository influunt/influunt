'use strict';

/**
 * @ngdoc service
 * @name influuntApp.util/controladores
 * @description
 * # util/controladores
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('utilControladores', function () {

    /**
     * converte os valores do controlador, que podem ser enviados como strings, para valores
     * inteiros.
     *
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var parseLimitsToInt = function(controlador) {
      controlador.amareloMax = parseInt(controlador.amareloMax);
      controlador.amareloMin = parseInt(controlador.amareloMin);
      controlador.atrasoGrupoMin = parseInt(controlador.atrasoGrupoMin);
      controlador.cicloMax = parseInt(controlador.cicloMax);
      controlador.cicloMin = parseInt(controlador.cicloMin);
      controlador.defasagemMin = parseInt(controlador.defasagemMin);
      controlador.extensaoVerdeMax = parseInt(controlador.extensaoVerdeMax);
      controlador.extensaoVerdeMin = parseInt(controlador.extensaoVerdeMin);
      controlador.maximoPermanenciaEstagioMax = parseInt(controlador.maximoPermanenciaEstagioMax);
      controlador.maximoPermanenciaEstagioMin = parseInt(controlador.maximoPermanenciaEstagioMin);
      controlador.verdeIntermediarioMax = parseInt(controlador.verdeIntermediarioMax);
      controlador.verdeIntermediarioMin = parseInt(controlador.verdeIntermediarioMin);
      controlador.verdeMax = parseInt(controlador.verdeMax);
      controlador.verdeMaximoMax = parseInt(controlador.verdeMaximoMax);
      controlador.verdeMaximoMin = parseInt(controlador.verdeMaximoMin);
      controlador.verdeMin = parseInt(controlador.verdeMin);
      controlador.verdeMinimoMax = parseInt(controlador.verdeMinimoMax);
      controlador.verdeMinimoMin = parseInt(controlador.verdeMinimoMin);
      controlador.verdeSegurancaPedestreMax = parseInt(controlador.verdeSegurancaPedestreMax);
      controlador.verdeSegurancaPedestreMin = parseInt(controlador.verdeSegurancaPedestreMin);
      controlador.verdeSegurancaVeicularMax = parseInt(controlador.verdeSegurancaVeicularMax);
      controlador.verdeSegurancaVeicularMin = parseInt(controlador.verdeSegurancaVeicularMin);
      controlador.vermelhoIntermitenteMax = parseInt(controlador.vermelhoIntermitenteMax);
      controlador.vermelhoIntermitenteMin = parseInt(controlador.vermelhoIntermitenteMin);
      controlador.vermelhoLimpezaPedestreMax = parseInt(controlador.vermelhoLimpezaPedestreMax);
      controlador.vermelhoLimpezaPedestreMin = parseInt(controlador.vermelhoLimpezaPedestreMin);
      controlador.vermelhoLimpezaVeicularMax = parseInt(controlador.vermelhoLimpezaVeicularMax);
      controlador.vermelhoLimpezaVeicularMin = parseInt(controlador.vermelhoLimpezaVeicularMin);

      return controlador;
    };

    return {
      parseLimitsToInt: parseLimitsToInt
    };
  });
