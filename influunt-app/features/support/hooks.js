'use strict';

var World = require('./world.js');
var driver = World.getDriver();
var fs = require('fs');
var path = require('path');
var sanitize = require('sanitize-filename');

var myHooks = function () {

  var world = new World.World();

  // Habilita o "modo debug"
  var debugMode = false;
  // Imprime na tela o conteúdo do console do navegador após cada cenário
  var debugConsoleLogs = true;
  // Tira um screenshot sempre que um cenário falhar
  var debugScreenshot = true;

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
      console.log("ERRO: ", ex);
			throw new Error(ex);
    });
  });

  this.registerHandler('AfterFeatures', function () {
    return driver.quit();
  });

  if (debugMode) {
    this.After(function(scenario) {
      if (debugConsoleLogs) {
        driver.manage().logs().get('browser').then(function(logs) {
          console.log('------ Console após o último cenário:');
          console.log(logs);
          console.log('-------------------------------------');
        });
      }

      if (debugScreenshot) {
        if(scenario.isFailed()) {
					driver.getPageSource().then(function(data){
            fs.writeFile(path.join('screenshots', sanitize(scenario.getName() + '.html').replace(/ /g,'_')), data, 'utf-8', function(err) {
              if(err) {
                console.log("PG: ------>>>>", err);
              }
            });
					})
          driver.takeScreenshot().then(function(data) {
            var base64Data = data.replace(/^data:image\/png;base64,/, '');
            fs.writeFile(path.join('screenshots', sanitize(scenario.getName() + '.png').replace(/ /g,'_')), base64Data, 'base64', function(err) {
              if(err) {
                console.log(err);
              }
            });
          });
        }
      }
    });
  }

};

module.exports = myHooks;
