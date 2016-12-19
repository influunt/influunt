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

      var counter = 0;
      var unblockTimeout;
      var unblock = function() {
        $timeout.cancel(unblockTimeout);
        if (counter <= 0) {
          unblockTimeout = $timeout(function() {
            return influuntBlockui.unblock(true);
          }, 200);
        }
      };

      var decrementAndUnblock = function() {
        counter--;
        if (counter <= 0) {
          counter = 0;
          unblock();
        }
      };

      return {
        request: function(request) {

          // não exibe o blockui para requests feitas somente para as requests dinamicas.
          if (request.headers['x-prevent-block-ui']) {
            delete request.headers['x-prevent-block-ui'];
            return request;
          }

          $timeout.cancel(unblockTimeout);
          counter++;

          if (request.url.match(new RegExp(APP_ROOT))) {
            influuntBlockui.block();
          }

          return request;
        },


        response: function(response) {
          decrementAndUnblock();
          return response;
        },

        responseError: function(rejection) {
          decrementAndUnblock();
          return $q.reject(rejection);
        }
      };
    }]);
