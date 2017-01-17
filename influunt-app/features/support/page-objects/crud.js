'use strict';

var worldObj = require('../world');
var world = new worldObj.World();

var CrudPage = function () {

  var campos = {
    'Área':                            '[name="area"]',
    'Altura Numérica':                 '[name="alturaNumerica"]',
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
    'Limite Estágios':                 '[name="limiteEstagio"]',
    'Limite Grupos Semafóricos':       '[name="limiteGrupoSemaforico"]',
    'Limite Aneies':                   '[name="limiteAnel"]',
    'Limite Detectores Pedestre':      '[name="limiteDetectorPedestre"]',
    'Limite Detectores Veicular':      '[name="limiteDetectorVeicular"]',
    'Limite Tabelas Entre Verdes':     '[name="limiteTabelasEntreVerdes"]',
    'Limite Planos':                   '[name="limitePlanos"]',
    'Dia':                             '[name="agrupamentoPlanoDiaSemana"]',
    'Hora':                            '[name="planoHora"]',
    'Minuto':                          '[name="planoMinuto"]',
    'Segundo':                         '[name="planoSegundo"]',
    'Plano':                           '[name="posicaoPlano"]',
    'Nome Perfil':                     '[name="perfil_nome"]',
    'Nome Usuário':                    '[name="usuario_nome"]',
    'Login':                           '[name="usuario_login"]',
    'Email':                           '[name="usuario_email"]',
    'Senha':                           '[name="usuario_senha"]',
    'Confirmação Senha':               '[name="usuario_confirmacao_senha"]'
  };

  this.toastMessage = function() {
    return world.sleep(1000).then(function() {
      return world.waitFor('#toast-container div.toast-message').then(function() {
        return world.getElement('#toast-container div').getText();
      });
    });
  };

  this.preencherCampo = function(campo, valor) {
    world.sleep(500);
    return world.setValue(campos[campo], valor);
  };

  this.selecionarValor = function(campo, valor) {
    return world.selectOption(campos[campo], valor);
  };

  this.limparEndereco1 = function() {
    var xpathSelector = '//helper-endereco[contains(@ng-model, "currentEndereco.localizacao")]//input';
    return world.clearFieldByXpath(xpathSelector);
  };

  this.limparEndereco2 = function() {
    var xpathSelector = '//helper-endereco[contains(@ng-model, "currentEndereco.localizacao2")]//input';
    return world.clearFieldByXpath(xpathSelector);
  };

  this.buscarEndereco1 = function(query){
    var cssSelector = 'div[data-ng-class$=".endereco.localizacao }"] helper-endereco > input';
    return world.buscarEndereco(query, cssSelector);
  };

  this.buscarEndereco2 = function(query){
    var cssSelector = 'div[data-ng-class$=".endereco.localizacao2 }"] helper-endereco > input';
    return world.buscarEndereco(query, cssSelector);
  };
};

module.exports = CrudPage;
