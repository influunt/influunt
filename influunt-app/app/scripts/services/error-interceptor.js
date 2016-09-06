'use strict';

/**
 * @ngdoc service
 * @name influuntApp.errorInterceptor
 * @description
 * # errorInterceptor
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('errorInterceptor', ['$q', '$filter', 'toast', 'ENV',
    function($q, $filter, toast, ENV) {
      return {
        request: function(request) {
          return request;
        },

        responseError: function(response) {
          if (response.status === 502) {
            toast.error($filter('translate')('sistema.mensagem_erro_502'));
          } else if (response.status === 404) {
            toast.error($filter('translate')('sistema.mensagem_erro_404'));
          } else {
            if (ENV === 'development') {
              var msg = response.status + ' ' + response.statusText + '             ' + JSON.stringify(response.data);
              toast.error(msg);
            }
          }

          return $q.reject(response);
        },

        response: function(response) {
          return response;
        }
      };
  }]);
