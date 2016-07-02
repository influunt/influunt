'use strict';

var World = require('./world.js');
var driver = World.getDriver();
var fs = require('fs');
var path = require('path');
var sanitize = require('sanitize-filename');

var myHooks = function () {

  var world = new World.World();

  this.registerHandler('BeforeFeatures', function () {
    return world.execScript('curl localhost:9000').then(function(){
      return world.execSqlScript('features/support/scripts/create_usuario.sql');
    }).then(function () {
      return world.visit('/login');
    }).then(function () {
      return world.setValue('input[name="usuario"]', 'root');
    }).then(function () {
      return world.setValue('input[name="senha"]', '1234');
    }).then(function () {
      return world.clickButton('#acessar');
    }).then(function () {
      return world.waitFor('a.navbar-brand');
    }).catch(function(ex) {
      console.log(ex)
    });
  });

  this.registerHandler('AfterFeatures', function () {
    return driver.quit();
  });

};

module.exports = myHooks;
