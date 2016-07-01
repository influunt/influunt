'use strict';

/**
 * @ngdoc service
 * @name influuntApp.crud/toast
 * @description
 * # crud/toast
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('toast', ['toaster', function (toaster) {

    var success = function(text) {
      toaster.pop({
        type: 'success',
        body: text,
        showCloseButton: true,
        timeout: 6000
      });
    };

    var warn = function(text) {
      toaster.pop({
        type: 'warning',
        body: text,
        showCloseButton: true,
        timeout: 6000
      });
    };

    var error = function(text) {
      toaster.pop({
        type: 'error',
        body: text,
        showCloseButton: true,
        timeout: 6000
      });
    };

    return {
      success: success,
      error: error,
      warn: warn
    };
  }]);
