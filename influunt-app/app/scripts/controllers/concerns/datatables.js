'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:DatatablesCtrl
 * @description
 * # DatatablesCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('DatatablesCtrl', ['$scope', 'DTOptionsBuilder',
    function ($scope, DTOptionsBuilder) {
      $scope.datatables = {
        instance: {},
        options: DTOptionsBuilder
                  .newOptions()
                  .withLanguageSource('//cdn.datatables.net/plug-ins/1.10.11/i18n/Portuguese-Brasil.json')
      };
    }]);
