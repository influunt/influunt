'use strict';

/**
* @ngdoc config
* @name influuntApp
* @description
* # Restangular configuration
* [Restangular](https://github.com/mgonto/restangular) is the package responsible to handle all accesses to remote resources.
*
* Inicializa as configurações do Restangular com os devidos valores (salvos em app/scripts/ngConstants.js)
*/
angular.module('influuntApp')
.config(['RestangularProvider', 'APP_ROOT', '$httpProvider',
  function(RestangularProvider, APP_ROOT, $httpProvider) {
    RestangularProvider.setBaseUrl(APP_ROOT);
    // RestangularProvider.setBaseUrl('https://demo7285146.mockable.io');
    $httpProvider.interceptors.push('authInterceptor');
    $httpProvider.interceptors.push('blockuiInterceptor');
  }]);
