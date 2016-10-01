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
          usuarioLogado = JSON.parse(localStorage.usuario);
        }
        return usuarioLogado;
      };

      var checkUsuario = function(usuario, permissionName) {
        var isUsuarioPermitted = false;
        for (var i = 0, length = usuario.permissoes.length; i < length; i++) {
          if (usuario.permissoes[i] === permissionName) {
            isUsuarioPermitted = true;
          }
        }
        return isUsuarioPermitted;
      };

      var permissionHandler = function(permissionName) {
        var usuario = getUsuarioLogado();
        if (usuario) {
          if (usuario.root) {
            return true;
          } else if (permissionName === 'PUT /api/v1/usuarios/$id<[^/]+>') {
            // usuário sempre pode editar suas próprias informações
            return true;
          } else {
            return checkUsuario(usuario, permissionName);
          }
        } else {
          return false;
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
