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
      };
    }]);
