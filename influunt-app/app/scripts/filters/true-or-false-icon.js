'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:trueOrFalseIcon
 * @function
 * @description
 * # trueOrFalseIcon
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('trueOrFalseIcon', function () {
    return function (input) {
      var CERTO = 'fa-check certo';
      var ERRADO = 'fa-times errado';
      var status = input ? CERTO : ERRADO;

      return '<span><i class="fa ' + status + '" aria-hidden="true"></i></span>';
    };
  });
