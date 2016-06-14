'use strict';

/**
 * @ngdoc function
 * @name influuntApp:CidadesCtrl
 * @description
 * # CidadesCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('CidadesCtrl', ['$scope', '$state', '$filter', 'Restangular', 'toast', 'SweetAlert',
    function ($scope, $state, $filter, Restangular, toast, SweetAlert) {
      /**
       * Lista todas as cidades cadastradas no sistema.
       *
       * @return     {Object}  Promise
       */
      $scope.index = function() {
        return Restangular.all('cidades').getList()
          .then(function(cidades) {
            $scope.cidades = cidades;
          })
          .catch(function(res) {
            toast.error(res);
          });
      };

      /**
       * Retorna objeto a partir do id.
       *
       * @return     {Object}  Promise
       */
      $scope.show = function() {
        var id = $state.params.id;

        return Restangular.one('cidades', id).get()
          .then(function(response) {
            $scope.minid = response.id.split('-')[0];
            $scope.cidade = response;
          });
      };

      /**
       * Inicializa a variavel de cidade.
       */
      $scope.new = function() {
        $scope.cidade = {};
      };

      /**
       * Salva o registro.
       *
       * @return     {Object}  Promise
       */
      $scope.save = function() {
        var promise = $scope.cidade.id ? $scope.update() : $scope.create();
        return promise
          .then(function() {
            $state.go('app.cidades');
            toast.success($filter('translate')('geral.mensagens.salvo_com_sucesso'));
          })
          .catch(function() {
            toast.error($filter('translate')('geral.mensagens.default_erro'));
          });
      };

      /**
       * Cria um novo registro.
       */
      $scope.create = function() {
        return Restangular.service('cidades').post($scope.cidade);
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
            return confirmado && Restangular.one('cidades', id).remove()
              .then(function() {
                toast.success($filter('translate')('geral.mensagens.removido_com_sucesso'));
                return $scope.index();
              })
              .catch(function() {
                toast.error($filter('translate')('geral.mensagens.default_erro'));
              });
        });
      };

      /**
       * Atualiza um registro.
       */
      $scope.update = function() {
        return $scope.cidade.save();
      };

      $scope.addArea = function() {
        $scope.cidade.areas = $scope.cidade.areas || [];
        $scope.cidade.areas.push({
          descricao: ''
        });
      };
    }]);
