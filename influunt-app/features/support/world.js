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
  var baseUrl = 'http://localhost/#';

  this.webdriver = webdriver;
  this.driver = driver;

  if(!fs.existsSync(screenshotPath)) {
    fs.mkdirSync(screenshotPath);
  }

  this.waitFor = function(cssLocator, timeout) {
    var waitTimeout = timeout || defaultTimeout;
    return driver.wait(function() {
      return driver.isElementPresent(webdriver.By.css(cssLocator));
    }, waitTimeout);
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

  this.findLinkByText = function(text) {
    return driver.findElement(webdriver.By.linkText(text));
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
