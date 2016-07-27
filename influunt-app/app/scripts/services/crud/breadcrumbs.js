'use strict';

/**
 * @ngdoc service
 * @name influuntApp.crud/breadcrumbs
 * @description
 * # crud/breadcrumbs
 *
 * Monta a estrutura de breadcrumbs do topo de todas as páginas.
 *
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('breadcrumbs', ['$state', function ($state) {
    /**
     * Monta a estrutura de breadcrumbs do topo de todas as páginas.
     *
     * Este método pesquisa recursivamente as rotas a partir da rota corrente até a
     * rota final desta estrutura (app).
     *
     * Este método deve retornar um array com a url e o título (rota para a tradução
     * do título).
     *
     * @param      {Object}  state       Estado atual.
     * @param      {Array}   breadcrumb  Array contendo o esquema de breadcrumbs atual.
     * @return     {Array}   The breadcrumb path.
     */
    var path = function(state, breadcrumb) {
      breadcrumb = breadcrumb || [];

      // Caso o estado seja abstrato (em geral, o último estado do breadcrumb se
      // encaixa neste contexto), o app deverá trabalhar com o estado alternativo,
      // determinado no campo data->redirectTo.
      var breadcrumbState = state.abstract ? $state.get(state.data.redirectTo) : state;
      breadcrumb.unshift({
        url: breadcrumbState.name,
        title: breadcrumbState.data.breadcrumb || breadcrumbState.data.title
      });

      // Verifica se a rota já é o estado final. Senão, ele deve rodar esta função
      // novamente, a fim de descrever toda a rota nos breadcrumbs.
      var splittedState = state.name.split('.');
      if (splittedState.length > 1) {
        splittedState.pop();
        var parentState = splittedState.join('.');
        parentState = $state.get(parentState);
        path(parentState, breadcrumb);
      }

      return breadcrumb;
    };


    return {
      path: path
    };
  }]);
