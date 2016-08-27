'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:nomeEndereco
 * @function
 * @description
 * # nomeEndereco
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('nomeEndereco', function () {
    return function (endereco) {
      if (!angular.isDefined(endereco)) { return false; }

      var nomeEndereco = endereco.localizacao;
      if (endereco.alturaNumerica) {
        nomeEndereco += ', nº ' + endereco.alturaNumerica;
      }

      if (endereco.localizacao2) {
        nomeEndereco += ' com ' + endereco.localizacao2;
      }

      if (endereco.referencia) {
        nomeEndereco += '. ref.: ' + endereco.referencia;
      }

      return nomeEndereco;
    };
  });
