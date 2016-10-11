'use strict';

/**
* @ngdoc function
* @name influuntApp.controller:SimulacaoCtrl
* @description
* # SimulacaoCtrl
* Controller of the influuntApp
**/

angular.module('influuntApp')

.controller('SimulacaoCtrl', ['$scope', '$controller', 'Restangular', 'influuntBlockui', 'HorariosService', 'influuntAlert',
function ($scope, $controller, Restangular, influuntBlockui, HorariosService, influuntAlert) {

  var loadControladores, atualizaDetectores;

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

    $scope.parametrosSimulacao = { disparoDetectores: [{}] };
  };

  loadControladores = function() {
    return Restangular.one('controladores').customGET('simulacao', { statusControlador_in: '[1,2]' })
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
            });
          });
        }
      }).finally(influuntBlockui.unblock);
  };

  atualizaDetectores = function(controladorId) {
    var controlador = _.find($scope.controladores, { id: controladorId });
    $scope.detectores = _
      .chain(controlador.aneis)
      .map('detectores')
      .flatten()
      .value();
  };

  $scope.$watch('parametrosSimulacao.idControlador', function(controladorId) {
    if (controladorId) {
      atualizaDetectores(controladorId);
    }
  });

  $scope.$watch('parametrosSimulacao.disparoDetectores', function(parametro) {
    if (parametro) {
      var length = $scope.parametrosSimulacao.disparoDetectores.length;
      var disparo = $scope.parametrosSimulacao.disparoDetectores[length - 1];
      if (disparo.detector && disparo.momentoDisparo) {
        $scope.parametrosSimulacao.disparoDetectores.push({});
      // } else if (length > 1) {
      //   $scope.parametrosSimulacao.disparoDetectores.splice(length - 1, 1);
      }
    }
  }, true);

  $scope.removerDisparoDetector = function(index) {
    var title = 'Remover disparo de detector?',
        text = 'Deseja remover este disparo de detector da simulação?';
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






  $scope.iniciaSimulacao = function(dataInicio, dataFim, velocidade, id) {
    var params = {
      inicioSimulacao: dataInicio.format('DD/MM/YYYY HH:mm:ss'),
      fimSimulacao: dataFim.format('DD/MM/YYYY HH:mm:ss'),
      idControlador: '6a6b7ebe-f985-4eb9-abe2-672a78bd381e'
    };

    Restangular.one('simulacao',id).post(null, params)
      .then(function(resp){
        return new influunt.components.Simulador(dataInicio, dataFim, velocidade, resp);
      }).finally(influuntBlockui.unblock);
  };

  // $scope.iniciaSimulacao(moment("20/09/2016 16:59:00", "DD/MM/YYYY HH:mm:ss").utc(+3),
  //                        moment("20/09/2016 17:05:00", "DD/MM/YYYY HH:mm:ss").utc(+3),
  //                        1,
  //                        "6a6b7ebe-f985-4eb9-abe2-672a78bd381e");

}]);
