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
    'Número de detectores pedestres':  '[name="quantidadeDetectorPedestre"]',
    'Número de detectores veiculares': '[name="quantidadeDetectorVeicular"]',
    'Tipo Grupo Semafórico':           'select[name="tipoGrupoSemaforico"]',
    'Tipo':                            '[name="tipo"]',
    'Número SMEE':                     '[name="numeroSMEE"]',
    'Alternativa':                     'select[name="alternativos"]',
    'Descrição':                       '[name="descricao"]',
    'Limite Estágios':                 '[name="limiteEstagio"]',
    'Limite Grupos Semafóricos':       '[name="limiteGrupoSemaforico"]',
    'Limite Aneies':                   '[name="limiteAnel"]',
    'Limite Detectores Pedestre':      '[name="limiteDetectorPedestre"]',
    'Limite Detectores Veicular':      '[name="limiteDetectorVeicular"]',
    'Limite Tabelas Entre Verdes':     '[name="limiteTabelasEntreVerdes"]',
    'Limite Planos':                   '[name="limitePlanos"]',
  };

  this.textoConfirmacaoApagarRegistro = function() {
    return world.getElement('div[class*="sweet-alert"] p').getText();
  };

  this.toastMessage = function() {
    return world.sleep(1000).then(function() {
      return world.waitFor('#toast-container div.toast-message').then(function() {
        return world.getElement('#toast-container div').getText();
      });
    });
  };

  this.preencherCampo = function(campo, valor) {
    return world.setValue(campos[campo], valor);
  };

  this.selecionarValor = function(campo, valor) {
    return world.selectOption(campos[campo], valor);
  };

  this.limparEndereco = function(numEndereco) {
    var cssSelector = 'div[data-ng-class$=".endereco.localizacao'+(numEndereco)+' }"] helper-endereco > input';
    return world.clearField(cssSelector);
  };

  this.buscarEndereco = function(query, numEndereco) {
    return world.setValueAsHuman('div[data-ng-class$=".endereco.localizacao'+(numEndereco)+' }"] helper-endereco > input', query).then(function() {
      return world.waitFor('div[g-places-autocomplete-drawer] > div.pac-container');
    }).then(function() {
      return world.getElements('div[g-places-autocomplete-drawer] > div.pac-container div:first-child');
    }).then(function(elements) {
      return new Promise(function(resolve, reject) {
        setTimeout(function() {
          if (elements.length > 0) {
            return elements[0].click().then(resolve);
          } else {
            reject('No results found for address "'+query+'"');
          }
        }, 500);
      });
    });
  };

};

module.exports = CrudPage;
