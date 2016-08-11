'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresVerdesConflitantesCtrl
 * @description
 * # ControladoresVerdesConflitantesCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresVerdesConflitantesCtrl', ['$scope', '$state', '$controller', 'assertControlador',
    function ($scope, $state, $controller, assertControlador) {
      $controller('ControladoresCtrl', {$scope: $scope});

      var buildMatrizVerdesConflitantes, inicializaMatrizVerdesConflitantes;

      /**
       * Pré-condições para acesso à tela de verdes conflitantes: Somente será possível acessar
       * esta tela se o objeto possuir grupos semafóricos relacionados. Isto é feito no passo anterior,
       * na tela de associações.
       *
       * @return     {boolean}  { description_of_the_return_value }
       */
      $scope.assertVerdesConflitantes = function() {
        var valid = assertControlador.hasAneis($scope.objeto) && assertControlador.hasEstagios($scope.objeto);
        if (!valid) {
          $state.go('app.wizard_controladores.associacao', {id: $scope.objeto.id});
        }

        return valid;
      };

      $scope.inicializaVerdesConflitantes = function() {
        return $scope.inicializaWizard().then(function() {
          if ($scope.assertVerdesConflitantes()) {
            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao']);
            $scope.aneis = _.filter($scope.objeto.aneis, {ativo: true});

            $scope.objeto.gruposSemaforicos = _.orderBy($scope.objeto.gruposSemaforicos, ['posicao']);
            $scope.grupoIds = _.map($scope.objeto.gruposSemaforicos, 'idJson');

            $scope.selecionaAnelVerdesConflitantes(0);
            buildMatrizVerdesConflitantes();
            inicializaMatrizVerdesConflitantes();
          }
        });
      };

      $scope.toggleVerdeConflitante = function(x, y, disabled) {
        if (disabled) {
          return false;
        }

        var grupoX = _.find($scope.currentGruposSemaforicos, {idJson: $scope.grupoIds[x]});
        var grupoY = _.find($scope.currentGruposSemaforicos, {idJson: $scope.grupoIds[y]});
        var verdeConflitante = {
          idJson: UUID.generate(),
          origem: {
            idJson: grupoX.idJson
          },
          destino: {
            idJson: grupoY.idJson
          }
        };

        if ($scope.verdesConflitantes[x][y]) {

          var obj   = _.find(
            $scope.objeto.verdesConflitantes,
            {origem: verdeConflitante.origem, destino: verdeConflitante.destino}
          );
          var index = _.findIndex($scope.objeto.verdesConflitantes, obj);

          var indexX = _.findIndex(grupoX.verdesConflitantesOrigem, {idJson: obj.idJson});
          var indexY = _.findIndex(grupoY.verdesConflitantesDestino, {idJson: obj.idJson});
          grupoX.verdesConflitantesOrigem.splice(indexX, 1);
          grupoY.verdesConflitantesDestino.splice(indexY, 1);

          $scope.objeto.verdesConflitantes.splice(index, 1);

        } else {
          grupoX.verdesConflitantesOrigem = grupoX.verdesConflitantesOrigem || [];
          grupoX.verdesConflitantesOrigem.push({idJson: verdeConflitante.idJson});

          grupoY.verdesConflitantesDestino = grupoY.verdesConflitantesDestino || [];
          grupoY.verdesConflitantesDestino.push({idJson: verdeConflitante.idJson});

          $scope.objeto.verdesConflitantes = $scope.objeto.verdesConflitantes || [];
          $scope.objeto.verdesConflitantes.push(verdeConflitante);

        }

        // Deve marcar/desmarcar os coordenadas (x, y) e (y, x) simultaneamente.
        $scope.verdesConflitantes[x][y] = !$scope.verdesConflitantes[x][y];
        $scope.verdesConflitantes[y][x] = !$scope.verdesConflitantes[y][x];
      };

      $scope.closeMensagensVerdes = function() {
        $scope.messages = [];
      };

      $scope.selecionaAnelVerdesConflitantes = function(index) {
        $scope.selecionaAnel(index);
        $scope.atualizaGruposSemaforicos();
        $scope.atualizaEstagios();
      };

      /**
       * Constroi uma matriz quadrada para representação gráfica da tabela de
       * verdes conflitantes.
       */
      buildMatrizVerdesConflitantes = function() {
        $scope.verdesConflitantes = [];
        for (var i = 0; i < $scope.objeto.limiteGrupoSemaforico; i++) {
          for (var j = 0; j < $scope.objeto.limiteGrupoSemaforico; j++) {
            $scope.verdesConflitantes[i] = $scope.verdesConflitantes[i] || [];
            $scope.verdesConflitantes[i][j] = false;
          }
        }
      };

      /**
       * inicializa os dados da matriz de entreverdes.
       */
      inicializaMatrizVerdesConflitantes = function() {
        return $scope.objeto.gruposSemaforicos &&
          $scope.objeto.gruposSemaforicos.forEach(function(gs) {
            return gs.verdesConflitantesOrigem && gs.verdesConflitantesOrigem.forEach(function(vc) {
              var obj = _.find($scope.objeto.verdesConflitantes, vc);
              var origem = _.find($scope.objeto.gruposSemaforicos, {idJson: obj.origem.idJson});
              var destino = _.find($scope.objeto.gruposSemaforicos, {idJson: obj.destino.idJson});

              $scope.verdesConflitantes[origem.posicao - 1][destino.posicao - 1] = true;
              $scope.verdesConflitantes[destino.posicao - 1][origem.posicao - 1] = true;
            });
          });
      };

    }]);
