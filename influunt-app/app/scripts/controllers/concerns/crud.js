'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:CrudCtrl
 * @description
 * # CrudCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('CrudCtrl', ['$scope', '$state', '$filter', 'Restangular', 'toast', 'SweetAlert',
    function ($scope, $state, $filter, Restangular, toast, SweetAlert) {
    var resourceName = null;

    /**
     * Inicializa novo crud.
     *
     * @param      {string}  param   Nome base para a rota do crud.
     */
    $scope.inicializaNovoCrud = function(param) {
      resourceName =  param;
    };

    /**
     * Lista todas os objetos cadastrados no resource.
     *
     * @return     {Object}  Promise
     */
    $scope.index = function() {
      return Restangular.all(resourceName).getList()
        .then(function(res) {
          $scope.lista = res;
        })
        .catch(function(err) {
          toast.error(err);
          throw new Error(err);
        });
    };

    /**
     * Retorna objeto a partir do id.
     *
     * @return     {Object}  Promise
     */
    $scope.show = function() {
      $scope.beforeShow();
      var id = $state.params.id;

      return Restangular.one(resourceName, id).get()
        .then(function(res) {
          $scope.minid = (typeof res.id === 'string') ? res.id.split('-')[0] : res.id;
          $scope.objeto = res;
          $scope.afterShow();
        });
    };

    /**
     * Inicializa o objeto a ser salvo.
     */
    $scope.new = function() {
      $scope.beforeShow();
      $scope.objeto = {};
      $scope.afterShow();
    };

    /**
     * Salva o registro.
     *
     * @return     {Object}  Promise
     */
    $scope.save = function() {
      var promise = $scope.objeto.id ? $scope.update() : $scope.create();
      return promise
        .then(function() {
          $state.go('app.' + resourceName);
          toast.success($filter('translate')('geral.mensagens.salvo_com_sucesso'));
        })
        .catch(function(err) {
          toast.error($filter('translate')('geral.mensagens.default_erro'));
          throw new Error(JSON.stringify(err));
        });
    };

    /**
     * Cria um novo registro.
     */
    $scope.create = function() {
      return Restangular.service(resourceName).post($scope.objeto);
    };

    /**
     * Atualiza um registro.
     */
    $scope.update = function() {
      return $scope.objeto.save();
    };

    $scope.confirmDelete = function(id) {
      SweetAlert.swal(
        {
          title: $filter('translate')('geral.mensagens.popup_delete.titulo'),
          text: $filter('translate')('geral.mensagens.popup_delete.mensagem'),
          showCancelButton: true,
          confirmButtonColor: '#DD6B55',
          confirmButtonText: $filter('translate')('geral.mensagens.sim'),
          cancelButtonText: $filter('translate')('geral.mensagens.cancelar'),
          closeOnConfirm: true,
          closeOnCancel: true
        }, function (confirmado) {
          return confirmado && Restangular.one(resourceName, id).remove()
            .then(function() {
              toast.success($filter('translate')('geral.mensagens.removido_com_sucesso'));
              return $scope.index();
            })
            .catch(function(err) {
              toast.error($filter('translate')('geral.mensagens.default_erro'));
              throw new Error(err);
            });
      });
    };

    // callbacks
    /**
     * Implementação de callbacks para o crud base.
     */
    $scope.beforeShow = function() {};

    /**
     * Implementação de callbacks para o crud base.
     */
    $scope.afterShow = function() {};

  }]);
