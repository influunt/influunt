'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:ControladoresCtrl
 * @description
 * # ControladoresCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('ControladoresCtrl', ['$controller', '$scope', '$state','Restangular', 'validacoesAneis',
    function ($controller, $scope, $state, Restangular, validacoesAneis) {

      // Herda todo o comportamento do crud basico.
      $controller('CrudCtrl', {$scope: $scope});
      $scope.inicializaNovoCrud('controladores');
      $scope.hideRemoveCoordenada = true;

      /**
       * Carrega as listas de dependencias dos controladores. Atua na tela de crud.
       */
      $scope.beforeShow = function() {
        Restangular.all('areas').getList().then(function(res) {
          $scope.areas = res;
        });
      };

      /**
       * Inicializa o objeto de coordenadas do controlador, caso este ainda não
       * tenha sido definido. Atua na tela de crud.
       */
      $scope.afterShow = function() {
        var coordenadaDefault = {
          latitude: null,
          longitude: null
        };

        $scope.objeto.coordenada = $scope.objeto.coordenada || coordenadaDefault;
        $scope.coordenada = $scope.objeto.coordenada;
      };

      $scope.initWizard = function() {
        $scope.getHelpersControlador();
        $scope.objeto = {};
        $scope.validacoes = {
          alerts: []
        };
        $scope.helpers = {};
      };

      $scope.submitForm = function(form, nextStep) {
        $scope.submited = true;
        if (form.$valid) {
          Restangular
            .all('controladores')
            .all('dados_basicos')
            .post($scope.objeto)
            .then(function(res) {
              console.log(res);
              $scope.objeto = res;
              $scope.submited = false;

              $state.go(nextStep);
            })
            .catch(function(res) {
              var messages = res.data.map(function(a) {
                return {
                  msg: 'validacoesAPI.' + _.lowerCase(a.root) + '.' + _.camelCase(a.message),
                  params: {
                    CAMPO: a.path
                  }
                };
              });

              $scope.validacoes.alerts = messages;
            });

          // $scope.objeto.idControlador = '1234567';
          // $scope.submited = false;
        }
      };

      $scope.inicializaAneis = function() {
        var numAneis = $scope.objeto.modelo.configuracao.limiteAnel;
        $scope.aneis = $scope.aneis || _.times(numAneis, 0).map(function(value, key) {
          return {
            checked: (key === 0),
            id_anel: $scope.objeto.idControlador + '-' + (key + 1),
            grupos_pedestres: null,
            grupos_sinais_veiculares: null,
            numero_detectores: null,
            numero_detectores_pedestres: null,
            descricao: null,
            numero_smee: null,
            latitude: null,
            longitude: null,
            valid: {
              form: true,
              required: {}
            }
          };
        });

        $scope.currentAnelId = 0;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelId];
      };

      $scope.selecionaAnel = function(index) {
        $scope.currentAnelId = index;
        $scope.currentAnel = $scope.aneis[$scope.currentAnelId];
      };

      $scope.submitFormAneis = function(nextStep) {
        if (validacoesAneis.valida($scope.aneis, $scope.objeto)) {
          $scope.submitForm({$valid: true}, nextStep);
        } else {
          $scope.validacoes.alerts = validacoesAneis.retornaMensagensValidacao($scope.aneis);
        }
      };

      $scope.closeAlert = function() {
        $scope.validacoes.alerts = [];
      };

      $scope.getHelpersControlador = function() {
        Restangular.all('helpers').all('controlador').customGET().then(function(res) {
          $scope.data = res;
          $scope.helpers = {cidade: $scope.data.cidades[0]};
          $scope.objeto = {area: $scope.helpers.cidade.areas[0]};
        });
      };

      /**
       * Filtra controladores baseado nos checkboxes
       * nos filtros à esquerda.
       */
      $scope.filtrarControlador = function(controlador) {
        if ($scope.filtroLateral[controlador.id]) {
          return true;
        }

        for (var controlador_id in $scope.filtroLateral) {
          if ($scope.filtroLateral[controlador_id]) {
            return false;
          }
        }
        return true;
      };

      $scope.inicializa_index = function(){
        $scope.filtros = {};
        $scope.filtroLateral = {};
        $scope.index();
      };

      $scope.inicializaVerdesConflitantes = function() {
        $scope.aneis = [{"checked": true,"id_anel": "1234567-1","grupos_pedestres": 12,"grupos_sinais_veiculares": 1,"numero_detectores": null,"numero_detectores_pedestres": null,"descricao": "sdfd","numero_smee": null,"latitude": 123,"longitude": 123,"valid": {"form": true,"required": {"descricao": true,"latitude": true,"longitude": true},"totalGruposSemaforicos": true,"totalDetectorVeicular": true,"totalDetectorPedestres": true},"$$hashKey": "object:7","grupos_semaforicos": [{"attr1": "value_1"},{"attr1": "value_1"},{"attr1": "value_1"},{"attr1": "value_1"}]},{"checked": true,"id_anel": "1234567-2","grupos_pedestres": 1,"grupos_sinais_veiculares": null,"numero_detectores": null,"numero_detectores_pedestres": null,"descricao": "1231","numero_smee": null,"latitude": 123,"longitude": 123,"valid": {"form": true,"required": {"descricao": true,"latitude": true,"longitude": true}},"grupos_semaforicos": [{"attr1": "value_1"},{"attr1": "value_1"}]},{"checked": true,"id_anel": "1234567-2","grupos_pedestres": 1,"grupos_sinais_veiculares": null,"numero_detectores": null,"numero_detectores_pedestres": null,"descricao": "1231","numero_smee": null,"latitude": 123,"longitude": 123,"valid": {"form": true,"required": {"descricao": true,"latitude": true,"longitude": true}},"grupos_semaforicos": [{"attr1": "value_1"},{"attr1": "value_1"}]},{"checked": true,"id_anel": "1234567-2","grupos_pedestres": 1,"grupos_sinais_veiculares": null,"numero_detectores": null,"numero_detectores_pedestres": null,"descricao": "1231","numero_smee": null,"latitude": 123,"longitude": 123,"valid": {"form": true,"required": {"descricao": true,"latitude": true,"longitude": true}},"grupos_semaforicos": [{"attr1": "value_1"},{"attr1": "value_1"}]}];
        $scope.grupos = _.times(16, function(i) {return 'G' + (i+1);});

        var aneis = _.filter($scope.aneis, {checked: true});
        var somador = 0;
        $scope.intervalosAneis = aneis.map(function(anel) {
          somador += anel.grupos_semaforicos.length;
          return somador;
        });
        $scope.intervalosAneis.unshift(0);
        $scope.gruposUtilizados = $scope.intervalosAneis[$scope.intervalosAneis.length - 1];

        $scope.verdesConflitantes = [];
        for (var i = 0; i < $scope.grupos.length; i++) {
          for (var j = 0; j < $scope.grupos.length; j++) {
            $scope.verdesConflitantes[i] = $scope.verdesConflitantes[i] || [];
            $scope.verdesConflitantes[i][j] = false;
          }
        }
      };

      $scope.toggleVerdeConflitante = function(x, y, disabled) {
        if (disabled) {
          return false;
        }

        $scope.verdesConflitantes[x][y] = !$scope.verdesConflitantes[x][y];
      };

    }]);
