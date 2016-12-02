'use strict';

var expect = require('chai').expect;
var AreasPage = require('../../support/page-objects/areas');
var ObjetosComuns = require('../../support/page-objects/objetos_comuns');

module.exports = function() {
  var areasPage = new AreasPage();
  var objetosComuns = new ObjetosComuns();

  this.Given(/^que exista ao menos uma área cadastrada no sistema$/, { timeout: 15 * 1000 }, function() {
    return areasPage.existeAoMenosUmaArea();
  });

  this.Given(/^o usuário acessar a tela de listagem de áreas$/, function() {
    return areasPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com as áreas já cadastradas no sistema$/, function() {
    return areasPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(3);
    });
  });

  this.Given(/^clicar no botão de Nova Área$/, function() {
    return objetosComuns.clicarLinkNovo();
  });

  this.Given(/^o usuário acessar a tela de cadastro de novas áreas$/, function() {
    return areasPage.newPage();
  });

  this.Given(/^o registro da área deverá ser salvo com número CTA igual a "([^"]*)"$/, function(numero) {
    return areasPage.textoExisteNaTabela(numero);
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de áreas$/, function() {
    return areasPage.isIndex();
  });

  this.Given(/^clicar no botão de visualizar área$/, function() {
    objetosComuns.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de áreas$/, function() {
    return areasPage.isShow().then(function(resp) {
      return expect(resp).to.not.be.null;
    });
  });

  this.Given(/^clicar no botão de editar área$/, function() {
    objetosComuns.clicarLinkComTexto('Editar');
  });

  this.Given(/^clicar no botão de adicionar limites geográficos$/, function() {
    objetosComuns.clicarLinkComTexto('adicionar limites geográficos');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição de áreas$/, function() {
    return areasPage.textoFieldDescricaoArea().then(function(descricao) {
      return expect(descricao).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de áreas$/, function() {
    areasPage.indexPage();
    return objetosComuns.clicarLinkComTexto('Editar');
  });

  this.Given(/^clicar no botão de excluir uma área$/, function() {
    objetosComuns.clicarLinkComTexto('Excluir');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a área$/, function() {
    return areasPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^a área deverá ser excluida$/, function() {
    return areasPage.toastMessage().then(function(text) {
      expect(text).to.match(/Removido com sucesso/);
    });
  });

  this.Given(/^nenhuma área deve ser excluída$/, function() {
    return areasPage.nenhumaAreaDeveSerExcluida().then(function(res) {
      return expect(res).to.be.true;
    });
  });

  this.Given(/^o usuário marcar a cidade como "([^"]*)"$/, function (cidade) {
    return areasPage.selecionarCidade(cidade);
  });

  this.Given(/^o sistema deverá possuir longitude e latidude com os valores "([^"]*)"$/, function (limetes) {
    return areasPage.limetesNaTabela(limetes);
  });

  this.Given(/^que o usuário limpe o campo área$/, function () {
    return objetosComuns.limparCampo("area");
  });
};
