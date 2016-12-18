'use strict';

var ObjetosComuns = require('../support/page-objects/objetos_comuns');
var expect = require('chai').expect;

module.exports = function() {
  var sharedSteps = new ObjetosComuns();

  this.Given(/^o usuário acessar a tela de listagem de "([^"]*)"$/, function(path) {
    return sharedSteps.indexPage(path);
  });

  this.Given(/^o usuário clicar em "([^"]*)"$/, function (botao) {
    return sharedSteps.clicarLinkComTexto(botao);
  });

  this.Given(/^o sistema deverá apresentar erro de "([^"]*)"$/, function (texto) {
    return sharedSteps.errosImpeditivos(texto);
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir$/, function() {
    return sharedSteps.textoSweetAlert().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^o sistema exibe um alerta com a mensagem "([^"]*)"$/, function(msg) {
    return sharedSteps.textoSweetAlert().then(function(text) {
      expect(text).to.equal(msg);
    });
  });

  this.Given(/^o sistema exibe uma mensagem "([^"]*)"$/, function(msg) {
    return sharedSteps.textoToast().then(function(text) {
      expect(text).to.equal(msg);
    });
  });

  this.Given(/^o usuário confirmar$/, function() {
    return sharedSteps.botaoConfirmSweetAlert();
  });

  this.Given(/^o usuário realize um scroll up$/, function() {
    return sharedSteps.realizarScrollUp();
  });

  this.Given(/^o usuário realizar um scroll down$/, function() {
    return sharedSteps.realizarScrollDown();
  });

  this.Given(/^o usuário realizar um scroll down no modal$/, function() {
    return sharedSteps.realizarScrollDownModal();
  });

  this.Given(/^que o usuário selecione o anel (\d+)$/, function (numeroAnel) {
    return sharedSteps.trocarAnel(numeroAnel);
  });

  // verificar o diagrama
  this.Given(/^o sistema deve mostrar o diagrama "([^"]*)" no grupo "([^"]*)" com "([^"]*)" em "([^"]*)" segundos$/, function (modoOperacao, grupo, indicacaoCor, tempo) {
    return sharedSteps.isDiagramaModo(modoOperacao, grupo, indicacaoCor, tempo);
  });

  this.Given(/^o sistema deverá redirecionar o usuário para a página de listagem de controladores$/, function () {
    return sharedSteps.isIndexPage();
  });

  this.Given(/^que o usuário deslogue no sistema$/, function () {
    return sharedSteps.deslogar();
  });

  this.Given(/^o usuário navegar pelo breadcrumb clicando em "([^"]*)"$/, function (opcao) {
    return sharedSteps.navegarBreadcrumb(opcao);
  });

  this.Given(/^o sistema deverá mostrar "([^"]*)" controladores cadastrados$/, function (numero) {
    return sharedSteps.checarTotalInseridosNaTabela(numero);
  });

  this.Given(/^o sistema deverá mostrar "([^"]*)" items na tabela$/, function (numero) {
    return sharedSteps.checarTotalInseridosNaTabela(numero);
  });

  this.Given(/^o sistema deverá mostrar "([^"]*)" na listagem$/, function (numero) {
    return sharedSteps.checarTotalInseridosNaTabela(numero);
  });

  this.Given(/^o usuário preencha o alert com "([^"]*)"$/, function (descricao) {
    return sharedSteps.preencherSweetAlert(descricao);
  });

  this.Given(/^o sistema deverá indicar erro no campo "([^"]*)"$/, function (nomeCampo) {
    return sharedSteps.getErrorMessageFor(nomeCampo).then(function(result) {
      return expect(result).to.exist;
    });
  });

  this.Given(/^o sistema deverá indicar erro no campo "([^"]*)" com a mensagem "([^"]*)"$/, function (nomeCampo, msg) {
    return sharedSteps.getErrorMessageFor(nomeCampo).then(function(result) {
      return expect(result).to.equal(msg);
    });
  });

  this.Given(/^o sistema deverá redirecionar para o formulário "([^"]*)"$/, function(formName) {
    return sharedSteps.form(formName).then(function(form) {
      return expect(form).to.exist;
    });
  });

  this.Given(/^o sistema deverá mostrar o controlador da rua "([^"]*)"$/, function (endereco) {
    return sharedSteps.checarControladorPorEndereco(endereco);
  });

  this.Given(/^o sistema deverá mostrar o status do controlador como "([^"]*)"$/, function (status) {
    return sharedSteps.checarBadgeStatusControlador(status);
  });

  this.Given(/^o sistema deverá mostrar na tabela o valor "([^"]*)"$/, function (status) {
    return sharedSteps.checarValoresNaTabela(status);
  });

  this.Given(/^em resumo clicar em "([^"]*)"$/, function (tooltipSelector) {
    return sharedSteps.clicarEditarEmResumo(tooltipSelector);
  });

  this.Given(/^o usuário clicar para visualizar o resumo$/, function () {
    return sharedSteps.clicarVisualizarResumo();
  });

  this.Given(/^o usuário esteja na listagem de controladores$/, function () {
    return sharedSteps.isListagemControladores();
  });

  this.Given(/^o usuário acesse a listagem de "([^"]*)"$/, function (localizacao) {
    return sharedSteps.visitarListagem(localizacao);
  });

  this.Given(/^o usuário selecionar o valor "([^"]*)" para o campo "([^"]*)"$/, function (valor, campo) {
    return sharedSteps.selecionarBySelect2Option(campo, valor);
  });

  this.Given(/^o usuário não consiga selecionar o valor "([^"]*)" para o campo "([^"]*)"$/, function (valor, campo) {
    return sharedSteps.naoConsigaSelecionar(campo, valor);
  });

  this.Given(/^o usuário remover o "([^"]*)" selecionado do campo "([^"]*)"$/, function (opcao, campo) {
    return sharedSteps.removeSelect2Option(opcao, campo);
  });

  this.Given(/^o usuário clicar em "([^"]*)" do controlador "([^"]*)"$/, function (botao, controlador) {
    return sharedSteps.clicarBotaoEspecificoTabelaControladores(botao, controlador);
  });

  this.Given(/^o usuário na transição proibida "([^"]*)" selecionar a alternativa "([^"]*)"$/, function (transicao, alternativa) {
    var campo = '#estagio-alternativo-'+transicao+'';
    var selectSelector = 'select[name="alternativos"]';
    var optionAtribute = 'label';
    var value = ''+alternativa+'';

    return sharedSteps.selectBySelectOptionAtribute(campo, selectSelector, optionAtribute, value);
  });

  this.Given(/^o sistema deverá mostrar na "([^"]*)" posição com a data "([^"]*)"$/, function (posicao, data) {
    return sharedSteps.checkPosicaoHistorico(posicao, data);
  });

  this.Given(/^o sistema não deverá mostrar o botão "([^"]*)" do controlador "([^"]*)"$/, function (botao, controlador) {
    return sharedSteps.naoPodeMostraBotaoControlador(botao, controlador);
  });

  this.Given(/^o sistema não deverá mostrar o botão "([^"]*)"$/, function (botao) {
    return sharedSteps.naoPodeMostraBotao(botao);
  });

  this.Given(/^o usuário clicar em fechar o modal "([^"]*)"$/, function (modal) {
    return sharedSteps.fecharModal(modal);
  });

  this.Given(/^o usuário digitar no campo "([^"]*)" com a informação "([^"]*)"$/, function (campo, texto) {
    return sharedSteps.preencherCampo(campo, texto);
  });

  this.Given(/^o sistema deverá mostrar em linhas com valor "([^"]*)" na tabela$/, function (valorLinha) {
    return sharedSteps.verificarValoresEmLinhasNaTabela(valorLinha);
  });

  this.Given(/^o sistema deverá mostrar na coluna "([^"]*)" com valor "([^"]*)"$/, function (coluna, valor) {
    return sharedSteps.verificarTabelaPorThETd(coluna, valor);
  });

  this.Given(/^o usuário limpar o campo "([^"]*)"$/, function (campo) {
    return sharedSteps.limparCampo(campo);
  });

  this.Given(/^o sistema deve mostrar o endereço "([^"]*)" no breadcrumb$/, function (endereco) {
    return sharedSteps.enderecoBreadcrumb(endereco);
  });

  this.Given(/^que o usuário aguarde um tempo de "([^"]*)" milisegundos$/, function (time) {
    return sharedSteps.aguardar(time);
  });
};
