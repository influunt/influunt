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
      $.blockUI({message: '<img class="influunt-block-ui" src="images/loading.gif">'});
    };

    var unblock = function(unblock) {
      if (unblock) {
        $.unblockUI();
      }
    };

    return  {
      block: block,
      unblock: unblock,
    };

  }]);
