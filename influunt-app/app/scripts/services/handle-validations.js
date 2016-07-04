'use strict';

/**
 * @ngdoc service
 * @name influuntApp.handleValidations
 * @description
 * # handleValidations
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('handleValidations', function () {
    var handle = function(errors, scope) {
      scope.validations = {};
      if (angular.isArray(errors)) {
        errors.forEach(function(err) {
          var path = err.path.match(/\d+\]$/) ? err.path + '.general' : err.path;
          if (!path) {
            path = 'general';
          }

          scope.validations[path] = scope.validations[path] || [];
          scope.validations[path].push(err.message);
        });

        scope.errors = {};
        _.each(scope.validations, function(val, key) {
          _.update(scope.errors, key, _.constant(val));
        });

        // Específicos para as validações em escopo de anel.
        _.each(scope.errors.aneis, function(anel) {
          if (anel) {
            anel.all = _.chain(anel).values().flatten().uniq().value();
          }
        });
      }
    };

    return {
      handle: handle
    };

  });
