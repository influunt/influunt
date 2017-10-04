'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:resourceToString
 * @function
 * @description
 * # resourceToString
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('resourceToString', function () {
    return function (input, nomeCampo) {
      nomeCampo = nomeCampo || 'nome';
      if (input) {
        return input[nomeCampo];
      }
    };
  })

  .filter('resourceToLink', ['$state', function ($state) {
    return function (input, label, uiSref, param, uiSrefKeyName) {
      label = label || 'descricao';
      uiSrefKeyName = uiSrefKeyName || param;
      if (input) {
        var query = {};
        query[uiSrefKeyName] = input[param];
        return '<a href="' + $state.href(uiSref, query) + '">' + input[label] + '</a>';
      }
    };
  }]);
