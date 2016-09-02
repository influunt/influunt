'use strict';

var worldObj = require('../world');

var world = new worldObj.World();

var TabelaHorariosPage = function () {

  var campos = {
    'Hora':                     'select[name="eventoHora"]',
    'Minuto':                   'select[name="eventoMinuto"]',
    'Segundo':                  'select[name="eventoSegundo"]',
    'Plano':                    'select[name="eventoPlano"]'
  };

  this.clicarBotao = function(button) {
    return world.waitForOverlayDisappear().then(function() {
      return world.findLinkByText(button).click();
    });
  };

  this.isTabelasHorarios = function() {
    return world.waitFor('influunt-tabs[data-on-activate="selecionaTipoEvento"]');
  };

  this.isEditavel = function() {
    return world.waitForInverse('select[disabled="disabled"]')
  };

  this.selecionarValor = function(campo, valor) {
    return world.selectOption(campos[campo], valor);
  };
};

module.exports = TabelaHorariosPage;
