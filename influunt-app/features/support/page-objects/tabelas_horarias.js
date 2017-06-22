'use strict';

var worldObj = require('../world');
var expect = require('chai').expect;
var world = new worldObj.World();

var TabelasHorariasPage = function () {
  var selects = {
    'Dias':                            '[name="eventoDiaDaSemana"]',
    'Hora':                            '[name="eventoHora"]',
    'Minuto':                          '[name="eventoMinuto"]',
    'Segundo':                         '[name="eventoSegundo"]',
    'Plano':                           '[name="eventoPlano"]'
  };

  var eventoAdd = 'tr[influunt-evento]:last-child';
  var eventAdded = 'tr[data-evento^="evento"]:first-child';

  this.isTabelaHoraria = function() {
    return world.waitForByXpath('//ng-include[contains(@src, "views/tabela_horarios/tabs-eventos.html")]');
  };

  this.checkErroMensagem = function(msg) {
    return world.waitForByXpath('//li[contains(text(), "'+msg+'")]');
  };

  this.mudarEvento = function(evento) {
    var xpath = ('//li[contains(@aria-selected, "false")]//a[contains(text(), "Eventos '+evento+'")]');
    return world.waitForOverlayDisappear().then(function (){
      return world.getElementByXpath(xpath).click();
    });
  };

  this.selecionarValor = function(valor, select) {
    return world.waitForOverlayDisappear().then(function (){
      return world.selectByOptionAtribute(eventoAdd, selects[select], 'value', valor);
    });
  };

  this.enventoPossuiErro = function() {
    return world.waitForByXpath('//tr[contains(@data-ng-repeat, "evento")]//span[contains(@class, "badge")]');
  };

  this.preencherDescricaoEvento = function(campo, valor) {
    world.sleep(300);
    return world.setValue('[name="'+campo+'"]', valor);
  };

  this.contagemNaAba = function(aba, valor){
    return world.waitForByXpath('//a[text()="'+aba+'"]//span[text()="'+valor+'"]');
  };

  this.removerEvento = function(){
    return world.waitForOverlayDisappear().then(function (){
      return world.getElement(''+eventAdded+' i.fa-trash').click();
    });
  };

  this.eventoRemovido = function(evento){
    return world.waitForInverse('tr[data-evento^="evento"]:nth-child('+evento+')');
  };

  this.eventosRemovidos = function(){
    return world.waitForInverse(eventAdded);
  };

  this.clicarEmVisualizarDiagrama = function(evento){
    var xpath = '//td[contains(@class, "horarioColor'+evento+'")]//following-sibling::td[6]//i[contains(@class, "fa-eye")]';
    return world.getElementByXpath(xpath).click();
  };

  this.checkTextInSweetAlertAtuado= function(){
    return world.getTextInSweetAlert().then(function(text) {
      return expect(text).to.be.equal('Os modos de operação MANUAL E ATUADO não possuem diagrama de intervalos para exibir');
    });
  };

  this.verificarCorEvento = function(cor, evento) {
    var _this = this;
    var cssCor = _this.setCssAtributoCor(cor);
    var cssSelector = 'tr[data-evento^="evento"]:nth-child('+evento+') td.'+cssCor+'';
    return world.waitFor(cssSelector);
  };

  this.verificaQuadroHorario = function(diaSemana, hora, cor) {
    var _this = this;
    var cssCor = _this.setCssAtributoCor(cor);
    var posicaoSemana = _this.setPosicaoByDia(diaSemana);

    var xpathSelector = '//td[text()='+hora+']//following-sibling::td['+posicaoSemana+'][contains(@class, "'+cssCor+'")]';
    return world.getElementByXpath(xpathSelector);
  };

  this.setCssAtributoCor = function(cor){
    switch(cor){
      case ('Vermelha'):
        return 'horarioColor1';
      case ('VerdeClara'):
        return 'horarioColor2';
      case ('Roxa'):
        return 'horarioColor3';
      case ('Azul'):
        return 'horarioColor4';
      case ('Laranja'):
        return 'horarioColor5';
      case ('Preta'):
        return 'horarioColor6';
      case ('VerdeEscuro'):
        return 'horarioColor7';
      case ('Branco'):
        return 'horarioColor8';
      case ('Rosa'):
        return 'horarioColor9';
      case ('Lilas'):
        return 'horarioColor10';
      case ('VermelhoClaro'):
        return 'horarioColor11';
      default:
        throw new Error('Cor: '+cor+' não encontrada.');
    }
  };

  this.setPosicaoByDia = function(diaSemana) {
      switch(diaSemana){
      case ('Domingo'):
        return '1';
      case ('Segunda'):
        return '2';
      case ('Terça'):
        return '3';
      case ('Quarta'):
        return '4';
      case ('Quinta'):
        return '5';
      case ('Sexta'):
        return '6';
      case ('Sabado'):
        return '7';
      default:
        throw new Error('Dia da semana: '+diaSemana+' não encontrado.');
    }
  };
};

module.exports = TabelasHorariasPage;
