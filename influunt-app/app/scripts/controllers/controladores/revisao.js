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

      var getVerdesConfitantesOrigemDestino = function() {
          return $scope.objeto.gruposSemaforicos &&
            $scope.objeto.gruposSemaforicos.forEach(function(gs) {
                gs.verdesConflitantesOrigem.forEach(function(vec) {
                  var obj = _.find($scope.objeto.verdesConflitantes, vec);
                  var origem = _.find($scope.objeto.gruposSemaforicos, {idJson: obj.origem.idJson});
                  var destino = _.find($scope.objeto.gruposSemaforicos, {idJson: obj.destino.idJson});
                  //
                  $scope.verdesConflitantes[origem.posicao][destino.posicao] = true;
                  $scope.verdesConflitantes[destino.posicao][origem.posicao] = true;

                  console.log(JSON.stringify(gs));

              });
            });
      };


      var getDetectoresAnel = function() {
        var ids = _.map($scope.currentAnel.detectores, 'idJson');
        $scope.detectoresAnel = _
          .chain($scope.objeto.detectores)
          .filter(function(d) {
            return ids.indexOf(d.idJson) >= 0;
          })
          .orderBy(['tipo', 'posicao'])
          .value();

          $scope.DetectoresAnelPedestres = _.filter($scope.detectoresAnel, {tipo : 'PEDESTRE'}).length;
          $scope.DetectoresAnelVeiculos =  _.filter($scope.detectoresAnel, {tipo : 'VEICULAR'}).length;
          //console.log(JSON.stringify($scope.detectoresAnel));
        };

      var getLocalizacaoAnel = function() {
        var end1 = _.filter( $scope.objeto.todosEnderecos , {idJson: $scope.currentAnel.enderecos[0].idJson});
        var end2 = _.filter( $scope.objeto.todosEnderecos , {idJson: $scope.currentAnel.enderecos[1].idJson});
        return end2[0].localizacao + " com " + end1[0].localizacao;
      };

      var getEstagiosAnel = function() {
        var ids = _.map($scope.currentAnel.estagios, 'idJson');
        $scope.currentEstagios = _
          .chain($scope.objeto.estagios)
          .filter(function(d) {
            return ids.indexOf(d.idJson) >= 0;
          })
          .orderBy(['posicao'])
          .value();
      };

      var getGruposSemaforicosAnel = function() {
        var ids = _.map($scope.currentAnel.gruposSemaforicos, 'idJson');
        $scope.currentGruposSemaforicos = _
          .chain($scope.objeto.gruposSemaforicos)
          .filter(function(d) {
            return ids.indexOf(d.idJson) >= 0;
          })
          .orderBy(['posicao', 'tipo'])
          .value();
      };

      var getAneisAtivos = function () {
        $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
        var aneisAtivos = _.filter($scope.objeto.aneis, {ativo : true});
        $scope.qdteAneisAtivos = aneisAtivos.length;

        // Dados do 1 anel
        $scope.anelOne = aneisAtivos[0];
        $scope.currentAnel = aneisAtivos[0];
        //$scope.anelTo = aneisAtivos[1];
        //console.log(JSON.stringify($scope.anelOne));

        $scope.numeroSMEE = $scope.anelOne.numeroSMEE;
        $scope.CLA = $scope.anelOne.CLA;
        $scope.localizacao = getLocalizacaoAnel();
        $scope.detectoresAnel = getDetectoresAnel();
        getEstagiosAnel();
        getGruposSemaforicosAnel();

        //transicoesProibidas
        $scope.currentTransicoesProibidas = $scope.objeto.transicoesProibidas;
        //console.log(JSON.stringify($scope.currentTransicoesProibidas));

      };



      $scope.inicializaRevisao = function() {
        return $scope.inicializaWizard().then(function() {
            getAneisAtivos();

            var area = _.find($scope.objeto.areas, {idJson: $scope.objeto.area.idJson});
            var cidade = _.find($scope.objeto.cidades, {idJson: area.cidade.idJson});
            $scope.cidadeNome = cidade.nome;
            $scope.area = area.descricao;

            $scope.fabricante = $scope.objeto.modelo.fabricante.nome;
            $scope.modelo = $scope.objeto.modelo.descricao;
            $scope.qtdeEstagios = $scope.objeto.estagios.length;

            // Detectores - todos
            $scope.detectoresVeicular = _.filter($scope.objeto.detectores, {tipo : 'VEICULAR'});
            $scope.detectoresVeicular = $scope.detectoresVeicular.length;
            $scope.detectoresPedestre = _.filter($scope.objeto.detectores, {tipo : 'PEDESTRE'});
            $scope.detectoresPedestre = $scope.detectoresPedestre.length;

            // Grupos Semaforicos
            $scope.semaforicosVeicular = _.filter($scope.objeto.gruposSemaforicos, {tipo : 'VEICULAR'});
            $scope.semaforicosVeicular = $scope.semaforicosVeicular.length;
            $scope.semaforicosPedestre = _.filter($scope.objeto.gruposSemaforicos, {tipo : 'PEDESTRE'});
            $scope.semaforicosPedestre = $scope.semaforicosPedestre.length;

            // Verdes conflitantes
            getVerdesConfitantesOrigemDestino();

            // estagios



        });
      };

}]);
