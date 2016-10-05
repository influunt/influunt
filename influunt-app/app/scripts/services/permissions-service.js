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


    var usuarioLogado;
    var getUsuarioLogado = function(refresh) {
      refresh = typeof refresh === 'undefined' ? false : !!refresh;
      if (!usuarioLogado || refresh) {
        var dataUsuario = localStorage.usuario || '{}';
        usuarioLogado = JSON.parse(dataUsuario);
      }
      return usuarioLogado;
    };


    var checkPermission = function(permissionName, usuario) {
      usuario = usuario || getUsuarioLogado();
      return _.some(usuario.permissoes, function(permissao) {
        return permissao === permissionName;
      });
    };


    var podeVisualizarTodasAreas = function() {
      return checkPermission('visualizarTodasAreas');
    };

    return {
      loadPermissions: loadPermissions,
      checkPermission: checkPermission,
      getUsuario: getUsuarioLogado,
      podeVisualizarTodasAreas: podeVisualizarTodasAreas
    };
  }]);
