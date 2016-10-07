'use strict';

var fs = require('fs');
var webdriver = require('selenium-webdriver');
var platform = process.env.PLATFORM || 'PHANTOMJS';
var driver = null;

var exec = require('child_process').exec;

var buildChromeDriver = function() {
  return new webdriver
    .Builder()
    .withCapabilities(webdriver.Capabilities.chrome())
    .build();
};

var buildPhantomDriver = function() {
  return new webdriver
    .Builder()
    .withCapabilities(webdriver.Capabilities.phantomjs())
    .build();
};

var getDriver = function() {
  return driver;
};

var World = function () {
  var defaultTimeout = 20 * 1000;
  var screenshotPath = 'screenshots';
  var baseUrl = 'http://localhost/#';
  var self = this;

  this.webdriver = webdriver;
  this.driver = driver;

  if(!fs.existsSync(screenshotPath)) {
    fs.mkdirSync(screenshotPath);
  }

  this.execScript = function(cmd) {
    return new Promise(function (resolve, reject) {
      exec(cmd, function(err, stdout, stderr) {
        if (err) {
          reject(err);
        } else {
          resolve({ stdout: stdout, stderr: stderr });
        }
      });
    });
  };

  this.execSqlScript = function(sqlScriptPath) {
    var cmd = 'java -cp lib/h2.jar org.h2.tools.RunScript -url "jdbc:h2:tcp://localhost:9092/mem:influunt;DATABASE_TO_UPPER=FALSE" -script '+ sqlScriptPath +' -user sa';
    return this.execScript(cmd);
  };

  this.sleep = function(timeout){
    return driver.sleep(timeout);
  };

  this.waitForToastMessageDisapear = function(timeout) {
    var _this = this;
    return _this.waitForInverse('#toast-container div.toast-message', timeout);
  };

  this.waitFor = function(cssLocator, timeout) {
    var waitTimeout = timeout || defaultTimeout;
    return driver.wait(function() {
      return driver.isElementPresent(webdriver.By.css(cssLocator));
    }, waitTimeout);
  };

  this.waitForInverse = function(cssLocator, timeout) {
    var waitTimeout = timeout || defaultTimeout;
    return driver.wait(function() {
      return driver.isElementPresent(webdriver.By.css(cssLocator)).then(function(isElementPresent) {
        return !isElementPresent;
      });
    }, waitTimeout);
  };

  this.waitForOverlayDisappear = function() {
    return self.waitForInverse('div.blockUI');
  };

  this.waitForByXpath = function(xpath, timeout) {
    var waitTimeout = timeout || defaultTimeout;
    return driver.wait(function() {
      return driver.isElementPresent(webdriver.By.xpath(xpath));
    }, waitTimeout);
  };

  this.waitForByXpathInverse = function(xpath, timeout) {
    var waitTimeout = timeout || defaultTimeout;
    return driver.wait(function() {
      return driver.isElementPresent(webdriver.By.xpath(xpath)).then(function(isElementPresent) {
        return !isElementPresent;
      });
    }, waitTimeout);
  };

  this.waitForAJAX = function(timeout) {
    timeout = timeout || defaultTimeout;
    var pollInterval = 500;
    var maxChecks = Math.ceil(timeout / pollInterval);
    var numChecks = 0;

    return new Promise(function(resolve, reject) {
      var interval = setInterval(function() {
        driver.executeScript('return jQuery.active === 0').then(function(result) {
          if (!!result) {
            clearInterval(interval);
            resolve(true);
          } else {
            numChecks++;
            if (numChecks >= maxChecks) {
              reject('Timeout waiting for AJAX calls to finish!');
            }
          }
        });
      }, pollInterval);
    });
  };

  this.waitForAnimationFinishes = function(animatedElementSelector, timeout) {
    timeout = timeout || defaultTimeout;
    var pollInterval = 500;
    var maxChecks = Math.ceil(timeout / pollInterval);
    var numChecks = 0;

    return new Promise(function(resolve, reject) {
      var interval = setInterval(function() {
        driver.executeScript('return $("'+animatedElementSelector+'").is(":animated")').then(function(result) {
          if (!result) {
            clearInterval(interval);
            resolve(true);
          } else {
            numChecks++;
            if (numChecks >= maxChecks) {
              reject('Timeout waiting for animation to finish!');
            }
          }
        });
      }, pollInterval);
    });
  };

  this.visit = function(path) {
    return driver.get(baseUrl + path);
  };

  this.getCurrentUrl = function() {
    return driver.getCurrentUrl();
  };

  this.setValue = function(cssSelector, value) {
    var _this = this;
    _this.waitFor(cssSelector);
    return driver.findElement(webdriver.By.css(cssSelector)).sendKeys(value);
  };

  this.setValueByXpath = function(xpathSelector, value) {
    var _this = this;
    _this.waitFor(xpathSelector);
    return driver.findElement(webdriver.By.xpath(xpathSelector)).sendKeys(value);
  };

  this.resetValue = function(cssSelector, value) {
    return driver.findElement(webdriver.By.css(cssSelector)).sendKeys(webdriver.Key.BACK_SPACE, webdriver.Key.BACK_SPACE, webdriver.Key.BACK_SPACE, value, webdriver.Key.ENTER);
  };

  this.setValueAsHuman = function(cssSelector, value) {
    var _this = this;
    var promises = [];
    value.split('').forEach(function(chr) {
      promises.push(function() { return _this.setValue(cssSelector, chr); });
      promises.push(function() { return _this.sleep(50); });
    });

    return promises.reduce(function(previous, current) {
      return previous.then(current);
    }, Promise.resolve());
  };

  this.clickButton = function(cssSelector) {
    return driver.findElement(webdriver.By.css(cssSelector)).sendKeys(webdriver.Key.ENTER);
  };

  this.getElement = function(selector) {
    return driver.findElement(webdriver.By.css(selector));
  };

  this.getElements = function(selector) {
    return driver.findElements(webdriver.By.css(selector));
  };

  this.getElementsByXpath = function(xpath) {
    return driver.findElements(webdriver.By.xpath(xpath));
  };

  this.getElementByXpath = function(xpath) {
    return driver.findElement(webdriver.By.xpath(xpath));
  };

  this.findLinkByText = function(text) {
    return driver.findElement(webdriver.By.linkText(text));
  };

  this.findByText = function(text) {
    return driver.findElement(webdriver.By.text(text));
  };

  this.selectByValue = function(campo, selectSelector, optionText) {
    var selector = (''+campo+' '+selectSelector+' option[value="'+optionText+'"]');
    return driver.findElement(webdriver.By.css(selector)).click();
  };

  this.selectOption = function(selectSelector, optionText) {
    return this.getElements(selectSelector + ' option').then(function(options) {
      return new Promise(function(resolve, reject) {
        var resolved = false;
        for (var i = 0; i < options.length; i++) {
          var option = options[i];
          option.getText().then(function(text) {
            if (text === optionText) {
              resolved = true;
              option.click();
            }
          });
        }
        setTimeout(function() {
          if (resolved) {
            resolve(true);
          } else {
            reject('Option not found in select: "'+optionText+'"');
          }
        }, 500);
      });
    });
  };

  this.selectSelect2Option = function(selectSelector, optionText) {
    var _this = this;
    return _this.getElement(selectSelector + ' + span[class^="select2 "]').click().then(function() {
      return _this.waitFor('.select2-results');
    }).then(function() {
      return _this.getElements('li[class^="select2-results__option"]');
    }).then(function(elements) {
      return new Promise(function(resolve, reject) {
        var resolved = false;
        for (var i = 0; i < elements.length; i++) {
          var element = elements[i];
          element.getText().then(function(text) {
            if (text === optionText) {
              resolved = true;
              element.click();
            }
          });
        }
        setTimeout(function() {
          if (resolved) {
            resolve(true);
          } else {
            reject('Option not found in select2: "'+optionText+'"');
          }
        }, 500);
      });
    });
  };

  this.execJavascript = function(script) {
    return driver.executeScript(script);
  };

  this.checkICheck = function(checkboxSelector) {
    var jQuerySelector = '$('+checkboxSelector+')';
    return this.execJavascript(jQuerySelector + '.iCheck("check");');
  };

  this.uncheckICheck = function(checkboxSelector) {
    return this.execJavascript('$('+checkboxSelector+').iCheck("uncheck");');
  };

  this.dropzoneUpload = function(filePath) {
    var _this = this;
    var script = 'fakeFileInput = $("#fakeFileInput"); if (fakeFileInput.length === 0) fakeFileInput = window.$("<input/>").attr({id: "fakeFileInput", type:"file"}).appendTo("body");';
    // Generate a fake input selector
    return _this.execJavascript(script).then(function() {
      // Attach the file to the fake input selector
      return _this.setValue('#fakeFileInput', filePath);
    }).then(function() {
      // Add the file to a fileList array
      return _this.execJavascript('var fileList = [fakeFileInput.get(0).files[0]]');
    }).then(function() {
      // Trigger the fake drop event
      script = 'var e = jQuery.Event("drop", { dataTransfer : { files : [fakeFileInput.get(0).files[0]] } }); $(".dropzone")[0].dropzone.listeners[0].events.drop(e);';
      return _this.execJavascript(script);
    });
  };

  // Relativo à posição atual do mouse
  this.moveMouseToLocation = function(x, y) {
    return driver.actions().mouseMove({ x: x, y: y }).perform();
  };

  this.moveMouseToElement = function(element) {
    return driver.actions().mouseMove(element).perform();
  };

  // Relativo à posição atual do mouse
  this.clickAtLocation = function(x, y) {
    return driver.actions().mouseMove({ x: x, y: y }).click().perform();
  };

  this.clickAtElement = function(element) {
    return driver.actions().mouseMove(element).click().perform();
  };

  this.sleep = function(ms) {
    return driver.sleep(ms);
  };

  this.scrollToDown = function() {
    return driver.executeScript('window.scrollTo(0, 1000);').then(function() {
      return driver.sleep(500);
    });
  };

  this.scrollToUp = function() {
    return driver.executeScript('window.scrollTo(0, 0);').then(function() {
      return driver.sleep(500);
    });
  };

  this.reloadPage = function() {
    return driver.navigate().refresh();
  };

  this.getTextInSweetAlert = function() {
    var _this = this;
    return _this.getElement('div[class*="sweet-alert"] p').getText();
  };

  this.dragAndDrop = function(element, location) {
    return driver.actions().dragAndDrop(element, location);
  };

  this.clearField = function(cssSelector) {
    return driver.findElement(webdriver.By.css(cssSelector)).clear();
  };

  this.clearFieldByXpath = function(xpathSelector) {
    return driver.findElement(webdriver.By.xpath(xpathSelector)).clear();
  };

  this.buscarEndereco = function(query, cssSelector) {
    var _this = this;
    return _this.setValueAsHuman(cssSelector, query).then(function() {
      return _this.waitFor('div[g-places-autocomplete-drawer] > div.pac-container');
    }).then(function() {
      return _this.getElements('div[g-places-autocomplete-drawer] > div.pac-container div:first-child');
    }).then(function(elements) {
      return new Promise(function(resolve, reject) {
        setTimeout(function() {
          if (elements.length > 0) {
            return elements[0].click().then(resolve);
          } else {
            reject('No results found for address "'+query+'"');
          }
        }, 500);
      });
    });
  };
};

switch(platform) {
  case 'CHROME':
    driver = buildChromeDriver();
    break;
  default:
    driver = buildPhantomDriver();
}

driver.manage().window().setSize(1024, 768);

module.exports.World = World;
module.exports.getDriver = getDriver;
