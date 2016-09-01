'use strict';

/**
 * @ngdoc service
 * @name influuntApp.blockuiInterceptor
 * @description
 * # blockuiInterceptor
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('blockuiInterceptor', ['influuntBlockui', '$q', '$timeout',
    function(influuntBlockui, $q, $timeout) {
      var loadingUnblockTimeout = null;
      return {
        request: function(request) {
          influuntBlockui.block();
          return request;
        },
        responseError: function(response) {
          $timeout.cancel(loadingUnblockTimeout);
          loadingUnblockTimeout = $timeout(function() {
            influuntBlockui.unblock();
          }, 500);
          return $q.reject(response);
        },
        response: function(response) {
          $timeout.cancel(loadingUnblockTimeout);
          loadingUnblockTimeout = $timeout(function() {
            influuntBlockui.unblock();
          }, 500);
          return response;
        }
      };
    }]);
