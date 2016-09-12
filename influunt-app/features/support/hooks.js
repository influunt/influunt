'use strict';

var World = require('./world.js');
var driver = World.getDriver();
var fs = require('fs');
var path = require('path');
var sanitize = require('sanitize-filename');

var myHooks = function () {

  var world = new World.World();

  // Imprime na tela o conteúdo do console do navegador após cada cenário
  var debugConsoleLogs = false;

  // Tira um screenshot sempre que um cenário falhar
  var debugScreenshot = false;

  // salva o HTML da página em um arquivo sempre que um cenário falhar
  var debugSource = false;

  // Seta um javascript para seguir o movimento do mouse
  var trackMouseMovement = false;


  this.registerHandler('BeforeFeatures', function () {
    return world.execScript('curl -XPOST localhost:9000/api/v1/cucumber').then(function(){
      return world.execSqlScript('features/support/scripts/create_usuario.sql');
    }).then(function () {
      return world.visit('/login');
    }).then(function () {
      world.sleep(1000);
      return world.setValue('input[name="usuario"]', 'root');
    }).then(function () {
      return world.setValue('input[name="senha"]', '1234');
    }).then(function () {
      return world.clickButton('#acessar');
    }).then(function () {
      return world.waitFor('a.navbar-brand');
    }).catch(function(ex) {
      console.log('ERRO: ', ex);
      throw new Error(ex);
    });
  });

  this.registerHandler('AfterFeatures', function () {
    return driver.quit();
  });



  if (trackMouseMovement) {
    this.registerHandler('BeforeFeatures', function () {
      var script = function() {
        var seleniumFollowerImg = document.createElement('img');
        seleniumFollowerImg.setAttribute('src', 'data:image/png;base64,' +
          'iVBORw0KGgoAAAANSUhEUgAAABQAAAAeCAQAAACGG/bgAAAAAmJLR0QA/4ePzL8AAAAJcEhZcwAA' +
          'HsYAAB7GAZEt8iwAAAAHdElNRQfgAwgMIwdxU/i7AAABZklEQVQ4y43TsU4UURSH8W+XmYwkS2I0' +
          '9CRKpKGhsvIJjG9giQmliHFZlkUIGnEF7KTiCagpsYHWhoTQaiUUxLixYZb5KAAZZhbunu7O/PKf' +
          'e+fcA+/pqwb4DuximEqXhT4iI8dMpBWEsWsuGYdpZFttiLSSgTvhZ1W/SvfO1CvYdV1kPghV68a3' +
          '0zzUWZH5pBqEui7dnqlFmLoq0gxC1XfGZdoLal2kea8ahLoqKXNAJQBT2yJzwUTVt0bS6ANqy1ga' +
          'VCEq/oVTtjji4hQVhhnlYBH4WIJV9vlkXLm+10R8oJb79Jl1j9UdazJRGpkrmNkSF9SOz2T71s7M' +
          'SIfD2lmmfjGSRz3hK8l4w1P+bah/HJLN0sys2JSMZQB+jKo6KSc8vLlLn5ikzF4268Wg2+pPOWW6' +
          'ONcpr3PrXy9VfS473M/D7H+TLmrqsXtOGctvxvMv2oVNP+Av0uHbzbxyJaywyUjx8TlnPY2YxqkD' +
          'dAAAAABJRU5ErkJggg==');
        seleniumFollowerImg.setAttribute('id', 'selenium_mouse_follower');
        seleniumFollowerImg.setAttribute('style', 'position: absolute; z-index: 99999999999; pointer-events: none;');
        document.body.appendChild(seleniumFollowerImg);
        window.cursorX = 0;
        window.cursorY = 0;
        jQuery(document).mousemove(function(e){
          window.cursorX = e.pageX;
          window.cursorY = e.pageY;
          jQuery('#selenium_mouse_follower').stop().animate({ left: window.cursorX, top: window.cursorY });
        });
      };

      return world.execJavascript(script);
    });
  }

  if (debugConsoleLogs || debugScreenshot || debugSource) {
    this.After(function(scenario) {
      if (debugConsoleLogs) {
        driver.manage().logs().get('browser').then(function(logs) {
          console.log('------ Console após o último cenário:');
          console.log(logs);
          console.log('-------------------------------------');
        });
      }

      if(scenario.isFailed()) {
        if (debugSource) {
					driver.getPageSource().then(function(data){
            fs.writeFile(path.join('screenshots', sanitize(scenario.getName() + '.html').replace(/ /g,'_')), data, 'utf-8', function(err) {
              if(err) {
                console.log('PG: ------>>>>', err);
              }
            });
					});
        }
        if (debugScreenshot) {
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
