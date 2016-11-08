'use strict';

/**
 * @ngdoc service
 * @name influuntApp.crud/influuntAlert
 * @description
 * # crud/influuntAlert
 *
 * Método auxiliar para exibição de alerts.
 */
angular.module('influuntApp')
  .factory('influuntAlert', ['SweetAlert', '$q', '$filter', 'Objects',
    function influuntAlert(SweetAlert, $q, $filter, Objects) {

      var defaultOptions = {
        showCancelButton: true,
        confirmButtonColor: '#DD6B55',
        closeOnConfirm: true,
        closeOnCancel: true,
        confirmButtonText: $filter('translate')('geral.mensagens.sim'),
        cancelButtonText: $filter('translate')('geral.mensagens.cancelar')
      };

      var alertPopup = function(title, text) {
        SweetAlert.swal({
          type: 'warning',
          title: title,
          text: text,
          showConfirmButton: true
        });
      };

      /**
       * Alert base. Considera-se aqui que todos utilizão as opções "sim" e "cancelar".
       *
       * @param      {<type>}  title   The title
       * @param      {<type>}  text    The text
       */
      var confirmPopup = function(title, text) {
        var defer = $q.defer(),
            options = Objects.merge(defaultOptions, {
              title: title,
              text: text
            });

        SweetAlert.swal(options, function (confirmado) {
          setTimeout(function() {
            defer.resolve(confirmado);
          }, 100);
        });

        return defer.promise;
      };

      var success = function(title, text){
        SweetAlert.swal(title, text, 'success');
      };

      var promptPopup = function(title, text) {
        var defer = $q.defer(),
            options = Objects.merge(defaultOptions, {
              title: title,
              text: text,
              type: 'input'
            });

        SweetAlert.swal(options, function (inputvalue) {
          setTimeout(function() {
            defer.resolve(inputvalue);
          }, 100);
        });

        return defer.promise;
      };

      /**
       * Alert padrão para exibição de alerts antes de deletar algo.
       */
      var deleteAlert = function() {
        var title = $filter('translate')('geral.mensagens.popup_delete.titulo');
        var text = $filter('translate')('geral.mensagens.popup_delete.mensagem');
        return confirmPopup(title, text);
      };

      var askPopup = function(title, text) {
        var defer = $q.defer(),
            options = Objects.merge(defaultOptions, {
              title: title,
              text: text,
              cancelButtonText: $filter('translate')('geral.mensagens.nao'),
            });

        SweetAlert.swal(options, function (confirmado) {
          setTimeout(function() {
            defer.resolve(confirmado);
          }, 100);
        });

        return defer.promise;
      };

      return  {
        alert: alertPopup,
        confirm: confirmPopup,
        delete: deleteAlert,
        prompt: promptPopup,
        ask: askPopup,
        success: success
      };

    }]);
