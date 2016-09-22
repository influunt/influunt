'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:BreadcrumbsCtrl
 * @description
 * # BreadcrumbsCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('BreadcrumbsCtrl', ['$scope', '$state', 'breadcrumbs', 'Restangular',
    function BreadcrumbsCtrl($scope, $state, breadcrumbs, Restangular) {
    $scope.DEFAULT_PAGE_TITLE = 'geral.titulo_padrao';

    /**
     * Atualiza os breadcrumbs a cada alteração de rota.
     */
    $scope.$on('$stateChangeSuccess', function(event, toState){
      $scope.udpateBreadcrumbs(toState);
    });

    $scope.setControladorEndereco = function() {
      var id = $state.params.id;

      Restangular.one('controladores', id).get().then(function(res) {
        $scope.controladorLocalizacao = res.nomeEndereco;
      });

    };

    /**
     * Função é necessária para evitar que fique pesquisando todos controladores
     *
     * toda vez que entrar em uma tela, irá pesquisar apenas nos controllers .
     *
     * especificados
     */

    $scope.filterControllers = function(state) {
      var controllersName = ['Controladores', 'TabelaHorarios', 'Planos'];
      var result = false;
      
        if (state !== undefined && state.controller !== undefined) { 
          var stateController = state.controller;
          result = _.some(controllersName, function(controllerName){
            return stateController.indexOf(controllerName) > -1; 
          });
        }
      return result;
    };

    /**
     * Função executada a cada alteração de state dentro do contexto do `app`.
     *
     * Esta função deverá atulizar os breadcrumbs das telas.
     *
     * @param      {<type>}  state   The state
     */
    $scope.udpateBreadcrumbs = function (state) {
      state = state || $state.current;
      $scope.pageTitle = state.data && state.data.title || $scope.DEFAULT_PAGE_TITLE;
      $scope.breadcrumbs = breadcrumbs.path(state);
      var temControlador = $scope.filterControllers(state);

      if (temControlador) {
        $scope.setControladorEndereco();  
      } else {
        $scope.controladorLocalizacao = null;
      }
    };
  }]);
