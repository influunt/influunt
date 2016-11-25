'use strict';

/**
 * @ngdoc service
 * @name influuntApp.audioNotifier
 * @description
 * # audioNotifier
 * Factory in the influuntApp.
 */
angular.module('influuntApp')
  .factory('audioNotifier', function () {
    var notify = function() {
      if (!!window.Audio) {
        var soundFile = 'audio/notification.mp3';
        var mp3 = new window.Audio(soundFile);
        mp3.play();
      }
    };

    return {
      notify: notify
    };
  });
