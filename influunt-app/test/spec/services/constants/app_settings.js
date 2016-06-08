'use strict';

describe('Service: app_settings', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var appSettings;
  beforeEach(inject(function (_APP_SETTINGS_) {
    appSettings = _APP_SETTINGS_;
  }));

  it('deve existir um objeto', function () {
    expect(!!appSettings).toBe(true);
  });

  it('deve possuir um atributo "defaultLocale"', function() {
    expect(appSettings.defaultLocale).toBeDefined();
  });

});
