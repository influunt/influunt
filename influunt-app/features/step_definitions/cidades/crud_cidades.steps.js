'use strict';

var expect = require('chai').expect;
var CidadesPage = require('../../support/page-objects/cidades');

module.exports = function() {
  var cidadesPage = new CidadesPage();

  // this.Given(/^que exista o usuário "([^"]*)" com senha "([^"]*)"$/, function(arg1, arg2, callback) {
  //   // Write code here that turns the phrase above into concrete actions
  //   callback(null, 'pending');
  // });

  // this.Given(/^que o usuário "([^"]*)" entre no sistema com a senha "([^"]*)"$/, function(arg1, arg2, callback) {
  //   // Write code here that turns the phrase above into concrete actions
  //   callback(null, 'pending');
  // });

  this.Given(/^que exista ao menos uma cidade cadastrada no sistema$/, { timeout: 15 * 1000 }, function() {
    return cidadesPage.existeAoMenosUmaCidade();
  });

  this.Given(/^o usuário acessar a tela de listagem de cidades$/, function() {
    return cidadesPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com as cidades já cadastradas no sistema$/, function() {
    return cidadesPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(3);
    });
  });

  this.Given(/^Clica no botão de Nova Cidade$/, function(callback) {
    cidadesPage.clicarBotaoNovaCidade();
    callback();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de Cadastro de nova Cidades$/, function() {
    return cidadesPage.fieldNomeCidade().then(function(field) {
      expect(field).to.exist;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de novas cidades$/, function() {
    return cidadesPage.newPage();
  });

  this.Given(/^preenche os campos corretamente$/, function(callback) {
    cidadesPage.fillCidadeForm('Teste Cadastro Cidade');
    callback();
  });

  this.Given(/^o registro deverá ser salvo com sucesso$/, function() {
    return cidadesPage.textoExisteNaTabela('Teste Cadastro Cidade');
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de cidades$/, function() {
    return cidadesPage.getPageTitleH2().then(function(title) {
      expect(title).to.equal('Cidades');
    });
  });

  this.Given(/^Clica no botão de visualizar cidade$/, function(callback) {
    cidadesPage.clicarLinkComTexto('Visualizar');
    callback();
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de cidades$/, function() {
    return cidadesPage.cidadeIdH5().then(function(cidadeId) {
      expect(cidadeId).to.match(/ - #/);
    });
  });

  this.Given(/^Clica no botão de editar cidade$/, function(callback) {
    cidadesPage.clicarLinkComTexto('Editar');
    callback();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição cidades$/, function() {
    return cidadesPage.textoFieldNomeCidade().then(function(fieldValue) {
      expect(fieldValue).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de cidades$/, function() {
    cidadesPage.indexPage();
    return cidadesPage.clicarLinkComTexto('Editar');
  });

  this.Given(/^clica no botão de excluir uma cidade$/, function(callback) {
    cidadesPage.clicarLinkComTexto('Excluir');
    callback();
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a cidade$/, function() {
    return cidadesPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('quer mesmo apagar este registro?');
    });
  });

  this.Given(/^o usuário responde sim$/, function() {
    return cidadesPage.clicarSimConfirmacaoApagarRegistro();
  });

  this.Given(/^a cidade deverá ser excluida$/, function() {
    // return cidadesPage.cidadeDeveSerExcluida().then(function(res) {
    //   expect(res).to.be.true
    // });
    return cidadesPage.toastMessage().then(function(text) {
      expect(text).to.match(/Removido com sucesso/);
    });
  });

  this.Given(/^o usuário responde não$/, function() {
    return cidadesPage.clicarNaoConfirmacaoApagarRegistro();
  });

  this.Given(/^nenhuma cidade deve ser excluída$/, function() {
    return cidadesPage.nenhumaCidadeDeveSerExcluida().then(function(res) {
      expect(res).to.be.true;
    });
  });
};
