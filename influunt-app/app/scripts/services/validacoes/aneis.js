'use strict';

/**
 * @ngdoc service
 * @name influuntApp.validacoes/aneis
 * @description
 * # validacoes/aneis
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('validacoesAneis', function () {

    var REQUIRED = ['descricao', 'latitude', 'longitude'];

    var inicializaValidador = function(anel) {
      anel.valid = {
        form: true,
        required: {}
      };
    };

    var validaCamposRequired = function(aneis) {
      aneis.forEach(function(anel) {
        inicializaValidador(anel);

        REQUIRED.forEach(function(field) {
          anel.valid.required[field] = angular.isDefined(anel[field]) && anel[field] !== null;
          anel.valid.form = anel.valid.form && anel.valid.required[field];
        });
      });

      return aneis.reduce(function(a, b) {return a && b.valid.form;}, true);
    };

    var validaQuantidadeGruposSemaforicos = function(aneis, controlador) {
      var maxGruposSemaforicos = controlador.modelo.configuracao.limiteGrupoSemaforico;
      var totalGruposSemaforicos = aneis.reduce(function(a, b) {
        return a + (b.grupos_pedestres || 0) + (b.grupos_sinais_veiculares || 0);
      }, 0);

      aneis[0].valid.totalGruposSemaforicos = maxGruposSemaforicos >= totalGruposSemaforicos;
      return aneis[0].valid.totalGruposSemaforicos;
    };

    var validaQuantidadeDetectoresVeicular = function(aneis, controlador) {
      var maxDetectorVeicular = controlador.modelo.configuracao.limiteDetectorVeicular;
      var totalDetectorVeicular = aneis.reduce(function(a, b) {
        return a + (b.numero_detectores || 0);
      }, 0);

      aneis[0].valid.totalDetectorVeicular = maxDetectorVeicular >= totalDetectorVeicular;
      return aneis[0].valid.totalDetectorVeicular;
    };

    var validaQuantidadeDetectoresPedestres = function(aneis, controlador) {
      var maxDetectorPedestres = controlador.modelo.configuracao.limiteDetectorPedestre;
      var totalDetectorPedestres = aneis.reduce(function(a, b) {
        return a + (b.numero_detectores_pedestres || 0);
      }, 0);

      aneis[0].valid.totalDetectorPedestres = maxDetectorPedestres >= totalDetectorPedestres;
      return aneis[0].valid.totalDetectorPedestres;
    };

    var valida = function(listaAneis, controlador) {
      var aneis = _.filter(listaAneis, {checked: true});
      var camposRequired = validaCamposRequired(aneis);
      var quantidadeGruposSemaforicos = validaQuantidadeGruposSemaforicos(aneis, controlador);
      var quantidadeDetectoresVeicular = validaQuantidadeDetectoresVeicular(aneis, controlador);
      var quantidadeDetectoresPedestres = validaQuantidadeDetectoresPedestres(aneis, controlador);

      return camposRequired &&
        quantidadeGruposSemaforicos &&
        quantidadeDetectoresVeicular &&
        quantidadeDetectoresPedestres;
    };

    var retornaMensagensValidacao = function(aneis) {
      var mensagens = [];

      if (!aneis[0].valid.totalDetectorPedestres) {
        mensagens.push({
          type: 'danger',
          msg: 'controladores.validacoes.totalDetectorPedestres'
        });
      }

      if (!aneis[0].valid.totalDetectorVeicular) {
        mensagens.push({
          type: 'danger',
          msg: 'controladores.validacoes.totalDetectorVeicular'
        });
      }

      if (!aneis[0].valid.totalGruposSemaforicos) {
        mensagens.push({
          type: 'danger',
          msg: 'controladores.validacoes.totalGruposSemaforicos'
        });
      }

      return mensagens;
    };

    return {
      valida: valida,
      retornaMensagensValidacao: retornaMensagensValidacao
    };
  });
