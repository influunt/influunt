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
      return world.execScript('java -cp libs/h2.jar org.h2.tools.RunScript -url "jdbc:h2:tcp://localhost:9092/mem:influunt;DATABASE_TO_UPPER=FALSE" -script features/support/scripts/usuario.sql -user sa');
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
