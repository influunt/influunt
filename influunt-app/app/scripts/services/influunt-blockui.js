'use strict';

/**
 * @ngdoc service
 * @name influuntApp.influuntBlockui
 * @description
 * # influuntBlockui
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .service('influuntBlockui', ['$timeout', function influuntBlockui($timeout) {
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
