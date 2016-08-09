'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var CrudPage = function () {

  var campos = {
    'Área':                            '[name="area"]',
    'Cidade':                          'select[name="cidade"]',
    'Configuração Controlador':        'select[name="configuracao"]',
    'Configuração':                    'select[name="configuracao"]',
    'Controladores':                   'select[name="controladores"]',
    'Descrição':                       '[name="descricao"]',
    'Fabricante':                      'select[name="fabricante"]',
    'Grupos Semafóricos de pedestre':  '[name="quantidadeGrupoPedestre"]',
    'Grupos Semafóricos veiculares':   '[name="quantidadeGrupoVeicular"]',
    'Latitude':                        '[name="latitude"]',
    'Localização':                     '[name="localizacao"]',
    'Longitude':                       '[name="longitude"]',
    'Modelo':                          'select[name="modelo"]',
    'Nome':                            '[name="nome"]',
    // 'Área':                            '[name="area_descricao"]',
    'Número de detectores pedestres':  '[name="quantidadeDetectorPedestre"]',
    'Número de detectores veiculares': '[name="quantidadeDetectorVeicular"]',
    'Tipo Grupo Semafórico':           'select[name="tipoGrupoSemaforico"]',
    'Tipo':                            '[name="tipo"]'
  };

  this.preencherCampo = function(campo, valor) {
    return world.setValue(campos[campo], valor);
  };

  this.selecionarValor = function(campo, valor) {
    return world.selectOption(campos[campo], valor);
  };

  this.buscarEndereco = function(query, numEndereco) {
    return world.setValueAsHuman('div[data-ng-class$=".enderecos['+(numEndereco - 1)+'].localizacao }"] helper-endereco > input', query).then(function() {
      return world.waitFor('div[g-places-autocomplete-drawer] > div.pac-container');
    }).then(function() {
      return world.getElements('div[g-places-autocomplete-drawer] > div.pac-container div:first-child')
    }).then(function(elements) {
      return new Promise(function(resolve, reject) {
        setTimeout(function() {
          return elements[0].click().then(resolve);
        }, 500);
      })
    })
  };

};

module.exports = CrudPage;
