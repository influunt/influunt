'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresAssociacaoCtrl
 * @description
 * # ControladoresAssociacaoCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresRevisaoCtrl', ['$scope', '$state', '$controller', 'assertControlador',
    function ($scope, $state, $controller, assertControlador) {
      $controller('ControladoresCtrl', {$scope: $scope});

      /**
       * Pré-condições para acesso à tela de revisao: Somente será possível acessar esta
       * tela se o objeto possuir estágios. Os estágios são informados no passo anterior, o
       * passo de aneis.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertRevisao = function() {
        var valid = assertControlador.hasAneis($scope.objeto) && assertControlador.hasEstagios($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.revisao', {id: $scope.objeto.id});
        }

        return valid;
      };

      var getCidade = function() {
        var area = _.find($scope.objeto.areas, {idJson:$scope.objeto.area.idJson});
        var cidade = _.find($scope.objeto.cidades, {idJson: area.cidade.idJson});
        $scope.cidade = cidade.nome;
        $scope.area = area.descricao;
        //return cidade;
      };

      var getAneisAtivos = function() {
        $scope.AneisAtivos = _.filter($scope.objeto.aneis, { ativo: true });
        $scope.codigoCLA = $scope.AneisAtivos[0].CLA;
        $scope.qtdeAneisAtivos = $scope.AneisAtivos.length;
        $scope.numeroSMEE = $scope.AneisAtivos[0].numeroSMEE;

        //semaforicos
        var semaforico = '';
        $scope.AneisAtivos[0].gruposSemaforicos.forEach(function(e) {
          semaforico = _.find($scope.objeto.gruposSemaforicos, {idJson: e.idJson});
          //console.log(semaforico);
        });

        $scope.semaforicoPedestres = _.filter(semaforico, {tipo : 'PEDESTRE'});
        $scope.semaforicoVeiculares= _.filter(semaforico, {tipo : 'VEICULAR'});

        //enderecos
        var local = [];
        $scope.AneisAtivos[0].enderecos.forEach(function(e) {
          local = _.find($scope.objeto.todosEnderecos[0], {idJson: e.idJson});
          console.log(local);
        });
        //$scope.localizacao = local.localizacao;

        //detectores
        $scope.AneisAtivos[0].detectores.forEach(function(e) {
          var detector = _.find($scope.objeto.detectores, {idJson: e.idJson});
           console.log(JSON.stringify(detector));
           $scope.detectoresPedestre = _.filter(detector, {tipo : 'PEDESTRE'});
           $scope.detectoresVeiculares = _.filter(detector, {tipo : 'PEDESTRE'});
        });
      };



      $scope.inicializaRevisao = function() {
        return $scope.inicializaWizard().then(function() {

          $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
          $scope.aneis = _.filter($scope.objeto.aneis, 'ativo');

          getCidade();
          $scope.qtdeDetectoresVeiculares = _.filter($scope.objeto.detectores, {tipo : 'VEICULAR'});
          $scope.qtdeDetectoresVeiculares = $scope.qtdeDetectoresVeiculares.length;
          $scope.qtdeDetectoresPedestres = _.filter($scope.objeto.detectores, {tipo : 'PEDESTRE'});
          $scope.qtdeDetectoresPedestres = $scope.qtdeDetectoresPedestres.length;
          $scope.fabricante = $scope.objeto.modelo.fabricante.nome;
          //$scope.modelo = $scope.objeto.modelo.descricao;

          getAneisAtivos();
          $scope.selecionaAnel(0);

      });
    };

   //console.log(JSON.stringify(anel))

    }]);
