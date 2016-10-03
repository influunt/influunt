'use strict';

/**
 * @ngdoc service
 * @name influuntApp.permissionsService
 * @description
 * # permissionsService
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('PermissionsService', ['Restangular', function (Restangular) {

    var loadPermissions = function() {
      return Restangular.all('permissoes').customGET('roles');
    };

    return {
      loadPermissions: loadPermissions
    };
  }]);
