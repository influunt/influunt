'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:CrudCtrl
 * @description
 * # CrudCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('CrudCtrl', ['$scope', '$state', '$filter', '$timeout',
                           'Restangular', 'toast', 'influuntAlert', 'handleValidations',
                           'influuntBlockui',
    function ($scope, $state, $filter, $timeout,
              Restangular, toast, influuntAlert, handleValidations,
              influuntBlockui) {
    var buildFilterQuery, buildSortQuery, buildFilterDataFields;
    var resourceName = null;
    $scope.pagination = {
      current: 1,
      perPage: 30,
      maxSize: 5
    };

    $scope.pesquisa = {
      orderField: '',
      orderReverse: true,
      campos: []
    };

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
      var query = $scope.buildQuery($scope.pesquisa);
      return Restangular.all(resourceName).customGET(null, query)
        .then(function(res) {
          $scope.lista = res.data;
          $scope.pagination.totalItems = res.total;
        })
        .finally(influuntBlockui.unblock);
    };

    $scope.buildQuery = function(pesquisa) {
      var query = {
        per_page: $scope.pagination.perPage,
        page: $scope.pagination.current - 1
      };

      buildFilterQuery(query, pesquisa);
      buildSortQuery(query, pesquisa);

      return query;
    };

    buildFilterQuery = function(query, pesquisa) {
      _.each(pesquisa.filtro, function(dadosFiltro, nomeCampo) {
        var field;
        if (dadosFiltro.tipoCampo === 'texto' || dadosFiltro.tipoCampo === 'numerico') {
          field = (nomeCampo + '_' + dadosFiltro.tipoFiltro).replace(/\_$/, '');
          query[field] = dadosFiltro.valor;
        } else if (dadosFiltro.tipoCampo === 'select') {
          field = (nomeCampo + '_eq');
          query[field] = dadosFiltro.valor;
        } else {
          buildFilterDataFields('start', dadosFiltro, query, nomeCampo);
          buildFilterDataFields('end', dadosFiltro, query, nomeCampo);
        }
      });
    };

    buildFilterDataFields = function(type, dadosFiltro, query, nomeCampo) {
      if (dadosFiltro[type]) {
        if(angular.isString(dadosFiltro[type])) {
          dadosFiltro[type] = moment(dadosFiltro[type], 'DD/MM/YYYY');
        }

        query[nomeCampo + '_' + type] = moment(dadosFiltro[type]).format('DD/MM/YYYY HH:mm:ss');
      }
    };

    buildSortQuery = function(query, pesquisa) {
      query.sort = pesquisa.orderField;
      query.sort_type = pesquisa.orderReverse ? 'desc' : 'asc';
    };

    var perPageTimeout = null;
    $scope.onPerPageChange = function() {
      $timeout.cancel(perPageTimeout);
      perPageTimeout = $timeout(function() {
        $scope.index();
      }, 500);
    };

    $scope.onPageChange = function() {
      return $scope.index();
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
          if (res) {
            $scope.minid = (typeof res.id === 'string') ? res.id.split('-')[0] : res.id;
            $scope.objeto = res;
            $scope.afterShow();
          }
        })
        .finally(influuntBlockui.unblock);
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
     * @param      {boolean}  formValido  The form valido
     * @return     {boolean}  { description_of_the_return_value }
     */
    $scope.save = function(formValido) {

      // Não deve tentar enviar os dados se há informações de formulário invalido.
      $scope.submited = true;
      if (angular.isDefined(formValido) && !formValido) {
        return false;
      }

      var promise = $scope.objeto.id ? $scope.update() : $scope.create();
      return promise
        .then(function() {
          $scope.submited = false;
          $state.go('app.' + resourceName);
          toast.success($filter('translate')('geral.mensagens.salvo_com_sucesso'));
        })
        .catch(function(err) {
          if (err.status === 422) {
            // $scope.errors = err.data;
            $scope.errors = handleValidations.handle(err.data);
          } else {
            toast.error($filter('translate')('geral.mensagens.default_erro'));
            throw new Error(JSON.stringify(err));
          }
        })
        .finally(influuntBlockui.unblock);
    };

    /**
     * Cria um novo registro.
     */
    $scope.create = function() {
      return Restangular
        .service(resourceName)
        .post($scope.objeto)
        .finally(influuntBlockui.unblock);
    };

    /**
     * Atualiza um registro.
     */
    $scope.update = function() {
      return $scope
        .objeto
        .save()
        .finally(influuntBlockui.unblock);
    };

    $scope.confirmDelete = function(id) {
      influuntAlert.delete().then(function(confirmado) {
        return confirmado && Restangular.one(resourceName, id).remove()
          .then(function() {
            toast.success($filter('translate')('geral.mensagens.removido_com_sucesso'));
            return $scope.index();
          })
          .catch(function() {
            toast.error($filter('translate')('geral.mensagens.default_erro'));
          })
          .finally(influuntBlockui.unblock);
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
