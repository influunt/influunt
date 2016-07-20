'use strict';

var expect = require('chai').expect;
var PermissoesPage = require('../../support/page-objects/permissoes');

module.exports = function() {
  var permissoesPage = new PermissoesPage();

  this.Given(/^que exista ao menos uma permissão cadastrada no sistema$/, function() {
    return permissoesPage.existeAoMenosUmaPermissao();
  });

  this.Given(/^o usuário acessar a tela de listagem de permissões$/, function() {
    return permissoesPage.indexPage();
  });

  this.Given(/^deve ser exibida uma lista com as permissões já cadastradas no sistema$/, function() {
    return permissoesPage.getItensTabela().then(function(itens) {
      expect(itens).to.have.length.at.least(3);
    });
  });

  this.Given(/^clicar no botão de Nova Permissao$/, function() {
    return permissoesPage.clicarBotaoNovaPermissao();
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de Cadastro de novas Permissões$/, function() {
    return permissoesPage.formPermissoes().then(function(form) {
      return expect(form).to.exist;
    });
  });

  this.Given(/^o usuário acessar a tela de cadastro de novas permissões$/, function() {
    return permissoesPage.newPage();
  });

  this.Given(/^o registro da permissão deverá ser salvo com descrição igual a "([^"]*)"$/, function(descricao) {
    return permissoesPage.textoExisteNaTabela(descricao);
  });

  this.Given(/^o sistema deverá retornar à tela de listagem de permissões$/, function() {
    return permissoesPage.isIndex();
  });

  this.Given(/^clicar no botão de visualizar permissão$/, function() {
    permissoesPage.clicarLinkComTexto('Visualizar');
  });

  this.Given(/^o sistema deverá redirecionar para a tela de visualização de permissões$/, function() {
    return permissoesPage.isShow().then(function(resp) {
      return expect(resp).to.not.be.null;
    });
  });

  this.Given(/^clicar no botão de editar permissão$/, function() {
    permissoesPage.clicarLinkComTexto('Editar');
  });

  this.Given(/^o sistema deverá redirecionar para o formulário de edição de permissões$/, function() {
    return permissoesPage.textoFieldDescricaoPermissao().then(function(descricao) {
      return expect(descricao).to.not.be.empty;
    });
  });

  this.Given(/^o usuário acessar o formulário de edição de permissões$/, function() {
    return permissoesPage.indexPage().then(function() {
      return permissoesPage.clicarLinkComTexto('Editar');
    });
  });

  this.Given(/^clicar no botão de excluir uma permissão$/, function() {
    permissoesPage.clicarLinkComTexto('Excluir');
  });

  this.Given(/^o sistema exibe uma caixa de confirmação se o usuário deve mesmo excluir a permissão$/, function() {
    return permissoesPage.textoConfirmacaoApagarRegistro().then(function(text) {
      expect(text).to.equal('Quer mesmo apagar este registro?');
    });
  });

  this.Given(/^nenhuma permissão deve ser excluída$/, function() {
    return permissoesPage.nenhumaPermissaoDeveSerExcluida().then(function(res) {
      return expect(res).to.be.true;
    });
  });

  // // this.Given(/^o usuário responde sim$/, function() {
  // //   return permissoesPage.clicarSimConfirmacaoApagarRegistro();
  // // });

  this.Given(/^a permissão deverá ser excluida$/, function() {
    return permissoesPage.toastMessage().then(function(text) {
      expect(text).to.match(/Removido com sucesso/);
    });
  });

  // // this.Given(/^o usuário responde não$/, function() {
  // //   return permissoesPage.clicarNaoConfirmacaoApagarRegistro();
  // // });

};
