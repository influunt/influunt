'use strict';

/**
 * @ngdoc function
 * @name influuntApp.controller:StatusCtrl
 * @description
 * # StatusCtrl
 * Controller of the influuntApp
 */
angular.module('influuntApp')
  .controller('StatusCtrl', ['$scope', function ($scope) {
    
    // called when the client connects
    function onConnect() {
      // Once a connection has been made, make a subscription and send a message.
      console.log("onConnect");
      $scope.client.subscribe("#");
    }
    
    
    var status = function(message){
      var id = message.destinationName.split("/")[2];
      $scope.controladores[id].ativo = true;
      $scope.controladores[id].status = JSON.parse(message.payloadString);
      console.log(id);
      console.log($scope.controladores[id]);
      console.log($scope.controladores[id].status);
      $scope.$apply();
    };
      
      
    $scope.controladores = {
      "controlador": {ativo: false, status:undefined},
      "1": {ativo: false, status:undefined},
      "2": {ativo: false, status:undefined}
    };
    
    // Create a client instance
    $scope.client = new Paho.MQTT.Client("mosquitto.rarolabs.com.br", 9001, "central");

    // set callback handlers
    $scope.client.onConnectionLost = function(responseObject) {
      if (responseObject.errorCode !== 0) {
        console.log("onConnectionLost:"+responseObject.errorMessage);
      }
    };
    
    $scope.client.onMessageArrived = function(message) {
      console.log(message.destinationName);
      if(message.destinationName.startsWith("central/status")){
        status(message);
      }else if(message.destinationName.startsWith("central/desconectar")){
        var id = message.destinationName.split("/")[2];
        $scope.controladores[id].ativo = false;
        $scope.controladores[id].status = {};
        $scope.$apply();
      }
    };

    // connect the client
    $scope.client.connect({onSuccess:onConnect});
    $scope.teste = $scope.client;
    
    $scope.class = function(estado){
      return "btn_" + estado;
    };
      
  }]);
