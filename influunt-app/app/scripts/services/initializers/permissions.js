'use strict';

/**
 * @ngdoc service
 * @name influuntApp.permissions
 * @description
 * # mapCustomizations
 * Inicializa permissões do app
 */
angular.module('influuntApp')
  .run(['$urlRouter', 'PermissionsService', 'influuntBlockui',
    function ($urlRouter, PermissionsService, influuntBlockui) {

      var unblockRendering = function() {
        // Once permissions are set-up
        // kick-off router and start the application rendering
        $urlRouter.sync();
        // Also enable router to listen to url changes
        $urlRouter.listen();
      };

      PermissionsService.loadPermissions()
        .then(unblockRendering)
        .finally(influuntBlockui.unblock);

    }]);
