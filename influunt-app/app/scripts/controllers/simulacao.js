'use strict';

/**
* @ngdoc function
* @name influuntApp.controller:SimulacaoCtrl
* @description
* # SimulacaoCtrl
* Controller of the influuntApp
*/
angular.module('influuntApp')

.controller('SimulacaoCtrl', ['$scope', '$controller','Restangular','influuntBlockui',
function ($scope, $controller,Restangular,influuntBlockui) {

  
  $scope.iniciaSimulacao = function(dataInicio,dataFim,velocidade,id){

    Restangular.one('simulacao',id).post(null,{inicioSimulacao: dataInicio.format('DD/MM/YYYY HH:mm:ss'), 
    fimSimulacao: dataFim.format('DD/MM/YYYY HH:mm:ss'),
    idControlador: 'b3d50ca0-383e-4fab-b10c-dd411b453b98'})
    .then(function(resp){
      return new influunt.components.Simulador(dataInicio,dataFim,velocidade,resp)
    })
    .finally(influuntBlockui.unblock);
  }
  
  $scope.iniciaSimulacao(moment("20/09/2016 16:59:00", "DD/MM/YYYY HH:mm:ss").utc(+3),
                         moment("20/09/2016 17:05:00", "DD/MM/YYYY HH:mm:ss").utc(+3), 1,
                        "031ef627-34b3-4841-963a-091b3fa19190");
  
}]);
