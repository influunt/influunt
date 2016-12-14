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
    var client = new Paho.MQTT.Client(
      MQTT_ROOT.url, MQTT_ROOT.port, 'influunt-app-' + JSON.parse(localStorage.usuario).id
    );
    var subscribers = {};
    var timeoutId;

    client.onConnectionLost = function(res) {
      isConnected = false;
      if (res.errorCode !== 0) {
        throw new Error(res.errorMessage);
      }
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

    var connectClient = function() {
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
            }
          });
        }, 200);
      }

      return deferred.promise;
    };

    var disconnectClient = function() {
      if (isConnected) {
        client.disconnect();
        isConnected = false;
        subscribers = {};
      }
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
