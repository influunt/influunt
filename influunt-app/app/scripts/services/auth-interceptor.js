'use strict';

/**
 * @ngdoc service
 * @name influuntApp.authInterceptor
 * @description
 * # authInterceptor
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('authInterceptor', ['APP_ROOT', 'toast',
    function(APP_ROOT, toast) {
      var LOGIN_PATH = '/login';

      return {
        request: function(request) {
          var token = localStorage.token;
          if (token && request.url.match(APP_ROOT)) {
            request.headers.authToken = token;
          }

          return request;
        },
        responseError: function(response) {
          // Recurso não permitido (usuário não tem acesso à determinada tela.).
          if (response.status === 403) {
            history.back();
            toast.warn('Ação não autorizada');
          }

          return response;
        },
        response: function(response) {
          if (response.status === 200 && response.config.url.match(LOGIN_PATH) && response.headers('authToken')) {
            localStorage.setItem('token', response.headers('authToken'));
          }

          return response;
        }
      };
    }]);
