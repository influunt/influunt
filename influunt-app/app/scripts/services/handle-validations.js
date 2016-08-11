'use strict';

/**
 * @ngdoc service
 * @name influuntApp.handleValidations
 * @description
 * # handleValidations
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('handleValidations', function handleValidations() {
    var handle = function(errors) {
      var validations = {};
      var response = {};

      if (_.isArray(errors)) {
        errors.forEach(function(err) {
          var path = err.path.match(/\d+\]$/) ? err.path + '.general' : err.path;
          if (!path) {
            path = 'general';
          }

          validations[path] = validations[path] || [];
          validations[path].push(err.message);
        });

        _.each(validations, function(path, messages) {
          _.update(response, messages, _.constant(path));
        });

        // Específicos para as validações em escopo de anel.
        _.each(response.aneis, function(anel) {
          if (anel) {
            anel.all = _.chain(anel).values().flatten().uniq().value();
          }
        });
      }

      return response;
    };

    return {
      handle: handle
    };

  });
