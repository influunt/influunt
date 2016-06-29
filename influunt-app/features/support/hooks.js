'use strict';

var driver = require('./world.js').getDriver();
var fs = require('fs');
var path = require('path');
var sanitize = require('sanitize-filename');

var myHooks = function () {

  this.registerHandler('AfterFeatures', function () {
    return driver.quit();
  });

};

module.exports = myHooks;
