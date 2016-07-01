'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('MainCtrl', ['$scope', '$state', '$filter', '$controller', '$http', 'SweetAlert',
    function MainCtrl($scope, $state, $filter, $controller, $http, SweetAlert) {
      // Herda todo o comportamento de breadcrumbs.
      $controller('BreadcrumbsCtrl', {$scope: $scope});
      $controller('DatatablesCtrl', {$scope: $scope});

      $scope.sair = function() {
        SweetAlert.swal({
            title: $filter('translate')('geral.mensagens.sair'),
            text: $filter('translate')('geral.mensagens.confirma_saida'),
            showCancelButton: true,
            confirmButtonColor: '#DD6B55',
            confirmButtonText: $filter('translate')('geral.mensagens.sim'),
            cancelButtonText: $filter('translate')('geral.mensagens.cancelar'),
            closeOnConfirm: true,
            closeOnCancel: true
          }, function (confirmado) {
            if (confirmado) {
              localStorage.removeItem('token');
              $state.go('login');
            }
        });
      };

      $http.get('/json/menus.json').then(function(res) {
        $scope.menus = res.data;
      });
    }
  ]);
