'use strict';

/**
 * @ngdoc service
 * @name influuntApp.influuntBlockui
 * @description
 * # influuntBlockui
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .service('influuntBlockui', [function influuntBlockui() {
    var block = function() {
      $.blockUI({message: '<img src="images/reload.gif">'});
    };

    var unblock = function() {
      $.unblockUI();
    };

    return  {
      block: block,
      unblock: unblock,
    };

  }]);
