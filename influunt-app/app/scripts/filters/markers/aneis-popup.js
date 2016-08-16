'use strict';

/**
 * @ngdoc filter
 * @name influuntApp.filter:markersAneisPopup
 * @function
 * @description
 * # markersAneisPopup
 * Filter in the influuntApp.
 */
angular.module('influuntApp')
  .filter('markersAneisPopup', function () {
    return function (anel, controlador) {
      if (anel) {
        var ids = _.map(anel.detectores, 'idJson');
        var detectores = _.filter(controlador.detectores, function(d) {
          return ids.indexOf(d.idJson) >= 0;
        });

        var veiculares = _.filter(detectores, {tipo: 'VEICULAR'}).length;
        var pedestres = _.filter(detectores, {tipo: 'PEDESTRE'}).length;

        var titulo = '<strong>' + anel.CLA + '</strong>';
        var listaDetectores = $('<ul></ul>');
        if (veiculares > 0) {
          listaDetectores.append('<li>' + veiculares + ' detector(es) veicular(es)</li>');
        }

        if (pedestres > 0) {
          listaDetectores.append('<li>' + pedestres + ' detector(es) de pedestres</li>');
        }

        var container = $('<div class="marker-popup-container"></div>');
        container.append(titulo + listaDetectores.prop('outerHTML'));
        return container.prop('outerHTML');
      }
    };
  });
