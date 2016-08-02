'use strict';

/**
 * @ngdoc service
 * @name influuntApp.util/uuid
 * @description
 * # util/uuid
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('influuntUUID', function () {

    var generate = function() {
      return UUID.generate();
    };


    return {
      generate: generate
    };
  });
