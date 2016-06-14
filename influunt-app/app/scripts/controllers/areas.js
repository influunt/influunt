'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:AreasCtrl
 * @description
 * # AreasCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('AreasCtrl', ['$controller', '$scope', '$state', '$filter', 'Restangular', 'toast', 'SweetAlert',
    function ($controller, $scope, $state, $filter, Restangular, toast, SweetAlert) {

      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('areas');

      // /**
      //  * Lista todas as areas cadastradas no sistema.
      //  *
      //  * @return     {Object}  Promise
      //  */
      // $scope.index = function() {
      //   return Restangular.all('areas').getList()
      //     .then(function(areas) {
      //       $scope.areas = areas;
      //     })
      //     .catch(function(res) {
      //       toast.error(res);
      //     });
      // };

      // /**
      //  * Retorna objeto a partir do id.
      //  *
      //  * @return     {Object}  Promise
      //  */
      // $scope.show = function() {
      //   var id = $state.params.id;

      //   return Restangular.one('areas', id).get()
      //     .then(function(response) {
      //       $scope.minid = response.id.split('-')[0];
      //       $scope.area = response;
      //     });
      // };

      // /**
      //  * Inicializa a variavel de area.
      //  */
      // $scope.new = function() {
      //   $scope.area = {};
      // };

      // /**
      //  * Salva o registro.
      //  *
      //  * @return     {Object}  Promise
      //  */
      // $scope.save = function() {
      //   var promise = $scope.area.id ? $scope.update() : $scope.create();
      //   return promise
      //     .then(function() {
      //       $state.go('app.areas');
      //       toast.success($filter('translate')('geral.mensagens.salvo_com_sucesso'));
      //     })
      //     .catch(function() {
      //       toast.error($filter('translate')('geral.mensagens.default_erro'));
      //     });
      // };

      // /**
      //  * Cria um novo registro.
      //  */
      // $scope.create = function() {
      //   return Restangular.service('areas').post($scope.area);
      // };

      // /**
      //  * Atualiza um registro.
      //  */
      // $scope.update = function() {
      //   return $scope.area.save();
      // };

      // $scope.confirmDelete = function(id) {
      //   SweetAlert.swal(
      //     {
      //       title: $filter('translate')('geral.mensagens.popup_delete.titulo'),
      //       text: $filter('translate')('geral.mensagens.popup_delete.mensagem'),
      //       showCancelButton: true,
      //       confirmButtonColor: '#DD6B55',
      //       confirmButtonText: $filter('translate')('geral.mensagens.sim'),
      //       cancelButtonText: $filter('translate')('geral.mensagens.cancelar'),
      //       closeOnConfirm: true,
      //       closeOnCancel: true
      //     }, function (confirmado) {
      //       return confirmado && Restangular.one('areas', id).remove()
      //         .then(function() {
      //           toast.success($filter('translate')('geral.mensagens.removido_com_sucesso'));
      //           return $scope.index();
      //         })
      //         .catch(function() {
      //           toast.error($filter('translate')('geral.mensagens.default_erro'));
      //         });
      //   });
      // };

    }]);
