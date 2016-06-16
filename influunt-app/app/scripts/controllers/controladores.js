'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresCtrl
 * @description
 * # ControladoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresCtrl', ['$scope', 'Restangular',
    function ($scope, Restangular) {
      $scope.initWizard = function() {
        Restangular
          .all('helpers')
          .all('controlador')
          .customGET()
          .then(function(res) {
            $scope.data = res;
          });

        $scope.objeto = {};
        $scope.helpers = {};
      };

      $scope.submitDadosBasicos = function() {
        console.log($scope.submitDadosValidos);
        console.log($scope.submitDadosValidos.$valid);
      };


      var resourceName = 'controladores';
      // Temporário. Deve ser substituído pela abstração de crud quando o PR for aceito.
      /**
       * Salva o registro.
       *
       * @return     {Object}  Promise
       */
      $scope.save = function() {
        var promise = $scope.objeto.id ? $scope.update() : $scope.create();
        return promise
          .then(function(res) {
            console.log(res);
            alert('geral.mensagens.salvo_com_sucesso');
          })
          .catch(function(err) {
            console.log(err);
            alert('geral.mensagens.default_erro');
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

    }]);
