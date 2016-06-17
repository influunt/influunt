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
          $state.go(nextStep);

          $scope.objeto.idControlador = '1234567';

          // $scope.save()
          //   .then(function(res) {
          //     console.log(res)
          //     $state.go(nextStep);
          //   })
          //   .catch(function(err) {
          //     console.log(err);
          //   });

          $scope.submited = false;
        }
      };

      $scope.inicializaAneis = function() {
        $scope.objeto = {"idControlador": "1234567", "area": {"id": "4b6079e0-02db-4d78-a420-d66fdf5fc76f","descricao": "Barreiro","limitesGeograficos": [],"dataCriacao": null,"dataAtualizacao": null,"$$hashKey": "object:48"},"descricao": "sdfsad","numeroSMEE": "asdfa","numeroSMEEConjugado1": "asdfa","numeroSMEEConjugado2": "asfda","numeroSMEEConjugado3": "asdfa","firmware": "asdfa","coordenada": {"latitude": "12","longitude": "12"},"modelo": {"id": "2ee0c714-adcb-4ede-90d0-b1224896d0a9","configuracao": {"id": "919c93c4-da97-44ac-864b-055d573b7e41","limiteEstagio": 4,"limiteGrupoSemaforico": 16,"limiteAnel": 4,"limiteDetectorPedestre": 4,"limiteDetectorVeicular": 4,"dataCriacao": {"year": 2016,"dayOfMonth": 16,"dayOfWeek": 4,"era": 1,"dayOfYear": 168,"centuryOfEra": 20,"yearOfEra": 2016,"yearOfCentury": 16,"weekyear": 2016,"monthOfYear": 6,"weekOfWeekyear": 24,"hourOfDay": 10,"minuteOfHour": 55,"secondOfMinute": 56,"millisOfSecond": 0,"millisOfDay": 39356000,"secondOfDay": 39356,"minuteOfDay": 655,"zone": {"fixed": false,"uncachedZone": {"fixed": false,"cachable": true,"id": "America/Sao_Paulo"},"id": "America/Sao_Paulo"},"millis": 1466085356000,"chronology": {"zone": {"fixed": false,"uncachedZone": {"fixed": false,"cachable": true,"id": "America/Sao_Paulo"},"id": "America/Sao_Paulo"}},"afterNow": false,"beforeNow": true,"equalNow": false},"dataAtualizacao": {"year": 2016,"dayOfMonth": 16,"dayOfWeek": 4,"era": 1,"dayOfYear": 168,"centuryOfEra": 20,"yearOfEra": 2016,"yearOfCentury": 16,"weekyear": 2016,"monthOfYear": 6,"weekOfWeekyear": 24,"hourOfDay": 10,"minuteOfHour": 55,"secondOfMinute": 56,"millisOfSecond": 0,"millisOfDay": 39356000,"secondOfDay": 39356,"minuteOfDay": 655,"zone": {"fixed": false,"uncachedZone": {"fixed": false,"cachable": true,"id": "America/Sao_Paulo"},"id": "America/Sao_Paulo"},"millis": 1466085356000,"chronology": {"zone": {"fixed": false,"uncachedZone": {"fixed": false,"cachable": true,"id": "America/Sao_Paulo"},"id": "America/Sao_Paulo"}},"afterNow": false,"beforeNow": true,"equalNow": false}},"descricao": "modelo B","dataCriacao": null,"dataAtualizacao": null,"$$hashKey": "object:81"}};
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
          // $scope.objeto = {area: $scope.helpers.cidade.areas[0]};
          $scope.objeto = {"idControlador": "1234567", "area": {"id": "4b6079e0-02db-4d78-a420-d66fdf5fc76f","descricao": "Barreiro","limitesGeograficos": [],"dataCriacao": null,"dataAtualizacao": null,"$$hashKey": "object:48"},"descricao": "sdfsad","numeroSMEE": "asdfa","numeroSMEEConjugado1": "asdfa","numeroSMEEConjugado2": "asfda","numeroSMEEConjugado3": "asdfa","firmware": "asdfa","coordenada": {"latitude": "12","longitude": "12"},"modelo": {"id": "2ee0c714-adcb-4ede-90d0-b1224896d0a9","configuracao": {"id": "919c93c4-da97-44ac-864b-055d573b7e41","limiteEstagio": 4,"limiteGrupoSemaforico": 16,"limiteAnel": 4,"limiteDetectorPedestre": 4,"limiteDetectorVeicular": 4,"dataCriacao": {"year": 2016,"dayOfMonth": 16,"dayOfWeek": 4,"era": 1,"dayOfYear": 168,"centuryOfEra": 20,"yearOfEra": 2016,"yearOfCentury": 16,"weekyear": 2016,"monthOfYear": 6,"weekOfWeekyear": 24,"hourOfDay": 10,"minuteOfHour": 55,"secondOfMinute": 56,"millisOfSecond": 0,"millisOfDay": 39356000,"secondOfDay": 39356,"minuteOfDay": 655,"zone": {"fixed": false,"uncachedZone": {"fixed": false,"cachable": true,"id": "America/Sao_Paulo"},"id": "America/Sao_Paulo"},"millis": 1466085356000,"chronology": {"zone": {"fixed": false,"uncachedZone": {"fixed": false,"cachable": true,"id": "America/Sao_Paulo"},"id": "America/Sao_Paulo"}},"afterNow": false,"beforeNow": true,"equalNow": false},"dataAtualizacao": {"year": 2016,"dayOfMonth": 16,"dayOfWeek": 4,"era": 1,"dayOfYear": 168,"centuryOfEra": 20,"yearOfEra": 2016,"yearOfCentury": 16,"weekyear": 2016,"monthOfYear": 6,"weekOfWeekyear": 24,"hourOfDay": 10,"minuteOfHour": 55,"secondOfMinute": 56,"millisOfSecond": 0,"millisOfDay": 39356000,"secondOfDay": 39356,"minuteOfDay": 655,"zone": {"fixed": false,"uncachedZone": {"fixed": false,"cachable": true,"id": "America/Sao_Paulo"},"id": "America/Sao_Paulo"},"millis": 1466085356000,"chronology": {"zone": {"fixed": false,"uncachedZone": {"fixed": false,"cachable": true,"id": "America/Sao_Paulo"},"id": "America/Sao_Paulo"}},"afterNow": false,"beforeNow": true,"equalNow": false}},"descricao": "modelo B","dataCriacao": null,"dataAtualizacao": null,"$$hashKey": "object:81"}};
        });
      };

    }]);
