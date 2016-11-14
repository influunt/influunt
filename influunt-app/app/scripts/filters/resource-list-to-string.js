'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:resourceListToString
 * @function
 * @description
 * # resourceListToString
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('resourceListToString', function () {
    return function (input, nomeCampo) {
      nomeCampo = nomeCampo || 'descricao';
      if (input) {
        return input.map(function(resource) {
          return '<li>' + resource[nomeCampo] + '</li>';
        }).join('');
      }
    };
  })

  .filter('resourceListToLink', ['$state', function ($state) {
    return function (input, label, uiSref, param) {
      label = label || 'descricao';
      if (input) {
        return input.map(function(resource) {
          var query = {};
          query[param] = resource[param];
          return '<li> <a href="' + $state.href(uiSref, query) + '">' + resource[label] + '</a></li>';
        }).join('');
      }
    };
  }]);
