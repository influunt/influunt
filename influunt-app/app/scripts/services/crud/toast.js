'use strict';

/**
 * @ngdoc service
 * @name influuntApp.crud/toast
 * @description
 * # crud/toast
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('toast', [function () {

    toastr.options = {
      closeButton: true,
      debug: false,
      newestOnTop: true,
      progressBar: false,
      positionClass: 'toast-top-right',
      preventDuplicates: false,
      showDuration: '300',
      hideDuration: '1000',
      timeOut: '6000',
      extendedTimeOut: '1000',
      showEasing: 'swing',
      hideEasing: 'linear',
      showMethod: 'fadeIn',
      hideMethod: 'fadeOut'
    };

    var success = function(text, title, options) {
      toastr.success(text, title, options);
    };

    var warn = function(text, title, options) {
      toastr.warning(text, title, options);
    };

    var error = function(text, title, options) {
      toastr.error(text, title, options);
    };

    var clear = function() {
      toastr.clear();
    };

    return {
      success: success,
      error: error,
      warn: warn,
      clear: clear
    };
  }]);
