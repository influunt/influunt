'use strict';

/**
 * @ngdoc service
 * @name influuntApp.blockuiInterceptor
 * @description
 * # blockuiInterceptor
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('blockuiInterceptor', ['influuntBlockui', '$q', '$timeout', 'APP_ROOT',
    function(influuntBlockui, $q, $timeout, APP_ROOT) {
      return {
        request: function(request) {

          // não exibe o blockui para requests feitas somente para as requests dinamicas.
          if (request.headers['x-prevent-block-ui']) {
            delete request.headers['x-prevent-block-ui'];
            return request;
          }

          if (request.url.match(new RegExp(APP_ROOT))) {
            influuntBlockui.block();
          }

          return request;
        }
      };
    }]);
