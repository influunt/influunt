'use strict';

/**
 * @ngdoc service
 * @name influuntApp.assertControlador
 * @description
 * # assertControlador
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('assertControlador', function () {

    /**
     * Verifica se há aneis no controlador
     *
     * @param      {Object}   controlador  The controlador
     * @return     {boolean}  True if has aneis, False otherwise.
     */
    var hasAneis = function(controlador) {
      return controlador.aneis && controlador.aneis.length > 0;
    };

    var hasGruposSemaforicos = function(controlador) {
      var countRefGruposSemaforicos = _.chain(controlador.aneis).map('gruposSemaforicos').flatten().compact().value().length;
      var countGruposSemaforicos = controlador.gruposSemaforicos.length;

      return countRefGruposSemaforicos > 0 && countGruposSemaforicos >= countRefGruposSemaforicos;
    };

    /**
     * Verifica se o controlador possui ao menos um estágio.
     *
     * @param      {Object}   controlador  The controlador
     * @return     {boolean}  True if has estagios, False otherwise.
     */
    var hasEstagios = function(controlador) {
      var countRefEstagios = _.chain(controlador.aneis).map('estagios').flatten().compact().value().length;
      var countEstagios = controlador.estagios.length;

      return hasAneis(controlador) && countRefEstagios > 0 && countEstagios >= countRefEstagios;
    };

    /**
     * Verifica se o controlador possui ao menos uma transição por grupo semafórico
     *
     * @param      {Object}   controlador  The controlador
     * @return     {boolean}  True if has transições, False otherwise.
     */
    var hasTransicoes = function(controlador) {
      if (!hasAneis(controlador)) {
        return false;
      }

      var gruposSemaforicos = _.chain(controlador.aneis).map('gruposSemaforicos').value();
      var transicoes = _.chain(gruposSemaforicos).map('transicoes').value();
      return transicoes.length >= gruposSemaforicos.length;
    };

    /**
     * Verifica se o controlador possui ao menos uma tabela entre-verdes por transição
     *
     * @param      {Object}   controlador  The controlador
     * @return     {boolean}  True if has tabela entre-verdes, False otherwise.
     */
    var hasTabelasEntreVerdes = function(controlador) {
      if (hasAneis(controlador) && hasTransicoes(controlador)) {
        var transicoes = _.chain(controlador.aneis)
                                 .map('gruposSemaforicos')
                                 .flatten()
                                 .map('transicoes')
                                 .flatten()
                                 .value();
        var tabelasEV = _.chain(transicoes)
                          .map('tabelaEntreVerdesTransicoes')
                          .flatten()
                          .value();
        return tabelasEV.length >= transicoes.length;
      } else {
        return false;
      }
    };

    var hasVerdesConflitantes = function(controlador) {
      return _.isArray(controlador.verdesConflitantes) && controlador.verdesConflitantes.length > 0;
    };

    var hasEstagiosGruposSemaforicos = function(controlador) {
      return _.isArray(controlador.estagiosGruposSemaforicos) && controlador.estagiosGruposSemaforicos.length > 0;
    };

    var hasAtrasosDeGrupo = function(controlador) {
      return _.isArray(controlador.atrasosDeGrupo) && controlador.atrasosDeGrupo.length > 0;
    };

    var hasPassedThroughTabelaEntreVerdes = function(controlador) {
      var tempoAmarelo = _
        .chain(controlador.tabelasEntreVerdesTransicoes)
        .map('tempoAmarelo')
        .compact()
        .value().length;
      var tempoVermelhoIntermitente = _
        .chain(controlador.tabelasEntreVerdesTransicoes)
        .map('tempoVermelhoIntermitente')
        .compact()
        .value().length;

      return tempoAmarelo + tempoVermelhoIntermitente > 0;
    };

    /**
     * Verifica se o controlador tem todas condições necessárias para abrir o step
     * de aneis.
     *
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var assertStepAneis = function(controlador) {
      return hasAneis(controlador);
    };

    /**
     * Verifica se o controlador tem todas as condições necessárias para abrir o step
     * de configuracao de grupos
     *
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var assertStepConfiguracaoGrupos = function(controlador) {
      return assertStepAneis(controlador) && hasEstagios(controlador);
    };

    /**
     * Verifica se o controlador tem todas as condições necessárias para abrir o step
     * de verdes conflitantes
     *
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var assertStepVerdesConflitantes = function(controlador) {
      return assertStepConfiguracaoGrupos(controlador) && hasGruposSemaforicos(controlador);
    };

    /**
     * Verifica se o controlador tem todas as condições necessárias para abrir o step
     * de associacao.
     *
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var assertStepAssociacao = function(controlador) {
      return assertStepVerdesConflitantes(controlador) && hasVerdesConflitantes(controlador);
    };

    /**
     * Verifica se o controlador tem todas as condições necessárias para abrir o step
     * de transicoes proibidas.
     *
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var assertStepTransicoesProibidas = function(controlador) {
      return assertStepAssociacao(controlador) && hasEstagiosGruposSemaforicos(controlador);
    };

    /**
     * Verifica se o controlador tem todas as condições necessárias para abrir o step
     * de verdes conflitantes.
     *
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var assertStepEntreVerdes = function(controlador) {
      return assertStepTransicoesProibidas(controlador) && hasTransicoes(controlador);
    };

    /**
     * Verifica se o controlador tem todas as condições necessárias para abrir o step
     * de atraso de grupos.
     *
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var assertStepAtrasoDeGrupo = function(controlador) {
      return assertStepEntreVerdes(controlador) && hasPassedThroughTabelaEntreVerdes(controlador);
    };

    /**
     * Verifica se o controlador tem todas as condições necessárias para abrir o step
     * de associacao detectores.
     *
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var assertStepAssociacaoDetectores = function(controlador) {
      return assertStepAtrasoDeGrupo(controlador) && hasAtrasosDeGrupo(controlador);
    };

    return {
      hasAneis: hasAneis,
      hasEstagios: hasEstagios,
      hasTransicoes: hasTransicoes,
      hasTabelasEntreVerdes: hasTabelasEntreVerdes,

      assertStepAneis: assertStepAneis,
      assertStepConfiguracaoGrupos: assertStepConfiguracaoGrupos,
      assertStepAssociacao: assertStepAssociacao,
      assertStepVerdesConflitantes: assertStepVerdesConflitantes,
      assertStepTransicoesProibidas: assertStepTransicoesProibidas,
      assertStepAtrasoDeGrupo: assertStepAtrasoDeGrupo,
      assertStepEntreVerdes: assertStepEntreVerdes,
      assertStepAssociacaoDetectores: assertStepAssociacaoDetectores
    };
  });
