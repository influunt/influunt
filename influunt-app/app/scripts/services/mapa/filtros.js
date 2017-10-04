'use strict';

/**
 * @ngdoc service
 * @name influuntApp.filtrosMapa
 * @description
 * # filtrosMapa
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('filtrosMapa', function () {
    var STATUS_FALHAS = ['FALHA', 'OPERANDO_COM_FALHAS'];

    var getAreas = function(areas, filtro) {
      var areasIds = _.map(filtro.areasSelecionadas, 'idJson');
      var lista = areas;
      if (_.isArray(areasIds) && areasIds.length > 0) {
        lista = _.filter(lista, function(i) { return areasIds.indexOf(i.idJson) >= 0; });
      }

      return lista;
    };

    var getControladores = function(filtro, controladores) {
      var areasIds = _.map(filtro.areasSelecionadas, 'idJson');
      var subareasIds = _.map(filtro.subareasSelecionadas, 'idJson');
      var controladoresIds = _.map(filtro.controladoresSelecionados, 'id');
      var agrupamentosIds = _.chain(filtro.agrupamentosSelecionados).map('aneis').flatten().map('controlador.id').uniq().value();

      if (filtro.exibirSomenteControladoresComPlanosImpostos) {
        controladores = _.filter(controladores, function(i) { return i.hasPlanoImposto || _.some(i.aneis, 'hasPlanoImposto'); });
      }

      if (filtro.exibirSomenteControladoresComFalhas) {
        controladores = _.filter(controladores, function(c) { return STATUS_FALHAS.indexOf(c.status) >= 0 ;});
      }

      if (_.isArray(filtro.tiposControleVigenteSelecionados) && filtro.tiposControleVigenteSelecionados.length > 0) {
        // filtra todos os controladores que possuem ao menos um anel em algum tipo de vigencia selecionada no filtro.
        controladores = _
          .chain(filtro.tiposControleVigenteSelecionados)
          .map(function(tipo) {
            return _.filter(controladores, function(c) {
              return _.map(c.aneis, function(a) {
                a.tipoControleVigente = a.tipoControleVigente || 'CENTRAL';
                return a.tipoControleVigente;
              }).indexOf(tipo) >= 0;
            });
          })
          .flatten()
          .uniq()
          .value();
      }

      if (_.isArray(agrupamentosIds) && agrupamentosIds.length > 0) {
        controladores = _.filter(controladores, function(i) { return agrupamentosIds.indexOf(i.id) >= 0; });
      }

      if (_.isArray(areasIds) && areasIds.length > 0) {
        controladores = _.filter(controladores, function(i) {return areasIds.indexOf(i.area.idJson) >= 0;});
      }

      if (_.isArray(subareasIds) && subareasIds.length > 0) {
        controladores = _.filter(controladores, function(c) { return c.subarea && subareasIds.indexOf(c.subarea.idJson) >= 0; });
      }

      if (_.isArray(controladoresIds) && controladoresIds.length > 0) {
        controladores = _.filter(controladores, function(i) {return controladoresIds.indexOf(i.id) >= 0;});
      }

      return controladores;
    };

    var getAneis = function(filtro, aneis) {
      if (filtro && _.isArray(filtro.tiposControleVigenteSelecionados) && filtro.tiposControleVigenteSelecionados.length > 0) {
        aneis = _.filter(aneis, function(anel) {
          return filtro.tiposControleVigenteSelecionados.indexOf(anel.tipoControleVigente) >= 0;
        });
      }

      return aneis;
    };

    var getAneisIds = function(controladoresFiltrados) {
      return _.chain(controladoresFiltrados).map('aneis').flatten().map('id').uniq().value();
    };

    var getSubareas = function(filtro, controladores) {
      var subareas = _
        .chain(controladores)
        .filter('subarea')
        .groupBy('subarea.idJson')
        .value();
      var subareasIds = _.map(filtro.subareasSelecionadas, 'idJson');

      if (_.isArray(subareasIds) && subareasIds.length > 0) {
        subareas = _.pick(subareas, subareasIds);
      }

      return subareas;
    };

    var getAgrupamentos = function(controladoresFiltrados, filtro, listaAgrupamentos) {
      var aneisFiltrados = getAneisIds(controladoresFiltrados);
      var agrupamentos;
      if (_.size(filtro.agrupamentosSelecionados) > 0) {
        agrupamentos = filtro.agrupamentosSelecionados;
      } else {
        agrupamentos = listaAgrupamentos;
      }

      // permite somente que os agrupamentos com todos os aneis associados presentes sejam exibidos.
      return _.filter(agrupamentos, function(agrupamento) {
        var aneisAgrupamento = agrupamento.aneis.length;
        var aneisExibidos = _.filter(agrupamento.aneis, function(a) {
          return aneisFiltrados.indexOf(a.id) >= 0;
        }).length;

        return aneisAgrupamento === aneisExibidos;
      });
    };

    return {
      getAreas: getAreas,
      getControladores: getControladores,
      getAneis: getAneis,
      getSubareas: getSubareas,
      getAgrupamentos: getAgrupamentos
    };
  });
