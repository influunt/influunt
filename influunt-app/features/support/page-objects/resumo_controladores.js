
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
      _this.checkAtribute('Logradouro:', informacoes.Logradouro),
      _this.checkAtribute('Número SMEE:', informacoes.SMEE),
      _this.checkAtribute('CLA:',    informacoes.CLA),
      _this.checkAtribute('Grupos semafóricos de pedestres:', informacoes.gsp),
      _this.checkAtribute('Grupos semafóricos veiculares:', informacoes.gsv),
      _this.checkAtribute('Número de detectores pedestres:', informacoes.gdp),
      _this.checkAtribute('Número de detectores veiculares:', informacoes.gdv)

    ]).then(function() {
      return Promise.resolve(true);
    });
  };

  this.informacaoGrupoSemaforico = function (informacoes) {
    var _this = this;
    return Promise.all([
      _this.checkTitle('h3', informacoes.Titulo1),
      _this.checkTitle('h3', informacoes.Titulo2),
      _this.checkTitle('h3', informacoes.Titulo3),
      _this.checkAtribute('Tipo:', informacoes.Tipo),
      _this.checkAtribute('Fase Vermelha:', informacoes.Fase),
      _this.checkAtribute('Verde segurança:',    informacoes.VerdeS)
    ]).then(function() {
      return Promise.resolve(true);
    });
  };

  this.informacaoAssociacaoVsGrupo = function (informacoes) {
    var _this = this;
    return Promise.all([
      _this.checkTitle('h3', informacoes.Estagio1),
      _this.checkDivText('div', informacoes.G1),
      _this.checkTitle('h3', informacoes.Estagio2),
      _this.checkDivText('div', informacoes.G2),
      _this.checkTitle('h3', informacoes.Estagio3),
      _this.checkDivText('div', informacoes.G3),
      _this.checkTitle('h3', informacoes.Estagio4),
      _this.checkDivText('div', informacoes.G3)
    ]).then(function() {
      return Promise.resolve(true);
    });
  };

  this.informacaoVerdesConflitantes = function (informacoes) {
    var _this = this;
    return Promise.all([
      _this.checkBasicAtribute('strong','Verdes Conflitantes', informacoes.g1g2),
      _this.checkBasicAtribute('strong','Verdes Conflitantes', informacoes.g2g3)
    ]).then(function() {
      return Promise.resolve(true);
    });
  };

  this.informacaoTransicoesProibidas = function (informacoes) {
    var _this = this;
    return Promise.all([
      _this.checkBasicAtribute('strong', 'Titulo', informacoes.TituloTp),
      _this.checkBasicAtribute('strong', 'Titulo', informacoes.TituloAlternativa),
      _this.checkBasicAtribute('b', 'Transição Probida', informacoes.Tp1),
      _this.checkBasicAtribute('b', 'Titulo', informacoes.Alternativa1),
      _this.checkBasicAtribute('b', 'Transição Probida', informacoes.Tp2),
      _this.checkBasicAtribute('b', 'Titulo', informacoes.Alternativa2),
    ]).then(function() {
      return Promise.resolve(true);
    });
  };

  this.informacaoDetectores = function (informacoes) {
    var _this = this;
    return Promise.all([
      _this.checkBasicAtribute('strong', 'Detector', informacoes.Dp1),
      _this.checkBasicAtribute('strong', 'Detector', informacoes.Dp2),
      _this.checkBasicAtribute('strong', 'Detector', informacoes.Dv1),
      _this.checkBasicAtribute('strong', 'Detector', informacoes.Dv2),
      _this.checkBasicAtribute('strong', 'Estagio', informacoes.E3),
      _this.checkBasicAtribute('strong', 'Estagio', informacoes.E4),
      _this.checkBasicAtribute('strong', 'Estagio', informacoes.E1),
      _this.checkBasicAtribute('strong', 'Estagio', informacoes.E2),
      _this.verificaMonitoramento(informacoes.temMonitoramento)
    ]).then(function() {
      return Promise.resolve(true);
    });
  };

  this.informacaoTabelaEntreverdesEAtrasoGrupo = function (informacoes) {
    var _this = this;
    return Promise.all([
      _this.checkBasicAtribute('strong', 'Padrao 1', informacoes.Padrao1),
      _this.checkBasicAtribute('strong', 'Padrao 2', informacoes.Padrao2),
      _this.checkBasicAtribute('strong', 'Padrao 3', informacoes.Padrao3)
    ]).then(function() {
      return Promise.resolve(true);
    });
  };


//-------- Checar informações ----------------

  this.checkBasicAtribute = function(cssAtrribute, campo, dado){
    var xpath = '//'+cssAtrribute+'[text()="'+dado+'"]';
    return world.checkIsElementPresent(xpath, campo, dado);
  };

  this.naoExistemDados = function(){
    var pathTransicoes = 'views/directives/influunt-revisao/_vazio.html';
    var mensage = 'Não há dados a serem exibidos nesta seção';
    return world.waitForByXpath('//ng-include[contains(@src, "'+pathTransicoes+'")]//p[text()="'+mensage+'"]');
  };

  this.checkAtribute = function(campo, dado){
    var xpath = '//p[text()= " '+dado+'"]//strong[text()="'+campo+'"]';
    return world.checkIsElementPresent(xpath, campo, dado);
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
    return world.waitForByXpath('//figure//div[contains (@markers, "markerEnderecoControlador")]');
  };

  this.verificaMonitoramento = function(monitoramento){
    return world.waitForByXpath('//i[contains (@class, "'+monitoramento+'")]');
  };

  this.selecionarGrupoSemaforico = function(grupo, path){
    return world.waitForOverlayDisappear().then(function (){
      return world.selectByOptionAtribute('div[name='+path+']', '[id="gsTabelasEntreVerdes"]', 'label', grupo);
    });
  };
};

module.exports = ResumoControladorPage;
