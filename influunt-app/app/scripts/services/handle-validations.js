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

    // funções privadas
    var translate, getLimits;

    translate = function(path, messages, objeto) {
      var translated = [];
      _.each(messages, function(message) {
        var limits = getLimits(path, objeto);
        translated.push(message.replace("{min}", limits[0]).replace("{max}", limits[1]));
      });
      return translated;
    };

    getLimits = function(path, objeto) {
      if (!angular.isDefined(objeto)) {
        return [0, 0];
      }

      var limits = {
        tempoVerdeSegurancaFieldVeicular: [objeto.verdeSegurancaVeicularMin, objeto.verdeSegurancaVeicularMax],
        tempoVerdeSegurancaFieldPedestre: [objeto.verdeSegurancaPedestreMin, objeto.verdeSegurancaPedestreMax],
        tempoMaximoPermanenciaOk: [objeto.maximoPermanenciaEstagioMin, objeto.maximoPermanenciaEstagioMax],
        tempoAtrasoDeGrupoDentroDaFaixa: [objeto.atrasoGrupoMin, objeto.atrasoGrupoMax],
        tempoAmareloOk: [objeto.amareloMin, objeto.amareloMax],
        tempoVermelhoIntermitenteOk: [objeto.vermelhoIntermitenteMin, objeto.vermelhoIntermitenteMax],
        tempoVermelhoLimpezaFieldVeicular: [objeto.vermelhoLimpezaVeicularMin, objeto.vermelhoLimpezaVeicularMax],
        tempoVermelhoLimpezaFieldPedestre: [objeto.vermelhoLimpezaPedestreMin, objeto.vermelhoLimpezaPedestreMax],
        tempoAusenciaDeteccaoEstaDentroDaFaixa: [objeto.ausenciaDeteccaoMin, objeto.ausenciaDeteccaoMax],
        tempoDeteccaoPermanenteEstaDentroDaFaixa: [objeto.deteccaoPermanenteMin, objeto.deteccaoPermanenteMax],
        tempoCiclo: [objeto.cicloMin, objeto.cicloMax],
        defasagem: [objeto.defasagemMin, objeto.defasagemMax],
        tempoVerdeMinimo: [objeto.verdeMinimoMin, objeto.verdeMinimoMax],
        tempoVerdeMaximo: [objeto.verdeMaximoMin, objeto.verdeMaximoMax],
        tempoVerdeIntermediario: [objeto.verdeIntermediarioMin, objeto.verdeIntermediarioMax],
        tempoExtensaoVerde: [objeto.extensaoVerdeMin, objeto.extensaoVerdeMax],
        tempoVerde: [objeto.verdeMin, objeto.verdeMax],
        tempoVerdeDemandaPrioritaria: [objeto.verdeMin, objeto.verdeMax]
      };

      return _.find(limits, function(value, patten) {
        return path.match(new RegExp(patten + '$')) && value;
      }) || [0,0];
    };

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
          if (_.isObject(anel)) {
            anel.all = _.chain(anel).values().flatten().uniq().value();
          }
        });
      }

      return response;
    };

    var buildValidationMessages = function(errors, objeto) {
      var validationMessages = handle(errors, objeto);

      if (validationMessages && _.isArray(validationMessages.aneis)) {
        for (var i = 0; i < validationMessages.aneis.length; i++) {
          validationMessages.aneis[i] = validationMessages.aneis[i] || {};
        }
      }

      console.log('scope.errors:', validationMessages)

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
