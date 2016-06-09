'use strict';

var expect = require('chai').expect;

module.exports = function() {
  var worldObj = require('../support/world.js');
  var LOGIN_URL = 'http://localhost:9000/#/login';

  this.World = worldObj.World;

  this.Given(/^o usuário acessa a tela de login$/, function () {
    return this.driver
      .get(LOGIN_URL);
  });

  this.Given(/^informa somente o nome "([^"]*)" no campo usuário$/, function (nome) {
    this.driver
      .findElement({name: 'usuario'})
      .sendKeys(nome);

    return this.driver
      .findElement({name: 'usuario'})
      .sendKeys(this.webdriver.Key.ENTER);
  });

  this.Given(/^o usuário não deve conseguir acessar o sistema$/, function () {
    return this.driver
      .getCurrentUrl()
      .then(function(url) {
        expect(url).to.equal(LOGIN_URL);
      });
  });

  this.Given(/^o sistema deve informar "([^"]*)" para o campo de senha$/, function (arg1) {
    return this.driver
      .findElements(this.webdriver.By.css('.senha p.error-msg:not(.ng-hide)'))
      .then(function(elements) {
        expect(elements.length).to.equal(1);
      });
  });

  this.Given(/^informa somente a senha "([^"]*)" no campo de senha$/, function (senha) {
    this.driver
      .findElement({name: 'senha'})
      .sendKeys(senha);

    return this.driver
      .findElement({name: 'senha'})
      .sendKeys(this.webdriver.Key.ENTER);
  });

  this.Given(/^o sistema deve informar "([^"]*)" para o campo de usuário$/, function (arg1) {
    return this.driver
      .findElements(this.webdriver.By.css('.usuario p.error-msg:not(.ng-hide)'))
      .then(function(elements) {
        expect(elements.length).to.equal(1);
      });
  });

  this.Given(/^informa usuário ou senha incorretos$/, function (callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o sistema deve informar "([^"]*)"$/, function (arg1, callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^informa usuário e senha corretos$/, function (callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

  this.Given(/^o usuário deve ser enviado para a tela de dashboard$/, function (callback) {
    // Write code here that turns the phrase above into concrete actions
    callback(null, 'pending');
  });

};
