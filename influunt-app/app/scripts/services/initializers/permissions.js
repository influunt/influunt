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

      var usuarioLogado;
      var getUsuarioLogado = function() {
        if (!usuarioLogado) {
          var dataUsuario = localStorage.usuario || '{}';
          usuarioLogado = JSON.parse(dataUsuario);
        }
        return usuarioLogado;
      };

      var checkUsuario = function(usuario, permissionName) {
        return _.some(usuario.permissoes, function(permissao) {
          return permissao === permissionName;
        });
      };

      var permissionHandler = function(permissionName) {
        var usuario = getUsuarioLogado();
        if (usuario.root || permissionName === 'PUT /api/v1/usuarios/$id<[^/]+>') {
          return true;
        } else {
          return checkUsuario(usuario, permissionName);
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
