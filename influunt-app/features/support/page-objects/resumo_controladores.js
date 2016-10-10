
'use strict';

var worldObj = require('../world');

var world = new worldObj.World();

var ResumoControladorPage = function () {
  this.isResumoControlador = function() {
    return world.waitForByXpath('//influunt-revisao[contains(@data-ng-controller, "ControladoresRevisaoCtrl")]');
  };

  this.informacaoDadoBasico = function () {
    var _this = this;
    return Promise.all([
      _this.checkAtribute('Área:', '1'),
      _this.checkAtribute('Cidade:', 'São Paulo'),
      _this.checkAtribute('CLC:',    '1.000.0001'),
      _this.checkAtribute('Fabricante:', 'Raro Labs'),
      _this.checkAtribute('Modelo:', 'Modelo Básico'),
      _this.checkAtribute('Total de Anéis:', '2'),
      _this.checkAtribute('Número de detector veicular:', '4'),
      _this.checkAtribute('Número de detector pedestre:', '2'),
      _this.checkAtribute('Localização:', 'Av. Paulista com R. Bela Cintra'),
      _this.findMapa()

    ]).then(function() {
      return Promise.resolve(true);
    });
  };

  this.informacaoBasicaAnel = function (informacoes) {
    var _this = this;
    return Promise.all([
      _this.checkAtribute('Logradouro:', informacoes["Logradouro"]),
      _this.checkAtribute('Número SMEE:', informacoes["SMEE"]),
      _this.checkAtribute('CLA:',    informacoes["CLA"]),
      _this.checkAtribute('Grupos semafóricos de pedestres:', informacoes["gsp"]),
      _this.checkAtribute('Grupos semafóricos veiculares:', informacoes["gsv"]),
      _this.checkAtribute('Número de detectores pedestres:', informacoes["gdp"]),
      _this.checkAtribute('Número de detectores veiculares:', informacoes["gdv"])

    ]).then(function() {
      return Promise.resolve(true);
    });
  };

  this.informacaoGrupoSemaforico = function (informacoes) {
    var _this = this;
    return Promise.all([
      _this.checkTitle('h3', informacoes["Titulo1"]),
      _this.checkTitle('h3', informacoes["Titulo2"]),
      _this.checkTitle('h3', informacoes["Titulo3"]),
      _this.checkAtribute('Tipo:', informacoes["Tipo"]),
      _this.checkAtribute('Fase Vermelha:', informacoes["Fase"]),
      _this.checkAtribute('Verde segurança:',    informacoes["VerdeS"])
    ]).then(function() {
      return Promise.resolve(true);
    });
  };

  this.informacaoAssociacaoVsGrupo = function (informacoes) {
    var _this = this;
    return Promise.all([
      _this.checkTitle('h3', informacoes["Estagio1"]),
      _this.checkDivText('div', informacoes["G1"]),
      _this.checkTitle('h3', informacoes["Estagio2"]),
      _this.checkDivText('div', informacoes["G2"]),
      _this.checkTitle('h3', informacoes["Estagio3"]),
      _this.checkDivText('div', informacoes["G3"]),
      _this.checkTitle('h3', informacoes["Estagio4"]),
      _this.checkDivText('div', informacoes["G3"])
    ]).then(function() {
      return Promise.resolve(true);
    });
  };

  this.informacaoVerdesConflitantes = function (informacoes) {
    var _this = this;
    return Promise.all([
      _this.checkAtribute('', informacoes["g1-g2"]),
      _this.checkAtribute('', informacoes["g2-g3"])
    ]).then(function() {
      return Promise.resolve(true);
    });
  };


// Checar informações
  this.checkAtribute = function(campo, dado){
    var xpath = '//p[text()= " '+dado+'"]//strong[text()="'+campo+'"]';
    return world.checkIsElementPresent(xpath, dado, campo);
  };

  this.checkDivText = function(attribute, text){
    var xpath = '//div[text()="'+text+'"]';
    return world.checkIsElementPresent(xpath, attribute, text);
  };

  this.checkTitle = function(attribute, text){
    var xpath = '//'+attribute+'[text()= "'+text+'"]';
    return world.checkIsElementPresent(xpath, attribute, text);
  };

  this.findMapa = function(){
    return world.waitForByXpath('//figure//div[contains (@class, "influunt-map ")]');
  };
};

module.exports = ResumoControladorPage;
