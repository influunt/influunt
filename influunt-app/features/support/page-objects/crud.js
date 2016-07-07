'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var CrudPage = function () {

  var campos = {
    'Descrição': '[name="descricao"]',
    'Fabricante': 'select[name="fabricante"]',
    'Configuração Controlador': 'select[name="configuracao"]',
    'Configuração': 'select[name="configuracao"]',
    'Cidade': 'select[name="cidade"]',
    'Área': 'select[name="area"]',
    'Localização': '[name="localizacao"]',
    'Latitude': '[name="latitude"]',
    'Longitude': '[name="longitude"]',
    'Modelo': 'select[name="modelo"]',
    'Grupos Semafóricos de pedestre': '[name="quantidadeGrupoPedestre"]',
    'Grupos Semafóricos veiculares': '[name="quantidadeGrupoVeicular"]',
    'Número de detectores veiculares': '[name="quantidadeDetectorVeicular"]',
    'Número de detectores pedestres': '[name="quantidadeDetectorPedestre"]',
    'Tipo Grupo Semafórico': 'select[name="tipoGrupoSemaforico"]',
    'Número CTA': '[name="area_descricao"]',
    'Nome': '[name="nome"]'
  };

  this.preencherCampo = function(campo, valor) {
    return world.setValue(campos[campo], valor);
  };

  this.selecionarValor = function(campo, valor) {
    return world.selectOption(campos[campo], valor);
  };

};

module.exports = CrudPage;
