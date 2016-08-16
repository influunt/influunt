'use strict';

/**
 * @ngdoc service
 * @name influuntApp.authInterceptor
 * @description
 * # authInterceptor
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('authInterceptor', ['APP_ROOT', 'toast', '$q',
    function(APP_ROOT, toast, $q) {
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
            toast.warn('Ação não autorizada');
          }

          if (response.status === 401) {
            location.hash = '#/login';
            localStorage.removeItem('token');
            toast.warn('Ação não autorizada');
          }

          return $q.reject(response);
        },
        response: function(response) {
          if (response.status === 200 && response.config.url.match(LOGIN_PATH) && response.headers('authToken')) {
            localStorage.setItem('token', response.headers('authToken'));
          }

          return response;
        }
      };
    }]);


angular.module('influuntApp')
  .factory('blockuiInterceptor', ['influuntBlockui', '$q',
    function(influuntBlockui, $q) {
      return {
        request: function(request) {
          influuntBlockui.block();
          return request;
        },
        responseError: function(response) {
          influuntBlockui.unblock();
          return $q.reject(response);
        },
        response: function(response) {
          influuntBlockui.unblock();
          return response;
        }
      };
    }]);
