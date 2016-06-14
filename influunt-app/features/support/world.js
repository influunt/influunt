'use strict';

var fs = require('fs');
var webdriver = require('selenium-webdriver');
var platform = process.env.PLATFORM || 'PHANTOMJS';
var driver = null;

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

  this.webdriver = webdriver;
  this.driver = driver;

  if(!fs.existsSync(screenshotPath)) {
    fs.mkdirSync(screenshotPath);
  }

  this.waitFor = function(cssLocator, timeout) {
    var waitTimeout = timeout || defaultTimeout;
    return driver.wait(function() {
      return driver.isElementPresent({ css: cssLocator });
    }, waitTimeout);
  };

  this.visit = function(url) {
    return driver.get(url);
  };

  this.getCurrentUrl = function() {
    return driver.getCurrentUrl();
  };

  this.setValue = function(selector, value) {
    return driver.findElement(selector).sendKeys(value);
  };

  this.clickButton = function(selector) {
    return driver.findElement(selector).sendKeys(webdriver.Key.ENTER);
  };

  this.getElements = function(selector) {
    return driver.findElements(selector);
  };
};

switch(platform) {
  case 'CHROME':
    driver = buildChromeDriver();
    break;
  default:
    driver = buildPhantomDriver();
}

module.exports.World = World;
module.exports.getDriver = getDriver;
