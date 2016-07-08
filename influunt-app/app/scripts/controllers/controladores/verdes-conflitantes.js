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

      var buildIntervaloAneis, buildMatrizVerdesConflitantes;

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
            $scope.objeto.aneis = _.orderBy($scope.objeto.aneis, ['posicao'], ['asc']);

            $scope.objeto.aneis.forEach(function(anel) {
              anel.gruposSemaforicos.forEach(function(gs) {
                gs.anel = {
                  id: anel.id
                };
              });
            });

            $scope.grupoIds = _.chain($scope.objeto.gruposSemaforicos).orderBy(['posicao'], ['asc']).map('id').value();
            var totalGrupos = $scope.objeto.modelo.configuracao.limiteGrupoSemaforico;
            $scope.grupos = _.times(totalGrupos, function(i) {return 'G' + (i+1);});

            buildIntervaloAneis();
            buildMatrizVerdesConflitantes();

            return $scope.objeto.gruposSemaforicos && $scope.objeto.gruposSemaforicos.forEach(function(gs) {
              return gs.verdesConflitantes && gs.verdesConflitantes.forEach(function(vc) {
                if (!$scope.verdesConflitantes[gs.posicao - 1][vc.posicao - 1]) {
                  $scope.toggleVerdeConflitante(gs.posicao - 1, vc.posicao - 1);
                }
              });
            });
          }
        });
      };

      $scope.toggleVerdeConflitante = function(x, y, disabled) {
        if (disabled) {
          return false;
        }

        var gruposAneis = _.chain($scope.objeto.aneis).map('gruposSemaforicos').flatten().value();
        var grupoX = _.find(gruposAneis, {id: $scope.grupoIds[x]});
        var grupoY = _.find(gruposAneis, {id: $scope.grupoIds[y]});

        if ($scope.verdesConflitantes[x][y]) {
          var indexX = _.findIndex(grupoX.verdesConflitantes, {id: $scope.grupoIds[y]});
          grupoX.verdesConflitantes.splice(indexX, 1);

          var indexY = _.findIndex(grupoY.verdesConflitantes, {id: $scope.grupoIds[x]});
          grupoY.verdesConflitantes.splice(indexY, 1);
        } else {
          grupoX.verdesConflitantes = grupoX.verdesConflitantes || [];
          grupoY.verdesConflitantes = grupoY.verdesConflitantes || [];
          grupoX.verdesConflitantes.push({
            id: $scope.grupoIds[y],
            anel: {
              id: grupoY.anel.id
            }
          });
          grupoY.verdesConflitantes.push({
            id: $scope.grupoIds[x],
            anel: {
              id: grupoX.anel.id
            }
          });
        }

        // Deve marcar/desmarcar os coordenadas (x, y) e (y, x) simultaneamente.
        $scope.verdesConflitantes[x][y] = !$scope.verdesConflitantes[x][y];
        $scope.verdesConflitantes[y][x] = !$scope.verdesConflitantes[y][x];
      };

      $scope.closeMensagensVerdes = function() {
        $scope.messages = [];
      };

      /**
       * Retorna um array com os intervalos de grupos para cada anel. Utilizado
       * somente para desenhar os limites dos aneis pelos grupos.
       */
      buildIntervaloAneis = function() {
        var aneis = _.filter($scope.objeto.aneis, {ativo: true});
        var somador = 0;
        $scope.intervalosAneis = aneis.map(function(anel) {
          somador += anel.gruposSemaforicos.length;
          return somador;
        });
        $scope.intervalosAneis.unshift(0);
        $scope.gruposUtilizados = $scope.intervalosAneis[$scope.intervalosAneis.length - 1];
      };

      /**
       * Constroi uma matriz quadrada para representação gráfica da tabela de
       * verdes conflitantes.
       */
      buildMatrizVerdesConflitantes = function() {
        $scope.verdesConflitantes = [];
        for (var i = 0; i < $scope.grupos.length; i++) {
          for (var j = 0; j < $scope.grupos.length; j++) {
            $scope.verdesConflitantes[i] = $scope.verdesConflitantes[i] || [];
            $scope.verdesConflitantes[i][j] = false;
          }
        }
      };

    }]);
