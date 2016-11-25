'use strict';

/**
 * @ngdoc service
 * @name influuntApp.pahoProvider
 * @description
 * # pahoProvider
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('pahoProvider', ['MQTT_ROOT', '$q', '$timeout', function pahoProvider(MQTT_ROOT, $q, $timeout) {

    var isConnected = false;
    var client = new Paho.MQTT.Client(MQTT_ROOT.url, MQTT_ROOT.port, UUID.generate());
    var subscribers = {};

    client.onConnectionLost = function(res) {
      isConnected = false;
      if (res.errorCode !== 0) {
        console.log('call here the onConnectionLostCallback');
        throw new Error(res.errorMessage);
      }
    };

    client.onMessageArrived = function(message) {
      var fn = null;
      _.each(subscribers, function(value, key) {
        if (message.destinationName.match(new RegExp(key))) {
          fn = value;
        }
      });

      if (!_.isFunction(fn)) {
        console.warn('Função de callback para o topic', message.destinationName, 'não implementada.');
        return false;
      }

      return $timeout(function() {
        return fn.apply(this, [message.payloadString, message.destinationName]);
      });
    };

    var connectClient = function() {
      var deferred = $q.defer();

      if (isConnected) {
        deferred.resolve(true);
      } else {
        client.connect({
          onSuccess: function() {
            console.log('======> connected');
            isConnected = true;
            deferred.resolve(true);
          }
        });
      }

      return deferred.promise;
    };

    var disconnectClient = function() {
      if (isConnected) {
        client.disconnect();
        subscribers = {};
      }
    };

    var register = function(subscribedUrl, onMessageArrivedCallback) {
      if (!isConnected) {
        throw new Error('Client is not connected.');
      }

      subscribers[subscribedUrl] = onMessageArrivedCallback;
      client.subscribe(subscribedUrl + '/+');
      client.subscribe(subscribedUrl);
    };

    return {
      register: register,
      connect: connectClient,
      disconnect: disconnectClient
    };

  }]);
