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
      var loadingUnblockTimeout = null;
      return {
        request: function(request) {
          if (request.url.match(new RegExp(APP_ROOT))) {
            console.log(request.url);
            influuntBlockui.block();
          }

          return request;
        },
        // responseError: function(response) {
        //   if (response.config.url.match(new RegExp(APP_ROOT))) {
        //     $timeout.cancel(loadingUnblockTimeout);
        //     loadingUnblockTimeout = $timeout(function() {
        //       influuntBlockui.unblock();
        //     }, 500);
        //   }

        //   return $q.reject(response);
        // },
        // response: function(response) {
        //   if (response.config.url.match(new RegExp(APP_ROOT))) {
        //     $timeout.cancel(loadingUnblockTimeout);
        //     loadingUnblockTimeout = $timeout(function() {
        //       influuntBlockui.unblock();
        //     }, 500);
        //   }

        //   return response;
        // }
      };
    }]);
