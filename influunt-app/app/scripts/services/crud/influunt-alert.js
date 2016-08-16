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
  .factory('influuntAlert', ['SweetAlert', '$q', '$filter',
    function influuntAlert(SweetAlert, $q, $filter) {

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
        var defer = $q.defer();
        SweetAlert.swal(
          {
            title: title,
            text: text,
            showCancelButton: true,
            confirmButtonColor: '#DD6B55',
            confirmButtonText: $filter('translate')('geral.mensagens.sim'),
            cancelButtonText: $filter('translate')('geral.mensagens.cancelar'),
            closeOnConfirm: true,
            closeOnCancel: true
          }, function (confirmado) {
            defer.resolve(confirmado);
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

      return  {
        alert: alertPopup,
        confirm: confirmPopup,
        delete: deleteAlert
      };

    }]);
