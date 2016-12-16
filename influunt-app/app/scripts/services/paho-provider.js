'use strict';

/**
 * @ngdoc service
 * @name influuntApp.pahoProvider
 * @description
 * # pahoProvider
 * Service in the influuntApp.
 */
angular.module('influuntApp')
  .factory('pahoProvider', ['MQTT_ROOT', '$q', '$timeout', '$rootScope', function pahoProvider(MQTT_ROOT, $q, $timeout, $rootScope) {

    var clearConnections, connectClient;
    var isConnected = false;
    var client = new Paho.MQTT.Client(
      MQTT_ROOT.url, MQTT_ROOT.port, 'influunt-app-' + JSON.parse(localStorage.usuario).id
    );
    var subscribers = {};
    var timeoutId;

    var RECONNECT_TIMEOUT_ID = 5000;
    var reconnectTimeoutId;
    var tryReconnect = function() {
      clearTimeout(reconnectTimeoutId);
      setTimeout(function() {
        return connectClient()
          .then(function(res) {
            $rootScope.$broadcast('influuntApp.mqttConnectionRecovered');
            return res;
          })
          .catch(function(err) {
            return err;
          });
      }, RECONNECT_TIMEOUT_ID);
    };

    client.onConnectionLost = function() {
      clearConnections();
      tryReconnect();
    };

    client.onMessageArrived = function(message) {
      var fn = null;
      _.each(subscribers, function(value, key) {
        if (message.destinationName.match(new RegExp(key.replace('+', '.*')))) {
          fn = value;
        }
      });

      if (!_.isFunction(fn)) {
        console.warn('Função de callback para o topic ', message.destinationName, ' não implementada.');
        return false;
      }

      return $timeout(function() {
        return fn.apply(this, [message.payloadString, message.destinationName]);
      });
    };

    connectClient = function() {
      var deferred = $q.defer();

      if (isConnected) {
        deferred.resolve(true);
      } else {
        $timeout.cancel(timeoutId);
        timeoutId = $timeout(function() {
          client.connect({
            onSuccess: function() {
              isConnected = true;
              deferred.resolve(true);
            },
            onFailure: function(error) {
              tryReconnect();
              deferred.reject(error);
            }
          });
        }, 200);
      }

      return deferred.promise;
    };

    var disconnectClient = function() {
        client.disconnect();
        clearConnections();
    };

    clearConnections = function() {
      isConnected = false;
      subscribers = {};
    };

    var register = function(subscribedUrl, onMessageArrivedCallback, dontListenToAll) {
      if (!isConnected) {
        throw new Error('Client is not connected.');
      }

      var listenToAll = !dontListenToAll;
      subscribers[subscribedUrl] = onMessageArrivedCallback;
      client.subscribe(subscribedUrl, {qos: 1});
      if (listenToAll) {
        client.subscribe(subscribedUrl + '/+', {qos: 1});
      }
    };

    var unregister = function(subscribedUrl) {
      if (!isConnected) {
        throw new Error('Client is not connected.');
      }

      client.unsubscribe(subscribedUrl);
      client.unsubscribe(subscribedUrl + '/+');
    };

    var publish = function(topic, body) {
      if (!isConnected) {
        throw new Error('Client is not connected.');
      }

      var message = new Paho.MQTT.Message(JSON.stringify(body));
      message.destinationName = topic;
      return client.send(message);
    };

    return {
      connect: connectClient,
      disconnect: disconnectClient,
      register: register,
      unregister: unregister,
      publish: publish
    };

  }]);
