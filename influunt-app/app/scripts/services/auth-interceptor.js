'use strict';

/**
 * @ngdoc service
 * @name influuntApp.authInterceptor
 * @description
 * # authInterceptor
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('authInterceptor', ['APP_ROOT', function(APP_ROOT) {
    var LOGIN_PATH = '/login';

    return {
      request: function(request) {
        var token = localStorage.token;
        if (token && request.url.match(APP_ROOT)) {
          request.headers['authToken'] = token;
        }

        return request;
      },
      response: function(response) {
        if (response.status === 200 && response.config.url.match(LOGIN_PATH)) {
          localStorage.setItem('token', response.headers('authToken'));
        }

        return response;
      }
    }
  }]);
