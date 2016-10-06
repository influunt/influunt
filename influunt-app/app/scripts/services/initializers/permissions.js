'use strict';

/**
 * @ngdoc service
 * @name influuntApp.permissions
 * @description
 * # mapCustomizations
 * Inicializa permissões do app
 */
angular.module('influuntApp')
  .run(['$urlRouter', 'PermissionsService', 'influuntBlockui', 'PermRoleStore', 'PermPermissionStore',
    function ($urlRouter, PermissionsService, influuntBlockui, PermRoleStore, PermPermissionStore) {

      var unblockRendering = function() {
        // Once permissions are set-up
        // kick-off router and start the application rendering
        $urlRouter.sync();
        // Also enable router to listen to url changes
        $urlRouter.listen();
      };

      var permissionHandler = function(permissionName) {
        var usuario = PermissionsService.getUsuario();
        if (usuario.root || permissionName === 'PUT /api/v1/usuarios/$id<[^/]+>') {
          return true;
        } else {
          return PermissionsService.checkPermission(permissionName);
        }
      };

      var loadAllPermissions = function() {
        return PermissionsService.loadPermissions()
          .then(function(response) {
            var allPermissions = _.map(response.permissoes, 'chave');
            PermPermissionStore.defineManyPermissions(allPermissions, permissionHandler);

            var allRoles = {};
            _.forEach(response.permissoesApp, function(role) {
              allRoles[role.chave] = _.map(role.permissoes, 'chave');
            });
            return PermRoleStore.defineManyRoles(allRoles);
          });
      };


      loadAllPermissions()
        .then(unblockRendering)
        .finally(influuntBlockui.unblock);

    }]);
