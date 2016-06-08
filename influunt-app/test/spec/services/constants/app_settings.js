'use strict';

describe('Service: app_settings', function () {

  // load the service's module
  beforeEach(module('influuntApp'));

  // instantiate service
  var app_settings;
  beforeEach(inject(function (_APP_SETTINGS_) {
    app_settings = _APP_SETTINGS_;
  }));

  it('deve existir um objeto', function () {
    expect(!!app_settings).toBe(true);
  });

  it('deve possuir um atributo "default_locale"', function() {
    expect(app_settings.default_locale).toBeDefined();
  });

});
