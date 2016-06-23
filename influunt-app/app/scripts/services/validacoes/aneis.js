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

    /**
     * Inicializa os metadados utilizados para a validação dos formularios de aneis.
     *
     * @param      {<type>}  anel    The anel
     */
    var inicializaValidador = function(anel) {
      anel.valid = {
        form: true,
        required: {}
      };
    };

    /**
     * Retorna se o formulario é válido de acordo com os aneis.
     *
     * @param      {<type>}  aneis   The aneis
     */
    var retornaStatusFormulario = function(aneis) {
      return aneis.reduce(function(a, b) {return a && b.valid.form;}, true);
    };

    /**
     * Verifica se os campos obrigatórios estão todos preenchidos em cada formulário de aneis.
     * Caso um destes formulários não complete este requisito, todo o formulário (ou o conjunto
     * de formulários de aneis) estará inválido.
     *
     * @param      {<type>}  aneis   The aneis
     * @return     {<type>}  { description_of_the_return_value }
     */
    var validaCamposRequired = function(aneis) {
      aneis.forEach(function(anel) {
        inicializaValidador(anel);

        REQUIRED.forEach(function(field) {
          anel.valid.required[field] = angular.isDefined(anel[field]) && anel[field] !== null;
          anel.valid.form = anel.valid.form && anel.valid.required[field];
        });
      });

      return retornaStatusFormulario(aneis);
    };

    /**
     * Verifica se cada formulário de anel possui ao menos um grupo semafórico (grupoPedestre + grupoVeicular).
     * Caso um destes formulários não complete este requisito, todo o formulário (ou o conjunto
     * de formulários de aneis) estará inválido.
     *
     * @param      {<type>}   aneis        The aneis
     * @return     {boolean}  { description_of_the_return_value }
     */
    var validaAneisPossuemAoMenosUmGrupo = function(aneis) {
      aneis.forEach(function(anel) {
        var anelValido = parseFloat(anel.quantidadeGrupoPedestre) + parseFloat(anel.quantidadeGrupoVeicular) > 0;

        anel.valid.required.minGrupos = anelValido;
        anel.valid.form = anel.valid.form && anelValido;
      });

      return retornaStatusFormulario(aneis);
    };

    /**
     * Este método testa se a quantidade de grupos semafóricos (grupoPedestre + grupoVeicular)
     * somam um valor menor que o máximo suportado pelo controlador.
     *
     * @param      {<type>}  aneis        The aneis
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var validaQuantidadeGruposSemaforicos = function(aneis, controlador) {
      var maxGruposSemaforicos = controlador.modelo.configuracao.limiteGrupoSemaforico;
      var totalGruposSemaforicos = aneis.reduce(function(a, b) {
        return a + (b.quantidadeGrupoPedestre || 0) + (b.quantidadeGrupoVeicular || 0);
      }, 0);

      aneis[0].valid.totalGruposSemaforicos = maxGruposSemaforicos >= totalGruposSemaforicos;
      return aneis[0].valid.totalGruposSemaforicos;
    };

    /**
     * Este método testa se a quantidade de detectores veiculares
     * é um valor menor que o máximo suportado pelo controlador.
     *
     * @param      {<type>}  aneis        The aneis
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var validaQuantidadeDetectoresVeicular = function(aneis, controlador) {
      var maxDetectorVeicular = controlador.modelo.configuracao.limiteDetectorVeicular;
      var totalDetectorVeicular = aneis.reduce(function(a, b) {
        return a + (b.quantidadeDetectorVeicular || 0);
      }, 0);

      aneis[0].valid.totalDetectorVeicular = maxDetectorVeicular >= totalDetectorVeicular;
      return aneis[0].valid.totalDetectorVeicular;
    };

    /**
     * Este método testa se a quantidade de detectores de pedestres
     * é um valor menor que o máximo suportado pelo controlador.
     *
     * @param      {<type>}  aneis        The aneis
     * @param      {<type>}  controlador  The controlador
     * @return     {<type>}  { description_of_the_return_value }
     */
    var validaQuantidadeDetectoresPedestres = function(aneis, controlador) {
      var maxDetectorPedestres = controlador.modelo.configuracao.limiteDetectorPedestre;
      var totalDetectorPedestres = aneis.reduce(function(a, b) {
        return a + (b.quantidadeDetectorPedestre || 0);
      }, 0);

      aneis[0].valid.totalDetectorPedestres = maxDetectorPedestres >= totalDetectorPedestres;
      return aneis[0].valid.totalDetectorPedestres;
    };

    /**
     * Reune todas as validações necessárias no anel em uma interface apenas.
     *
     * @param      {<type>}   listaAneis   The lista aneis
     * @param      {<type>}   controlador  The controlador
     * @return     {boolean}  { description_of_the_return_value }
     */
    var valida = function(listaAneis, controlador) {
      var aneis = _.filter(listaAneis, {ativo: true});

      if (!aneis || !controlador) {
        return false;
      }

      var camposRequired = !!validaCamposRequired(aneis);
      var aneisPossuemAoMenosUmGrupo = !!validaAneisPossuemAoMenosUmGrupo(aneis, controlador);
      var quantidadeGruposSemaforicos = !!validaQuantidadeGruposSemaforicos(aneis, controlador);
      var quantidadeDetectoresVeicular = !!validaQuantidadeDetectoresVeicular(aneis, controlador);
      var quantidadeDetectoresPedestres = !!validaQuantidadeDetectoresPedestres(aneis, controlador);

      return camposRequired &&
        aneisPossuemAoMenosUmGrupo &&
        quantidadeGruposSemaforicos &&
        quantidadeDetectoresVeicular &&
        quantidadeDetectoresPedestres;
    };

    /**
     * Este método retorna uma lista de mensagens de validação que são reconhecidas por este validador.
     * Elas são exibidas em escopo de anel (ou seja, são validações que ficam no topo da página e correspondem
     * a cada formulário de anel).
     *
     * @param      {<type>}  aneis   The aneis
     * @return     {Array}   { description_of_the_return_value }
     */
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
