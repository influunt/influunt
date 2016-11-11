'use strict';

/**
 * @ngdoc service
 * @name influuntApp.permissionsService
 * @description
 * # permissionsService
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('PermissionsService', ['Restangular', 'PermRoleStore', 'PermPermissionStore', '$q',
    function (Restangular, PermRoleStore, PermPermissionStore, $q) {

      var getPermissions, loadPermissions, checkPermission, resetPermissions, getUsuario, podeVisualizarTodasAreas,
          isUsuarioRoot, setUsuario, refreshUsuario;

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
        var usuario = getUsuario();
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
        getUsuario(true);
      };

      var usuarioLogado;
      getUsuario = function(refresh) {
        if (!usuarioLogado || !!refresh) {
          var dataUsuario = localStorage.usuario || '{}';
          usuarioLogado = JSON.parse(dataUsuario);
        }
        return usuarioLogado;
      };

      setUsuario = function(usuarioJson) {
        var usuario = _.pick(usuarioJson, ['id', 'login', 'email', 'root', 'permissoes']);
        if (usuarioJson.area) {
          usuario.area = { idJson: usuarioJson.area.idJson };
        }
        usuarioLogado = usuario;
        localStorage.setItem('usuario', JSON.stringify(usuario));
      }

      refreshUsuario = function() {
        var usuario = getUsuario(true);
        return Restangular.one('usuarios', usuario.id).get()
          .then(function(response) {
            setUsuario(response);
          });
      };

      podeVisualizarTodasAreas = function() {
        return checkPermission('visualizarTodasAreas');
      };

      isUsuarioRoot = function(){
        return !!getUsuario().root;
      };

      var checkRole = function(roleName) {
        var role = PermRoleStore.getRoleDefinition(roleName);
        if (role) {
          return role.validateRole();
        }

        return $q.reject(false);
      };

      return {
        loadPermissions: loadPermissions,
        getPermissions: getPermissions,
        podeVisualizarTodasAreas: podeVisualizarTodasAreas,
        getUsuario: getUsuario,
        setUsuario: setUsuario,
        refreshUsuario: refreshUsuario,
        isUsuarioRoot: isUsuarioRoot,
        checkRole: checkRole
      };

    }]);
