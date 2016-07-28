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
  var defaultTimeout = 3 * 1000;
  var screenshotPath = 'screenshots';
  var baseUrl = 'http://localhost/#';

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

  this.waitFor = function(cssLocator, timeout) {
    var waitTimeout = timeout || defaultTimeout;
    return driver.wait(function() {
      return driver.isElementPresent(webdriver.By.css(cssLocator));
    }, waitTimeout);
  };

  this.waitForByXpath = function(xpath, timeout) {
    var waitTimeout = timeout || defaultTimeout;
    return driver.wait(function() {
      return driver.isElementPresent(webdriver.By.xpath(xpath));
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
          if (result) {
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

  this.visit = function(path) {
    return driver.get(baseUrl + path);
  };

  this.getCurrentUrl = function() {
    return driver.getCurrentUrl();
  };

  this.setValue = function(cssSelector, value) {
    return driver.findElement(webdriver.By.css(cssSelector)).sendKeys(value);
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

  this.selectOption = function(selectSelector, optionText) {
    return this.getElements(selectSelector + ' option').then(function(options) {
      for (var i = 0; i < options.length; i++) {
        var option = options[i];
        option.getText().then(function(text) {
          if (text === optionText) {
            return option.click();
          }
        });
      }
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
    return this.execJavascript('$('+checkboxSelector+').iCheck("check");');
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
