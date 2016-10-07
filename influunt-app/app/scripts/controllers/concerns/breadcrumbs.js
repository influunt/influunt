'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:BreadcrumbsCtrl
 * @description
 * # BreadcrumbsCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('BreadcrumbsCtrl', ['$scope', '$state', 'breadcrumbs',
    function BreadcrumbsCtrl($scope, $state, breadcrumbs) {
    $scope.DEFAULT_PAGE_TITLE = 'geral.titulo_padrao';

    /**
     * Atualiza os breadcrumbs a cada alteração de rota.
     */
    $scope.$on('$stateChangeSuccess', function(event, toState){
      breadcrumbs.removeNomeEndereco();
      $scope.udpateBreadcrumbs(toState);
    });

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
    };
  }]);
