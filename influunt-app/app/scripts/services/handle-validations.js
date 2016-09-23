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

    var handle = function(errors, objeto) {
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

        _.each(validations, function(messages, path) {
          _.update(response, path, _.constant(translate(path, messages, objeto)));
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

    var translate = function(path, messages, objeto) {
      var translated = [];
      _.each(messages, function(message) {
        var limits = getLimits(path, objeto);
        translated.push(message.replace("{min}", limits[0]).replace("{max}", limits[1]));
      });
      return translated;
    };

    var getLimits = function(path, objeto) {
      console.log('objeto: ', objeto)
      if (path.match(/tempoVerdeSegurancaFieldVeicular$/)) {
        return [objeto.verdeSegurancaVeicularMin, objeto.verdeSegurancaVeicularMax];
      } else if (path.match(/tempoVerdeSegurancaFieldPedestre$/)) {
        return [objeto.verdeSegurancaPedestreMin, objeto.verdeSegurancaPedestreMax];
      } else if (path.match(/tempoMaximoPermanenciaOk$/)) {
        return [objeto.maximoPermanenciaEstagioMin, objeto.maximoPermanenciaEstagioMax];
      } else if (path.match(/tempoAtrasoDeGrupoDentroDaFaixa$/)) {
        return [objeto.atrasoGrupoMin, objeto.atrasoGrupoMax];
      } else if (path.match(/tempoAmareloOk$/)) {
        return [objeto.amareloMin, objeto.amareloMax];
      } else if (path.match(/tempoVermelhoIntermitenteOk$/)) {
        return [objeto.vermelhoIntermitenteMin, objeto.vermelhoIntermitenteMax];
      } else if (path.match(/tempoVermelhoLimpezaFieldVeicular$/)) {
        return [objeto.vermelhoLimpezaVeicularMin, objeto.vermelhoLimpezaVeicularMax];
      } else if (path.match(/tempoVermelhoLimpezaFieldPedestre$/)) {
        return [objeto.vermelhoLimpezaPedestreMin, objeto.vermelhoLimpezaPedestreMax];
      } else if (path.match(/tempoAusenciaDeteccaoEstaDentroDaFaixa$/)) {
        return [objeto.ausenciaDeteccaoMin, objeto.ausenciaDeteccaoMax];
      } else if (path.match(/tempoDeteccaoPermanenteEstaDentroDaFaixa$/)) {
        return [objeto.deteccaoPermanenteMin, objeto.deteccaoPermanenteMax];
      } else if (path.match(/tempoCiclo$/)) {
        return [objeto.cicloMin, objeto.cicloMax];
      } else if (path.match(/defasagem$/)) {
        return [objeto.defasagemMin, objeto.defasagemMax];
      } else if (path.match(/tempoVerdeMinimo$/)) {
        return [objeto.verdeMinimoMin, objeto.verdeMinimoMax];
      } else if (path.match(/tempoVerdeMaximo$/)) {
        return [objeto.verdeMaximoMin, objeto.verdeMaximoMax];
      } else if (path.match(/tempoVerdeIntermediario$/)) {
        return [objeto.verdeIntermediarioMin, objeto.verdeIntermediarioMax];
      } else if (path.match(/tempoExtensaoVerde$/)) {
        return [objeto.extensaoVerdeMin, objeto.extensaoVerdeMax];
      } else if (path.match(/tempoVerde$/)) {
        return [objeto.verdeMin, objeto.verdeMax];
      }
      return [0, 0];
    };

    var buildValidationMessages = function(errors, objeto) {
      var validationMessages = handle(errors, objeto);

      if (validationMessages && _.isArray(validationMessages.aneis)) {
        for (var i = 0; i < validationMessages.aneis.length; i++) {
          validationMessages.aneis[i] = validationMessages.aneis[i] || {};
        }
      }

      console.log('$scope.errors: ', validationMessages)
      return validationMessages;
    };

    var anelTemErro = function(errors, indice) {
      errors = _.get(errors, 'aneis[' + indice + ']');
      return _.isObject(errors) && Object.keys(errors).length > 0;
    };

    return {
      handle: handle,
      buildValidationMessages: buildValidationMessages,
      anelTemErro: anelTemErro
    };

  });
