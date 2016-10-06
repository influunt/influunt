'use strict';

/**
 * @ngdoc service
 * @name influuntApp.permissionsService
 * @description
 * # permissionsService
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('PermissionsService', ['Restangular', 'PermRoleStore', 'PermPermissionStore',
    function (Restangular, PermRoleStore, PermPermissionStore) {

      var getPermissions, loadPermissions, checkPermission, resetPermissions, getUsuarioLogado, podeVisualizarTodasAreas;

      getPermissions = function() {
        return Restangular.all('permissoes').customGET('roles');
      };

      loadPermissions = function() {
        return getPermissions()
          .then(function(response) {
            resetPermissions();

            var allPermissions = _.map(response.permissoes, 'chave');
            PermPermissionStore.defineManyPermissions(allPermissions, checkPermission);

            var allRoles = {};
            _.forEach(response.permissoesApp, function(role) {
              allRoles[role.chave] = _.map(role.permissoes, 'chave');
            });
            return PermRoleStore.defineManyRoles(allRoles);
          });
      };

      checkPermission = function(permissionName) {
        var usuario = getUsuarioLogado();
        if (usuario.root || permissionName === 'PUT /api/v1/usuarios/$id<[^/]+>') {
          return true;
        } else {
          return _.some(usuario.permissoes, function(permissao) {
            return permissao === permissionName;
          });
        }
      };

      resetPermissions = function() {
        PermPermissionStore.clearStore();
        PermRoleStore.clearStore();
        getUsuarioLogado(true);
      };

      var usuarioLogado;
      getUsuarioLogado = function(refresh) {
        if (!usuarioLogado || !!refresh) {
          var dataUsuario = localStorage.usuario || '{}';
          usuarioLogado = JSON.parse(dataUsuario);
        }
        return usuarioLogado;
      };

      podeVisualizarTodasAreas = function() {
        return checkPermission('visualizarTodasAreas');
      };

      return {
        loadPermissions: loadPermissions,
        getPermissions: getPermissions,
        podeVisualizarTodasAreas: podeVisualizarTodasAreas
      };

    }]);
