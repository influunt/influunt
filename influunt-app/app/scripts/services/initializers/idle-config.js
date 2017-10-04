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
.config(['IdleProvider',
  function(IdleProvider) {

    var UM_MINUTO = 60;
    IdleProvider.idle(30 * UM_MINUTO);
    IdleProvider.timeout(5);

  }]);
