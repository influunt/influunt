'use strict';

/**
* @ngdoc function
* @name influuntApp.controller:SimulacaoCtrl
* @description
* # SimulacaoCtrl
* Controller of the influuntApp
**/

angular.module('influuntApp')

.controller('SimulacaoCtrl', ['$scope', '$controller', 'Restangular', 'influuntBlockui', 'HorariosService', 'influuntAlert', '$filter', 'handleValidations', '$stateParams', 'MQTT_ROOT',
function ($scope, $controller, Restangular, influuntBlockui, HorariosService, influuntAlert, $filter, handleValidations, $stateParams, MQTT_ROOT) {

  var loadControlador, atualizaDetectores, atualizaPlanos, iniciarSimulacao;

  $scope.init = function() {
    var controladorId = $stateParams.idControlador;
    loadControlador(controladorId);

    $scope.velocidades = [
      { value: 0.25 },
      { value: 0.5 },
      { value: 1 },
      { value: 2 },
      { value: 4 },
      { value: 8 }
    ];

    $scope.parametrosSimulacao = { idControlador: controladorId,
                                   velocidade: 1,
                                   disparoDetectores: [{}],
                                   imposicaoPlanos: [{}] };

    var now = new Date();
    $scope.inicioControlador = { time: new Date(0, 0, 0, 0, 0, 0), date: new Date(new Date().setHours(0,0,0,0)) };

    $scope.inicioSimulacao = { time: new Date(0, 0, 0, 0, 0,0), date: new Date(new Date().setHours(0,0,0,0)) };

    $scope.fimSimulacao = { time: new Date(0, 0, 0, 0, 5, 0), date: new Date(new Date().setHours(0,0,0,0)) };

    $scope.disparosDetectores = { disparos: [] };
    $scope.imposicoesPlanos = { imposicoes: [] };

    $scope.horas = HorariosService.getHoras();
    $scope.minutos = HorariosService.getMinutos();
    $scope.segundos = HorariosService.getSegundos();

    $scope.dateOptions = {
      showWeeks: false
    };
  };

  loadControlador = function(controladorId) {
    console.log('vai loadar controlador. ID: ', controladorId)
    return Restangular.one('controladores', controladorId).customGET('simulacao')
      .then(function(response) {
        console.log('response: ', response)
        $scope.controlador = response;
        $scope.controlador.aneis = _.orderBy($scope.controlador.aneis, 'posicao');
        _.forEach($scope.controlador.aneis, function(anel) {
          anel.detectores = _.orderBy(anel.detectores, ['tipo', 'posicao']);
          _.forEach(anel.detectores, function(detector) {
            detector.nome = 'Anel ' + anel.posicao + ' - D' + detector.tipo[0] + detector.posicao;
          });
          _.forEach(anel.planos, function(plano) {
            plano.nome = 'Plano ' + plano.posicao;
          });
        });
        console.log('$scope.controlador', $scope.controlador)
        atualizaDetectores($scope.controlador);
        atualizaPlanos($scope.controlador);
      }).finally(influuntBlockui.unblock);
  };

  atualizaDetectores = function(controlador) {
    if (controlador) {
      $scope.detectores = _
        .chain(controlador.aneis)
        .map('detectores')
        .flatten()
        .value();
    }
  };

  atualizaPlanos = function(controlador) {
    if (controlador) {
      $scope.planos = _
        .chain(controlador.aneis)
        .map('planos')
        .flatten()
        .uniqBy('posicao')
        .orderBy('posicao')
        .value();
    }
  };

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

  $scope.$watch('inicioControlador', function(inicioControlador) {
    if (inicioControlador && inicioControlador.date && inicioControlador.time) {
      var date = moment(inicioControlador.date),
          time = moment(inicioControlador.time);
      var dateStr = '' + date.year() + '-' + _.padStart(date.month()+1, 2, 0) + '-' + _.padStart(date.date(), 2, 0) + ' ' + _.padStart(time.hour(), 2, 0) + ':' + _.padStart(time.minute(), 2, 0) + ':' + _.padStart(time.second(), 2, 0);
      $scope.parametrosSimulacao.inicioControlador = moment(dateStr);
    }
  }, true);

  $scope.$watch('inicioSimulacao', function(inicioSimulacao) {
    if (inicioSimulacao && inicioSimulacao.date && inicioSimulacao.time) {
      var date = moment(inicioSimulacao.date),
          time = moment(inicioSimulacao.time);
      var dateStr = '' + date.year() + '-' + _.padStart(date.month()+1, 2, 0) + '-' + _.padStart(date.date(), 2, 0) + ' ' + _.padStart(time.hour(), 2, 0) + ':' + _.padStart(time.minute(), 2, 0) + ':' + _.padStart(time.second(), 2, 0);
      $scope.parametrosSimulacao.inicioSimulacao = moment(dateStr);
    }
  }, true);

  $scope.$watch('fimSimulacao', function(fimSimulacao) {
    if (fimSimulacao && fimSimulacao.date && fimSimulacao.time) {
      var date = moment(fimSimulacao.date),
          time = moment(fimSimulacao.time);
      var dateStr = '' + date.year() + '-' + _.padStart(date.month()+1, 2, 0) + '-' + _.padStart(date.date(), 2, 0) + ' ' + _.padStart(time.hour(), 2, 0) + ':' + _.padStart(time.minute(), 2, 0) + ':' + _.padStart(time.second(), 2, 0);
      $scope.parametrosSimulacao.fimSimulacao = moment(dateStr);
    }
  }, true);

  $scope.$watch('disparosDetectores.disparos', function(disparos) {
    if (disparos) {
      _.forEach($scope.disparosDetectores.disparos, function(disparo, index) {
        if (disparo.date && disparo.hora && disparo.minuto && disparo.segundo) {
          var date = moment(disparo.date);
          var dateStr = '' + date.year() + '-' + _.padStart(date.month()+1, 2, 0) + '-' + _.padStart(date.date(), 2, 0) + ' ' + _.padStart(disparo.hora, 2, 0) + ':' + _.padStart(disparo.minuto, 2, 0) + ':' + _.padStart(disparo.segundo, 2, 0);
          $scope.parametrosSimulacao.disparoDetectores[index].disparo = moment(dateStr);
        }
      });
    }
  }, true);

  $scope.$watch('imposicoesPlanos.imposicoes', function(imposicoes) {
    if (imposicoes) {
      _.forEach($scope.imposicoesPlanos.imposicoes, function(imposicao, index) {
        if (imposicao.date && imposicao.hora && imposicao.minuto && imposicao.segundo) {
          var date = moment(imposicao.date);
          var dateStr = '' + date.year() + '-' + _.padStart(date.month()+1, 2, 0) + '-' + _.padStart(date.date(), 2, 0) + ' ' + _.padStart(imposicao.hora, 2, 0) + ':' + _.padStart(imposicao.minuto, 2, 0) + ':' + _.padStart(imposicao.segundo, 2, 0);
          $scope.parametrosSimulacao.imposicaoPlanos[index].disparo = moment(dateStr);
        }
      });
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
    return new influunt.components.Simulador(
      inicioSimulacao, fimSimulacao, velocidade, config, MQTT_ROOT.url, MQTT_ROOT.port
    );
  };

}]);
