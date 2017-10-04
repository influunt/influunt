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

    var getPlainText = function(endereco) {
      var nomeEndereco = endereco.localizacao;
      if (endereco.alturaNumerica || angular.isNumber(endereco.alturaNumerica)) {
        nomeEndereco += ' ' + endereco.alturaNumerica;
      }

      if (endereco.localizacao2) {
        nomeEndereco += ' x ' + endereco.localizacao2;
      }

      return nomeEndereco;
    };

    var getHumanizedText = function(endereco) {
      var nomeEndereco = endereco.localizacao;
      if (endereco.alturaNumerica || angular.isNumber(endereco.alturaNumerica)) {
        nomeEndereco += ', nº ' + endereco.alturaNumerica;
      }

      if (endereco.localizacao2) {
        nomeEndereco += ' x ' + endereco.localizacao2;
      }

      if (endereco.referencia) {
        nomeEndereco += '. ref.: ' + endereco.referencia;
      }

      return nomeEndereco;
    };

    return function (endereco, opts) {
      if (!angular.isDefined(endereco) || !angular.isDefined(endereco.localizacao)) { return ''; }

      return opts === 'plain' ? getPlainText(endereco) : getHumanizedText(endereco);
    };
  });
