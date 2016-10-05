'use strict';

/**
 * @ngdoc service
 * @name influuntApp.util/estagios
 * @description
 * # util/estagios
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('Objects', function () {
    /**
     * Faz o merge de dois objetos. Retorna um novo objeto, sem
     * alterar nenhum deles;
     *
     * @param      {obj}  obj objeto to merge
     * @param      {obj}  obj objeto to merge
     * @return     {obj}  merged objeto
     */
    var merge = function(obj1, obj2) {
      var newObj = {};
      _.forOwn(obj1, function(value, key) {
        newObj[key] = value;
      });
      _.forOwn(obj2, function(value, key) {
        newObj[key] = value;
      });
      return newObj;
    };

    return {
      merge: merge
    };
  });
