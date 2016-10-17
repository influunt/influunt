'use strict';

/**
* @ngdoc function
* @name influuntApp.controller:SimulacaoCtrl
* @description
* # SimulacaoCtrl
* Controller of the influuntApp
**/

angular.module('influuntApp')

.controller('SimulacaoCtrl', ['$scope', '$controller', 'Restangular', 'influuntBlockui', 'HorariosService', 'influuntAlert', '$filter', 'handleValidations', '$stateParams',
function ($scope, $controller, Restangular, influuntBlockui, HorariosService, influuntAlert, $filter, handleValidations, $stateParams) {

  var loadControladores, atualizaDetectores, atualizaPlanos, iniciarSimulacao;

  $scope.init = function() {
    loadControladores();

    $scope.velocidades = [
      { value: 0.25 },
      { value: 0.5 },
      { value: 1 },
      { value: 2 },
      { value: 4 },
      { value: 8 }
    ];

    $scope.parametrosSimulacao = { velocidade: 1, disparoDetectores: [{}], imposicaoPlanos: [{}], inicioControlador: moment(), 
                                  inicioSimulacao: moment(), fimSimulacao: moment().add(5,"minutes") };
  };

  loadControladores = function() {
    return Restangular.one('controladores').customGET('simulacao')
      .then(function(response) {
        if (response.data) {
          $scope.controladores = response.data;
          _.forEach($scope.controladores, function(controlador) {
            controlador.aneis = _.orderBy(controlador.aneis, 'posicao');
            _.forEach(controlador.aneis, function(anel) {
              anel.detectores = _.orderBy(anel.detectores, ['tipo', 'posicao']);
              _.forEach(anel.detectores, function(detector) {
                detector.nome = 'Anel ' + anel.posicao + ' - D' + detector.tipo[0] + detector.posicao;
              });
              _.forEach(anel.planos, function(plano) {
                plano.nome = 'Plano ' + plano.posicao;
              });
            });
          });

          var controladorId = $stateParams.idControlador;
          if (controladorId) {
            $scope.parametrosSimulacao.idControlador = controladorId;
          }
        }
      }).finally(influuntBlockui.unblock);
  };

  atualizaDetectores = function(controlador) {
    $scope.detectores = _
      .chain(controlador.aneis)
      .map('detectores')
      .flatten()
      .value();
  };

  atualizaPlanos = function(controlador) {
    $scope.planos = _
      .chain(controlador.aneis)
      .map('planos')
      .flatten()
      .uniqBy('posicao')
      .orderBy('posicao')
      .value();
  };

  $scope.$watch('parametrosSimulacao.idControlador', function(controladorId) {
    if (controladorId) {
      var controlador = _.find($scope.controladores, { id: controladorId });
      atualizaDetectores(controlador);
      atualizaPlanos(controlador);
    }
  });

  $scope.$watch('parametrosSimulacao.disparoDetectores', function(parametro) {
    if (parametro) {
      var length = $scope.parametrosSimulacao.disparoDetectores.length;
      var disparo = $scope.parametrosSimulacao.disparoDetectores[length - 1];
      if (disparo && disparo.detector && disparo.disparo) {
        $scope.parametrosSimulacao.disparoDetectores.push({});
      }
    }
  }, true);

  $scope.$watch('parametrosSimulacao.imposicaoPlanos', function(parametro) {
    if (parametro) {
      var length = $scope.parametrosSimulacao.imposicaoPlanos.length;
      var imposicao = $scope.parametrosSimulacao.imposicaoPlanos[length - 1];
      if (imposicao && imposicao.plano && imposicao.disparo) {
        $scope.parametrosSimulacao.imposicaoPlanos.push({});
      }
    }
  }, true);

  $scope.removerDisparoDetector = function(index) {
    var title = $filter('translate')('simulacao.detectorAlert.title'),
        text = $filter('translate')('simulacao.detectorAlert.text');
    return influuntAlert.confirm(title, text)
      .then(function(confirmado) {
        if (confirmado) {
          if ($scope.parametrosSimulacao.disparoDetectores.length > 1) {
            $scope.parametrosSimulacao.disparoDetectores.splice(index, 1);
          } else {
            $scope.parametrosSimulacao.disparoDetectores = [{}];
          }
        }
      });
  };

  $scope.removerImposicaoPlano = function(index) {
    var title = $filter('translate')('simulacao.planoAlert.title'),
        text = $filter('translate')('simulacao.planoAlert.text');
    return influuntAlert.confirm(title, text)
      .then(function(confirmado) {
        if (confirmado) {
          if ($scope.parametrosSimulacao.imposicaoPlanos.length > 1) {
            $scope.parametrosSimulacao.imposicaoPlanos.splice(index, 1);
          } else {
            $scope.parametrosSimulacao.imposicaoPlanos = [{}];
          }
        }
      });
  };

  $scope.submitForm = function() {
    $scope.parametrosSimulacao.disparoDetectores = _.filter($scope.parametrosSimulacao.disparoDetectores, 'detector');
    $scope.parametrosSimulacao.imposicaoPlanos = _.filter($scope.parametrosSimulacao.imposicaoPlanos, 'plano');

    return Restangular.all('simulacao').post($scope.parametrosSimulacao)
      .then(function(response) {
        $scope.errors = {};
        iniciarSimulacao($scope.parametrosSimulacao, response);
      })
      .catch(function(response) {
        if (response.status === 422) {
          $scope.errors = handleValidations.buildValidationMessages(response.data);
          if (_.isEmpty($scope.parametrosSimulacao.disparoDetectores)) {
            $scope.parametrosSimulacao.disparoDetectores.push({});
          }
          if (_.isEmpty($scope.parametrosSimulacao.imposicaoPlanos)) {
            $scope.parametrosSimulacao.imposicaoPlanos.push({});
          }
        } else {
          console.error(response);
        }
      })
      .finally(influuntBlockui.unblock);
  };

  iniciarSimulacao = function(params, config) {
    var inicioSimulacao = moment(params.inicioSimulacao),
        fimSimulacao = moment(params.fimSimulacao),
        velocidade = params.velocidade;
    return new influunt.components.Simulador(inicioSimulacao, fimSimulacao, velocidade, config);
  };

}]);
