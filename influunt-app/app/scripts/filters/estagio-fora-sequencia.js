'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:estagioForaSequencia
 * @function
 * @description
 * # estagioForaSequencia
 * Retorna a lista de estágios que estão fora da sequencia de estagios de determinado plano.
 */
angular.module('influuntApp')
  .filter('estagioForaSequencia', function () {
    return function (estagios, sequencia) {
      if (estagios && sequencia) {
        var ids = _.map(sequencia, 'estagio.idJson');

        return _.filter(estagios, function(e) {
          return ids.indexOf(e.idJson) < 0;
        });
      }
    };
  });
